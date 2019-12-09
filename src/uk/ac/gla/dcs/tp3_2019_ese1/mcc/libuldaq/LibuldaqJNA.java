package uk.ac.gla.dcs.tp3_2019_ese1.mcc.libuldaq;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.LongBuffer;

import com.sun.jna.Callback;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.DoubleByReference;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.LongByReference;

/**
 * JNA bridge to the native library.
 * 
 * @author Duncan Lowther (2402789L)
 */
public class LibuldaqJNA {
    public static final String JNA_LIBRARY_NAME = "uldaq";
    public static final NativeLibrary JNA_NATIVE_LIB = NativeLibrary.getInstance(LibuldaqJNA.JNA_LIBRARY_NAME);

    static {
        Native.register(LibuldaqJNA.JNA_LIBRARY_NAME);
    }

    /** Analog input modes */
    public static final class AInMode {
        private AInMode() {
        }

        /** Differential */
        public static final int DIFFERENTIAL = 1;
        /** Single-ended */
        public static final int SINGLE_ENDED = 2;
        /** Pseudo-differential */
        public static final int PSEUDO_DIFFERENTIAL = 3;
    }

    /**
     * Analog input channel types
     *
     * Bitmask indicating all supported channel types.
     */
    public static final class AInType {
        private AInType() {
        }

        /** Voltage */
        public static final int VOLTAGE = 1 << 0;
        /** Thermocouple */
        public static final int TC = 1 << 1;
        /** Resistance Temperature Detector (RTD) */
        public static final int RTD = 1 << 2;
        /** Thermistor */
        public static final int THERMISTOR = 1 << 3;
        /** Semiconductor */
        public static final int SEMICONDUCTOR = 1 << 4;
        /** Disabled */
        public static final int DISABLED = 1 << 30;
    }

    /** Thermocouple types */
    public static final class TCType {
        private TCType() {
        }

        /** Type J */
        public static final int J = 1;
        /** Type K */
        public static final int K = 2;
        /** Type T */
        public static final int T = 3;
        /** Type E */
        public static final int E = 4;
        /** Type R */
        public static final int R = 5;
        /** Type S */
        public static final int S = 6;
        /** Type B */
        public static final int B = 7;
        /** Type N */
        public static final int N = 8;
    }

    /** Sensor connection types */
    public static final class ConnectionType {
        private ConnectionType() {
        }

        /** 2-wire with a single sensor per differential channel pair **/
        public static final int SCT_2_WIRE_1 = 1;
        /** 2-wire with two sensors per differential channel pair **/
        public static final int SCT_2_WIRE_2 = 2;
        /** 3-wire with a single sensor per differential channel pair **/
        public static final int SCT_3_WIRE = 3;
        /** 4-wire with a single sensor per differential channel pair **/
        public static final int SCT_4_WIRE = 4;
    }

    /** Temperature units */
    public static final class TempScale {
        private TempScale() {
        }

        /** Celsius */
        public static final int CELSIUS = 1;
        /** Fahrenheit */
        public static final int FAHRENHEIT = 2;
        /** Kelvin */
        public static final int KELVIN = 3;
        /** Volts */
        public static final int VOLTS = 4;
        /** No scale (Raw) */
        public static final int NOSCALE = 5;
    }

    /** Auto zero modes */
    public static final class AZMode {
        private AZMode() {
        }

        /** Disabled */
        public static final int NONE = 1;
        /** Perform auto zero on every thermocouple reading. */
        public static final int EVERY_SAMPLE = 2;
        /** Perform auto zero before every scan. */
        public static final int ONCE = 3;
    }

    /** ADC timing modes */
    public static final class ADCTiming {
        private ADCTiming() {
        }

        /** The timing mode is set automatically. */
        public static final int AUTO = 1;
        /** Acquires data in samples per 1000 seconds per channel. */
        public static final int HIGH_RES = 2;
        /** High speed timing mode. */
        public static final int HIGH_SPEED = 3;
    }

    /** IEPE modes */
    public static final class IEPEMode {
        private IEPEMode() {
        }

        /** IEPE excitation current is disabled. */
        public static final int IEPE_DISABLED = 1;
        /** IEPE excitation current is enabled. */
        public static final int IEPE_ENABLED = 2;
    }

    /** Open Thermocouple detection modes */
    public static final class OTDMode {
        private OTDMode() {
        }

        /** Open Thermocouple detection modes is disabled. */
        public static final int DISABLED = 1;

        /** Open Thermocouple detection modes is enabled. */
        public static final int ENABLED = 2;
    }

