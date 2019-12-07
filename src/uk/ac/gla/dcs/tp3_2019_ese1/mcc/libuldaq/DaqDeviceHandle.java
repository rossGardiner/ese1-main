package uk.ac.gla.dcs.tp3_2019_ese1.mcc.libuldaq;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.util.EnumSet;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.DoubleByReference;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.LongByReference;

import uk.ac.gla.dcs.tp3_2019_ese1.mcc.libuldaq.LibuldaqJNA.AInMode;
import uk.ac.gla.dcs.tp3_2019_ese1.mcc.libuldaq.LibuldaqJNA.DIODirection;
import uk.ac.gla.dcs.tp3_2019_ese1.mcc.shared.DIOPortType;
import uk.ac.gla.dcs.tp3_2019_ese1.mcc.shared.EnumADCRange;
import uk.ac.gla.dcs.tp3_2019_ese1.mcc.shared.EnumEventType;
import uk.ac.gla.dcs.tp3_2019_ese1.mcc.shared.EnumScanFlags;
import uk.ac.gla.dcs.tp3_2019_ese1.mcc.shared.IUSB_1608FS;

/**
 * Represents a single connected DAQ device.
 * 
 * @author Duncan Lowther (2402789L)
 */
public class DaqDeviceHandle implements AutoCloseable {

    protected final long _handle;

    DaqDeviceHandle(long handle) throws LibuldaqException {
        _handle = handle;

        int err = LibuldaqJNA.ulConnectDaqDevice(_handle);
        if(err != LibuldaqException.NO_ERROR) throw LibuldaqException.fromErrorCode(err);
    }

    @Override
    public void close() throws LibuldaqException {
        int err = LibuldaqJNA.ulDisconnectDaqDevice(_handle);
        int err2 = LibuldaqJNA.ulReleaseDaqDevice(_handle);
        if(err != LibuldaqException.NO_ERROR) throw LibuldaqException.fromErrorCode(err);
        if(err2 != LibuldaqException.NO_ERROR) throw LibuldaqException.fromErrorCode(err2);
    }

    /**
     * @author Duncan Lowther (2402789L)
     *
     *         Represents a specific connected USB-1608FS board. The board has the
     *         following specs:<br>
     *         <br>
     *         ===Analogue Input===<br>
     *         Functions : ulAIn(), ulAInScan(), ulAInLoadQueue(),
     *         ulAInScanStatus(), ulAInScanStop(), ulAInScanWait(),
     *         ulAInSetTrigger()<br>
     *         Scan Options : SO_DEFAULTIO, SO_BLOCKIO, SO_BURSTIO(Count &lt;
     *         32768), SO_CONTINUOUS, SO_EXTCLOCK, SO_EXTTRIGGER, SO_SINGLEIO<br>
     *         Mode : Single-ended<br>
     *         Channels : 0 to 7<br>
     *         Count : in BURSTIO, divisible by number of channels<br>
     *         Rate : 1 Hz - 50 kHz per channel, max 100 kHz over all channels (200
     *         kHz for BURSTIO)<br>
     *         Range : BIPxVOLTS; x = 1, 2, 5, 10<br>
     *         <br>
     *         ===Triggering===<br>
     *         Function : ulAInSetTrigger()<br>
     *         Digital triggering : TRIG_POS_EDGE, TRIG_NEG_EDGE<br>
     *         <br>
     *         ===Digital I/O===<br>
     *         Functions : ulDConfigBit(), ulDConfigPort(), ulDOut(), ulDIn(),
     *         ulDBitIn(), ulDBitOut(), ulDIOGetConfig<br>
     *         Port : AUXPORT<br>
     *         Bits : 0 to 7<br>
     *         Config Item : DIO_CFG_PORT_DIRECTION_MASK<br>
     *         <br>
     *         ===Counter I/O===<br>
     *         Functions : ulCClear(), ulCIn(), ulCRead()<br>
     *         CounterNum : 1<br>
     *         <br>
     *         ===Event Notification===<br>
     *         Functions : ulEnableEvent(), ulDisableEvent()<br>
     *         Event Types : DE_ON_SCAN_ERROR, DE_ON_DATA_AVAILABLE,
     *         DE_ON_END_OF_INPUT_SCAN<br>
     *         <br>
     *         ==Miscellaneous===<br>
     *         Functions : ulFlashLED()<br>
     * 
     */
    public static class USB_1608FS extends DaqDeviceHandle implements IUSB_1608FS {
        private DoubleBuffer _bkgnd_buffer = null;
        private int _bkgnd_buffer_size = 0;
        private int _bkgnd_buffer_pos = -1;
        private int _bkgnd_channel_count = 0;

