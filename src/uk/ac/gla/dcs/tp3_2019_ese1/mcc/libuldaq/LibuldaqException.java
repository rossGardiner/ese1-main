package uk.ac.gla.dcs.tp3_2019_ese1.mcc.libuldaq;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import uk.ac.gla.dcs.tp3_2019_ese1.mcc.shared.MCCException;

/**
 * An exception stemming from a native libuldaq error code.
 * 
 * @author Duncan Lowther (2402789L)
 */
public class LibuldaqException extends MCCException {
    private static final long serialVersionUID = 6288579781162944857L;

    private static final int ERR_MSG_LEN = 512;

    /** No error has occurred */
    public static final int NO_ERROR = 0;
    /** Unhandled internal exception */
    public static final int UNHANDLED_EXCEPTION = 1;

    /** Invalid device handle */
    public static final int BAD_DEV_HANDLE = 2;

    /** This function cannot be used with this device */
    public static final int BAD_DEV_TYPE = 3;

    /** Insufficient permission to access this device */
    public static final int USB_DEV_NO_PERMISSION = 4;

    /** USB interface is already claimed */
    public static final int USB_INTERFACE_CLAIMED = 5;

    /** Device not found */
    public static final int DEV_NOT_FOUND = 6;

    /** Device not connected or connection lost */
    public static final int DEV_NOT_CONNECTED = 7;

    /** Device no longer responding */
    public static final int DEAD_DEV = 8;

    /** Buffer too small for operation */
    public static final int BAD_BUFFER_SIZE = 9;

    /** Invalid buffer */
    public static final int BAD_BUFFER = 10;

    /** Invalid memory type */
    public static final int BAD_MEM_TYPE = 11;

    /** Invalid memory region */
    public static final int BAD_MEM_REGION = 12;

    /** Invalid range */
    public static final int BAD_RANGE = 13;

    /** Invalid analog input channel specified */
    public static final int BAD_AI_CHAN = 14;

    /** Invalid input mode specified */
    public static final int BAD_INPUT_MODE = 15;

    /** A background process is already in progress */
    public static final int ALREADY_ACTIVE = 16;

    /** Invalid trigger type specified */
    public static final int BAD_TRIG_TYPE = 17;

    /** FIFO overrun, data was not transferred from device fast enough */
    public static final int OVERRUN = 18;

    /** FIFO underrun, data was not transferred to device fast enough */
    public static final int UNDERRUN = 19;

    /** Operation timed out */
    public static final int TIMEDOUT = 20;

    /** Invalid option specified */
    public static final int BAD_OPTION = 21;

    /** Invalid sampling rate specified */
    public static final int BAD_RATE = 22;

    /** Sample count cannot be greater than FIFO size for BURSTIO scans */
    public static final int BAD_BURSTIO_COUNT = 23;

    /** Configuration not supported */
    public static final int CONFIG_NOT_SUPPORTED = 24;

    /** Invalid configuration value */
    public static final int BAD_CONFIG_VAL = 25;

    /** Invalid analog input channel type specified */
    public static final int BAD_AI_CHAN_TYPE = 26;

    /** ADC overrun occurred */
    public static final int ADC_OVERRUN = 27;

    /** Invalid thermocouple type specified */
    public static final int BAD_TC_TYPE = 28;

    /** Invalid unit specified */
    public static final int BAD_UNIT = 29;

    /** Invalid queue size */
    public static final int BAD_QUEUE_SIZE = 30;

    /** Invalid config item specified */
    public static final int BAD_CONFIG_ITEM = 31;

    /** Invalid info item specified */
    public static final int BAD_INFO_ITEM = 32;

    /** Invalid flag specified */
    public static final int BAD_FLAG = 33;

    /** Invalid sample count specified */
    public static final int BAD_SAMPLE_COUNT = 34;

    /** Internal error */
    public static final int INTERNAL = 35;

    /** Invalid coupling mode */
    public static final int BAD_COUPLING_MODE = 36;

    /** Invalid sensor sensitivity */
    public static final int BAD_SENSOR_SENSITIVITY = 37;

    /** Invalid IEPE mode */
    public static final int BAD_IEPE_MODE = 38;

    /** Invalid channel queue specified */
    public static final int BAD_AI_CHAN_QUEUE = 39;

    /** Invalid gain queue specified */
    public static final int BAD_AI_GAIN_QUEUE = 40;

    /** Invalid mode queue specified */
    public static final int BAD_AI_MODE_QUEUE = 41;

    /** FPGA file not found */
    public static final int FPGA_FILE_NOT_FOUND = 42;

    /** Unable to read FPGA file */
    public static final int UNABLE_TO_READ_FPGA_FILE = 43;

    /** FPGA not loaded */
    public static final int NO_FPGA = 44;

    /** Invalid argument */
    public static final int BAD_ARG = 45;

    /** Minimum slope value reached */
    public static final int MIN_SLOPE_VAL_REACHED = 46;

    /** Maximum slope value reached */
    public static final int MAX_SLOPE_VAL_REACHED = 47;

    /** Minimum offset value reached */
    public static final int MIN_OFFSET_VAL_REACHED = 48;

