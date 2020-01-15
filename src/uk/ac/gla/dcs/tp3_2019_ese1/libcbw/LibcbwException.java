package uk.ac.gla.dcs.tp3_2019_ese1.libcbw;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * An exception stemming from a libcbw error code.
 * 
 * @author Duncan Lowther (2402789L)
 */
public class LibcbwException extends Exception {
    private static final long serialVersionUID = -4837377909253322962L;

    /** System error code */
    public static final class ErrorCode {
        private ErrorCode() {
        }
    
        /** No error occurred */
        public static final int NOERRORS = 0;
        /** Invalid board number specified */
        public static final int BADBOARD = 1;
        /** Digital I/O device is not responding */
        public static final int DEADDIGITALDEV = 2;
        /** Counter I/O device is not responding */
        public static final int DEADCOUNTERDEV = 3;
        /** D/A is not responding */
        public static final int DEADDADEV = 4;
        /** A/D is not responding */
        public static final int DEADADDEV = 5;
        /** Specified board does not have digital I/O */
        public static final int NOTDIGITALCONF = 6;
        /** Specified board does not have a counter */
        public static final int NOTCOUNTERCONF = 7;
        /** Specified board is does not have D/A */
        public static final int NOTDACONF = 8;
        /** Specified board does not have A/D */
        public static final int NOTADCONF = 9;
        /** Specified board does not have thermocouple inputs */
        public static final int NOTMUXCONF = 10;
        /** Invalid port number specified */
        public static final int BADPORTNUM = 11;
        /** Invalid counter device */
        public static final int BADCOUNTERDEVNUM = 12;
        /** Invalid D/A device */
        public static final int BADDADEVNUM = 13;
        /** Invalid sampling mode option specified */
        public static final int BADSAMPLEMODE = 14;
        /** Board configured for invalid interrupt level */
        public static final int BADINT = 15;
        /** Invalid A/D channel Specified */
        public static final int BADADCHAN = 16;
        /** Invalid count specified */
        public static final int BADCOUNT = 17;
        /** invalid counter configuration specified */
        public static final int BADCNTRCONFIG = 18;
        /** Invalid D/A output value specified */
        public static final int BADDAVAL = 19;
        /** Invalid D/A channel specified */
        public static final int BADDACHAN = 20;
        /** A background process is already in progress */
        public static final int ALREADYACTIVE = 22;
        /** DMA transfer crossed page boundary, may have gaps in data */
        public static final int PAGEOVERRUN = 23;
        /** Inavlid sampling rate specified */
        public static final int BADRATE = 24;
        /** Board switches set for "compatible" mode */
        public static final int COMPATMODE = 25;
        /** Incorrect intial trigger state D0 must=TTL low) */
        public static final int TRIGSTATE = 26;
        /** A/D is not responding */
        public static final int ADSTATUSHUNG = 27;
        /** Too few samples before trigger occurred */
        public static final int TOOFEW = 28;
        /** Data lost due to overrun, rate too high */
        public static final int OVERRUN = 29;
        /** Invalid range specified */
        public static final int BADRANGE = 30;
        /** Board does not have programmable gain */
        public static final int NOPROGGAIN = 31;
        /** Not a legal DOS filename */
        public static final int BADFILENAME = 32;
        /** Couldn't complete, disk is full */
        public static final int DISKISFULL = 33;
        /** Board is in compatible mode, so DMA will be used */
        public static final int COMPATWARN = 34;
        /** Invalid pointer (NULL) */
        public static final int BADPOINTER = 35;
        /** Too many gains */
        public static final int TOOMANYGAINS = 36;
        /** Rate may be too high for interrupt I/O */
        public static final int RATEWARNING = 37;
        /** CONVERTDATA cannot be used with DMA I/O */
        public static final int CONVERTDMA = 38;
        /** Board doesn't have DT Connect */
        public static final int DTCONNECTERR = 39;
        /** CONTINUOUS can only be used with BACKGROUND */
        public static final int FORECONTINUOUS = 40;
        /** This function can not be used with this board */
        public static final int BADBOARDTYPE = 41;
        /** Digital I/O is configured incorrectly */
        public static final int WRONGDIGCONFIG = 42;
        /** Digital port is not configurable */
        public static final int NOTCONFIGURABLE = 43;
        /** Invalid port configuration specified */
        public static final int BADPORTCONFIG = 44;
        /** First point argument is not valid */
        public static final int BADFIRSTPOINT = 45;
        /** Attempted to read past end of file */
        public static final int ENDOFFILE = 46;
        /** This board does not have an 8254 counter */
        public static final int NOT8254CTR = 47;
        /** This board does not have a 9513 counter */
        public static final int NOT9513CTR = 48;
        /** Invalid trigger type */
        public static final int BADTRIGTYPE = 49;
        /** Invalid trigger value */
        public static final int BADTRIGVALUE = 50;
        /** Invalid option specified for this function */
        public static final int BADOPTION = 52;
        /** Invalid pre-trigger count sepcified */
        public static final int BADPRETRIGCOUNT = 53;
        /** Invalid fout divider value */
        public static final int BADDIVIDER = 55;
        /** Invalid source value */
        public static final int BADSOURCE = 56;
        /** Invalid compare value */
        public static final int BADCOMPARE = 57;
        /** Invalid time of day value */
        public static final int BADTIMEOFDAY = 58;
        /** Invalid gate interval value */
        public static final int BADGATEINTERVAL = 59;
        /** Invalid gate control value */
        public static final int BADGATECNTRL = 60;
        /** Invalid counter edge value */
        public static final int BADCOUNTEREDGE = 61;
        /** Invalid special gate value */
        public static final int BADSPCLGATE = 62;
        /** Invalid reload value */
        public static final int BADRELOAD = 63;
        /** Invalid recycle flag value */
        public static final int BADRECYCLEFLAG = 64;
        /** Invalid BCD flag value */
        public static final int BADBCDFLAG = 65;
        /** Invalid count direction value */
        public static final int BADDIRECTION = 66;
        /** Invalid output control value */
        public static final int BADOUTCONTROL = 67;
        /** Invalid bit number */
        public static final int BADBITNUMBER = 68;
        /** None of the counter channels are enabled */
        public static final int NONEENABLED = 69;
        /** Element of control array not ENABLED/DISABLED */
        public static final int BADCTRCONTROL = 70;
        /** Invalid EXP channel */
        public static final int BADEXPCHAN = 71;
        /** Wrong A/D range selected for cbtherm */
        public static final int WRONGADRANGE = 72;
        /** Temperature input is out of range */
        public static final int OUTOFRANGE = 73;
        /** Invalid temperate scale */
        public static final int BADTEMPSCALE = 74;
        /** Invalid error code specified */
        public static final int BADERRCODE = 75;
        /** Specified board does not have chan/gain queue */
        public static final int NOQUEUE = 76;
        /** CONTINUOUS can not be used with this count value */
        public static final int CONTINUOUSCOUNT = 77;
        /** D/A FIFO hit empty while doing output */
        public static final int UNDERRUN = 78;
        /** Invalid memory mode specified */
        public static final int BADMEMMODE = 79;
        /** Measured frequency too high for gating interval */
        public static final int FREQOVERRUN = 80;
        /** Board does not have CJC chan configured */
        public static final int NOCJCCHAN = 81;
        /** Invalid chip number used with cbC9513Init */
        public static final int BADCHIPNUM = 82;
        /** Digital I/O not enabled */
        public static final int DIGNOTENABLED = 83;
        /** CONVERT option not allowed with 16 bit A/D */
        public static final int CONVERT16BITS = 84;
        /** EXTMEMORY option requires memory board */
        public static final int NOMEMBOARD = 85;
        /** Memory I/O while DT Active */
        public static final int DTACTIVE = 86;
        /** Specified board is not a memory board */
        public static final int NOTMEMCONF = 87;
        /** First chan in queue can not be odd */
        public static final int ODDCHAN = 88;
        /** Counter was not initialized */
        public static final int CTRNOINIT = 89;
        /** Specified counter is not an 8536 */
        public static final int NOT8536CTR = 90;
        /** A/D sampling is not timed */
        public static final int FREERUNNING = 91;
        /** Operation interrupted with CTRL-C */
        public static final int INTERRUPTED = 92;
        /** Selector could not be allocated */
        public static final int NOSELECTORS = 93;
        /** Burst mode is not supported on this board */
        public static final int NOBURSTMODE = 94;
        /** This function not available in Windows lib */
        public static final int NOTWINDOWSFUNC = 95;
        /** Not configured for simultaneous update */
        public static final int NOTSIMULCONF = 96;
        /** Even channel in odd slot in the queue */
        public static final int EVENODDMISMATCH = 97;
        /** DAS16/M1 sample rate too fast */
        public static final int M1RATEWARNING = 98;
        /** Board is not an RS-485 board */
        public static final int NOTRS485 = 99;
        /** This function not avaliable in DOS */
        public static final int NOTDOSFUNC = 100;
        /** Unipolar and Bipolar can not be used together in A/D que */
        public static final int RANGEMISMATCH = 101;
        /** Sample rate too fast for clock jumper setting */
        public static final int CLOCKTOOSLOW = 102;
        /** Cal factors were out of expected range of values */
        public static final int BADCALFACTORS = 103;
        /** Invalid configuration type information requested */
        public static final int BADCONFIGTYPE = 104;
        /** Invalid configuration item specified */
        public static final int BADCONFIGITEM = 105;
        /** Can't acces PCMCIA board */
        public static final int NOPCMCIABOARD = 106;
        /** Board does not support background I/O */
        public static final int NOBACKGROUND = 107;
        /** String passed to cbGetBoardName is to short */
        public static final int STRINGTOOSHORT = 108;
        /** Convert data option not allowed with external memory */
        public static final int CONVERTEXTMEM = 109;
        /** e_ToEngUnits addition error */
        public static final int BADEUADD = 110;
        /** use 10 MHz clock for rates &gt; 125KHz */
        public static final int DAS16JRRATEWARNING = 111;
        /** DAS08 rate set too low for AInScan warning */
        public static final int DAS08TOOLOWRATE = 112;
        /** more than one sensor type defined for EXP-GP */
        public static final int AMBIGSENSORONGP = 114;
        /** no sensor type defined for EXP-GP */
        public static final int NOSENSORTYPEONGP = 115;
        /** 12 bit board without chan tags - converted in ISR */
        public static final int NOCONVERSIONNEEDED = 116;
        /** External memory cannot be used in CONTINUOUS mode */
        public static final int NOEXTCONTINUOUS = 117;
        /** cbAConvertPretrigData was called after failure in cbAPretrig */
        public static final int INVALIDPRETRIGCONVERT = 118;
        /** bad arg to CLoad for 9513 */
        public static final int BADCTRREG = 119;
        /** Invalid trigger threshold specified in cbSetTrigger */
        public static final int BADTRIGTHRESHOLD = 120;
        /** No PCM card in specified slot */
        public static final int BADPCMSLOTREF = 121;
        /** More than one MCC PCM card in slot */
        public static final int AMBIGPCMSLOTREF = 122;
        /** Bad sensor type selected in Instacal */
        public static final int BADSENSORTYPE = 123;
        /** tried to delete board number which doesn't exist */
        public static final int DELBOARDNOTEXIST = 124;
        /** board name file not found */
        public static final int NOBOARDNAMEFILE = 125;
        /** configuration file not found */
        public static final int CFGFILENOTFOUND = 126;
        /** CBUL.386 device driver not installed */
        public static final int NOVDDINSTALLED = 127;
        /** No Windows memory available */
        public static final int NOWINDOWSMEMORY = 128;
        /** ISR data struct alloc failure */
        public static final int OUTOFDOSMEMORY = 129;
        /** Obsolete option for cbGetConfig/cbSetConfig */
        public static final int OBSOLETEOPTION = 130;
        /** No registry entry for this PCMCIA board */
        public static final int NOPCMREGKEY = 131;
        /** CBUL32.SYS device driver is not loaded */
        public static final int NOCBUL32SYS = 132;
        /** No DMA buffer available to device driver */
        public static final int NODMAMEMORY = 133;
        /** IRQ in being used by another device */
        public static final int IRQNOTAVAILABLE = 134;
        /** This board does not have an LS7266 counter */
        public static final int NOT7266CTR = 135;
        /** Invalid quadrature specified */
        public static final int BADQUADRATURE = 136;
        /** Invalid counting mode specified */
        public static final int BADCOUNTMODE = 137;
        /** Invalid data encoding specified */
        public static final int BADENCODING = 138;
        /** Invalid index mode specified */
        public static final int BADINDEXMODE = 139;
        /** Invalid invert index specified */
        public static final int BADINVERTINDEX = 140;
        /** Invalid flag pins specified */
        public static final int BADFLAGPINS = 141;
        /** This board does not support cbCStatus() */
        public static final int NOCTRSTATUS = 142;
        /** Gating and indexing not allowed simultaneously */
        public static final int NOGATEALLOWED = 143;
        /** Indexing not allowed in non-quadratue mode */
        public static final int NOINDEXALLOWED = 144;
        /** Temperature input has open connection */
        public static final int OPENCONNECTION = 145;
        /** Count must be integer multiple of packetsize for recycle mode. */
        public static final int BMCONTINUOUSCOUNT = 146;
        /** Invalid pointer to callback function passed as arg */
        public static final int BADCALLBACKFUNC = 147;
        /** MetraBus in use */
        public static final int MBUSINUSE = 148;
        /** MetraBus I/O card has no configured controller card */
        public static final int MBUSNOCTLR = 149;
        /** Invalid event type specified for this board. */
        public static final int BADEVENTTYPE = 150;
        /** An event handler has already been enabled for this event type */
        public static final int ALREADYENABLED = 151;
        /** Invalid event count specified. */
        public static final int BADEVENTSIZE = 152;
        /** Unable to install event handler */
        public static final int CANTINSTALLEVENT = 153;
        /** Buffer is too small for operation */
        public static final int BADBUFFERSIZE = 154;
        /** Invalid analog input mode(RSE, NRSE, or DIFF) */
        public static final int BADAIMODE = 155;
        /** Invalid signal type specified. */
        public static final int BADSIGNAL = 156;
        /** Invalid connection specified. */
        public static final int BADCONNECTION = 157;
        /** Invalid index specified, or reached end of internal connection list. */
        public static final int BADINDEX = 158;
        /** No connection is assigned to specified signal. */
        public static final int NOCONNECTION = 159;
        /** Count cannot be greater than the FIFO size for BURSTIO mode. */
        public static final int BADBURSTIOCOUNT = 160;
        /** Device has stopped responding. Please check connections. */
        public static final int DEADDEV = 161;
        /** Invalid access or privilege for specified operation */
        public static final int INVALIDACCESS = 163;
        /** Device unavailable at time of request. Please repeat operation. */
        public static final int UNAVAILABLE = 164;
        /** Device is not ready to send data. Please repeat operation. */
        public static final int NOTREADY = 165;
        /** The specified bit is used for alarm. */
        public static final int BITUSEDFORALARM = 169;
        /** One or more bits on the specified port are used for alarm. */
        public static final int PORTUSEDFORALARM = 170;
        /** Pacer overrun, external clock rate too fast. */
        public static final int PACEROVERRUN = 171;
        /** Invalid channel type specified. */
        public static final int BADCHANTYPE = 172;
        /** Invalid trigger sensitivity specified. */
        public static final int BADTRIGSENSE = 173;
        /** Invalid trigger channel specified. */
        public static final int BADTRIGCHAN = 174;
        /** Invalid trigger level specified. */
        public static final int BADTRIGLEVEL = 175;
        /** Pre-trigger mode is not supported for the specified trigger type. */
        public static final int NOPRETRIGMODE = 176;
        /** Invalid debounce time specified. */
        public static final int BADDEBOUNCETIME = 177;
        /** Invalid debounce trigger mode specified. */
        public static final int BADDEBOUNCETRIGMODE = 178;
        /** Invalid mapped counter specified. */
        public static final int BADMAPPEDCOUNTER = 179;
        /**
         * This function can not be used with the current mode of the specified counter.
         */
        public static final int BADCOUNTERMODE = 180;
        /** Single-Ended mode can not be used for temperature input. */
        public static final int BADTCCHANMODE = 181;
        /** Invalid frequency specified. */
        public static final int BADFREQUENCY = 182;
        /** Invalid event parameter specified. */
        public static final int BADEVENTPARAM = 183;
        /** No interface devices were found with required PAN ID and/or RF Channel. */
        public static final int NONETIFC = 184;
        /**
         * The interface device(s) with required PAN ID and RF Channel has failed.
         * Please check connection.
         */
        public static final int DEADNETIFC = 185;
        /**
         * The remote device is not responding to commands and queries. Please check
         * device.
         */
        public static final int NOREMOTEACK = 186;
        /**
         * The device acknowledged the operation, but has not completed before the
         * timeout.
         */
        public static final int INPUTTIMEOUT = 187;
        /**
         * Number of Setpoints not equal to number of channels with setpoint flag set
         */
        public static final int MISMATCHSETPOINTCOUNT = 188;
        /** Setpoint Level is outside channel range */
        public static final int INVALIDSETPOINTLEVEL = 189;
        /** Setpoint Output Type is invalid */
        public static final int INVALIDSETPOINTOUTPUTTYPE = 190;
        /** Setpoint Output Value is outside channel range */
        public static final int INVALIDSETPOINTOUTPUTVALUE = 191;
        /** Setpoint Comparison limit B greater than Limit A */
        public static final int INVALIDSETPOINTLIMITS = 192;
        /** The string entered is too long for the operation and/or device. */
        public static final int STRINGTOOLONG = 193;
        /** The account name and/or password entered is incorrect. */
        public static final int INVALIDLOGIN = 194;
        /** The device session is already in use. */
        public static final int SESSIONINUSE = 195;
        /** External power is not connected. */
        public static final int NOEXTPOWER = 196;
        /** Invalid duty cycle specified. */
        public static final int BADDUTYCYCLE = 197;
        /** Invalid initial delay specified */
        public static final int BADINITIALDELAY = 199;
        /** No TEDS sensor was detected on the specified channel. */
        public static final int NOTEDSSENSOR = 1000;
        /** Connected TEDS sensor to the specified channel is not supported */
        public static final int INVALIDTEDSSENSOR = 1001;
        /** Calibration failed */
        public static final int CALIBRATIONFAILED = 1002;
        /** The specified bit is used for terminal count stauts. */
        public static final int BITUSEDFORTERMINALCOUNTSTATUS = 1003;
        /**
         * One or more bits on the specified port are used for terminal count stauts.
         */
        public static final int PORTUSEDFORTERMINALCOUNTSTATUS = 1004;
        /** Invalid excitation specified */
        public static final int BADEXCITATION = 1005;
        /** Invalid bridge type specified */
        public static final int BADBRIDGETYPE = 1006;
        /** Invalid load value specified */
        public static final int BADLOADVAL = 1007;
        /** Invalid tick size specified */
        public static final int BADTICKSIZE = 1008;
        /** Bluetooth connection failed */
        public static final int BTHCONNECTIONFAILED = 1013;
        /** Invalid Bluetooth frame */
        public static final int INVALIDBTHFRAME = 1014;
        /** Invalid trigger event specified */
        public static final int BADTRIGEVENT = 1015;
        /** Network connection failed */
        public static final int NETCONNECTIONFAILED = 1016;
        /** Data socket connection failed */
        public static final int DATASOCKETCONNECTIONFAILED = 1017;
        /** Invalid Network frame */
        public static final int INVALIDNETFRAME = 1018;
        /** Network device did not respond within expected time */
        public static final int NETTIMEOUT = 1019;
        /** Network device not found */
        public static final int NETDEVNOTFOUND = 1020;
        /** Invalid connection code */
        public static final int INVALIDCONNECTIONCODE = 1021;
        /** Connection code ignored */
        public static final int CONNECTIONCODEIGNORED = 1022;
        /** Network device already in use */
        public static final int NETDEVINUSE = 1023;
        /** Network device already in use by another process */
        public static final int NETDEVINUSEBYANOTHERPROC = 1024;
        /** Socket Disconnected */
        public static final int SOCKETDISCONNECTED = 1025;
        /** Board Number already in use */
        public static final int BOARDNUMINUSE = 1026;
        /** Specified DAQ device already created */
        public static final int DEVALREADYCREATED = 1027;
        /** Tried to release a board which doesn't exist */
        public static final int BOARDNOTEXIST = 1028;
        /** Invalid host specified */
        public static final int INVALIDNETHOST = 1029;
        /** Invalid port specified */
        public static final int INVALIDNETPORT = 1030;
        /** Invalid interface specified */
        public static final int INVALIDIFC = 1031;
        /** Invalid input mode specified */
        public static final int INVALIDAIINPUTMODE = 1032;
        /** Input mode not configurable */
        public static final int AIINPUTMODENOTCONFIGURABLE = 1033;
        /** Invalid external pacer edge */
        public static final int INVALIDEXTPACEREDGE = 1034;
        /** Common-mode voltage range exceeded */
        public static final int CMREXCEEDED = 1035;
    }