    /** Coupling modes */
    public static final class Coupling {
        private Coupling() {
        }

        /** DC coupling */
        public static final int DC = 1;
        /** AC coupling */
        public static final int AC = 2;
    }

    /**
     * Bitmask indicating supported queue types. Returned to the \p infoValue
     * argument by ulAIGetInfo() using #AiInfoItem ::AI_INFO_QUEUE_TYPES.
     */
    public static final class AInQueueType {
        private AInQueueType() {
        }

        /** The AI subsystem supports a channel queue. */
        public static final int CHAN_QUEUE = 1 << 0;
        /** The AI subsystem supports a gain queue. */
        public static final int GAIN_QUEUE = 1 << 1;
        /** The AI subsystem supports a mode queue. */
        public static final int MODE_QUEUE = 1 << 2;
    }

    /**
     * Device queue limitations
     *
     * Bitmask indicating all queue limitations. Returned to the \p infoValue
     * argument by ulAIGetInfo() using AiInfoItem #AI_INFO_QUEUE_LIMITS. \n See also
     * #AI_INFO_QUEUE_TYPES and #AI_INFO_MAX_QUEUE_LENGTH_BY_MODE to determine queue
     * capabilities.
     */
    public static final class AInQueueRestrict {
        private AInQueueRestrict() {
        }

        /** A particular channel number cannot appear more than once in the queue. */
        public static final int UNIQUE = 1 << 0;
        /** Channel numbers must be listed in ascending order within the queue. */
        public static final int ASCENDING = 1 << 1;
        /** Channel numbers must be listed in contiguous order within the queue. */
        public static final int CONSECUTIVE = 1 << 2;
    }

    /**
     * Used with ulDIOGetInfo() as the \p infoValue argument value when used with
     * ::DIO_INFO_PORT_IO_TYPE.
     */
    public static final class DIOPortType {
        private DIOPortType() {}
        /** Fixed input port */
        public static final int IN = 1;
        /** Fixed output port */
        public static final int OUT = 2;
        /** Bidirectional (input or output) port */
        public static final int IO = 3;
        /** Bitwise configurable */
        public static final int BITIO = 4;
        /** Bidirectional (input or output) port; configuration is not required. */
        public static final int NONCONFIG = 5;
    }
    
    /** Used with ulDConfigPort() and ulDConfigBit() as the \p direction argument value. */
    public static final class DIODirection {
        private DIODirection() {}
        /** Input */
        public static final int IN = 1;
        /** Output */
        public static final int OUT = 2;
    }

    /**
     * Original signature :
     * <code>typedef void (*DaqEventCallback)(DaqDeviceHandle, DaqEventType, unsigned long long, void*);</code>
     */
    @FunctionalInterface
    interface DaqEventCallback extends Callback {
        void apply(long handle, int type, long long1, Pointer voidPtr1);
    }

    /**
     * Original signature :
     * <code>UlError ulGetDaqDeviceInventory(DaqDeviceInterface, DaqDeviceDescriptor[], unsigned int*)</code>
     */
    static native int ulGetDaqDeviceInventory(int interfaceTypes, LibuldaqDeviceDescriptor daqDevDescriptors,
            IntByReference numDescriptors);

    /**
     * Original signature :
     * <code>UlError ulGetDaqDeviceDescriptor(DaqDeviceHandle, DaqDeviceDescriptor*)</code>
     */
    static native int ulGetDaqDeviceDescriptor(long daqDeviceHandle, LibuldaqDeviceDescriptor daqDeviceDescriptor);

    /**
     * Original signature : <code>UlError ulConnectDaqDevice(DaqDeviceHandle)</code>
     */
    static native int ulConnectDaqDevice(long daqDeviceHandle);

    /**
     * Original signature :
     * <code>UlError ulDisonnectDaqDevice(DaqDeviceHandle)</code>
     */
    static native int ulDisconnectDaqDevice(long daqDeviceHandle);

    /**
     * Original signature : <code>UlError ulReleaseDaqDevice(DaqDeviceHandle)</code>
     */
    static native int ulReleaseDaqDevice(long daqDeviceHandle);

    /**
     * Original signature :
     * <code>UlError ulIsDaqDeviceConnected(DaqDeviceHandle, int*)</code>
     */
    static native int ulIsDaqDeviceConnected(long daqDeviceHandle, IntByReference connected);

    /**
     * Original signature : <code>UlError ulFlashLed(DaqDeviceHandle, int)</code>
     */
    static native int ulFlashLed(long daqDeviceHandle, int flashCount);