    /** Maximum offset value reached */
    public static final int MAX_OFFSET_VAL_REACHED = 49;

    /** Invalid port type specified */
    public static final int BAD_PORT_TYPE = 50;

    /** Digital I/O is configured incorrectly */
    public static final int WRONG_DIG_CONFIG = 51;

    /** Invalid bit number */
    public static final int BAD_BIT_NUM = 52;

    /** Invalid port value specified */
    public static final int BAD_PORT_VAL = 53;

    /** Invalid re-trigger count */
    public static final int BAD_RETRIG_COUNT = 54;

    /** Invalid analog output channel specified */
    public static final int BAD_AO_CHAN = 55;

    /** Invalid D/A output value specified */
    public static final int BAD_DA_VAL = 56;

    /** Invalid timer specified */
    public static final int BAD_TMR = 57;

    /** Invalid frequency specified */
    public static final int BAD_FREQUENCY = 58;

    /** Invalid duty cycle specified */
    public static final int BAD_DUTY_CYCLE = 59;
    /** Invalid initial delay specified */
    public static final int BAD_INITIAL_DELAY = 60;

    /** Invalid counter specified */
    public static final int BAD_CTR = 61;

    /** Invalid counter value specified */
    public static final int BAD_CTR_VAL = 62;

    /** Invalid DAQ input channel type specified */
    public static final int BAD_DAQI_CHAN_TYPE = 63;

    /** Invalid number of channels specified */
    public static final int BAD_NUM_CHANS = 64;

    /** Invalid counter register specified */
    public static final int BAD_CTR_REG = 65;

    /** Invalid counter measurement type specified */
    public static final int BAD_CTR_MEASURE_TYPE = 66;

    /** Invalid counter measurement mode specified */
    public static final int BAD_CTR_MEASURE_MODE = 67;

    /** Invalid debounce time specified */
    public static final int BAD_DEBOUNCE_TIME = 68;

    /** Invalid debounce mode specified */
    public static final int BAD_DEBOUNCE_MODE = 69;

    /** Invalid edge detection mode specified */
    public static final int BAD_EDGE_DETECTION = 70;

    /** Invalid tick size specified */
    public static final int BAD_TICK_SIZE = 71;

    /** Invalid DAQ output channel type specified */
    public static final int BAD_DAQO_CHAN_TYPE = 72;

    /** No connection established */
    public static final int NO_CONNECTION_ESTABLISHED = 73;

    /** Invalid event type specified */
    public static final int BAD_EVENT_TYPE = 74;

    /** An event handler has already been enabled for this event type */
    public static final int EVENT_ALREADY_ENABLED = 75;

    /** Invalid event parameter specified */
    public static final int BAD_EVENT_PARAMETER = 76;

    /** Invalid callback function specified */
    public static final int BAD_CALLBACK_FUCNTION = 77;

    /** Invalid memory address */
    public static final int BAD_MEM_ADDRESS = 78;

    /** Memory access denied */
    public static final int MEM_ACCESS_DENIED = 79;

    /** Device is not available at time of request */
    public static final int DEV_UNAVAILABLE = 80;

    /** Re-trigger option is not supported for the specified trigger type */
    public static final int BAD_RETRIG_TRIG_TYPE = 81;

    /** This function cannot be used with this version of the device */
    public static final int BAD_DEV_VER = 82;

    /** This digital operation is not supported on the specified port */
    public static final int BAD_DIG_OPERATION = 83;

    /** Invalid digital port index specified */
    public static final int BAD_PORT_INDEX = 84;

    /** Temperature input has open connection */
    public static final int OPEN_CONNECTION = 85;

    /** Device is not ready to send data */
    public static final int DEV_NOT_READY = 86;

    /** Pacer overrun, external clock rate too fast. */
    public static final int PACER_OVERRUN = 87;

    /** Invalid trigger channel specified */
    public static final int BAD_TRIG_CHANNEL = 88;

    /** Invalid trigger level specified */
    public static final int BAD_TRIG_LEVEL = 89;

    /** Invalid channel order */
    public static final int BAD_CHAN_ORDER = 90;

    /** Temperature input is out of range */
    public static final int TEMP_OUT_OF_RANGE = 91;

    /** Trigger threshold is out of range */
    public static final int TRIG_THRESHOLD_OUT_OF_RANGE = 92;

    /** Incompatible firmware version, firmware update required */
    public static final int INCOMPATIBLE_FIRMWARE = 93;

    private LibuldaqException(String s) {
        super(s);
    }

    /**
     * Creates an exception object to wrap an integer error code returned by the
     * native library.
     * 
     * @param errCode - the error code returned by the native library.
     * @return the new exception object.
     */
    public static LibuldaqException fromErrorCode(int errCode) {
        ByteBuffer msgBuf = ByteBuffer.allocateDirect(LibuldaqException.ERR_MSG_LEN);

        int err2 = LibuldaqJNA.ulGetErrMsg(errCode, msgBuf);
        if(err2 != LibuldaqException.NO_ERROR) return fromErrorCode(err2);

        return new LibuldaqException(StandardCharsets.UTF_8.decode(msgBuf).toString());
    }
}
