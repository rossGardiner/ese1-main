package uk.ac.gla.dcs.tp3_2019_ese1.libcbw;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.FloatByReference;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.NativeLongByReference;
import com.sun.jna.ptr.ShortByReference;

import uk.ac.gla.dcs.tp3_2019_ese1.libcbw.LibcbwException.ErrorCode;
import uk.ac.gla.dcs.tp3_2019_ese1.libcbw.LibcbwJNA.CBWOptions;
import uk.ac.gla.dcs.tp3_2019_ese1.libcbw.LibcbwJNA.ConfigInfo;
import uk.ac.gla.dcs.tp3_2019_ese1.libcbw.LibcbwJNA.DIOPortType;
import uk.ac.gla.dcs.tp3_2019_ese1.libcbw.LibcbwJNA.HGLOBAL;
import uk.ac.gla.dcs.tp3_2019_ese1.libcbw.LibcbwJNA.IOFunctions;
import uk.ac.gla.dcs.tp3_2019_ese1.libcbw.LibcbwJNA.JavaCallback;
import uk.ac.gla.dcs.tp3_2019_ese1.libcbw.LibcbwJNA.TriggerType;

/**
 * Represents a single connected breakout board.
 * 
 * @author Duncan Lowther (2402789L)
 */
public class LibcbwBoard implements AutoCloseable {

    protected final int _boardnum;
    protected final String _boardname;
    private boolean _closed = false;

    /**
     * Initialize board already set up with InstaCal
     * 
     * @param boardnum - the numeric ID of the board as assigned by InstaCal
     * @throws LibcbwException if the board cannot be accessed.
     */
    public LibcbwBoard(int boardnum) throws LibcbwException {
        _boardnum = boardnum;

        int err = LibcbwJNA.cbFlashLED(_boardnum);

        if(err == ErrorCode.BADBOARDTYPE) {
            /*
             * FlashLED not supported, use another function to check for live device
             */
            err = LibcbwJNA.cbStopIOBackground(_boardnum, IOFunctions.AIFUNCTION);
        }

        if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);

        ByteBuffer buf = ByteBuffer.allocateDirect(LibcbwJNA.BOARDNAMELEN);