    /**
     * Original signature :
     * <code>UlError ulAIn(DaqDeviceHandle, int, AiInputMode, Range, AInFlag, double*)</code>
     */
    static native int ulAIn(long daqDeviceHandle, int channel, int inputMode, int range, int flags,
            DoubleByReference data);

    /**
     * Original signature :
     * <code>UlError ulAInScan(DaqDeviceHandle, int, int, AiInputMode, Range, int, double*, ScanOption, AInScanFlag, double[])</code>
     */
    static native int ulAInScan(long daqDeviceHandle, int lowChan, int highChan, int inputMode, int range,
            int samplesPerChan, DoubleByReference rate, int options, int flags, DoubleBuffer data);

    /**
     * Original signature :
     * <code>UlError ulAInScanStatus(DaqDeviceHandle, ScanStatus*, TransferStatus*)</code>
     */
    static native int ulAInScanStatus(long daqDeviceHandle, IntByReference status, TransferStatus xferStatus);

    /**
     * Original signature : <code>UlError ulAInScanStop(DaqDeviceHandle)</code>
     */
    static native int ulAInScanStop(long daqDeviceHandle);

    /**
     * Original signature :
     * <code>UlError ulAInScanWait(DaqDeviceHandle, WaitType, long long, double)</code>
     */
    static native int ulAInScanWait(long daqDeviceHandle, int waitType, long waitParam, double timeout);

    /**
     * Original signature :
     * <code>UlError ulAInLoadQueue(DaqDeviceHandle, AiQueueElement[], unsigned int)</code>
     */
    static native int ulAInLoadQueue(long daqDeviceHandle, AInQueueElement queue,
            int numElements); /* XXX check for error passing array */

    /**
     * Original signature :
     * <code>UlError ulAInSetTrigger(DaqDeviceHandle, TriggerType, int, double, double, unsigned int)</code>
     */
    static native int ulAInSetTrigger(long daqDeviceHandle, int type, int trigChan, double level, double variance,
            int retriggerSampleCount);

    /**
     * Original signature :
     * <code>UlError ulTIn(DaqDeviceHandle, int, TempScale, TInFlag, double*)</code>
     */
    static native int ulTIn(long daqDeviceHandle, int channel, int scale, int flags, DoubleByReference data);

    /**
     * Original signature :
     * <code>UlError ulTInArray(DaqDeviceHandle, int, int, TempScale, TInArrayFlag, double[])</code>
     */
    static native int ulTInArray(long daqDeviceHandle, int lowChan, int highChan, int scale, int flags,
            DoubleBuffer data);

    /**
     * Original signature :
     * <code>UlError ulAOut(DaqDeviceHandle, int, Range, AOutFlag, double)</code>
     */
    static native int ulAOut(long daqDeviceHandle, int channel, int range, int flags, double data);

    /**
     * Original signature :
     * <code>UlError ulAOutArray(DaqDeviceHandle, int, int, Range[], AOutArrayFlag, double[])</code>
     */
    static native int ulAOutArray(long daqDeviceHandle, int lowChan, int highChan, int[] range, int flags,
            DoubleBuffer data);

    /**
     * Original signature :
     * <code>UlError ulAOutScan(DaqDeviceHandle, int, int, Range, int, double*, ScanOption, AOutScanFlag, DoubleBuffer)</code>
     */
    static native int ulAOutScan(long daqDeviceHandle, int lowChan, int highChan, int range, int samplesPerChan,
            DoubleByReference rate, int options, int flags, DoubleBuffer data);

    /**
     * Original signature :
     * <code>UlError ulAOutScanWait(DaqDeviceHandle, WaitType, long long, double)</code>
     */
    static native int ulAOutScanWait(long daqDeviceHandle, int waitType, long waitParam, double timeout);

    /**
     * Original signature :
     * <code>UlError ulAOutScanStatus(DaqDeviceHandle, ScanStatus*, TransferStatus*)</code>
     */
    static native int ulAOutScanStatus(long daqDeviceHandle, IntByReference status, TransferStatus xferStatus);

    /**
     * Original signature : <code>UlError ulAOutScanStop(DaqDeviceHandle)</code>
     */
    static native int ulAOutScanStop(long daqDeviceHandle);

    /**
     * Original signature :
     * <code>UlError ulAOutSetTrigger(DaqDeviceHandle, TriggerType, int, double, double, unsigned int)</code>
     */
    static native int ulAOutSetTrigger(long daqDeviceHandle, int type, int trigChan, double level, double variance,
            int retriggerSampleCount);