        public USB_1608FS(long handle) throws LibuldaqException {
            super(handle);
        }

        @Override
        public double analogueInVolts(int channel, EnumADCRange range) throws LibuldaqException {
            DoubleByReference ret = new DoubleByReference();
            int err = LibuldaqJNA.ulAIn(_handle, channel, LibuldaqJNA.AInMode.SINGLE_ENDED, range.getValLibuldaq(), 0, ret);
            if(err != LibuldaqException.NO_ERROR) throw LibuldaqException.fromErrorCode(err);
            return ret.getValue();
        }

        @Override
        public void analogueInStartAsync(int lowchan, int numChannels, EnumADCRange range, int samplesPerChannel, int freq, EnumSet<EnumScanFlags> flags)
                throws LibuldaqException {
            analogueInStartAsync(lowchan, numChannels, range, samplesPerChannel, freq, EnumScanFlags.asLibuldaqFlags(flags));
        }

        /**
         * Begin an asynchronous scan, reading at regular intervals from one or more the
         * ADC channels.
         * 
         * @param lowchan           - the ADC channel number of the first channel to
         *                          poll (0 to 7, inclusive)
         * @param numChannels       - the number of consecutive channels to poll
         * @param range             - the voltage range for the reading. Must be one of
         *                          the following constants specified in
         *                          {@link LibuldaqJNA.ADCRange}: BIP1VOLTS, BIP2VOLTS,
         *                          BIP5VOLTS, or BIP10VOLTS
         * @param samplesPerChannel - the number of samples to take from each channel
         * @param freq              - the sampling frequency (Hz) for each individual
         *                          channel. Must be between 1 and 50000, inclusive, and
         *                          <code>freq*numChannels</code> should not exceed
         *                          200000.
         * @param options           - a bitwise-OR combination of zero or more option
         *                          flags from amongst the following list of constance
         *                          specified in {@link LibuldaqJNA.ScanOptions}:
         *                          SO_BLOCKIO, SO_BURSTIO, SO_CONTINUOUS, SO_EXTCLOCK,
         *                          SO_EXTTRIGGER, SO_INGLEIO. Note that SO_BLOCKIO,
         *                          SO_BURSTIO, and SO_SINGLEIO are mutually exclusive
         *                          and that if SO_BLOCKIO is specified,
         *                          <code>numChannels*samplesPerChannel</code> must not
         *                          exceed 32768.
         * @throws LibuldaqException if an error occurs.
         */
        public void analogueInStartAsync(int lowchan, int numChannels, EnumADCRange range, int samplesPerChannel, int freq,
                int options) throws LibuldaqException {
            _bkgnd_buffer = ByteBuffer.allocateDirect(Double.BYTES * numChannels * samplesPerChannel)
                    .order(ByteOrder.nativeOrder()).asDoubleBuffer();
            _bkgnd_buffer_pos = 0;
            _bkgnd_buffer_size = samplesPerChannel * numChannels;
            _bkgnd_channel_count = numChannels;

            DoubleByReference rateRef = new DoubleByReference(freq);

            int err = LibuldaqJNA.ulAInScan(_handle, lowchan, lowchan + numChannels - 1, AInMode.SINGLE_ENDED, range.getValLibuldaq(),
                    samplesPerChannel, rateRef, options, 0, _bkgnd_buffer);
            if(err != LibuldaqException.NO_ERROR) {
                _bkgnd_buffer = null;
                throw LibuldaqException.fromErrorCode(err);
            }
        }