    /******************************************************************
     *
     * **** ATTENTION ALL DEVELOPERS ****
     *
     * When adding error codes, first determine if these are errors that can be
     * caused by the user or if they will never happen in normal operation unless
     * there is a bug.
     *
     * Only if they are user error should you put them in the list above. In that
     * case be sure to give them a name that means something from the user's point
     * of view - rather than from the programmer. For example NO_VDD_INSTALLED
     * rather than DEVICE_CALL_FAILED.
     *
     * Do not add any errors to the section above without getting agreement by the
     * dept. so that all user header files and header files for other versions of
     * the library can be updates together.
     *
     * If it's an internal error, then be sure to add it to the correct section
     * below.
     * 
     ********************************************************************/
    public static final class InternalErrorCode {
        private InternalErrorCode() {
        }
    
        /* Internal errors returned by 16 bit library */
        /** 200-299 Internal library error */
        public static final int INTERNALERR = 200;
        /** DMA buffer could not be locked */
        public static final int CANT_LOCK_DMA_BUF = 201;
        /** DMA already controlled by another VxD */
        public static final int DMA_IN_USE = 202;
        /** Invalid Windows memory handle */
        public static final int BAD_MEM_HANDLE = 203;
        /** Windows Enhance mode is not running */
        public static final int NO_ENHANCED_MODE = 204;
        /** Program error getting memory board source */
        public static final int MEMBOARDPROGERROR = 211;
    