    /**
     * Original signature :
     * <code>UlError ulDConfigPort(DaqDeviceHandle, DigitalPortType, DigitalDirection)</code>
     */
    static native int ulDConfigPort(long daqDeviceHandle, int portType, int direction);

    /**
     * Original signature :
     * <code>UlError ulDConfigBit(DaqDeviceHandle, DigitalPortType, int, DigitalDirection)</code>
     */
    static native int ulDConfigBit(long daqDeviceHandle, int portType, int bitNum, int direction);

    /**
     * Original signature :
     * <code>UlError ulDIn(DaqDeviceHandle, DigitalPortType, unsigned long long*)</code>
     */
    static native int ulDIn(long daqDeviceHandle, int portType, LongByReference data);

    /**
     * Original signature :
     * <code>UlError ulDOut(DaqDeviceHandle, DigitalPortType, unsigned long long)</code>
     */
    static native int ulDOut(long daqDeviceHandle, int portType, long data);

    /**
     * Original signature :
     * <code>UlError ulDInArray(DaqDeviceHandle, DigitalPortType, DigitalPortType, unsigned long long[])</code>
     */
    static native int ulDInArray(long daqDeviceHandle, int lowPort, int highPort, LongBuffer data);

    /**
     * Original signature :
     * <code>UlError ulDOutArray(DaqDeviceHandle, DigitalPortType, DigitalPortType, unsigned long long[])</code>
     */
    static native int ulDOutArray(long daqDeviceHandle, int lowPort, int highPort, LongBuffer data);

    /**
     * Original signature :
     * <code>UlError ulDBitIn(DaqDeviceHandle, DigitalPortType, int, unsigned int*)</code>
     */
    static native int ulDBitIn(long daqDeviceHandle, int portType, int bitNum, IntByReference bitValue);

    /**
     * Original signature :
     * <code>UlError ulDBitOut(DaqDeviceHandle, DigitalPortType, int, unsigned int)</code>
     */
    static native int ulDBitOut(long daqDeviceHandle, int portType, int bitNum, boolean bitValue);

    /**
     * Original signature :
     * <code>UlError ulDInScan(DaqDeviceHandle, DigitalPortType, DigitalPortType, int, double*, ScanOption, DInScanFlag, unsigned long long[])</code>
     */
    static native int ulDInScan(long daqDeviceHandle, int lowPort, int highPort, int samplesPerPort,
            DoubleByReference rate, int options, int flags, LongBuffer data);

    /**
     * Original signature :
     * <code>UlError ulDInScanStatus(DaqDeviceHandle, ScanStatus*, TransferStatus*)</code>
     */
    static native int ulDInScanStatus(long daqDeviceHandle, IntByReference status, TransferStatus xferStatus);

    /**
     * Original signature : <code>UlError ulDInScanStop(DaqDeviceHandle)</code>
     */
    static native int ulDInScanStop(long daqDeviceHandle);

    /**
     * Original signature :
     * <code>UlError ulDInScanWait(DaqDeviceHandle, WaitType, long long, double)</code>
     */
    static native int ulDInScanWait(long daqDeviceHandle, int waitType, long waitParam, double timeout);

    /**
     * Original signature :
     * <code>UlError ulDInSetTrigger(DaqDeviceHandle, TriggerType, int, double, double, unsigned int)</code>
     */
    static native int ulDInSetTrigger(long daqDeviceHandle, int type, int trigChan, double level, double variance,
            int retriggerSampleCount);

    /**
     * Original signature :
     * <code>UlError ulDOutScan(DaqDeviceHandle, DigitalPortType, DigitalPortType, int, double*, ScanOption, DOutScanFlag, unsigned long long[])</code>
     */
    static native int ulDOutScan(long daqDeviceHandle, int lowPort, int highPort, int samplesPerPort,
            DoubleByReference rate, int options, int flags, LongBuffer data);

    /**
     * Original signature :
     * <code>UlError ulDOutScanStatus(DaqDeviceHandle, ScanStatus*, TransferStatus*)</code>
     */
    static native int ulDOutScanStatus(long daqDeviceHandle, IntByReference status, TransferStatus xferStatus);

    /**
     * Original signature : <code>UlError ulDOutScanStop(DaqDeviceHandle)</code>
     */
    static native int ulDOutScanStop(long daqDeviceHandle);

    /**
     * Original signature :
     * <code>UlError ulDOutScanWait(DaqDeviceHandle, WaitType, long long, double)</code>
     */
    static native int ulDOutScanWait(long daqDeviceHandle, int waitType, long waitParam, double timeout);