        err = LibcbwJNA.cbGetBoardName(_boardnum, buf);
        if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);
        _boardname = new String(StandardCharsets.UTF_8.decode(buf).toString());
    }

    /**
     * @return the board name as specified in "Measurement Computing Device IDs"
     */
    public String getName() {
        return _boardname;
    }

    /**
     * Gets an integer-valued configuration setting for the board.
     * 
     * @param item   - one of the constants specified in
     *               {@link LibcbwJNA.ConfigInfo}
     * @param devNum - an extra parameter whose significance depends on the item
     *               chosen.
     * @return the configuration value
     * @throws LibcbwException if an error occurs.
     */
    public int getBoardInfo(int item, int devNum) throws LibcbwException {
        IntByReference val_ref = new IntByReference();
        int err = LibcbwJNA.cbGetConfig(ConfigInfo.BOARDINFO, _boardnum, devNum, item, val_ref);
        if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);
        return val_ref.getValue();
    }

    /**
     * Gets an string-valued configuration setting for the board.
     * 
     * @param item   - one of the constants specified in
     *               {@link LibcbwJNA.ConfigInfo}
     * @param devNum - an extra parameter whose significance depends on the item
     *               chosen.
     * @param maxLen - the maximum length of string to return.
     * @return the configuration value
     * @throws LibcbwException if an error occurs.
     */
    public String getBoardInfoString(int item, int devNum, int maxLen) throws LibcbwException {
        ByteBuffer buffer = ByteBuffer.allocateDirect(maxLen);
        int err = LibcbwJNA.cbGetConfigString(ConfigInfo.BOARDINFO, _boardnum, devNum, item, buffer,
                new IntByReference(maxLen));
        if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);
        return new String(StandardCharsets.UTF_8.decode(buffer).toString());
    }

    /**
     * Sets an integer-valued configuration setting for the board.
     * 
     * @param item   - one of the constants specified in
     *               {@link LibcbwJNA.ConfigInfo}
     * @param devNum - an extra parameter whose significance depends on the item
     *               chosen.
     * @param value  - the value to set
     * @throws LibcbwException if an error occurs.
     */
    public void setBoardInfo(int item, int devNum, int value) throws LibcbwException {
        int err = LibcbwJNA.cbSetConfig(ConfigInfo.BOARDINFO, _boardnum, devNum, item, value);
        if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);
    }

    /**
     * Sets an string-valued configuration setting for the board.
     * 
     * @param item   - one of the constants specified in
     *               {@link LibcbwJNA.ConfigInfo}
     * @param devNum - an extra parameter whose significance depends on the item
     *               chosen.
     * @param value  - the value to set
     * @throws LibcbwException if an error occurs.
     */
    public void setBoardInfoString(int item, int devNum, String value) throws LibcbwException {
        int err = LibcbwJNA.cbSetConfigString(ConfigInfo.BOARDINFO, _boardnum, devNum, item, value,
                new IntByReference(value.length()));
        if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);
    }

    /**
     * @author Duncan Lowther (2402789L)
     *
     *         Represents a specific connected USB-1608FS board. The board has the
     *         following specs:<br>
     *         <br>
     *         ===Analogue Input===<br>
     *         Functions : cbAIn(), cbAInScan(), cbALoadQueue(), cbFileAInScan(),
     *         cbATrig()<br>
     *         Options : BACKGROUND, BLOCKIO, BURSTIO(Count &lt; 32768), CONTINUOUS,
     *         CONVERTDATA, EXTCLOCK, EXTTRIGGER, NOCALIBRATEDATA, SINGLEIO<br>
     *         Mode : Single-ended<br>
     *         Channels : 0 to 7<br>
     *         Count : in BURSTIO, divisible by number of channels<br>
     *         Rate : 1 Hz - 50 kHz per channel, max 100 kHz over all channels (200
     *         kHz for BURSTIO)<br>
     *         Range : BIPxVOLTS; x = 1, 2, 5, 10<br>
     *         <br>
     *         ===Triggering===<br>
     *         Function : cbSetTrigger()<br>
     *         Digital triggering : TRIGPOSEDGE, TRIGNEGEDGE<br>
     *         <br>
     *         ===Digital I/O===<br>
     *         Functions : cbDConfigBit(), cbDConfigPort(), cbDOut(), cbDIn(),
     *         cbDBitIn(), cbDBitOut()<br>
     *         Port : AUXPORT<br>
     *         Bits : 0 to 7<br>
     *         <br>
     *         ===Counter I/O===<br>
     *         Functions : cbCClear(), cbCIn32(), cbCLoad32(Count=0)<br>
     *         CounterNum : 1<br>
     *         <br>
     *         ===Event Notification===<br>
     *         Functions : cbEnableEvent(), cbDisableEvent()<br>
     *         Event Types : ON_SCAN_ERROR, ON_DATA_AVAILABLE,
     *         ON_END_OF_INPUT_SCAN<br>
     *         <br>
     *         ==Miscellaneous===<br>
     *         Functions : cbFlashLED()<br>
     * 
     */
    public static class USB_1608FS extends LibcbwBoard {
        private static final String BOARD_NAME = "USB-1608FS";
        private HGLOBAL _bkgnd_buffer = null;
        private int _bkgnd_buffer_size = 0;
        private int _bkgnd_buffer_pos = -1;
        private int _bkgnd_channel_count = 0;

        /**
         * Initialize board already set up with InstaCal
         * 
         * @param boardnum - the numeric ID of the board as assigned by InstaCal
         * @throws LibcbwException if the board cannot be accessed, or if the board is
         *                         not an USB-1608FS.
         */
        public USB_1608FS(int boardnum) throws LibcbwException {
            super(boardnum);
            if(!BOARD_NAME.equals(_boardname.trim())) {
                System.err.println(_boardname);
                throw LibcbwException.fromErrorCode(ErrorCode.BADBOARDTYPE);
            }
        }

        /**
         * Read one of the ADC channels and return the raw (16-bit unsigned) result.
         * 
         * @param channel - the ADC channel number (0 to 7, inclusive)
         * @param range   - the voltage range for the reading. Must be one of the
         *                following constants specified in {@link LibcbwJNA.ADCRange}:
         *                BIP1VOLTS, BIP2VOLTS, BIP5VOLTS, or BIP10VOLTS
         * @return the 16-bit unsigned measurement, zero-extended to fit an
         *         <code>int</code>
         * @throws LibcbwException if an error occurs.
         */
        public int analogueInRaw(int channel, int range) throws LibcbwException {
            ShortByReference val = new ShortByReference();
            int err = LibcbwJNA.cbAIn(_boardnum, channel, range, val);
            if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);
            return (val.getValue() & 0xFFFF);
        }

        /**
         * Read one of the ADC channels and return the result in volts.
         * 
         * @param channel - the ADC channel number (0 to 7, inclusive)
         * @param range   - the voltage range for the reading. Must be one of the
         *                following constants specified in {@link LibcbwJNA.ADCRange}:
         *                BIP1VOLTS, BIP2VOLTS, BIP5VOLTS, or BIP10VOLTS
         * @return the measurement in volts, as a single-precision float.
         * @throws LibcbwException if an error occurs.
         */
        public float analogueInVolts(int channel, int range) throws LibcbwException {
            ShortByReference raw = new ShortByReference();
            int err = LibcbwJNA.cbAIn(_boardnum, channel, range, raw);
            if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);
            FloatByReference volts = new FloatByReference();
            err = LibcbwJNA.cbToEngUnits(_boardnum, range, raw.getValue(), volts);
            if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);
            return volts.getValue();
        }

        /**
         * Read at regular intervals from one or more the ADC channels and return the
         * results.
         * 
         * @param lowchan           - the ADC channel number of the first channel to
         *                          poll (0 to 7, inclusive)
         * @param numChannels       - the number of consecutive channels to poll
         * @param range             - the voltage range for the reading. Must be one of
         *                          the following constants specified in
         *                          {@link LibcbwJNA.ADCRange}: BIP1VOLTS, BIP2VOLTS,
         *                          BIP5VOLTS, or BIP10VOLTS
         * @param samplesPerChannel - the number of samples to take from each channel
         * @param freq              - the sampling frequency (Hz) for each individual
         *                          channel. Must be between 1 and 50000, inclusive, and
         *                          <code>freq*numChannels</code> should not exceed
         *                          200000.
         * @param options           - a bitwise-OR combination of zero or more option
         *                          flags from amongst the following list of constance
         *                          specified in {@link LibcbwJNA.CBWOptions}: BLOCKIO,
         *                          BURSTIO, EXTCLOCK, EXTTRIGGER, NOCALIBRATEDATA,
         *                          SINGLEIO. Note that BLOCKIO, BURSTIO, and SINGLEIO
         *                          are mutually exclusive and that if BLOCKIO is
         *                          specified,
         *                          <code>numChannels*samplesPerChannel</code> must not
         *                          exceed 32768.
         * @return a two-dimensional array indexed as
         *         <code>array[channel][sample]</code> of 16-bit unsigned measurements,
         *         zero-extended to fit <code>int</code>s
         * @throws LibcbwException if an error occurs.
         */
        public int[][] analogueInScan(int lowchan, int numChannels, int range, int samplesPerChannel, int freq,
                int options) throws LibcbwException {
            NativeLong lcount = new NativeLong(samplesPerChannel * numChannels);
            HGLOBAL memhandle = LibcbwJNA.cbWinBufAlloc(lcount);
            NativeLongByReference rateref = new NativeLongByReference(new NativeLong(freq * numChannels));

            int err = LibcbwJNA.cbAInScan(_boardnum, lowchan, lowchan + numChannels - 1, lcount, rateref, range,
                    memhandle, options);
            if(err != ErrorCode.NOERRORS) {
                LibcbwJNA.cbWinBufFree(memhandle);
                throw LibcbwException.fromErrorCode(err);
            }

            short[] buffer = memhandle.getPointer().getShortArray(0, samplesPerChannel * numChannels);
            int[][] ret = new int[numChannels][samplesPerChannel];

            for(int chan = 0; chan < numChannels; chan++) {
                for(int sample = 0; sample < samplesPerChannel; sample++) {
                    ret[chan][sample] = buffer[sample * numChannels + chan];
                }
            }
            LibcbwJNA.cbWinBufFree(memhandle);
            return ret;
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
         *                          {@link LibcbwJNA.ADCRange}: BIP1VOLTS, BIP2VOLTS,
         *                          BIP5VOLTS, or BIP10VOLTS
         * @param samplesPerChannel - the number of samples to take from each channel
         * @param freq              - the sampling frequency (Hz) for each individual
         *                          channel. Must be between 1 and 50000, inclusive, and
         *                          <code>freq*numChannels</code> should not exceed
         *                          200000.
         * @param options           - a bitwise-OR combination of zero or more option
         *                          flags from amongst the following list of constance
         *                          specified in {@link LibcbwJNA.CBWOptions}: BLOCKIO,
         *                          BURSTIO, CONTINUOUS, EXTCLOCK, EXTTRIGGER,
         *                          NOCALIBRATEDATA, SINGLEIO. Note that BLOCKIO,
         *                          BURSTIO, and SINGLEIO are mutually exclusive and
         *                          that if BLOCKIO is specified,
         *                          <code>numChannels*samplesPerChannel</code> must not
         *                          exceed 32768.
         * @throws LibcbwException if an error occurs.
         */
        public void analogueInStartAsync(int lowchan, int numChannels, int range, int samplesPerChannel, int freq,
                int options) throws LibcbwException {
            if(_bkgnd_buffer != null) throw LibcbwException.fromErrorCode(ErrorCode.ALREADYACTIVE);

            NativeLong lcount = new NativeLong(samplesPerChannel * numChannels);
            _bkgnd_buffer = LibcbwJNA.cbWinBufAlloc(lcount);
            _bkgnd_buffer_pos = 0;
            _bkgnd_buffer_size = samplesPerChannel * numChannels;
            _bkgnd_channel_count = numChannels;
            NativeLongByReference rateref = new NativeLongByReference(new NativeLong(freq * numChannels));

            int err = LibcbwJNA.cbAInScan(_boardnum, lowchan, lowchan + numChannels - 1, lcount, rateref, range,
                    _bkgnd_buffer, options | CBWOptions.BACKGROUND);
            if(err != ErrorCode.NOERRORS) {
                LibcbwJNA.cbWinBufFree(_bkgnd_buffer);
                _bkgnd_buffer = null;
                throw LibcbwException.fromErrorCode(err);
            }
        }

        /**
         * End an asynchronous scan, and return all samples that have yet to be
         * processed.
         * 
         * @return a two-dimensional array indexed as
         *         <code>array[channel][sample]</code> of 16-bit unsigned measurements,
         *         zero-extended to fit <code>int</code>s, or <code>null</code> if no
         *         asynchronous scan is active.
         */
        public int[][] analogueInStopAsync() {
            if(_bkgnd_buffer == null) return null;

            LibcbwJNA.cbStopIOBackground(_boardnum, IOFunctions.AIFUNCTION);

            ShortByReference status = new ShortByReference();
            NativeLongByReference cur_count_ref = new NativeLongByReference();
            NativeLongByReference cur_idx_ref = new NativeLongByReference();
            LibcbwJNA.cbGetIOStatus(_boardnum, status, cur_count_ref, cur_idx_ref, IOFunctions.AIFUNCTION);

            if(status.getValue() == LibcbwJNA.RUNNING) {
                LibcbwJNA.cbStopIOBackground(_boardnum, IOFunctions.AIFUNCTION);
            }

            int cur_idx = cur_idx_ref.getValue().intValue();

            short[] buffer, buffer2 = null;

            if(cur_idx == _bkgnd_buffer_pos - 1) {
                LibcbwJNA.cbWinBufFree(_bkgnd_buffer);
                _bkgnd_buffer = null;
                return new int[_bkgnd_channel_count][0];
            } else if(cur_idx < _bkgnd_buffer_pos) {
                buffer = _bkgnd_buffer.getPointer().getShortArray(_bkgnd_buffer_pos,
                        _bkgnd_buffer_size - _bkgnd_buffer_pos);
                buffer2 = _bkgnd_buffer.getPointer().getShortArray(0, cur_idx + 1);
            } else {
                buffer = _bkgnd_buffer.getPointer().getShortArray(_bkgnd_buffer_pos, cur_idx + 1 - _bkgnd_buffer_pos);
                buffer2 = new short[0];
            }

            int[][] ret = new int[_bkgnd_channel_count][(buffer.length + buffer2.length) / _bkgnd_channel_count];

            for(int sample = 0; (sample * _bkgnd_channel_count) < buffer.length; sample++) {
                for(int chan = 0; chan < _bkgnd_channel_count; chan++) {
                    ret[chan][sample] = buffer[sample * _bkgnd_channel_count + chan] & 0xFFFF;
                }
            }
            for(int sample = 0; (sample * _bkgnd_channel_count) < buffer2.length; sample++) {
                for(int chan = 0; chan < _bkgnd_channel_count; chan++) {
                    ret[chan][sample
                            + (buffer.length / _bkgnd_channel_count)] = buffer2[sample * _bkgnd_channel_count + chan]
                                    & 0xFFFF;
                }
            }

            LibcbwJNA.cbWinBufFree(_bkgnd_buffer);
            _bkgnd_buffer = null;
            return ret;
        }

        /**
         * Read at regular intervals from one or more the ADC channels and write the
         * results to a file.
         * 
         * @param path              - the file to which to save the data.
         * @param lowchan           - the ADC channel number of the first channel to
         *                          poll (0 to 7, inclusive)
         * @param numChannels       - the number of consecutive channels to poll
         * @param range             - the voltage range for the reading. Must be one of
         *                          the following constants specified in
         *                          {@link LibcbwJNA.ADCRange}: BIP1VOLTS, BIP2VOLTS,
         *                          BIP5VOLTS, or BIP10VOLTS
         * @param samplesPerChannel - the number of samples to take from each channel
         * @param freq              - the sampling frequency (Hz) for each individual
         *                          channel. Must be between 1 and 50000, inclusive, and
         *                          <code>freq*numChannels</code> should not exceed
         *                          200000.
         * @param options           - a bitwise-OR combination of zero or more option
         *                          flags from amongst the following list of constance
         *                          specified in {@link LibcbwJNA.CBWOptions}: BLOCKIO,
         *                          BURSTIO, EXTCLOCK, EXTTRIGGER, NOCALIBRATEDATA,
         *                          SINGLEIO. Note that BLOCKIO, BURSTIO, and SINGLEIO
         *                          are mutually exclusive and that if BLOCKIO is
         *                          specified,
         *                          <code>numChannels*samplesPerChannel</code> must not
         *                          exceed 32768.
         * @throws LibcbwException if an error occurs.
         */
        public void analogueInToFile(String path, int lowchan, int numChannels, int range, int samplesPerChannel,
                int freq, int options) throws LibcbwException {
            NativeLong lcount = new NativeLong(samplesPerChannel * numChannels);
            NativeLongByReference rateref = new NativeLongByReference(new NativeLong(freq * numChannels));

            int err = LibcbwJNA.cbFileAInScan(_boardnum, lowchan, lowchan + numChannels - 1, lcount, rateref, range,
                    path, options);
            if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);
        }

        /**
         * Waits for an analogue input to pass an arbitrary threshold.
         * 
         * @param channel   - the analogue input to monitor
         * @param above     - <code>true</code> if the threshold is a lower limit, or
         *                  <code>false</code> if it is an upper limit
         * @param threshold - The threshold value as a 16-bit unsigned integer
         * @param range     - the voltage range for the reading. Must be one of the
         *                  following constants specified in {@link LibcbwJNA.ADCRange}:
         *                  BIP1VOLTS, BIP2VOLTS, BIP5VOLTS, or BIP10VOLTS
         * @throws LibcbwException if an error occurs.
         */
        public void waitForThreshold(int channel, boolean above, int threshold, int range) throws LibcbwException {
            int err = LibcbwJNA.cbATrig(_boardnum, channel, above ? TriggerType.TRIGABOVE : TriggerType.TRIGBELOW,
                    (short) threshold, range, new ShortByReference());
            if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);
        }

        /**
         * Waits for an analogue input to pass an arbitrary threshold voltage.
         * 
         * @param channel   - the analogue input to monitor
         * @param above     - <code>true</code> if the threshold is a lower limit, or
         *                  <code>false</code> if it is an upper limit
         * @param threshold - The threshold voltage
         * @param range     - the voltage range for the reading. Must be one of the
         *                  following constants specified in {@link LibcbwJNA.ADCRange}:
         *                  BIP1VOLTS, BIP2VOLTS, BIP5VOLTS, or BIP10VOLTS
         * @throws LibcbwException if an error occurs.
         */
        public void waitForThreshold(int channel, boolean above, float threshold, int range) throws LibcbwException {
            ShortByReference raw_level = new ShortByReference();
            int err = LibcbwJNA.cbFromEngUnits(_boardnum, range, threshold, raw_level);
            if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);
            err = LibcbwJNA.cbATrig(_boardnum, channel, above ? TriggerType.TRIGABOVE : TriggerType.TRIGBELOW,
                    raw_level.getValue(), range, new ShortByReference());
            if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);
        }

        /* TODO -- analogueInPollAsync() */

        /**
         * Sets the sense of the external trigger for use with a later call to
         * {@link #analogueInScan(int, int, int, int, int, int)} with option
         * {@link LibcbwJNA.CBWOptions#EXTTRIGGER}.
         * 
         * @param rising - <code>true</code> for a rising-edge trigger, or
         *               <code>false</code> for a falling-edge trigger.
         * @throws LibcbwException if an error occurs.
         */
        public void setTrigger(boolean rising) throws LibcbwException {
            int err = LibcbwJNA.cbSetTrigger(_boardnum, rising ? TriggerType.TRIG_POS_EDGE : TriggerType.TRIG_NEG_EDGE,
                    (short) 0, (short) 0);
            if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);
        }

        /**
         * Reads from a single DIO line.
         * 
         * @param bit - the index of the DIO line to read
         * @return the logic value of the DIO line
         * @throws LibcbwException if an error occurs.
         */
        public boolean digitalIn(int bit) throws LibcbwException {
            int err = LibcbwJNA.cbDConfigBit(_boardnum, DIOPortType.AUXPORT, bit, LibcbwJNA.DIGITALIN);
            if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);

            ShortByReference valref = new ShortByReference();
            err = LibcbwJNA.cbDBitIn(_boardnum, DIOPortType.AUXPORT, bit, valref);
            if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);

            return (0 != valref.getValue());
        }

        /**
         * Simultaneously reads from all eight DIO lines.
         * 
         * @return the logic values of the DIO lines taken together as a
         *         <code>byte</code>.
         * @throws LibcbwException if an error occurs.
         */
        public byte digitalIn() throws LibcbwException {
            int err = LibcbwJNA.cbDConfigPort(_boardnum, DIOPortType.AUXPORT, LibcbwJNA.DIGITALIN);
            if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);

            ShortByReference valref = new ShortByReference();
            err = LibcbwJNA.cbDIn(_boardnum, DIOPortType.AUXPORT, valref);
            if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);

            return (byte) valref.getValue();
        }

        /**
         * Writes to a single DIO line.
         * 
         * @param bit   - the index of the DIO line to write to
         * @param value - the logic value to write
         * @throws LibcbwException if an error occurs.
         */
        public void digitalOut(int bit, boolean value) throws LibcbwException {
            int err = LibcbwJNA.cbDConfigBit(_boardnum, DIOPortType.AUXPORT, bit, LibcbwJNA.DIGITALOUT);
            if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);

            err = LibcbwJNA.cbDBitOut(_boardnum, DIOPortType.AUXPORT, bit, (short) (value ? 1 : 0));
            if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);
        }

        /**
         * Simultaneously writes to all eight DIO lines.
         * 
         * @param value - a <code>byte</code> with each bit representing the value to
         *              write to a different DIO line.
         * @throws LibcbwException if an error occurs.
         */
        public void digitalOut(byte value) throws LibcbwException {
            int err = LibcbwJNA.cbDConfigPort(_boardnum, DIOPortType.AUXPORT, LibcbwJNA.DIGITALOUT);
            if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);

            err = LibcbwJNA.cbDOut(_boardnum, DIOPortType.AUXPORT, (short) (value & 0xFF));
            if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);
        }

        /**
         * Registers an event handler for the specified interrupt type on this board.
         * 
         * @param type     - the type of event to handle. Must be one of the following
         *                 constants (declared in {@link LibcbwJNA.EventType}):
         *                 ON_SCAN_ERROR, ON_DATA_AVAILABLE, ON_END_OF_INPUT_SCAN
         * @param callback - the event handler to call when the specified event occurs
         * @throws LibcbwException if an error occurs.
         */
        public void enableEvent(int type, JavaCallback callback) throws LibcbwException {
            int err = LibcbwJNA.cbEnableEvent(_boardnum, type, 0,
                    (bnum, etype, edata, udata) -> callback.apply(bnum, etype, edata), Pointer.NULL);
            if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);
        }

        /**
         * Deregisters any event handler for the specified interrupt type(s) on this
         * board.
         * 
         * @param type - the type of event to stop handling. Must be one of the
         *             following constants (declared in {@link LibcbwJNA.EventType}):
         *             ON_SCAN_ERROR, ON_DATA_AVAILABLE, ON_END_OF_INPUT_SCAN, or
         *             ALL_EVENT_TYPES
         * @throws LibcbwException if an error occurs.
         */
        public void disableEvent(int type) throws LibcbwException {
            int err = LibcbwJNA.cbDisableEvent(_boardnum, type);
            if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);
        }

        /**
         * Causes the LED on the board to flash.
         * 
         * @throws LibcbwException if an error occurs.
         */
        public void flashLED() throws LibcbwException {
            int err = LibcbwJNA.cbFlashLED(_boardnum);
            if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);
        }

        /**
         * Clears the board's event counter.
         * 
         * @throws LibcbwException if an error occurs.
         */
        public void clearCounter() throws LibcbwException {
            int err = LibcbwJNA.cbCClear(_boardnum, 1);
            if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);
        }

        /**
         * Reads the board's event counter.
         * 
         * @return the current count value as a 32-bit unsigned integer, zero extended
         *         to fit a <code>long</code>
         * @throws LibcbwException if an error occurs.
         */
        public long readCounter() throws LibcbwException {
            NativeLongByReference count = new NativeLongByReference();
            int err = LibcbwJNA.cbCIn32(_boardnum, 1, count);
            if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);
            return (count.getValue().longValue() & 0xFFFFFFFF);
        }

        @Override
        public void close() throws LibcbwException {
            try {
                super.close();
            } finally {
                if(_bkgnd_buffer != null) {
                    LibcbwJNA.cbWinBufFree(_bkgnd_buffer);
                    _bkgnd_buffer = null;
                }
            }
        }
    }

    @Override
    public void close() throws LibcbwException {
        if(_closed) return;
        _closed = true;
        int err = LibcbwJNA.cbReleaseDaqDevice(_boardnum);
        if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);
    }
}