        /* Internal errors returned by 32 bit library */
        /** 300-399 32 bit library internal errors */
        public static final int INTERNAL32_ERR = 300;
        /** 32 bit - default buffer allocation when no user buffer used with file */
        public static final int NO_MEMORY_FOR_BUFFER = 301;
        /** 32 bit - failure on INIT_ISR_DATA IOCTL call */
        public static final int WIN95_CANNOT_SETUP_ISR_DATA = 302;
        /** 32 bit - failure on INIT_ISR_DATA IOCTL call */
        public static final int WIN31_CANNOT_SETUP_ISR_DATA = 303;
        /** 32 bit - error reading board configuration file */
        public static final int CFG_FILE_READ_FAILURE = 304;
        /** 32 bit - error writing board configuration file */
        public static final int CFG_FILE_WRITE_FAILURE = 305;
        /** 32 bit - failed to create board */
        public static final int CREATE_BOARD_FAILURE = 306;
        /** 32 bit - Config Option item used in development only */
        public static final int DEVELOPMENT_OPTION = 307;
        /** 32 bit - cannot open configuration file. */
        public static final int CFGFILE_CANT_OPEN = 308;
        /** 32 bit - incorrect file id. */
        public static final int CFGFILE_BAD_ID = 309;
        /** 32 bit - incorrect file version. */
        public static final int CFGFILE_BAD_REV = 310;
        /** ; */
        public static final int CFGFILE_NOINSERT = 311;
        /** ; */
        public static final int CFGFILE_NOREPLACE = 312;
        /** ; */
        public static final int BIT_NOT_ZERO = 313;
        /** ; */
        public static final int BIT_NOT_ONE = 314;
        /** No control register at this location. */
        public static final int BAD_CTRL_REG = 315;
        /** No output register at this location. */
        public static final int BAD_OUTP_REG = 316;
        /** No read back register at this location. */
        public static final int BAD_RDBK_REG = 317;
        /** No control register on this board. */
        public static final int NO_CTRL_REG = 318;
        /** No control register on this board. */
        public static final int NO_OUTP_REG = 319;
        /** No control register on this board. */
        public static final int NO_RDBK_REG = 320;
        /** internal ctrl reg test failed. */
        public static final int CTRL_REG_FAIL = 321;
        /** internal output reg test failed. */
        public static final int OUTP_REG_FAIL = 322;
        /** internal read back reg test failed. */
        public static final int RDBK_REG_FAIL = 323;
        public static final int FUNCTION_NOT_IMPLEMENTED = 324;
        /** Overflow in RTD calculation */
        public static final int BAD_RTD_CONVERSION = 325;
        /** PCI BIOS not present in the PC */
        public static final int NO_PCI_BIOS = 326;
        /** Invalid PCI board index passed to PCI BIOS */
        public static final int BAD_PCI_INDEX = 327;
        /** Specified PCI board not detected */
        public static final int NO_PCI_BOARD = 328;
        /** PCI resource assignment failed */
        public static final int PCI_ASSIGN_FAILED = 329;
        /** No PCI address returned */
        public static final int PCI_NO_ADDRESS = 330;
        /** No PCI IRQ returned */
        public static final int PCI_NO_IRQ = 331;
        /** IOCTL call failed on VDD_API_INIT_ISR_INFO */
        public static final int CANT_INIT_ISR_INFO = 332;
        /** IOCTL call failed on VDD_API_PASS_USER_BUFFER */
        public static final int CANT_PASS_USER_BUFFER = 333;
        /** IOCTL call failed on VDD_API_INSTALL_INT */
        public static final int CANT_INSTALL_INT = 334;
        /** IOCTL call failed on VDD_API_UNINSTALL_INT */
        public static final int CANT_UNINSTALL_INT = 335;
        /** IOCTL call failed on VDD_API_START_DMA */
        public static final int CANT_START_DMA = 336;
        /** IOCTL call failed on VDD_API_GET_STATUS */
        public static final int CANT_GET_STATUS = 337;
        /** IOCTL call failed on VDD_API_GET_PRINT_PORT */
        public static final int CANT_GET_PRINT_PORT = 338;
        /** IOCTL call failed on VDD_API_MAP_PCM_CIS */
        public static final int CANT_MAP_PCM_CIS = 339;
        /** IOCTL call failed on VDD_API_GET_PCM_CFG */
        public static final int CANT_GET_PCM_CFG = 340;
        /** IOCTL call failed on VDD_API_GET_PCM_CCSR */
        public static final int CANT_GET_PCM_CCSR = 341;
        /** IOCTL call failed on VDD_API_GET_PCI_INFO */
        public static final int CANT_GET_PCI_INFO = 342;
        /** Specified USB board not detected */
        public static final int NO_USB_BOARD = 343;
        /** No more files in the directory */
        public static final int NOMOREFILES = 344;
        /** Invalid file number */
        public static final int BADFILENUMBER = 345;
        /** Invalid structure size */
        public static final int INVALIDSTRUCTSIZE = 346;
        /** EOF marker not found, possible loss of data */
        public static final int LOSSOFDATA = 347;
        /** File is not a valid MCC binary file */
        public static final int INVALIDBINARYFILE = 348;
        /** Invlid delimiter specified for CSV file */
        public static final int INVALIDDELIMITER = 349;
        /** Specified Bluetooth board not detected */
        public static final int NO_BTH_BOARD = 350;
        /** Specified Network board not detected */
        public static final int NO_NET_BOARD = 351;
    