    /**
     * Original signature :
     * <code>UlError ulDOutSetTrigger(DaqDeviceHandle, TriggerType, int, double, double, unsigned int)</code>
     */
    static native int ulDOutSetTrigger(long daqDeviceHandle, int type, int trigChan, double level, double variance,
            int retriggerSampleCount);

    /**
     * Original signature :
     * <code>UlError ulCIn(DaqDeviceHandle, int, unsigned long long*)</code>
     */
    static native int ulCIn(long daqDeviceHandle, int counterNum, LongByReference data);

    /**
     * Original signature :
     * <code>UlError ulCRead(DaqDeviceHandle, int, CounterRegisterType, unsigned long long*)</code>
     */
    static native int ulCRead(long daqDeviceHandle, int counterNum, int regType, LongByReference data);

    /**
     * Original signature :
     * <code>UlError ulCLoad(DaqDeviceHandle, int, CounterRegisterType, unsigned long long)</code>
     */
    static native int ulCLoad(long daqDeviceHandle, int counterNum, int registerType, long loadValue);

    /**
     * Original signature : <code>UlError ulCClear(DaqDeviceHandle, int)</code>
     */
    static native int ulCClear(long daqDeviceHandle, int counterNum);

    /**
     * Original signature :
     * <code>UlError ulCConfigScan(DaqDeviceHandle, int, CounterMeasurementType, CounterMeasaurementMode, CounterEdgeDetection,
     *                              CounterTickSize, CounterDebounceMode, CounterDebounceTime, CConfigScanFlag)</code>
     */
    static native int ulCConfigScan(long daqDeviceHandle, int counterNum, int type, int mode, int edgeDetection,
            int tickSize, int debounceMode, int debounceTime, int flags);

    /**
     * Original signature :
     * <code>UlError ulCInScan(DaqDeviceHandle, int, int, int, double*, ScanOption, CInScanFlag, unsigned long long[])</code>
     */
    static native int ulCInScan(long daqDeviceHandle, int lowCounterNum, int highCounterNum, int samplesPerCounter,
            DoubleByReference rate, int options, int flags, LongBuffer data);

    /**
     * Original signature :
     * <code>UlError ulCInSetTrigger(DaqDeviceHandle, TriggerType, int, double, double, unsigned int)</code>
     */
    static native int ulCInSetTrigger(long daqDeviceHandle, int type, int trigChan, double level, double variance,
            int retriggerSampleCount);

    /**
     * Original signature :
     * <code>UlError ulCInScanStatus(DaqDeviceHandle, ScanStatus*, TransferStatus*)</code>
     */
    static native int ulCInScanStatus(long daqDeviceHandle, IntByReference status, TransferStatus xferStatus);

    /**
     * Original signature : <code>UlError ulCInScanStop(DaqDeviceHandle)</code>
     */
    static native int ulCInScanStop(long daqDeviceHandle);

    /**
     * Original signature :
     * <code>UlError ulCInScanWait(DaqDeviceHandle, WaitType, long long, double)</code>
     */
    static native int ulCInScanWait(long daqDeviceHandle, int waitType, long waitParam, double timeout);

    /**
     * Original signature :
     * <code>UlError ulTmrPulseOutStart(DaqDeviceHandle, int, double*, double*, unsigned long long, double*, TmrIdleState, PulseOutOption)</code>
     */
    static native int ulTmrPulseOutStart(long daqDeviceHandle, int timerNum, DoubleByReference frequency,
            DoubleByReference dutyCycle, long pulseCount, DoubleByReference initialDelay, int idleState, int options);

    /**
     * Original signature :
     * <code>UlError ulTmrPulseOutStop(DaqDeviceHandle, int)</code>
     */
    static native int ulTmrPulseOutStop(long daqDeviceHandle, int timerNum);

    /**
     * Original signature :
     * <code>UlError ulTmrPulseOutStop(DaqDeviceHandle, int, TmrStatus*)</code>
     */
    static native int ulTmrPulseOutStatus(long daqDeviceHandle, int timerNum, IntByReference status);

    /**
     * Original signature :
     * <code>UlError ulTmrSetTrigger(DaqDeviceHandle, TriggerType, int, double, double, unsigned int)</code>
     */
    static native int ulTmrSetTrigger(long daqDeviceHandle, int type, int trigChan, double level, double variance,
            int retriggerSampleCount);