        @Override
        public double[][] analogueInStopAsync() {
            if(_bkgnd_buffer == null) return null;
            
            LibuldaqJNA.ulAInScanStop(_handle);

            IntByReference status = new IntByReference();
            TransferStatus xferStatus = new TransferStatus();
            LibuldaqJNA.ulAInScanStatus(_handle, status, xferStatus);

            if(0 != status.getValue()) {
                LibuldaqJNA.ulAInScanStop(_handle);
            }
            
            double[] bg_buf = _bkgnd_buffer.array();
            _bkgnd_buffer = null;

            if(xferStatus.currentIndex == _bkgnd_buffer_pos - 1) return new double[_bkgnd_channel_count][0];

            if(_bkgnd_channel_count == 1) {
                if((_bkgnd_buffer_pos == 0) && (xferStatus.currentIndex == _bkgnd_buffer_size)) {
                    return new double[][] { bg_buf };
                } else if(_bkgnd_buffer_pos <= xferStatus.currentIndex) {
                    int len = (int) xferStatus.currentIndex - _bkgnd_buffer_pos + 1;
                    double[][] ret = new double[1][len];
                    System.arraycopy(bg_buf, _bkgnd_buffer_pos, ret[0], 0, len);
                    return ret;
                } else {
                    int len1 = _bkgnd_buffer_size - _bkgnd_buffer_pos, len = len1 + (int) xferStatus.currentIndex + 1;
                    double[][] ret = new double[1][len];
                    double[] src = bg_buf;
                    System.arraycopy(src, _bkgnd_buffer_pos, ret[0], 0, len1);
                    System.arraycopy(src, 0, ret[0], len1, len - len1);
                    return ret;
                }
            } else {
                double[] buffer, buffer2;

                if(xferStatus.currentIndex < _bkgnd_buffer_pos) {
                    buffer = new double[_bkgnd_buffer_size - _bkgnd_buffer_pos];
                    System.arraycopy(bg_buf, _bkgnd_buffer_pos, buffer, 0, buffer.length);
                    buffer2 = new double[(int)xferStatus.currentIndex+ 1];
                    System.arraycopy(bg_buf, 0, buffer, 0, buffer2.length);
                } else {
                    buffer = new double[(int)xferStatus.currentIndex - _bkgnd_buffer_pos + 1];
                    System.arraycopy(bg_buf, _bkgnd_buffer_pos, buffer, 0, buffer.length);
                    buffer2 = new double[0];
                }
                
                double[][] ret = new double[_bkgnd_channel_count][(buffer.length + buffer2.length) / _bkgnd_channel_count];

                for(int sample = 0; (sample * _bkgnd_channel_count) < buffer.length; sample++) {
                    for(int chan = 0; chan < _bkgnd_channel_count; chan++) {
                        ret[chan][sample] = buffer[sample * _bkgnd_channel_count + chan];
                    }
                }
                for(int sample = 0; (sample * _bkgnd_channel_count) < buffer2.length; sample++) {
                    for(int chan = 0; chan < _bkgnd_channel_count; chan++) {
                        ret[chan][sample + (buffer.length / _bkgnd_channel_count)] 
                                = buffer2[sample * _bkgnd_channel_count + chan];
                    }
                }
                
                return ret;
            }
        }

        @Override
        public boolean digitalIn(int pin) throws LibuldaqException {
            int err = LibuldaqJNA.ulDConfigBit(_handle, DIOPortType.AUXPORT, pin, DIODirection.IN);
            if(err != LibuldaqException.NO_ERROR) throw LibuldaqException.fromErrorCode(err);

            IntByReference valref = new IntByReference();
            err = LibuldaqJNA.ulDBitIn(_handle, DIOPortType.AUXPORT, pin, valref);
            if(err != LibuldaqException.NO_ERROR) throw LibuldaqException.fromErrorCode(err);

            return (0 != valref.getValue());
        }