        /** DOS errors are remapped by adding DOS_ERR_OFFSET to them */
        public static final int DOS_ERR_OFFSET = 500;
    
        /* These are the commonly occurring remapped DOS error codes */
        public static final int DOSBADFUNC = 501;
        public static final int DOSFILENOTFOUND = 502;
        public static final int DOSPATHNOTFOUND = 503;
        public static final int DOSNOHANDLES = 504;
        public static final int DOSACCESSDENIED = 505;
        public static final int DOSINVALIDHANDLE = 506;
        public static final int DOSNOMEMORY = 507;
        public static final int DOSBADDRIVE = 515;
        public static final int DOSTOOMANYFILES = 518;
        public static final int DOSWRITEPROTECT = 519;
        public static final int DOSDRIVENOTREADY = 521;
        public static final int DOSSEEKERROR = 525;
        public static final int DOSWRITEFAULT = 529;
        public static final int DOSREADFAULT = 530;
        public static final int DOSGENERALFAULT = 531;
    
        /* Windows internal error codes */
        public static final int WIN_CANNOT_ENABLE_INT = 603;
        public static final int WIN_CANNOT_DISABLE_INT = 605;
        public static final int WIN_CANT_PAGE_LOCK_BUFFER = 606;
        public static final int NO_PCM_CARD = 630;
    }

    /** Maximum length of error string */
    public static final int ERRSTRLEN = 256;


    private LibcbwException(String s) {
        super(s);
    }

    /**
     * Creates an exception object to wrap an integer error code returned by the
     * native library.
     * 
     * @param errCode - the error code returned by the native library.
     * @return the new exception object.
     */
    public static LibcbwException fromErrorCode(int errCode) {
        ByteBuffer msgBuf = ByteBuffer.allocateDirect(LibcbwException.ERRSTRLEN);

        int err2 = LibcbwJNA.cbGetErrMsg(errCode, msgBuf);
        if(err2 != LibcbwException.ErrorCode.NOERRORS) return fromErrorCode(err2);

        return new LibcbwException(StandardCharsets.UTF_8.decode(msgBuf).toString());
    }
}