    /**
     * Original signature :
     * <code>UlError ulDaqInScan(DaqDeviceHandle, DaqInChanDescriptor[], int, int, double*, ScanOption, DaqInScanFlag, double[])</code>
     *//* XXX check array passing of DaqInChanDescriptor */
    static native int ulDaqInScan(long daqDeviceHandle, DaqInChanDescriptor chanDescriptors, int numChans,
            int samplesPerChan, DoubleByReference rate, int options, int flags, DoubleBuffer data);

    /**
     * Original signature :
     * <code>UlError ulDaqInScanStatus(DaqDeviceHandle, ScanStatus*, TransferStatus*)</code>
     */
    static native int ulDaqInScanStatus(long daqDeviceHandle, IntByReference status, TransferStatus xferStatus);

    /**
     * Original signature : <code>UlError ulDaqInScanStop(DaqDeviceHandle)</code>
     */
    static native int ulDaqInScanStop(long daqDeviceHandle);

    /**
     * Original signature :
     * <code>UlError ulDaqInScanWait(DaqDeviceHandle, WaitType, long long, double)</code>
     */
    static native int ulDaqInScanWait(long daqDeviceHandle, int waitType, long waitParam, double timeout);

    /**
     * Original signature :
     * <code>UlError ulDaqInSetTrigger(DaqDeviceHandle, TriggerType, int, double, double, unsigned int)</code>
     */
    static native int ulDaqInSetTrigger(long daqDeviceHandle, int type, int trigChan, double level, double variance,
            int retriggerSampleCount);

    /**
     * Original signature :
     * <code>UlError ulDaqOutScan(DaqDeviceHandle, DaqInChanDescriptor[], int, int, double*, ScanOption, DaqOutScanFlag, double[])</code>
     *//* XXX check array passing of DaqOutChanDescriptor */
    static native int ulDaqOutScan(long daqDeviceHandle, DaqOutChanDescriptor chanDescriptors, int numChans,
            int samplesPerChan, DoubleByReference rate, int options, int flags, DoubleBuffer data);

    /**
     * Original signature :
     * <code>UlError ulDaqOutScanStatus(DaqDeviceHandle, ScanStatus*, TransferStatus*)</code>
     */
    static native int ulDaqOutScanStatus(long daqDeviceHandle, IntByReference status, TransferStatus xferStatus);

    /**
     * Original signature : <code>UlError ulDaqOutScanStop(DaqDeviceHandle)</code>
     */
    static native int ulDaqOutScanStop(long daqDeviceHandle);

    /**
     * Original signature :
     * <code>UlError ulDaqOutScanWait(DaqDeviceHandle, WaitType, long long, double)</code>
     */
    static native int ulDaqOutScanWait(long daqDeviceHandle, int waitType, long waitParam, double timeout);

    /**
     * Original signature :
     * <code>UlError ulDaqOutSetTrigger(DaqDeviceHandle, TriggerType, int, double, double, unsigned int)</code>
     */
    static native int ulDaqOutSetTrigger(long daqDeviceHandle, int type, int trigChan, double level, double variance,
            int retriggerSampleCount);

    /**
     * Original signature :
     * <code>UlError ulEnableEvent(DaqDeviceHandle, DaqEventType, unsigned long long, DaqEventCallback, void*)</code>
     */
    static native int ulEnableEvent(long daqDeviceHandle, int eventTypes, long eventParameter,
            DaqEventCallback eventCallbackFunction, Pointer userData);

    /**
     * Original signature :
     * <code>UlError ulDisableEvent(DaqDeviceHandle, DaqEventType)</code>
     */
    static native int ulDisableEvent(long daqDeviceHandle, int eventTypes);

    /**
     * Original signature :
     * <code>UlError ulMemRead(DaqDeviceHandle, MemRegion, unsigned int, unsigned char*, unsigned int)</code>
     */
    static native int ulMemRead(long daqDeviceHandle, int memRegion, int address, ByteBuffer buffer, int count);

    /**
     * Original signature :
     * <code>UlError ulMemWrite(DaqDeviceHandle, MemRegion, unsigned int, unsigned char*, unsigned int)</code>
     */
    static native int ulMemWrite(long daqDeviceHandle, int memRegion, int address, ByteBuffer buffer, int count);

    /**
     * Original signature :
     * <code>UlError ulGetErrMsg(UlError, char[ERR_MSG_LEN])</code>
     */
    static native int ulGetErrMsg(int errCode, ByteBuffer msgBuf);

    /**
     * Original signature :
     * <code>UlError ulGetInfoStr(UlInfoItemStr, unsigned int, char*, unsigned int*)</code>
     */
    static native int ulGetInfoStr(int infoItem, int index, ByteBuffer infoStr, IntByReference maxConfigLen);