        @Override
        public byte digitalIn() throws LibuldaqException {
            int err = LibuldaqJNA.ulDConfigPort(_handle, DIOPortType.AUXPORT, DIODirection.IN);
            if(err != LibuldaqException.NO_ERROR) throw LibuldaqException.fromErrorCode(err);

            LongByReference valref = new LongByReference();
            err = LibuldaqJNA.ulDIn(_handle, DIOPortType.AUXPORT, valref);
            if(err != LibuldaqException.NO_ERROR) throw LibuldaqException.fromErrorCode(err);

            return (byte) valref.getValue();
        }

        @Override
        public void digitalOut(int pin, boolean value) throws LibuldaqException {
            int err = LibuldaqJNA.ulDConfigBit(_handle, DIOPortType.AUXPORT, pin, DIODirection.OUT);
            if(err != LibuldaqException.NO_ERROR) throw LibuldaqException.fromErrorCode(err);

            err = LibuldaqJNA.ulDBitOut(_handle, DIOPortType.AUXPORT, pin, value);
            if(err != LibuldaqException.NO_ERROR) throw LibuldaqException.fromErrorCode(err);
        }

        @Override
        public void digitalOut(byte value) throws LibuldaqException {
            int err = LibuldaqJNA.ulDConfigPort(_handle, DIOPortType.AUXPORT, DIODirection.OUT);
            if(err != LibuldaqException.NO_ERROR) throw LibuldaqException.fromErrorCode(err);

            err = LibuldaqJNA.ulDOut(_handle, DIOPortType.AUXPORT, value);
            if(err != LibuldaqException.NO_ERROR) throw LibuldaqException.fromErrorCode(err);
        }

        @Override
        public void enableEvent(EnumEventType type, JavaCallback callback) throws LibuldaqException {
            enableEvent(EnumSet.of(type), callback);
        }
        @Override
        public void enableEvent(EnumSet<EnumEventType> type, JavaCallback callback) throws LibuldaqException {
            int err = LibuldaqJNA.ulEnableEvent(_handle, EnumEventType.asLibuldaqFlags(type), 0,
                    (h, t, d, p) -> callback.apply(this, EnumEventType.forLibuldaqFlag(t), d),
                    Pointer.NULL);
            if(err != LibuldaqException.NO_ERROR) throw LibuldaqException.fromErrorCode(err);
        }

        @Override
        public void disableEvent(EnumEventType type) throws LibuldaqException {
            disableEvent(EnumSet.of(type));
        }
        @Override
        public void disableEvent(EnumSet<EnumEventType> type) throws LibuldaqException {
            int err = LibuldaqJNA.ulDisableEvent(_handle, EnumEventType.asLibuldaqFlags(type));
            if(err != LibuldaqException.NO_ERROR) throw LibuldaqException.fromErrorCode(err);
        }

        @Override
        public void flashLED() throws LibuldaqException {
            int err = LibuldaqJNA.ulFlashLed(_handle, 1);
            if(err != LibuldaqException.NO_ERROR) throw LibuldaqException.fromErrorCode(err);
        }

        @Override
        public void clearCounter() throws LibuldaqException {
            int err = LibuldaqJNA.ulCClear(_handle, 1);
            if(err != LibuldaqException.NO_ERROR) throw LibuldaqException.fromErrorCode(err);
        }

        @Override
        public long readCounter() throws LibuldaqException {
            LongByReference countref = new LongByReference();
            int err = LibuldaqJNA.ulCIn(_handle, 1, countref);
            if(err != LibuldaqException.NO_ERROR) throw LibuldaqException.fromErrorCode(err);
            return countref.getValue();
        }
    }
}