    /**
     * Original signature :
     * <code>UlError ulSetConfig(UlConfigItem, unsigned int, long long)</code>
     */
    static native int ulSetConfig(int configItem, int index, long configValue);

    /**
     * Original signature :
     * <code>UlError ulGetConfig(UlConfigItem, unsigned int, long long*)</code>
     */
    static native int ulGetConfig(int configItem, int index, LongByReference configValue);

    /**
     * Original signature :
     * <code>UlError ulDevGetInfo(DaqDeviceHandle, DevInfoItem, unsigned int, long long*)</code>
     */
    static native int ulDevGetInfo(long daqDeviceHandle, int infoItem, int index, LongByReference infoValue);

    /**
     * Original signature :
     * <code>UlError ulDevGetConfig(DaqDeviceHandle, DevConfigItem, unsigned int, long long*)</code>
     */
    static native int ulDevGetConfig(long daqDeviceHandle, int configItem, int index, LongByReference configValue);

    /**
     * Original signature :
     * <code>UlError ulDevGetConfigStr(DaqDeviceHandle, DevConfigItem, unsigned int, char*, unsigned int*)</code>
     */
    static native int ulDevGetConfigStr(long daqDeviceHandle, int configItem, int index, ByteBuffer configStr,
            IntByReference maxConfigLen);

    /**
     * Original signature :
     * <code>UlError ulAIGetInfo(DaqDeviceHandle, AiInfoItem, unsigned int, long long*)</code>
     */
    static native int ulAIGetInfo(long daqDeviceHandle, int infoItem, int index, LongByReference infoValue);

    /**
     * Original signature :
     * <code>UlError ulAIGetInfoDbl(DaqDeviceHandle, AiInfoItemDbl, unsigned int, double*)</code>
     */
    static native int ulAIGetInfoDbl(long daqDeviceHandle, int infoItem, int index, DoubleByReference infoValue);

    /**
     * Original signature :
     * <code>UlError ulAISetConfig(DaqDeviceHandle, AiConfigItem, unsigned int, long long)</code>
     */
    static native int ulAISetConfig(long daqDeviceHandle, int configItem, int index, long configValue);

    /**
     * Original signature :
     * <code>UlError ulAIGetConfig(DaqDeviceHandle, AiConfigItem, unsigned int, long long*)</code>
     */
    static native int ulAIGetConfig(long daqDeviceHandle, int configItem, int index, LongByReference configValue);

    /**
     * Original signature :
     * <code>UlError ulAISetConfigDbl(DaqDeviceHandle, AiConfigItemDbl, unsigned int, double)</code>
     */
    static native int ulAISetConfigDbl(long daqDeviceHandle, int configItem, int index, double configValue);

    /**
     * Original signature :
     * <code>UlError ulAIGetConfigDbl(DaqDeviceHandle, AiConfigItemDbl, unsigned int, double*)</code>
     */
    static native int ulAIGetConfigDbl(long daqDeviceHandle, int configItem, int index, DoubleByReference configValue);

    /**
     * Original signature :
     * <code>UlError ulAIGetConfigStr(DaqDeviceHandle, AiConfigItemStr, unsigned int, char*, unsigned int*)</code>
     */
    static native int ulAIGetConfigStr(long daqDeviceHandle, int configItem, int index, ByteBuffer configStr,
            IntByReference maxConfigLen);

    /**
     * Original signature :
     * <code>UlError ulAOGetInfo(DaqDeviceHandle, AoInfoItem, unsigned int, long long*)</code>
     */
    static native int ulAOGetInfo(long daqDeviceHandle, int infoItem, int index, LongByReference infoValue);

    /**
     * Original signature :
     * <code>UlError ulAOGetInfoDbl(DaqDeviceHandle, AoInfoItemDbl, unsigned int, double*)</code>
     */
    static native int ulAOGetInfoDbl(long daqDeviceHandle, int infoItem, int index, DoubleByReference infoValue);

    /**
     * Original signature :
     * <code>UlError ulAOSetConfig(DaqDeviceHandle, AoConfigItem, unsigned int, long long)</code>
     */
    static native int ulAOSetConfig(long daqDeviceHandle, int configItem, int index, long configValue);

    /**
     * Original signature :
     * <code>UlError ulAOGetConfig(DaqDeviceHandle, AoConfigItem, unsigned int, long long*)</code>
     */
    static native int ulAOGetConfig(long daqDeviceHandle, int configItem, int index, LongByReference configValue);

    /**
     * Original signature :
     * <code>UlError ulDIOGetInfo(DaqDeviceHandle, DioInfoItem, unsigned int, long long*)</code>
     */
    static native int ulDIOGetInfo(long daqDeviceHandle, int infoItem, int index, LongByReference infoValue);

    /**
     * Original signature :
     * <code>UlError ulDIOGetInfoDbl(DaqDeviceHandle, DioInfoItemDbl, unsigned int, double*)</code>
     */
    static native int ulDIOGetInfoDbl(long daqDeviceHandle, int infoItem, int index, DoubleByReference infoValue);

    /**
     * Original signature :
     * <code>UlError ulDIOSetConfig(DaqDeviceHandle, DioConfigItem, unsigned int, long long)</code>
     */
    static native int ulDIOSetConfig(long daqDeviceHandle, int configItem, int index, long configValue);

    /**
     * Original signature :
     * <code>UlError ulDIOGetConfig(DaqDeviceHandle, DioConfigItem, unsigned int, long long*)</code>
     */
    static native int ulDIOGetConfig(long daqDeviceHandle, int configItem, int index, LongByReference configValue);

    /**
     * Original signature :
     * <code>UlError ulCtrGetInfo(DaqDeviceHandle, CtrInfoItem, unsigned int, long long*)</code>
     */
    static native int ulCtrGetInfo(long daqDeviceHandle, int infoItem, int index, LongByReference infoValue);

    /**
     * Original signature :
     * <code>UlError ulCtrGetInfoDbl(DaqDeviceHandle, CtrInfoItemDbl, unsigned int, double*)</code>
     */
    static native int ulCtrGetInfoDbl(long daqDeviceHandle, int infoItem, int index, DoubleByReference infoValue);

    /**
     * Original signature :
     * <code>UlError ulCtrSetConfig(DaqDeviceHandle, CtrConfigItem, unsigned int, long long)</code>
     */
    static native int ulCtrSetConfig(long daqDeviceHandle, int configItem, int index, long configValue);

    /**
     * Original signature :
     * <code>UlError ulCtrGetConfig(DaqDeviceHandle, CtrConfigItem, unsigned int, long long*)</code>
     */
    static native int ulCtrGetConfig(long daqDeviceHandle, int configItem, int index, LongByReference configValue);

    /**
     * Original signature :
     * <code>UlError ulTmrGetInfo(DaqDeviceHandle, TmrInfoItem, unsigned int, long long*)</code>
     */
    static native int ulTmrGetInfo(long daqDeviceHandle, int infoItem, int index, LongByReference infoValue);

    /**
     * Original signature :
     * <code>UlError ulTmrGetInfoDbl(DaqDeviceHandle, TmrInfoItemDbl, unsigned int, double*)</code>
     */
    static native int ulTmrGetInfoDbl(long daqDeviceHandle, int infoItem, int index, DoubleByReference infoValue);

    /**
     * Original signature :
     * <code>UlError ulDaqIGetInfo(DaqDeviceHandle, DaqIInfoItem, unsigned int, long long*)</code>
     */
    static native int ulDaqIGetInfo(long daqDeviceHandle, int infoItem, int index, LongByReference infoValue);

    /**
     * Original signature :
     * <code>UlError ulDaqIGetInfoDbl(DaqDeviceHandle, DaqIInfoItemDbl, unsigned int, double*)</code>
     */
    static native int ulDaqIGetInfoDbl(long daqDeviceHandle, int infoItem, int index, DoubleByReference infoValue);

    /**
     * Original signature :
     * <code>UlError ulDaqOGetInfo(DaqDeviceHandle, DaqOInfoItem, unsigned int, long long*)</code>
     */
    static native int ulDaqOGetInfo(long daqDeviceHandle, int infoItem, int index, LongByReference infoValue);

    /**
     * Original signature :
     * <code>UlError ulDaqOGetInfoDbl(DaqDeviceHandle, DaqOInfoItemDbl, unsigned int, double*)</code>
     */
    static native int ulDaqOGetInfoDbl(long daqDeviceHandle, int infoItem, int index, DoubleByReference infoValue);

    /**
     * Original signature :
     * <code>UlError ulMemGetInfo(DaqDeviceHandle, MemRegion, MemDescriptor*)</code>
     */
    static native int ulMemGetInfo(long daqDeviceHandle, int memRegion, MemDescriptor memDescriptor);

    /**
     * Original signature :
     * <code>DaqDeviceHandle ulCreateDaqDevicePtr(DaqDeviceDescriptor*)</code>
     */
    static native long ulCreateDaqDevicePtr(LibuldaqDeviceDescriptor daqDevDescriptor);

}
