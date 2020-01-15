package uk.ac.gla.dcs.tp3_2019_ese1.libcbw;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.DoubleByReference;
import com.sun.jna.ptr.FloatByReference;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.LongByReference;
import com.sun.jna.ptr.NativeLongByReference;
import com.sun.jna.ptr.ShortByReference;
import com.sun.jna.win32.StdCallLibrary;

/**
 * JNA bridge to the native library.
 * 
 * @author Duncan Lowther (2402789L)
 */
public class LibcbwJNA implements StdCallLibrary {
    public static final String JNA_LIBRARY_NAME = "cbw" + System.getProperty("sun.arch.data.model");
    public static final NativeLibrary JNA_NATIVE_LIB = NativeLibrary.getInstance(LibcbwJNA.JNA_LIBRARY_NAME);
    public static final float CURRENTREVNUM = 6.7F;

    /** types of error reporting */
    public static final class ErrorReporting {
        private ErrorReporting() {
        }

        public static final int DONTPRINT = 0;
        public static final int PRINTWARNINGS = 1;
        public static final int PRINTFATAL = 2;
        public static final int PRINTALL = 3;
    }

    /** types of error handling */
    public static final class ErrorHandling {
        private ErrorHandling() {
        }

        public static final int DONTSTOP = 0;
        public static final int STOPFATAL = 1;
        public static final int STOPALL = 2;
    }

    public static final boolean USE_INSTACAL = false;

    static {
        Native.register(LibcbwJNA.JNA_LIBRARY_NAME);
        if(!USE_INSTACAL) cbIgnoreInstaCal();
        int err = cbDeclareRevision(new FloatByReference(CURRENTREVNUM));
        if(err != LibcbwException.ErrorCode.NOERRORS) throw new ExceptionInInitializerError(LibcbwException.fromErrorCode(err));

        err = cbErrHandling(ErrorReporting.DONTPRINT, ErrorHandling.DONTSTOP);
        if(err != LibcbwException.ErrorCode.NOERRORS) throw new ExceptionInInitializerError(LibcbwException.fromErrorCode(err));
    }

    public static class HGLOBAL extends WinNT.HANDLE {
        public HGLOBAL() {
            super();
        }

        public HGLOBAL(Pointer p) {
            super(p);
        }
    }

    public static final class IOFunctions {
        private IOFunctions() {
        }

        /** Analog Input Function */
        public static final int AIFUNCTION = 1;
        /** Analog Output Function */
        public static final int AOFUNCTION = 2;
        /** Digital Input Function */
        public static final int DIFUNCTION = 3;
        /** Digital Output Function */
        public static final int DOFUNCTION = 4;
        /** Counter Function */
        public static final int CTRFUNCTION = 5;
        /** Daq Input Function */
        public static final int DAQIFUNCTION = 6;
        /** Daq Output Function */
        public static final int DAQOFUNCTION = 7;
    }

    /** Calibration coefficient types */
    public static final class CalibrationCoeff {
        private CalibrationCoeff() {
        }

        public static final int COARSE_GAIN = 0x01;
        public static final int COARSE_OFFSET = 0x02;
        public static final int FINE_GAIN = 0x04;
        public static final int FINE_OFFSET = 0x08;
        public static final int GAIN = COARSE_GAIN;
        public static final int OFFSET = COARSE_OFFSET;
    }

    /** Maximum length of board name */
    public static final int BOARDNAMELEN = 64;

    /* Status values */
    public static final int IDLE = 0;
    public static final int RUNNING = 1;

    /** Option Flags */
    public static final class CBWOptions {
        private CBWOptions() {
        }

        /** Run in foreground, don't return till done */
        public static final int FOREGROUND = 0x0000;
        /** Run in background, return immediately */
        public static final int BACKGROUND = 0x0001;

        /** One execution */
        public static final int SINGLEEXEC = 0x0000;
        /** Run continuously until cbstop() called */
        public static final int CONTINUOUS = 0x0002;

        /** Time conversions with internal clock */
        public static final int TIMED = 0x0000;
        /** Time conversions with external clock */
        public static final int EXTCLOCK = 0x0004;

        /** Return raw data */
        public static final int NOCONVERTDATA = 0x0000;
        /** Return converted A/D data */
        public static final int CONVERTDATA = 0x0008;

        /** Disable DT Connect */
        public static final int NODTCONNECT = 0x0000;
        /** Enable DT Connect */
        public static final int DTCONNECT = 0x0010;
        /** Scale scan data to engineering units */
        public static final int SCALEDATA = 0x0010;

        /** Use whatever makes sense for board */
        public static final int DEFAULTIO = 0x0000;
        /** Interrupt per A/D conversion */
        public static final int SINGLEIO = 0x0020;
        /** DMA transfer */
        public static final int DMAIO = 0x0040;
        /** Interrupt per block of conversions */
        public static final int BLOCKIO = 0x0060;
        /** Transfer upon scan completion */
        public static final int BURSTIO = 0x10000;
        /** Re-arm trigger upon acquiring trigger count samples */
        public static final int RETRIGMODE = 0x20000;
        /** Non-streamed D/A output */
        public static final int NONSTREAMEDIO = 0x040000;
        /** Output operation is triggered on ADC clock */
        public static final int ADCCLOCKTRIG = 0x080000;
        /** Output operation is paced by ADC clock */
        public static final int ADCCLOCK = 0x100000;
        /** Use high resolution rate */
        public static final int HIGHRESRATE = 0x200000;
        /** Enable Shunt Calibration */
        public static final int SHUNTCAL = 0x400000;

        /** Digital IN/OUT a byte at a time */
        public static final int BYTEXFER = 0x0000;
        /** Digital IN/OUT a word at a time */
        public static final int WORDXFER = 0x0100;
        /** Digital IN/OUT a double word at a time */
        public static final int DWORDXFER = 0x0200;

        /** Individual D/A output */
        public static final int INDIVIDUAL = 0x0000;
        /** Simultaneous D/A output */
        public static final int SIMULTANEOUS = 0x0200;

        /** Filter thermocouple inputs */
        public static final int FILTER = 0x0000;
        /** Disable filtering for thermocouple */
        public static final int NOFILTER = 0x0400;

        /** Return data to data array */
        public static final int NORMMEMORY = 0x0000;
        /** Send data to memory board ia DT-Connect */
        public static final int EXTMEMORY = 0x0800;

        /** Enable burst mode */
        public static final int BURSTMODE = 0x1000;

        /** Disbale time-of-day interrupts */
        public static final int NOTODINTS = 0x2000;
        /** Wait for new data to become available */
        public static final int WAITFORNEWDATA = 0x2000;

        /** A/D is triggered externally */
        public static final int EXTTRIGGER = 0x4000;

        /** Return uncalibrated PCM data */
        public static final int NOCALIBRATEDATA = 0x8000;
        /** Return calibrated PCM A/D data */
        public static final int CALIBRATEDATA = 0x0000;

        /** Return 16-bit counter data */
        public static final int CTR16BIT = 0x0000;
        /** Return 32-bit counter data */
        public static final int CTR32BIT = 0x0100;
        /** Return 48-bit counter data */
        public static final int CTR48BIT = 0x0200;
        /** Return 64-bit counter data */
        public static final int CTR64BIT = 0x0400;
        /** Disables clearing counters when scan starts */
        public static final int NOCLEAR = 0x0800;
    }

    public static final int ENABLED = 1;
    public static final int DISABLED = 0;

    public static final int UPDATEIMMEDIATE = 0;
    public static final int UPDATEONCOMMAND = 1;

    /**
     * Arguments that are used in a particular function call should be set to
     * NOTUSED
     */
    public static final int NOTUSED = -1;

    /** channel types */
    public static final class ChannelType {
        private ChannelType() {
        }

        /** Analog channel */
        public static final int ANALOG = 0;
        /** 8-bit digital port */
        public static final int DIGITAL8 = 1;
        /** 16-bit digital port */
        public static final int DIGITAL16 = 2;
        /** 16-bit counter */
        public static final int CTR16 = 3;
        /** Lower 16-bits of 32-bit counter */
        public static final int CTR32LOW = 4;
        /** Upper 16-bits of 32-bit counter */
        public static final int CTR32HIGH = 5;
        /** CJC channel */
        public static final int CJC = 6;
        /** Thermocouple channel */
        public static final int TC = 7;
        /** Analog channel, singel-ended mode */
        public static final int ANALOG_SE = 8;
        /** Analog channel, Differential mode */
        public static final int ANALOG_DIFF = 9;
        /** Setpoint status channel */
        public static final int SETPOINTSTATUS = 10;
        /** Bank 0 of counter */
        public static final int CTRBANK0 = 11;
        /** Bank 1 of counter */
        public static final int CTRBANK1 = 12;
        /** Bank 2 of counter */
        public static final int CTRBANK2 = 13;
        /** Bank 3 of counter */
        public static final int CTRBANK3 = 14;
        /** Dummy channel. Fills the corresponding data elements with zero */
        public static final int PADZERO = 15;
        public static final int DIGITAL = 16;
        public static final int CTR = 17;
    }

    /* channel type flags */
    /** Enable setpoint detection */
    public static final int SETPOINT_ENABLE = 0x100;

    /** setpoint flags */
    public static final class SetpointFlags {
        private SetpointFlags() {
        }

        /** Channel = LimitA value */
        public static final int SF_EQUAL_LIMITA = 0x00;
        /** Channel &lt; LimitA value */
        public static final int SF_LESSTHAN_LIMITA = 0x01;
        /** Channel Inside LimitA and LimitB (LimitA &lt; Channel &lt; LimitB) */
        public static final int SF_INSIDE_LIMITS = 0x02;
        /** Channel &gt; LimitB */
        public static final int SF_GREATERTHAN_LIMITB = 0x03;
        /**
         * Channel Outside LimitA and LimitB (LimitA &lt; Channel or Channel &gt;
         * LimitB)
         */
        public static final int SF_OUTSIDE_LIMITS = 0x04;
        /** Use As Hysteresis */
        public static final int SF_HYSTERESIS = 0x05;
        /** Latch output condition (output = output1 for duration of acquisition) */
        public static final int SF_UPDATEON_TRUEONLY = 0x00;
        /**
         * Do not latch output condition (output = output1 when criteria met else output
         * = output2)
         */
        public static final int SF_UPDATEON_TRUEANDFALSE = 0x08;
    }

    /** Setpoint output channels */
    public static final class SetpointOutputs {
        private SetpointOutputs() {
        }

        /** No Output */
        public static final int SO_NONE = 0;
        /** Output to digital Port */
        public static final int SO_DIGITALPORT = 1;
        /** Output to first PortC */
        public static final int SO_FIRSTPORTC = 1;
        /** Output to DAC0 */
        public static final int SO_DAC0 = 2;
        /** Output to DAC1 */
        public static final int SO_DAC1 = 3;
        /** Output to DAC2 */
        public static final int SO_DAC2 = 4;
        /** Output to DAC3 */
        public static final int SO_DAC3 = 5;
        /** Output to TMR0 */
        public static final int SO_TMR0 = 6;
        /** Output to TMR1 */
        public static final int SO_TMR1 = 7;
    }

    /** cbDaqSetTrigger trigger sources */
    public static final class TriggerSource {
        private TriggerSource() {
        }

        public static final int TRIG_IMMEDIATE = 0;
        public static final int TRIG_EXTTTL = 1;
        public static final int TRIG_ANALOG_HW = 2;
        public static final int TRIG_ANALOG_SW = 3;
        public static final int TRIG_DIGPATTERN = 4;
        public static final int TRIG_COUNTER = 5;
        public static final int TRIG_SCANCOUNT = 6;
    }

    /** cbDaqSetTrigger trigger sensitivities */
    public static final class TriggerSensitivity {
        private TriggerSensitivity() {
        }

        public static final int RISING_EDGE = 0;
        public static final int FALLING_EDGE = 1;
        public static final int ABOVE_LEVEL = 2;
        public static final int BELOW_LEVEL = 3;
        public static final int EQ_LEVEL = 4;
        public static final int NE_LEVEL = 5;
        public static final int HIGH_LEVEL = 6;
        public static final int LOW_LEVEL = 7;
    }

    /* trigger events */
    public static final int START_EVENT = 0;
    public static final int STOP_EVENT = 1;

    /** settling time settings */
    public static final class SettleTime {
        private SettleTime() {
        }

        public static final int SETTLE_DEFAULT = 0;
        public static final int SETTLE_1us = 1;
        public static final int SETTLE_5us = 2;
        public static final int SETTLE_10us = 3;
        public static final int SETTLE_1ms = 4;
    }

    /* Types of digital input ports */
    public static final int DIGITALOUT = 1;
    public static final int DIGITALIN = 2;

    /* DT Modes for cbMemSetDTMode() */
    public static final int DTIN = 0;
    public static final int DTOUT = 2;

    /** read/write from current position */
    public static final int FROMHERE = -1;
    /** Get first item in list */
    public static final int GETFIRST = -2;
    /** Get next item in list */
    public static final int GETNEXT = -3;

    /** Temperature scales */
    public static final class TemperatureScale {
        private TemperatureScale() {
        }

        public static final int CELSIUS = 0;
        public static final int FAHRENHEIT = 1;
        public static final int KELVIN = 2;
        /** special scale for DAS-TC boards */
        public static final int VOLTS = 4;
        public static final int NOSCALE = 5;
    }

    /** Default option */
    public static final int DEFAULTOPTION = 0x0000;

    /** Types of digital I/O Ports */
    public static final class DIOPortType {
        private DIOPortType() {
        }

        public static final int AUXPORT = 1;
        public static final int AUXPORT0 = 1;
        public static final int AUXPORT1 = 2;
        public static final int AUXPORT2 = 3;
        public static final int FIRSTPORTA = 10;
        public static final int FIRSTPORTB = 11;
        public static final int FIRSTPORTCL = 12;
        public static final int FIRSTPORTC = 12;
        public static final int FIRSTPORTCH = 13;
        public static final int SECONDPORTA = 14;
        public static final int SECONDPORTB = 15;
        public static final int SECONDPORTCL = 16;
        public static final int SECONDPORTCH = 17;
        public static final int THIRDPORTA = 18;
        public static final int THIRDPORTB = 19;
        public static final int THIRDPORTCL = 20;
        public static final int THIRDPORTCH = 21;
        public static final int FOURTHPORTA = 22;
        public static final int FOURTHPORTB = 23;
        public static final int FOURTHPORTCL = 24;
        public static final int FOURTHPORTCH = 25;
        public static final int FIFTHPORTA = 26;
        public static final int FIFTHPORTB = 27;
        public static final int FIFTHPORTCL = 28;
        public static final int FIFTHPORTCH = 29;
        public static final int SIXTHPORTA = 30;
        public static final int SIXTHPORTB = 31;
        public static final int SIXTHPORTCL = 32;
        public static final int SIXTHPORTCH = 33;
        public static final int SEVENTHPORTA = 34;
        public static final int SEVENTHPORTB = 35;
        public static final int SEVENTHPORTCL = 36;
        public static final int SEVENTHPORTCH = 37;
        public static final int EIGHTHPORTA = 38;
        public static final int EIGHTHPORTB = 39;
        public static final int EIGHTHPORTCL = 40;
        public static final int EIGHTHPORTCH = 41;
    }

    /** Analog input modes */
    public static final class AInMode {
        private AInMode() {
        }

        public static final int DIFFERENTIAL = 0;
        public static final int SINGLE_ENDED = 1;
        public static final int GROUNDED = 16;
    }

    /** Selectable analog input modes (PCI-6000 series) */
    public static final class PCI6000AInMode {
        private PCI6000AInMode() {
        }

        /** Referenced Single-Ended */
        public static final int RSE = 0x1000;
        /** Non-Referenced Single-Ended */
        public static final int NRSE = 0x2000;
        /** Differential */
        public static final int DIFF = 0x4000;
    }

    /** Selectable A/D Ranges codes */
    public static final class ADCRange {
        private ADCRange() {
        }

        /** -60 to 60 Volts */
        public static final int BIP60VOLTS = 20;
        public static final int BIP30VOLTS = 23;
        /** -20 to +20 Volts */
        public static final int BIP20VOLTS = 15;
        /** -15 to +15 Volts */
        public static final int BIP15VOLTS = 21;
        /** -10 to +10 Volts */
        public static final int BIP10VOLTS = 1;
        /** -5 to +5 Volts */
        public static final int BIP5VOLTS = 0;
        /** -4 to + 4 Volts */
        public static final int BIP4VOLTS = 16;
        /** -2.5 to +2.5 Volts */
        public static final int BIP2PT5VOLTS = 2;
        /** -2.0 to +2.0 Volts */
        public static final int BIP2VOLTS = 14;
        /** -1.25 to +1.25 Volts */
        public static final int BIP1PT25VOLTS = 3;
        /** -1 to +1 Volts */
        public static final int BIP1VOLTS = 4;
        /** -.625 to +.625 Volts */
        public static final int BIPPT625VOLTS = 5;
        /** -.5 to +.5 Volts */
        public static final int BIPPT5VOLTS = 6;
        /** -0.25 to +0.25 Volts */
        public static final int BIPPT25VOLTS = 12;
        /** -0.2 to +0.2 Volts */
        public static final int BIPPT2VOLTS = 13;
        /** -.1 to +.1 Volts */
        public static final int BIPPT1VOLTS = 7;
        /** -.05 to +.05 Volts */
        public static final int BIPPT05VOLTS = 8;
        /** -.01 to +.01 Volts */
        public static final int BIPPT01VOLTS = 9;
        /** -.005 to +.005 Volts */
        public static final int BIPPT005VOLTS = 10;
        /** -1.67 to +1.67 Volts */
        public static final int BIP1PT67VOLTS = 11;
        /** -0.312 to +0.312 Volts */
        public static final int BIPPT312VOLTS = 17;
        /** -0.156 to +0.156 Volts */
        public static final int BIPPT156VOLTS = 18;
        /** -0.125 to +0.125 Volts */
        public static final int BIPPT125VOLTS = 22;
        /** -0.078 to +0.078 Volts */
        public static final int BIPPT078VOLTS = 19;

        /** 0 to 10 Volts */
        public static final int UNI10VOLTS = 100;
        /** 0 to 5 Volts */
        public static final int UNI5VOLTS = 101;
        /** 0 to 4 Volts */
        public static final int UNI4VOLTS = 114;
        /** 0 to 2.5 Volts */
        public static final int UNI2PT5VOLTS = 102;
        /** 0 to 2 Volts */
        public static final int UNI2VOLTS = 103;
        /** 0 to 1.67 Volts */
        public static final int UNI1PT67VOLTS = 109;
        /** 0 to 1.25 Volts */
        public static final int UNI1PT25VOLTS = 104;
        /** 0 to 1 Volt */
        public static final int UNI1VOLTS = 105;
        /** 0 to .5 Volt */
        public static final int UNIPT5VOLTS = 110;
        /** 0 to 0.25 Volt */
        public static final int UNIPT25VOLTS = 111;
        /** 0 to .2 Volt */
        public static final int UNIPT2VOLTS = 112;
        /** 0 to .1 Volt */
        public static final int UNIPT1VOLTS = 106;
        /** 0 to .05 Volt */
        public static final int UNIPT05VOLTS = 113;
        /** 0 to .02 Volt */
        public static final int UNIPT02VOLTS = 108;
        /** 0 to .01 Volt */
        public static final int UNIPT01VOLTS = 107;

        /** 4 to 20 ma */
        public static final int MA4TO20 = 200;
        /** 2 to 10 ma */
        public static final int MA2TO10 = 201;
        /** 1 to 5 ma */
        public static final int MA1TO5 = 202;
        /** .5 to 2.5 ma */
        public static final int MAPT5TO2PT5 = 203;
        /** 0 to 20 ma */
        public static final int MA0TO20 = 204;
        /** -0.025 to 0.025 ma */
        public static final int BIPPT025AMPS = 205;

        public static final int UNIPOLAR = 300;
        public static final int BIPOLAR = 301;

        /** -0.025 to +0.025 V/V */
        public static final int BIPPT025VOLTSPERVOLT = 400;
    }

    /* Types of D/A */
    public static final int ADDA1 = 0;
    public static final int ADDA2 = 1;

    /** 8536 counter output 1 control */
    public static final class Counter8536Output {
        private Counter8536Output() {
        }

        public static final int NOTLINKED = 0;
        public static final int GATECTR2 = 1;
        public static final int TRIGCTR2 = 2;
        public static final int INCTR2 = 3;
    }

    /** 8536 trigger types */
    public static final class Trigger8536 {
        private Trigger8536() {
        }

        public static final int HW_START_TRIG = 0;
        public static final int HW_RETRIG = 1;
        public static final int SW_START_TRIG = 2;
    }

    /** Types of 8254 counter configurations */
    public static final class Config8254 {
        private Config8254() {
        }

        public static final int HIGHONLASTCOUNT = 0;
        public static final int ONESHOT = 1;
        public static final int RATEGENERATOR = 2;
        public static final int SQUAREWAVE = 3;
        public static final int SOFTWARESTROBE = 4;
        public static final int HARDWARESTROBE = 5;
    }

    /* Where to reload from for 9513 counters */
    public static final int LOADREG = 0;
    public static final int LOADANDHOLDREG = 1;

    /* Counter recycle modes for 9513 and 8536 */
    public static final int ONETIME = 0;
    public static final int RECYCLE = 1;

    /* Direction of counting for 9513 counters */
    public static final int COUNTDOWN = 0;
    public static final int COUNTUP = 1;

    /* Types of count detection for 9513 counters */
    public static final int POSITIVEEDGE = 0;
    public static final int NEGATIVEEDGE = 1;

    /** Counter output control */
    public static final class CounterOutput {
        private CounterOutput() {
        }

        /** 9513 */
        public static final int ALWAYSLOW = 0;
        /** 9513 and 8536 */
        public static final int HIGHPULSEONTC = 1;
        /** 9513 and 8536 */
        public static final int TOGGLEONTC = 2;
        /** 9513 */
        public static final int DISCONNECTED = 4;
        /** 9513 */
        public static final int LOWPULSEONTC = 5;
        /** 8536 */
        public static final int HIGHUNTILTC = 6;
    }

    /** 9513 Counter input sources */
    public static final class Counter9513Input {
        private Counter9513Input() {
        }

        public static final int TCPREVCTR = 0;
        public static final int CTRINPUT1 = 1;
        public static final int CTRINPUT2 = 2;
        public static final int CTRINPUT3 = 3;
        public static final int CTRINPUT4 = 4;
        public static final int CTRINPUT5 = 5;
        public static final int GATE1 = 6;
        public static final int GATE2 = 7;
        public static final int GATE3 = 8;
        public static final int GATE4 = 9;
        public static final int GATE5 = 10;
        public static final int FREQ1 = 11;
        public static final int FREQ2 = 12;
        public static final int FREQ3 = 13;
        public static final int FREQ4 = 14;
        public static final int FREQ5 = 15;
        public static final int CTRINPUT6 = 101;
        public static final int CTRINPUT7 = 102;
        public static final int CTRINPUT8 = 103;
        public static final int CTRINPUT9 = 104;
        public static final int CTRINPUT10 = 105;
        public static final int GATE6 = 106;
        public static final int GATE7 = 107;
        public static final int GATE8 = 108;
        public static final int GATE9 = 109;
        public static final int GATE10 = 110;
        public static final int FREQ6 = 111;
        public static final int FREQ7 = 112;
        public static final int FREQ8 = 113;
        public static final int FREQ9 = 114;
        public static final int FREQ10 = 115;
        public static final int CTRINPUT11 = 201;
        public static final int CTRINPUT12 = 202;
        public static final int CTRINPUT13 = 203;
        public static final int CTRINPUT14 = 204;
        public static final int CTRINPUT15 = 205;
        public static final int GATE11 = 206;
        public static final int GATE12 = 207;
        public static final int GATE13 = 208;
        public static final int GATE14 = 209;
        public static final int GATE15 = 210;
        public static final int FREQ11 = 211;
        public static final int FREQ12 = 212;
        public static final int FREQ13 = 213;
        public static final int FREQ14 = 214;
        public static final int FREQ15 = 215;
        public static final int CTRINPUT16 = 301;
        public static final int CTRINPUT17 = 302;
        public static final int CTRINPUT18 = 303;
        public static final int CTRINPUT19 = 304;
        public static final int CTRINPUT20 = 305;
        public static final int GATE16 = 306;
        public static final int GATE17 = 307;
        public static final int GATE18 = 308;
        public static final int GATE19 = 309;
        public static final int GATE20 = 310;
        public static final int FREQ16 = 311;
        public static final int FREQ17 = 312;
        public static final int FREQ18 = 313;
        public static final int FREQ19 = 314;
        public static final int FREQ20 = 315;
    }

    /** Counter load registers */
    public static final class CounterLoadRegister {
        private CounterLoadRegister() {
        }

        public static final int LOADREG0 = 0;
        public static final int LOADREG1 = 1;
        public static final int LOADREG2 = 2;
        public static final int LOADREG3 = 3;
        public static final int LOADREG4 = 4;
        public static final int LOADREG5 = 5;
        public static final int LOADREG6 = 6;
        public static final int LOADREG7 = 7;
        public static final int LOADREG8 = 8;
        public static final int LOADREG9 = 9;
        public static final int LOADREG10 = 10;
        public static final int LOADREG11 = 11;
        public static final int LOADREG12 = 12;
        public static final int LOADREG13 = 13;
        public static final int LOADREG14 = 14;
        public static final int LOADREG15 = 15;
        public static final int LOADREG16 = 16;
        public static final int LOADREG17 = 17;
        public static final int LOADREG18 = 18;
        public static final int LOADREG19 = 19;
        public static final int LOADREG20 = 20;
    }

    /** 9513 Counter registers */
    public static final class Counter9513Register {
        private Counter9513Register() {
        }

        public static final int HOLDREG1 = 101;
        public static final int HOLDREG2 = 102;
        public static final int HOLDREG3 = 103;
        public static final int HOLDREG4 = 104;
        public static final int HOLDREG5 = 105;
        public static final int HOLDREG6 = 106;
        public static final int HOLDREG7 = 107;
        public static final int HOLDREG8 = 108;
        public static final int HOLDREG9 = 109;
        public static final int HOLDREG10 = 110;
        public static final int HOLDREG11 = 111;
        public static final int HOLDREG12 = 112;
        public static final int HOLDREG13 = 113;
        public static final int HOLDREG14 = 114;
        public static final int HOLDREG15 = 115;
        public static final int HOLDREG16 = 116;
        public static final int HOLDREG17 = 117;
        public static final int HOLDREG18 = 118;
        public static final int HOLDREG19 = 119;
        public static final int HOLDREG20 = 120;

        public static final int ALARM1CHIP1 = 201;
        public static final int ALARM2CHIP1 = 202;
        public static final int ALARM1CHIP2 = 301;
        public static final int ALARM2CHIP2 = 302;
        public static final int ALARM1CHIP3 = 401;
        public static final int ALARM2CHIP3 = 402;
        public static final int ALARM1CHIP4 = 501;
        public static final int ALARM2CHIP4 = 502;
    }

    /** LS7266 Counter registers */
    public static final class LS7266Register {
        private LS7266Register() {
        }

        public static final int COUNT1 = 601;
        public static final int COUNT2 = 602;
        public static final int COUNT3 = 603;
        public static final int COUNT4 = 604;

        public static final int PRESET1 = 701;
        public static final int PRESET2 = 702;
        public static final int PRESET3 = 703;
        public static final int PRESET4 = 704;

        public static final int PRESCALER1 = 801;
        public static final int PRESCALER2 = 802;
        public static final int PRESCALER3 = 803;
        public static final int PRESCALER4 = 804;

        public static final int MINLIMITREG0 = 900;
        public static final int MINLIMITREG1 = 901;
        public static final int MINLIMITREG2 = 902;
        public static final int MINLIMITREG3 = 903;
        public static final int MINLIMITREG4 = 904;
        public static final int MINLIMITREG5 = 905;
        public static final int MINLIMITREG6 = 906;
        public static final int MINLIMITREG7 = 907;

        public static final int MAXLIMITREG0 = 1000;
        public static final int MAXLIMITREG1 = 1001;
        public static final int MAXLIMITREG2 = 1002;
        public static final int MAXLIMITREG3 = 1003;
        public static final int MAXLIMITREG4 = 1004;
        public static final int MAXLIMITREG5 = 1005;
        public static final int MAXLIMITREG6 = 1006;
        public static final int MAXLIMITREG7 = 1007;

        public static final int OUTPUTVAL0REG0 = 1100;
        public static final int OUTPUTVAL0REG1 = 1101;
        public static final int OUTPUTVAL0REG2 = 1102;
        public static final int OUTPUTVAL0REG3 = 1103;
        public static final int OUTPUTVAL0REG4 = 1104;
        public static final int OUTPUTVAL0REG5 = 1105;
        public static final int OUTPUTVAL0REG6 = 1106;
        public static final int OUTPUTVAL0REG7 = 1107;

        public static final int OUTPUTVAL1REG0 = 1200;
        public static final int OUTPUTVAL1REG1 = 1201;
        public static final int OUTPUTVAL1REG2 = 1202;
        public static final int OUTPUTVAL1REG3 = 1203;
        public static final int OUTPUTVAL1REG4 = 1204;
        public static final int OUTPUTVAL1REG5 = 1205;
        public static final int OUTPUTVAL1REG6 = 1206;
        public static final int OUTPUTVAL1REG7 = 1207;
    }

    /** Counter Gate Control */
    public static final class CounterGateCtrl {
        private CounterGateCtrl() {
        }

        public static final int NOGATE = 0;
        public static final int AHLTCPREVCTR = 1;
        public static final int AHLNEXTGATE = 2;
        public static final int AHLPREVGATE = 3;
        public static final int AHLGATE = 4;
        public static final int ALLGATE = 5;
        public static final int AHEGATE = 6;
        public static final int ALEGATE = 7;
    }

    /** 7266 Counter Quadrature values */
    public static final class Counter7266Quadrature {
        private Counter7266Quadrature() {
        }

        public static final int NO_QUAD = 0;
        public static final int X1_QUAD = 1;
        public static final int X2_QUAD = 2;
        public static final int X4_QUAD = 4;
    }

    /** 7266 Counter Counting Modes */
    public static final class Counter7266CountMode {
        private Counter7266CountMode() {
        }

        public static final int NORMAL_MODE = 0;
        public static final int RANGE_LIMIT = 1;
        public static final int NO_RECYCLE = 2;
        public static final int MODULO_N = 3;
    }

    /* 7266 Counter encodings */
    public static final int BCD_ENCODING = 1;
    public static final int BINARY_ENCODING = 2;

    /** 7266 Counter Index Modes */
    public static final class Counter7266IdxMode {
        private Counter7266IdxMode() {
        }

        public static final int INDEX_DISABLED = 0;
        public static final int LOAD_CTR = 1;
        public static final int LOAD_OUT_LATCH = 2;
        public static final int RESET_CTR = 3;
    }

    /** 7266 Counter Flag Pins */
    public static final class Counter7266FlagPin {
        private Counter7266FlagPin() {
        }

        public static final int CARRY_BORROW = 1;
        public static final int COMPARE_BORROW = 2;
        public static final int CARRYBORROW_UPDOWN = 3;
        public static final int INDEX_ERROR = 4;
    }

    /** Counter status bits */
    public static final class CounterStatus {
        private CounterStatus() {
        }

        public static final int C_UNDERFLOW = 0x0001;
        public static final int C_OVERFLOW = 0x0002;
        public static final int C_COMPARE = 0x0004;
        public static final int C_SIGN = 0x0008;
        public static final int C_ERROR = 0x0010;
        public static final int C_UP_DOWN = 0x0020;
        public static final int C_INDEX = 0x0040;
    }

    /** Scan counter mode constants */
    public static final class ScanCounterMode {
        private ScanCounterMode() {
        }

        public static final int TOTALIZE = 0x0000;
        public static final int CLEAR_ON_READ = 0x0001;
        public static final int ROLLOVER = 0x0000;
        public static final int STOP_AT_MAX = 0x0002;
        public static final int DECREMENT_OFF = 0x0000;
        public static final int DECREMENT_ON = 0x0020;
        public static final int BIT_16 = 0x0000;
        public static final int BIT_32 = 0x0004;
        public static final int BIT_48 = 0x10000;
        public static final int GATING_OFF = 0x0000;
        public static final int GATING_ON = 0x0010;
        public static final int LATCH_ON_SOS = 0x0000;
        public static final int LATCH_ON_MAP = 0x0008;
        public static final int UPDOWN_OFF = 0x0000;
        public static final int UPDOWN_ON = 0x1000;
        public static final int RANGE_LIMIT_OFF = 0x0000;
        public static final int RANGE_LIMIT_ON = 0x2000;
        public static final int NO_RECYCLE_OFF = 0x0000;
        public static final int NO_RECYCLE_ON = 0x4000;
        public static final int MODULO_N_OFF = 0x0000;
        public static final int MODULO_N_ON = 0x8000;
        public static final int COUNT_DOWN_OFF = 0x00000;
        public static final int COUNT_DOWN_ON = 0x10000;
        public static final int INVERT_GATE = 0x20000;
        public static final int GATE_CONTROLS_DIR = 0x40000;
        public static final int GATE_CLEARS_CTR = 0x80000;
        public static final int GATE_TRIG_SRC = 0x100000;
        public static final int OUTPUT_ON = 0x200000;
        public static final int OUTPUT_INITIAL_STATE_LOW = 0x000000;
        public static final int OUTPUT_INITIAL_STATE_HIGH = 0x400000;

        public static final int PERIOD = 0x0200;
        public static final int PERIOD_MODE_X1 = 0x0000;
        public static final int PERIOD_MODE_X10 = 0x0001;
        public static final int PERIOD_MODE_X100 = 0x0002;
        public static final int PERIOD_MODE_X1000 = 0x0003;
        public static final int PERIOD_MODE_BIT_16 = 0x0000;
        public static final int PERIOD_MODE_BIT_32 = 0x0004;
        public static final int PERIOD_MODE_BIT_48 = 0x10000;
        public static final int PERIOD_MODE_GATING_ON = 0x0010;
        public static final int PERIOD_MODE_INVERT_GATE = 0x20000;

        public static final int PULSEWIDTH = 0x0300;
        public static final int PULSEWIDTH_MODE_BIT_16 = 0x0000;
        public static final int PULSEWIDTH_MODE_BIT_32 = 0x0004;
        public static final int PULSEWIDTH_MODE_BIT_48 = 0x10000;
        public static final int PULSEWIDTH_MODE_GATING_ON = 0x0010;
        public static final int PULSEWIDTH_MODE_INVERT_GATE = 0x20000;

        public static final int TIMING = 0x0400;
        public static final int TIMING_MODE_BIT_16 = 0x0000;
        public static final int TIMING_MODE_BIT_32 = 0x0004;
        public static final int TIMING_MODE_BIT_48 = 0x10000;
        public static final int TIMING_MODE_INVERT_GATE = 0x20000;

        public static final int ENCODER = 0x0500;
        public static final int ENCODER_MODE_X1 = 0x0000;
        public static final int ENCODER_MODE_X2 = 0x0001;
        public static final int ENCODER_MODE_X4 = 0x0002;
        public static final int ENCODER_MODE_BIT_16 = 0x0000;
        public static final int ENCODER_MODE_BIT_32 = 0x0004;
        public static final int ENCODER_MODE_BIT_48 = 0x10000;
        public static final int ENCODER_MODE_LATCH_ON_Z = 0x0008;
        public static final int ENCODER_MODE_CLEAR_ON_Z_OFF = 0x0000;
        public static final int ENCODER_MODE_CLEAR_ON_Z_ON = 0x0020;
        public static final int ENCODER_MODE_RANGE_LIMIT_OFF = 0x0000;
        public static final int ENCODER_MODE_RANGE_LIMIT_ON = 0x2000;
        public static final int ENCODER_MODE_NO_RECYCLE_OFF = 0x0000;
        public static final int ENCODER_MODE_NO_RECYCLE_ON = 0x4000;
        public static final int ENCODER_MODE_MODULO_N_OFF = 0x0000;
        public static final int ENCODER_MODE_MODULO_N_ON = 0x8000;

        // deprecated encoder mode constants, use preferred constants above.
        @Deprecated
        public static final int LATCH_ON_Z = 0x0008;
        @Deprecated
        public static final int CLEAR_ON_Z_OFF = 0x0000;
        @Deprecated
        public static final int CLEAR_ON_Z_ON = 0x0020;
    }

    /** 25xx series counter debounce time constants */
    public static final class Counter25xxDebounce {
        private Counter25xxDebounce() {
        }

        public static final int CTR_DEBOUNCE500ns = 0;
        public static final int CTR_DEBOUNCE1500ns = 1;
        public static final int CTR_DEBOUNCE3500ns = 2;
        public static final int CTR_DEBOUNCE7500ns = 3;
        public static final int CTR_DEBOUNCE15500ns = 4;
        public static final int CTR_DEBOUNCE31500ns = 5;
        public static final int CTR_DEBOUNCE63500ns = 6;
        public static final int CTR_DEBOUNCE127500ns = 7;
        public static final int CTR_DEBOUNCE100us = 8;
        public static final int CTR_DEBOUNCE300us = 9;
        public static final int CTR_DEBOUNCE700us = 10;
        public static final int CTR_DEBOUNCE1500us = 11;
        public static final int CTR_DEBOUNCE3100us = 12;
        public static final int CTR_DEBOUNCE6300us = 13;
        public static final int CTR_DEBOUNCE12700us = 14;
        public static final int CTR_DEBOUNCE25500us = 15;
        public static final int CTR_DEBOUNCE_NONE = 16;
    }

    /* 25xx series counter debounce trigger constants */
    public static final int CTR_TRIGGER_AFTER_STABLE = 0;
    public static final int CTR_TRIGGER_BEFORE_STABLE = 1;

    /* 25xx series counter edge detection constants */
    public static final int CTR_RISING_EDGE = 0;
    public static final int CTR_FALLING_EDGE = 1;

    /** 25xx series counter tick size constants */
    public static final class Counter25xxTickSize {
        private Counter25xxTickSize() {
        }

        public static final int CTR_TICK20PT83ns = 0;
        public static final int CTR_TICK208PT3ns = 1;
        public static final int CTR_TICK2083PT3ns = 2;
        public static final int CTR_TICK20833PT3ns = 3;

        public static final int CTR_TICK20ns = 10;
        public static final int CTR_TICK200ns = 11;
        public static final int CTR_TICK2000ns = 12;
        public static final int CTR_TICK20000ns = 13;
    }

    /** Types of triggers */
    public static final class TriggerType {
        private TriggerType() {
        }

        public static final int TRIGABOVE = 0;
        public static final int TRIGBELOW = 1;
        public static final int GATE_NEG_HYS = 2;
        public static final int GATE_POS_HYS = 3;
        public static final int GATE_ABOVE = 4;
        public static final int GATE_BELOW = 5;
        public static final int GATE_IN_WINDOW = 6;
        public static final int GATE_OUT_WINDOW = 7;
        public static final int GATE_HIGH = 8;
        public static final int GATE_LOW = 9;
        public static final int TRIG_HIGH = 10;
        public static final int TRIG_LOW = 11;
        public static final int TRIG_POS_EDGE = 12;
        public static final int TRIG_NEG_EDGE = 13;
        public static final int TRIG_RISING = 14;
        public static final int TRIG_FALLING = 15;
        public static final int TRIG_PATTERN_EQ = 16;
        public static final int TRIG_PATTERN_NE = 17;
        public static final int TRIG_PATTERN_ABOVE = 18;
        public static final int TRIG_PATTERN_BELOW = 19;
    }

    /* External Pacer Edge */
    public static final int EXT_PACER_EDGE_RISING = 1;
    public static final int EXT_PACER_EDGE_FALLING = 2;

    /* Timer idle state */
    public static final int IDLE_LOW = 0;
    public static final int IDLE_HIGH = 1;

    /** Signal I/O Configuration Parameters */
    public static final class SignalIOConfig {
        private SignalIOConfig() {
        }

        /* --Connections */
        public static final int AUXIN0 = 0x01;
        public static final int AUXIN1 = 0x02;
        public static final int AUXIN2 = 0x04;
        public static final int AUXIN3 = 0x08;
        public static final int AUXIN4 = 0x10;
        public static final int AUXIN5 = 0x20;
        public static final int AUXOUT0 = 0x0100;
        public static final int AUXOUT1 = 0x0200;
        public static final int AUXOUT2 = 0x0400;

        public static final int DS_CONNECTOR = 0x01000;

        /** maximum number connections per output signal type */
        public static final int MAX_CONNECTIONS = 4;

        /* --Signal Types */
        public static final int ADC_CONVERT = 0x0001;
        public static final int ADC_GATE = 0x0002;
        public static final int ADC_START_TRIG = 0x0004;
        public static final int ADC_STOP_TRIG = 0x0008;
        public static final int ADC_TB_SRC = 0x0010;
        public static final int ADC_SCANCLK = 0x0020;
        public static final int ADC_SSH = 0x0040;
        public static final int ADC_STARTSCAN = 0x0080;
        public static final int ADC_SCAN_STOP = 0x0100;

        public static final int DAC_UPDATE = 0x0200;
        public static final int DAC_TB_SRC = 0x0400;
        public static final int DAC_START_TRIG = 0x0800;

        public static final int SYNC_CLK = 0x1000;

        public static final int CTR1_CLK = 0x2000;
        public static final int CTR2_CLK = 0x4000;

        public static final int DGND = 0x8000;

        /* -- Signal Direction */
        public static final int SIGNAL_IN = 2;
        public static final int SIGNAL_OUT = 4;

        /* -- Signal Polarity */
        public static final int INVERTED = 1;
        public static final int NONINVERTED = 0;
    }

    /** Types of configuration information */
    public static final class ConfigInfo {
        private ConfigInfo() {
        }

        public static final int GLOBALINFO = 1;
        public static final int BOARDINFO = 2;
        public static final int DIGITALINFO = 3;
        public static final int COUNTERINFO = 4;
        public static final int EXPANSIONINFO = 5;
        public static final int MISCINFO = 6;
        public static final int EXPINFOARRAY = 7;
        public static final int MEMINFO = 8;

        /* Types of global configuration information */
        /** Config file format version number */
        public static final int GIVERSION = 36;
        /** Maximum number of boards */
        public static final int GINUMBOARDS = 38;
        /** Maximum number of expansion boards */
        public static final int GINUMEXPBOARDS = 40;

        /* Types of board configuration information */
        /** Base Address */
        public static final int BIBASEADR = 0;
        /** Board Type (0x101 - 0x7FFF) */
        public static final int BIBOARDTYPE = 1;
        /** Interrupt level */
        public static final int BIINTLEVEL = 2;
        /** DMA channel */
        public static final int BIDMACHAN = 3;
        /** TRUE or FALSE */
        public static final int BIINITIALIZED = 4;
        /** Clock freq (1, 10 or bus) */
        public static final int BICLOCK = 5;
        /** Switch selectable range */
        public static final int BIRANGE = 6;
        /** Number of A/D channels */
        public static final int BINUMADCHANS = 7;
        /** Supports expansion boards TRUE/FALSE */
        public static final int BIUSESEXPS = 8;
        /** Number of digital devices */
        public static final int BIDINUMDEVS = 9;
        /** Index into digital information */
        public static final int BIDIDEVNUM = 10;
        /** Number of counter devices */
        public static final int BICINUMDEVS = 11;
        /** Index into counter information */
        public static final int BICIDEVNUM = 12;
        /** Number of D/A channels */
        public static final int BINUMDACHANS = 13;
        /** Wait state enabled TRUE/FALSE */
        public static final int BIWAITSTATE = 14;
        /** I/O address space used by board */
        public static final int BINUMIOPORTS = 15;
        /** Board number of parent board */
        public static final int BIPARENTBOARD = 16;
        /** Board number of connected DT board */
        public static final int BIDTBOARD = 17;
        /** Number of EXP boards installed */
        public static final int BINUMEXPS = 18;

        /* NEW CONFIG ITEMS for 32 bit library */
        /** NO-OP return no data and returns DEVELOPMENT_OPTION error code */
        public static final int BINOITEM = 99;
        /** DAC sample and hold jumper state */
        public static final int BIDACSAMPLEHOLD = 100;
        /** DIO enable */
        public static final int BIDIOENABLE = 101;
        /** DAS16-330 operation mode (ENHANCED/COMPATIBLE) */
        public static final int BI330OPMODE = 102;
        /** 9513 HD CTR source (DevNo = ctr no.) */
        public static final int BI9513CHIPNSRC = 103;
        /** CTR 0 source */
        public static final int BICTR0SRC = 104;
        /** CTR 1 source */
        public static final int BICTR1SRC = 105;
        /** CTR 2 source */
        public static final int BICTR2SRC = 106;
        /** Pacer CTR 0 source */
        public static final int BIPACERCTR0SRC = 107;
        /** DAC 0 voltage reference */
        public static final int BIDAC0VREF = 108;
        /** DAC 1 voltage reference */
        public static final int BIDAC1VREF = 109;
        /** P2 interrupt for CTR10 and CTR20HD */
        public static final int BIINTP2LEVEL = 110;
        /** Wait state 2 */
        public static final int BIWAITSTATEP2 = 111;
        /** DAS1600 Polarity state(UNI/BI) */
        public static final int BIADPOLARITY = 112;
        /** DAS1600 trigger edge(RISING/FALLING) */
        public static final int BITRIGEDGE = 113;
        /** DAC Range (DevNo is channel) */
        public static final int BIDACRANGE = 114;
        /** DAC Update (INDIVIDUAL/SIMULTANEOUS) (DevNo) */
        public static final int BIDACUPDATE = 115;
        /** DAC Installed */
        public static final int BIDACINSTALLED = 116;
        /** AD Config (SE/DIFF) (DevNo) */
        public static final int BIADCFG = 117;
        /** AD Input Mode (Voltage/Current) */
        public static final int BIADINPUTMODE = 118;
        /** DAC Startup state (UNI/BI) */
        public static final int BIDACPOLARITY = 119;
        /** DAS-TEMP Mode (NORMAL/CALIBRATE) */
        public static final int BITEMPMODE = 120;
        /** DAS-TEMP reject frequency */
        public static final int BITEMPREJFREQ = 121;
        /** DISO48 line filter (EN/DIS) (DevNo) */
        public static final int BIDISOFILTER = 122;
        /** INT32 Intr Src */
        public static final int BIINT32SRC = 123;
        /** INT32 Intr Priority */
        public static final int BIINT32PRIORITY = 124;
        /** MEGA-FIFO module size */
        public static final int BIMEMSIZE = 125;
        /** MEGA-FIFO # of modules */
        public static final int BIMEMCOUNT = 126;
        /** PPIO series printer port */
        public static final int BIPRNPORT = 127;
        /** PPIO series printer port delay */
        public static final int BIPRNDELAY = 128;
        /** PPIO digital line I/O state */
        public static final int BIPPIODIO = 129;
        /** CTR 3 source */
        public static final int BICTR3SRC = 130;
        /** CTR 4 source */
        public static final int BICTR4SRC = 131;
        /** CTR 5 source */
        public static final int BICTR5SRC = 132;
        /** PCM-D24/CTR3 interrupt source */
        public static final int BICTRINTSRC = 133;
        /** PCM-D24/CTR3 ctr linking */
        public static final int BICTRLINKING = 134;
        /** SBX #0 board number */
        public static final int BISBX0BOARDNUM = 135;
        /** SBX #0 address */
        public static final int BISBX0ADDRESS = 136;
        /** SBX #0 DMA channel */
        public static final int BISBX0DMACHAN = 137;
        /** SBX #0 Int Level 0 */
        public static final int BISBX0INTLEVEL0 = 138;
        /** SBX #0 Int Level 1 */
        public static final int BISBX0INTLEVEL1 = 139;
        /** SBX #0 board number */
        public static final int BISBX1BOARDNUM = 140;
        /** SBX #0 address */
        public static final int BISBX1ADDRESS = 141;
        /** SBX #0 DMA channel */
        public static final int BISBX1DMACHAN = 142;
        /** SBX #0 Int Level 0 */
        public static final int BISBX1INTLEVEL0 = 143;
        /** SBX #0 Int Level 1 */
        public static final int BISBX1INTLEVEL1 = 144;
        /** SBX Bus width */
        public static final int BISBXBUSWIDTH = 145;
        /** DAS08/Jr Cal factor */
        public static final int BICALFACTOR1 = 146;
        /** DAS08/Jr Cal factor */
        public static final int BICALFACTOR2 = 147;
        /** PCI-DAS1602 Dac trig edge */
        public static final int BIDACTRIG = 148;
        /** 801/802 chan config (devno =ch) */
        public static final int BICHANCFG = 149;
        /** 422 protocol */
        public static final int BIPROTOCOL = 150;
        /** dual 422 2nd address */
        public static final int BICOMADDR2 = 151;
        /** dual 422 cts/rts1 */
        public static final int BICTSRTS1 = 152;
        /** dual 422 cts/rts2 */
        public static final int BICTSRTS2 = 153;
        /** pcm com 422 ctrl lines */
        public static final int BICTRLLINES = 154;
        /** Wait state P1 */
        public static final int BIWAITSTATEP1 = 155;
        /** P1 interrupt for CTR10 and CTR20HD */
        public static final int BIINTP1LEVEL = 156;
        /** CTR 6 source */
        public static final int BICTR6SRC = 157;
        /** CTR 7 source */
        public static final int BICTR7SRC = 158;
        /** CTR 8 source */
        public static final int BICTR8SRC = 159;
        /** CTR 9 source */
        public static final int BICTR9SRC = 160;
        /** CTR 10 source */
        public static final int BICTR10SRC = 161;
        /** CTR 11 source */
        public static final int BICTR11SRC = 162;
        /** CTR 12 source */
        public static final int BICTR12SRC = 163;
        /** CTR 13 source */
        public static final int BICTR13SRC = 164;
        /** CTR 14 source */
        public static final int BICTR14SRC = 165;
        /** DASTC global average */
        public static final int BITCGLOBALAVG = 166;
        /** DASTC CJC State(=ON or OFF) */
        public static final int BITCCJCSTATE = 167;
        /** DASTC Channel Gain */
        public static final int BITCCHANRANGE = 168;
        /** DASTC Channel thermocouple type */
        public static final int BITCCHANTYPE = 169;
        /** DASTC Firmware Version */
        public static final int BITCFWVERSION = 170;
        public static final int BIFWVERSION = BITCFWVERSION; /* Firmware Version */
        /** Quad PhaseA config (devNo =ch) */
        public static final int BIPHACFG = 180;
        /** Quad PhaseB config (devNo =ch) */
        public static final int BIPHBCFG = 190;
        /** Quad Index Ref config (devNo =ch) */
        public static final int BIINDEXCFG = 200;
        /** PCI/PCM card slot number */
        public static final int BISLOTNUM = 201;
        /** analog input wave type (for demo board) */
        public static final int BIAIWAVETYPE = 202;
        /** DDA06 pwr up state jumper */
        public static final int BIPWRUPSTATE = 203;
        /** DAS08 pin6 to 24 jumper */
        public static final int BIIRQCONNECT = 204;
        /** PCM DAS16xx Trig Polarity */
        public static final int BITRIGPOLARITY = 205;
        /** MetraBus controller board number */
        public static final int BICTLRNUM = 206;
        /** MetraBus controller board Pwr jumper */
        public static final int BIPWRJMPR = 207;
        /** Number of Temperature channels */
        public static final int BINUMTEMPCHANS = 208;
        /** A/D trigger source */
        public static final int BIADTRIGSRC = 209;
        /** BNC source */
        public static final int BIBNCSRC = 210;
        /** BNC Threshold 2.5V or 0.0V */
        public static final int BIBNCTHRESHOLD = 211;
        /** Board supports BURSTMODE */
        public static final int BIBURSTMODE = 212;
        /** A/D Dithering enabled */
        public static final int BIDITHERON = 213;
        /** Serial Number for USB boards */
        public static final int BISERIALNUM = 214;
        /** Update immediately or upon AOUPDATE command */
        public static final int BIDACUPDATEMODE = 215;
        /** Issue D/A UPDATE command */
        public static final int BIDACUPDATECMD = 216;
        /** Store last value written for startup */
        public static final int BIDACSTARTUP = 217;
        /** Number of samples to acquire per trigger in retrigger mode */
        public static final int BIADTRIGCOUNT = 219;
        /** Set FIFO override size for retrigger mode */
        public static final int BIADFIFOSIZE = 220;
        /** Set source to internal reference or external connector(-1) */
        public static final int BIADSOURCE = 221;
        /** CAL output pin setting */
        public static final int BICALOUTPUT = 222;
        /** Source A/D Pacer output */
        public static final int BISRCADPACER = 223;
        /** Manufacturers 8-byte serial number */
        public static final int BIMFGSERIALNUM = 224;
        /** Revision Number stored in PCI header */
        public static final int BIPCIREVID = 225;
        public static final int BIEXTCLKTYPE = 227;
        public static final int BIDIALARMMASK = 230;

        public static final int BINETIOTIMEOUT = 247;
        public static final int BIADCHANAIMODE = 249;
        public static final int BIDACFORCESENSE = 250;

        /** Sync mode */
        public static final int BISYNCMODE = 251;

        public static final int BICALTABLETYPE = 254;
        /** Digital inputs reset state */
        public static final int BIDIDEBOUNCESTATE = 255;
        /** Digital inputs debounce Time */
        public static final int BIDIDEBOUNCETIME = 256;

        public static final int BIPANID = 258;
        public static final int BIRFCHANNEL = 259;

        public static final int BIRSS = 261;
        public static final int BINODEID = 262;
        public static final int BIDEVNOTES = 263;
        public static final int BIINTEDGE = 265;

        public static final int BIADCSETTLETIME = 270;

        public static final int BIFACTORYID = 272;
        public static final int BIHTTPPORT = 273;
        public static final int BIHIDELOGINDLG = 274;
        public static final int BITEMPSCALE = 280;
        /** Number of samples to generate per trigger in retrigger mode */
        public static final int BIDACTRIGCOUNT = 284;
        public static final int BIADTIMINGMODE = 285;
        public static final int BIRTDCHANTYPE = 286;

        public static final int BIADRES = 291;
        public static final int BIDACRES = 292;

        public static final int BIADXFERMODE = 306;
        public static final int BICTRTRIGCOUNT = 307;
        public static final int BIDAQITRIGCOUNT = 308;
        public static final int BINETCONNECTCODE = 341;
        /** Number of digital input samples to acquire per trigger */
        public static final int BIDITRIGCOUNT = 343;
        /** Number of digital output samples to generate per trigger */
        public static final int BIDOTRIGCOUNT = 344;
        public static final int BIPATTERNTRIGPORT = 345;
        /** Channel thermocouple type */
        public static final int BICHANTCTYPE = 347;
        public static final int BIEXTINPACEREDGE = 348;
        public static final int BIEXTOUTPACEREDGE = 349;
        /** Enable/Disable input Pacer output */
        public static final int BIINPUTPACEROUT = 350;
        /** Enable/Disable output Pacer output */
        public static final int BIOUTPUTPACEROUT = 351;
        public static final int BITEMPAVG = 352;
        public static final int BIEXCITATION = 353;
        public static final int BICHANBRIDGETYPE = 354;
        public static final int BIADCHANTYPE = 355;
        public static final int BICHANRTDTYPE = 356;
        /** Unique identifier of DAQ device */
        public static final int BIDEVUNIQUEID = 357;
        public static final int BIUSERDEVID = 358;
        public static final int BIDEVVERSION = 359;
        public static final int BITERMCOUNTSTATBIT = 360;
        public static final int BIDETECTOPENTC = 361;
        public static final int BIADDATARATE = 362;
        public static final int BIDEVSERIALNUM = 363;
        public static final int BIDEVMACADDR = 364;
        public static final int BIUSERDEVIDNUM = 365;
        public static final int BIADAIMODE = 373;

        /* Type of digital device information */
        /** Base address */
        public static final int DIBASEADR = 0;
        /** TRUE or FALSE */
        public static final int DIINITIALIZED = 1;
        /** AUXPORT or xPORTA - CH */
        public static final int DIDEVTYPE = 2;
        /** Bit mask for this port */
        public static final int DIMASK = 3;
        /** Read required before write */
        public static final int DIREADWRITE = 4;
        /** Current configuration */
        public static final int DICONFIG = 5;
        /** Number of bits in port */
        public static final int DINUMBITS = 6;
        /** Current value of outputs */
        public static final int DICURVAL = 7;
        /** Input bit mask for port */
        public static final int DIINMASK = 8;
        /** Output bit mask for port */
        public static final int DIOUTMASK = 9;
        /**
         * Disables checking port/bit direction in cbDOut and cbDBitOut
         * functions
         */
        public static final int DIDISABLEDIRCHECK = 13; 

        /* Types of counter device information */
        /** Base address */
        public static final int CIBASEADR = 0;
        /** TRUE or FALSE */
        public static final int CIINITIALIZED = 1;
        /** Counter type 8254, 9513 or 8536 */
        public static final int CICTRTYPE = 2;
        /** Which counter on chip */
        public static final int CICTRNUM = 3;
        /** Configuration byte */
        public static final int CICONFIGBYTE = 4;

        /* Types of expansion board information */
        /** Board type */
        public static final int XIBOARDTYPE = 0;
        /** 0 - 7 */
        public static final int XIMUX_AD_CHAN1 = 1;
        /** 0 - 7 or NOTUSED */
        public static final int XIMUX_AD_CHAN2 = 2;
        /** Range (gain) of low 16 chans */
        public static final int XIRANGE1 = 3;
        /** Range (gain) of high 16 chans */
        public static final int XIRANGE2 = 4;
        /** TYPE_8254_CTR or TYPE_9513_CTR */
        public static final int XICJCCHAN = 5;
        /** TYPEJ, TYPEK, TYPET, TYPEE, TYPER, or TYPES */
        public static final int XITHERMTYPE = 6;
        /** Number of expansion channels on board */
        public static final int XINUMEXPCHANS = 7;
        /** Board number of parent A/D board */
        public static final int XIPARENTBOARD = 8;
        /** 16 words of misc options */
        public static final int XISPARE0 = 9;

        /** ICAL DATA - 5 volt source */
        public static final int XI5VOLTSOURCE = 100;
        /** exp Data - chan config 2/4 or 3-wire devNo=chan */
        public static final int XICHANCONFIG = 101;
        /** ICAL DATA - voltage source */
        public static final int XIVSOURCE = 102;
        /** ICAL Data - voltage select */
        public static final int XIVSELECT = 103;
        /** exp Data - individual ch gain */
        public static final int XICHGAIN = 104;
        /** ICAL DATA - exp grounding */
        public static final int XIGND = 105;
        /** ICAL DATA - Vexe A/D chan */
        public static final int XIVADCHAN = 106;
        /** exp Data - resistance @0 (devNo =ch) */
        public static final int XIRESISTANCE = 107;
        /** ICAL DATA - RTD factory gain */
        public static final int XIFACGAIN = 108;
        /** ICAL DATA - RTD custom gain */
        public static final int XICUSTOMGAIN = 109;
        /** ICAL DATA - RTD custom gain setting */
        public static final int XICHCUSTOM = 110;
        /** ICAL DATA - RTD Iexe */
        public static final int XIIEXE = 111;

        /* Types of memory board information */
        /** mem data - base address */
        public static final int MIBASEADR = 100;
        /** mem data - intr level */
        public static final int MIINTLEVEL = 101;
        /** MEGA-FIFO module size */
        public static final int MIMEMSIZE = 102;
        /** MEGA-FIFO # of modules */
        public static final int MIMEMCOUNT = 103;
    }

    /** AI channel Types */
    public static final class AIChannelType {
        private AIChannelType() {
        }

        public static final int AI_CHAN_TYPE_VOLTAGE = 0;
        public static final int AI_CHAN_TYPE_CURRENT = 100;
        public static final int AI_CHAN_TYPE_RESISTANCE_10K4W = 201;
        public static final int AI_CHAN_TYPE_RESISTANCE_1K4W = 202;
        public static final int AI_CHAN_TYPE_RESISTANCE_10K2W = 203;
        public static final int AI_CHAN_TYPE_RESISTANCE_1K2W = 204;
        public static final int AI_CHAN_TYPE_TC = 300;
        public static final int AI_CHAN_TYPE_RTD_1000OHM_4W = 401;
        public static final int AI_CHAN_TYPE_RTD_100OHM_4W = 402;
        public static final int AI_CHAN_TYPE_RTD_1000OHM_3W = 403;
        public static final int AI_CHAN_TYPE_RTD_100OHM_3W = 404;
        public static final int AI_CHAN_TYPE_QUART_BRIDGE_350OHM = 501;
        public static final int AI_CHAN_TYPE_QUART_BRIDGE_120OHM = 502;
        public static final int AI_CHAN_TYPE_HALF_BRIDGE = 503;
        public static final int AI_CHAN_TYPE_FULL_BRIDGE_62PT5mVV = 504;
        public static final int AI_CHAN_TYPE_FULL_BRIDGE_7PT8mVV = 505;
    }

    /** Thermocouple Types */
    public static final class ThermocoupleType {
        private ThermocoupleType() {
        }

        public static final int TC_TYPE_J = 1;
        public static final int TC_TYPE_K = 2;
        public static final int TC_TYPE_T = 3;
        public static final int TC_TYPE_E = 4;
        public static final int TC_TYPE_R = 5;
        public static final int TC_TYPE_S = 6;
        public static final int TC_TYPE_B = 7;
        public static final int TC_TYPE_N = 8;
    }

    /** Bridge Types */
    public static final class BridgeType {
        private BridgeType() {
        }

        public static final int BRIDGE_FULL = 1;
        public static final int BRIDGE_HALF = 2;
        public static final int BRIDGE_QUARTER = 3;
    }

    /** Platinum RTD Types */
    public static final class RTDType {
        private RTDType() {
        }

        public static final int RTD_CUSTOM = 0x00;
        public static final int RTD_PT_3750 = 0x01;
        public static final int RTD_PT_3851 = 0x02;
        public static final int RTD_PT_3911 = 0x03;
        public static final int RTD_PT_3916 = 0x04;
        public static final int RTD_PT_3920 = 0x05;
        public static final int RTD_PT_3928 = 0x06;
        public static final int RTD_PT_3850 = 0x07;
    }

    /** Version types */
    public static final class VersionType {
        private VersionType() {
        }

        public static final int VER_FW_MAIN = 0;
        public static final int VER_FW_MEASUREMENT = 1;
        public static final int VER_FW_RADIO = 2;
        public static final int VER_FPGA = 3;
        public static final int VER_FW_MEASUREMENT_EXP = 4;
    }

    /** Types of events */
    public static final class EventType {
        private EventType() {
        }

        public static final int ON_SCAN_ERROR = 0x0001;
        public static final int ON_EXTERNAL_INTERRUPT = 0x0002;
        public static final int ON_PRETRIGGER = 0x0004;
        public static final int ON_DATA_AVAILABLE = 0x0008;
        @Deprecated
        /** deprecated, use ON_END_OF_INPUT_SCAN */
        public static final int ON_END_OF_AI_SCAN = 0x0010;
        @Deprecated
        /** deprecated, use ON_END_OF_OUTPUT_SCAN */
        public static final int ON_END_OF_AO_SCAN = 0x0020;
        public static final int ON_END_OF_INPUT_SCAN = 0x0010;
        public static final int ON_END_OF_OUTPUT_SCAN = 0x0020;
        public static final int ON_CHANGE_DI = 0x0040;
        public static final int ALL_EVENT_TYPES = 0xffff;

        public static final int NUM_EVENT_TYPES = 6;
        public static final int MAX_NUM_EVENT_TYPES = 32;

        public static final int SCAN_ERROR_IDX = 0;
        public static final int EXTERNAL_INTERRUPT_IDX = 1;
        public static final int PRETRIGGER_IDX = 2;
        public static final int DATA_AVAILABLE_IDX = 3;
        public static final int END_OF_AI_IDX = 4;
        public static final int END_OF_AO_IDX = 5;
    }

    /* ON_EXTERNAL_INTERRUPT event parameters */
    public static final int LATCH_DI = 1;
    public static final int LATCH_DO = 2;

    // time zone constants
    public static final int TIMEZONE_LOCAL = 0;
    public static final int TIMEZONE_GMT = 1;

    // time format constants
    public static final int TIMEFORMAT_12HOUR = 0;
    public static final int TIMEFORMAT_24HOUR = 1;

    /** delimiter constants */
    public static final class Delimiter {
        private Delimiter() {
        }

        public static final int DELIMITER_COMMA = 0;
        public static final int DELIMITER_SEMICOLON = 1;
        public static final int DELIMITER_SPACE = 2;
        public static final int DELIMITER_TAB = 3;
    }

    // AI channel units in binary file
    public static final int UNITS_TEMPERATURE = 0;
    public static final int UNITS_RAW = 1;

    // Transfer Mode
    public static final int XFER_KERNEL = 0;
    public static final int XFER_USER = 1;

    // Clock type
    public static final int CONTINUOUS_CLK = 1;
    public static final int GATED_CLK = 2;

    // Calibration Table types
    public static final int CAL_TABLE_FACTORY = 0;
    public static final int CAL_TABLE_FIELD = 1;

    /**
     * Original signature :
     * <code>typedef void (__stdcall *EVENTCALLBACK)(int, unsigned, unsigned, void*)</code>
     */
    @FunctionalInterface
    interface EVENTCALLBACK extends StdCallCallback {
        void apply(int int1, int u1, int u2, Pointer voidPtr1);
    };

    /**
     * A functional interface defining a Java-friendly event callback.
     * 
     * @author Duncan Lowther (2402789L)
     */
    @FunctionalInterface
    public interface JavaCallback {
        void apply(int boardNum, int evtType, int data);
    }

    /**
     * Original signature :
     * <code>int cbACalibrateData(int, long, int, USHORT*)</code>
     */
    static native int cbACalibrateData(int BoardNum, NativeLong NumPoints, int Gain, ShortBuffer ADData);

    /**
     * Original signature : <code>int cbGetRevision(float*, float*)</code>
     */
    static native int cbGetRevision(FloatByReference RevNum, FloatByReference VxDRevNum);

    /**
     * Original signature : <code>int cbLoadConfig(char*)</code>
     */
    static native int cbLoadConfig(String CfgFileName);

    /**
     * Original signature : <code>int cbSaveConfig(char*)</code>
     */
    static native int cbSaveConfig(String CfgFileName);

    /**
     * Original signature :
     * <code>int cbAConvertData(int, long, USHORT*, USHORT*)</code>
     */
    static native int cbAConvertData(int BoardNum, NativeLong NumPoints, ShortBuffer ADData, ShortBuffer ChanTags);

    /**
     * Original signature :
     * <code>int cbAConvertPretrigData(int, long, long, USHORT*, USHORT*)</code>
     */
    static native int cbAConvertPretrigData(int BoardNum, NativeLong PreTrigCount, NativeLong TotalCount,
            ShortBuffer ADData, ShortBuffer ChanTags);

    /**
     * Original signature : <code>int cbAIn(int, int, int, USHORT*)</code>
     */
    static native int cbAIn(int BoardNum, int Chan, int Gain, ShortByReference DataValue);

    /**
     * Original signature : <code>int cbAIn32(int, int, int, ULONG*, int)</code>
     */
    static native int cbAIn32(int BoardNum, int Chan, int Gain, NativeLongByReference DataValue, int Options);

    /**
     * Original signature :
     * <code>int cbAInScan(int, int, int, long, long*, int, HGLOBAL, int)</code>
     */
    static native int cbAInScan(int BoardNum, int LowChan, int HighChan, NativeLong Count, NativeLongByReference Rate,
            int Gain, HGLOBAL MemHandle, int Options);

    /**
     * Original signature : <code>int cbALoadQueue(int, short*, short*, int)</code>
     */
    static native int cbALoadQueue(int BoardNum, ShortBuffer ChanArray, ShortBuffer GainArray, int NumChans);

    /**
     * Original signature : <code>int cbAOut(int, int, int, USHORT)</code>
     */
    static native int cbAOut(int BoardNum, int Chan, int Gain, short DataValue);

    /**
     * Original signature :
     * <code>int cbAOutScan(int, int, int, long, long*, int, HGLOBAL, int)</code>
     */
    static native int cbAOutScan(int BoardNum, int LowChan, int HighChan, NativeLong Count, NativeLongByReference Rate,
            int Gain, HGLOBAL MemHandle, int Options);

    /**
     * Original signature :
     * <code>int cbAPretrig(int, int, int, long*, long*, long*, int, HGLOBAL, int)</code>
     */
    static native int cbAPretrig(int BoardNum, int LowChan, int HighChan, NativeLongByReference PreTrigCount,
            NativeLongByReference TotalCount, NativeLongByReference Rate, int Gain, HGLOBAL MemHandle, int Options);

    /**
     * Original signature :
     * <code>int cbATrig(int, int, int, USHORT, int, USHORT*)</code>
     */
    static native int cbATrig(int BoardNum, int Chan, int TrigType, short TrigValue, int Gain,
            ShortByReference DataValue);

    /**
     * Original signature :
     * <code>int cbC7266Config(int, int, int, int, int, int, int, int, int)</code>
     */
    static native int cbC7266Config(int BoardNum, int CounterNum, int Quadrature, int CountingMode, int DataEncoding,
            int IndexMode, int InvertIndex, int FlagPins, int GateEnable);

    /**
     * Original signature : <code>int cbC8254Config(int, int, int)</code>
     */
    static native int cbC8254Config(int BoardNum, int CounterNum, int Config);

    /**
     * Original signature : <code>int cbC8536Config(int, int, int, int, int)</code>
     */
    static native int cbC8536Config(int BoardNum, int CounterNum, int OutputControl, int RecycleMode, int TrigType);

    /**
     * Original signature :
     * <code>int cbC9513Config(int, int, int, int, int, int, int, int, int, int, int)</code>
     */
    static native int cbC9513Config(int BoardNum, int CounterNum, int GateControl, int CounterEdge, int CountSource,
            int SpecialGate, int Reload, int RecycleMode, int BCDMode, int CountDirection, int OutputControl);

    /**
     * Original signature : <code>int cbC8536Init(int, int, int)</code>
     */
    static native int cbC8536Init(int BoardNum, int ChipNum, int Ctr1Output);

    /**
     * Original signature :
     * <code>int cbC9513Init(int, int, int, int, int, int, int)</code>
     */
    static native int cbC9513Init(int BoardNum, int ChipNum, int FOutDivider, int FOutSource, int Compare1,
            int Compare2, int TimeOfDay);

    /**
     * Original signature :
     * <code>int cbCFreqIn(int, int, int, USHORT*, long*)</code>
     */
    static native int cbCFreqIn(int BoardNum, int SigSource, int GateInterval, ShortByReference Count,
            NativeLongByReference Freq);

    /**
     * Original signature : <code>int cbCIn(int, int, USHORT*)</code>
     */
    static native int cbCIn(int BoardNum, int CounterNum, ShortByReference Count);

    /**
     * Original signature : <code>int cbCIn32(int, int, ULONG*)</code>
     */
    static native int cbCIn32(int BoardNum, int CounterNum, NativeLongByReference Count);

    /**
     * Original signature : <code>int cbCIn64(int, int, ULONGLONG*)</code>
     */
    static native int cbCIn64(int BoardNum, int CounterNum, LongByReference Count);

    /**
     * Original signature : <code>int cbCLoad(int, int, unsigned int)</code>
     */
    static native int cbCLoad(int BoardNum, int RegNum, int LoadValue);

    /**
     * Original signature : <code>int cbCLoad32(int, int, ULONG)</code>
     */
    static native int cbCLoad32(int BoardNum, int RegNum, NativeLong LoadValue);

    /**
     * Original signature : <code>int cbCLoad64(int, int, ULONGLONG)</code>
     */
    static native int cbCLoad64(int BoardNum, int RegNum, long LoadValue);

    /**
     * Original signature : <code>int cbCStatus(int, int, ULONG*)</code><br>
     * <i>native declaration : line 1610</i>
     */
    static native int cbCStatus(int BoardNum, int CounterNum, NativeLongByReference StatusBits);

    /**
     * Original signature :
     * <code>int cbCStoreOnInt(int, int, short*, HGLOBAL)</code>
     */
    static native int cbCStoreOnInt(int BoardNum, int IntCount, ShortBuffer CntrControl, HGLOBAL MemHandle);

    /**
     * Original signature :
     * <code>int cbCInScan(int, int, int, long, long*, HGLOBAL, ULONG)</code>
     */
    static native int cbCInScan(int BoardNum, int FirstCtr, int LastCtr, NativeLong Count, NativeLongByReference Rate,
            HGLOBAL MemHandle, NativeLong Options);

    /**
     * Original signature :
     * <code>int cbCConfigScan(int, int, int, int, int, int, int, int)</code>
     */
    static native int cbCConfigScan(int BoardNum, int CounterNum, int Mode, int DebounceTime, int DebounceMode,
            int EdgeDetection, int TickSize, int MappedChannel);

    /**
     * Original signature : <code>int cbCClear(int, int)</code>
     */
    static native int cbCClear(int BoardNum, int CounterNum);

    /**
     * Original signature : <code>int cbTimerOutStart(int, int, double*)</code>
     */
    static native int cbTimerOutStart(int BoardNum, int TimerNum, DoubleByReference Frequency);

    /**
     * Original signature : <code>int cbTimerOutStop(int, int)</code>
     */
    static native int cbTimerOutStop(int BoardNum, int TimerNum);

    /**
     * Original signature :
     * <code>int cbPulseOutStart(int, int, double*, double*, unsigned int, double*, int, int)</code>
     */
    static native int cbPulseOutStart(int BoardNum, int TimerNum, DoubleByReference Frequency,
            DoubleByReference DutyCycle, int PulseCount, DoubleByReference InitialDelay, int IdleState, int Options);

    /**
     * Original signature : <code>int cbPulseOutStop(int, int)</code>
     */
    static native int cbPulseOutStop(int BoardNum, int TimerNum);

    /**
     * Original signature : <code>int cbDBitIn(int, int, int, USHORT*)</code>
     */
    static native int cbDBitIn(int BoardNum, int PortType, int BitNum, ShortByReference BitValue);

    /**
     * Original signature : <code>int cbDBitOut(int, int, int, USHORT)</code>
     */
    static native int cbDBitOut(int BoardNum, int PortType, int BitNum, short BitValue);

    /**
     * Original signature : <code>int cbDConfigPort(int, int, int)</code>
     */
    static native int cbDConfigPort(int BoardNum, int PortType, int Direction);

    /**
     * Original signature : <code>int cbDConfigBit(int, int, int, int)</code>
     */
    static native int cbDConfigBit(int BoardNum, int PortType, int BitNum, int Direction);

    /**
     * Original signature : <code>int cbDIn(int, int, USHORT*)</code>
     */
    static native int cbDIn(int BoardNum, int PortType, ShortByReference DataValue);

    /**
     * Original signature : <code>int cbDIn32(int, int, UINT*)</code>
     */
    static native int cbDIn32(int BoardNum, int PortType, IntByReference DataValue);

    /**
     * Original signature :
     * <code>int cbDInScan(int, int, long, long*, HGLOBAL, int)</code>
     */
    static native int cbDInScan(int BoardNum, int PortType, NativeLong Count, NativeLongByReference Rate,
            HGLOBAL MemHandle, int Options);

    /**
     * Original signature : <code>int cbDOut(int, int, USHORT)</code>
     */
    static native int cbDOut(int BoardNum, int PortType, short DataValue);

    /**
     * Original signature : <code>int cbDOut32(int, int, UINT)</code>
     */
    static native int cbDOut32(int BoardNum, int PortType, int DataValue);

    /**
     * Original signature :
     * <code>int cbDOutScan(int, int, long, long*, HGLOBAL, int)</code>
     */
    static native int cbDOutScan(int BoardNum, int PortType, NativeLong Count, NativeLongByReference Rate,
            HGLOBAL MemHandle, int Options);

    /**
     * Original signature : <code>int cbDInArray(int, int, int, ULONG*)</code>
     */
    static native int cbDInArray(int BoardNum, int LowPort, int HighPort, int[] DataArray);

    /**
     * Original signature : <code>int cbDOutArray(int, int, int, ULONG*)</code>
     */
    static native int cbDOutArray(int BoardNum, int LowPort, int HighPort, int[] DataArray);

    /**
     * Original signature : <code>int cbDClearAlarm(int, int, UINT)</code>
     */
    static native int cbDClearAlarm(int BoardNum, int PortType, int Mask);

    /**
     * Original signature : <code>int cbErrHandling(int, int)</code>
     */
    static native int cbErrHandling(int ErrReporting, int ErrHandling);

    /**
     * Original signature :
     * <code>int cbFileAInScan(int, int, int, long, long*, int, char*, int)</code>
     */
    static native int cbFileAInScan(int BoardNum, int LowChan, int HighChan, NativeLong Count,
            NativeLongByReference Rate, int Gain, String FileName, int Options);

    /**
     * Original signature :
     * <code>int cbFileGetInfo(char*, short*, short*, long*, long*, long*, int*)</code>
     */
    static native int cbFileGetInfo(String FileName, ShortByReference LowChan, ShortByReference HighChan,
            NativeLongByReference PreTrigCount, NativeLongByReference TotalCount, NativeLongByReference Rate,
            IntByReference Gain);

    /**
     * Original signature :
     * <code>int cbFilePretrig(int, int, int, long*, long*, long*, int, char*, int)</code>
     */
    static native int cbFilePretrig(int BoardNum, int LowChan, int HighChan, NativeLongByReference PreTrigCount,
            NativeLongByReference TotalCount, NativeLongByReference Rate, int Gain, String FileName, int Options);

    /**
     * Original signature : <code>int cbFileRead(char*, long, long*, USHORT*)</code>
     */
    static native int cbFileRead(String FileName, NativeLong FirstPoint, NativeLongByReference NumPoints,
            ShortBuffer DataBuffer);

    /**
     * Original signature : <code>int cbFlashLED(int)</code>
     */
    static native int cbFlashLED(int BoardNum);

    /**
     * Original signature : <code>int cbGetErrMsg(int, char*)</code>
     */
    static native int cbGetErrMsg(int ErrCode, ByteBuffer msgBuf);

    /**
     * Original signature :
     * <code>int cbGetIOStatus(int, short*, long*, long*, int)</code>
     */
    static native int cbGetIOStatus(int BoardNum, ShortByReference Status, NativeLongByReference CurCount,
            NativeLongByReference CurIndex, int FunctionType);

    /**
     * Original signature : <code>int cbRS485(int, int, int)</code>
     */
    static native int cbRS485(int BoardNum, boolean Transmit, boolean Receive);

    /**
     * Original signature : <code>int cbStopIOBackground(int, int)</code>
     */
    static native int cbStopIOBackground(int BoardNum, int FunctionType);

    /**
     * Original signature : <code>int cbTIn(int, int, int, float*, int)</code>
     */
    static native int cbTIn(int BoardNum, int Chan, int Scale, FloatByReference TempValue, int Options);

    /**
     * Original signature :
     * <code>int cbTInScan(int, int, int, int, float*, int)</code>
     */
    static native int cbTInScan(int BoardNum, int LowChan, int HighChan, int Scale, FloatBuffer DataBuffer,
            int Options);

    /**
     * Original signature : <code>int cbMemSetDTMode(int, int)</code>
     */
    // XXX can't be found in dll??? static native int cbMemSetDTMode(int BoardNum,
    // int Mode);

    /**
     * Original signature : <code>int cbMemReset(int)</code>
     */
    static native int cbMemReset(int BoardNum);

    /**
     * Original signature : <code>int cbMemRead(int, USHORT*, long, long)</code>
     */
    static native int cbMemRead(int BoardNum, ShortBuffer DataBuffer, NativeLong FirstPoint, NativeLong Count);

    /**
     * Original signature : <code>int cbMemWrite(int, USHORT*, long, long)</code>
     */
    static native int cbMemWrite(int BoardNum, ShortBuffer DataBuffer, NativeLong FirstPoint, NativeLong Count);

    /**
     * Original signature :
     * <code>int cbMemReadPretrig(int, USHORT*, long, long)</code>
     */
    static native int cbMemReadPretrig(int BoardNum, ShortBuffer DataBuffer, NativeLong FirstPoint, NativeLong Count);

    /**
     * Original signature :
     * <code>int cbWinBufToArray(HGLOBAL, USHORT*, long, long)</code>
     */
    static native int cbWinBufToArray(HGLOBAL MemHandle, ShortBuffer DataArray, NativeLong FirstPoint,
            NativeLong Count);

    /**
     * Original signature :
     * <code>int cbWinBufToArray32(HGLOBAL, ULONG*, long, long)</code>
     */
    static native int cbWinBufToArray32(HGLOBAL MemHandle, IntBuffer DataArray, NativeLong FirstPoint,
            NativeLong Count);

    /**
     * Original signature :
     * <code>int cbWinBufToArray64(HGLOBAL, ULONGLONG*, long, long)</code>
     */
    static native int cbWinBufToArray64(HGLOBAL MemHandle, LongBuffer DataArray, NativeLong FirstPoint,
            NativeLong Count);

    /**
     * Original signature : <code>HGLOBAL cbScaledWinBufAlloc(long)</code><br>
     * <i>native declaration : line 1678</i>
     */
    static native HGLOBAL cbScaledWinBufAlloc(NativeLong NumPoints);

    /**
     * Original signature :
     * <code>int cbScaledWinBufToArray(HGLOBAL, double*, long, long)</code>
     */
    static native int cbScaledWinBufToArray(HGLOBAL MemHandle, DoubleBuffer DataArray, NativeLong FirstPoint,
            NativeLong Count);

    /**
     * Original signature :
     * <code>int cbWinArrayToBuf(USHORT*, HGLOBAL, long, long)</code>
     */
    static native int cbWinArrayToBuf(ShortBuffer DataArray, HGLOBAL MemHandle, NativeLong FirstPoint,
            NativeLong Count);

    /**
     * Original signature :
     * <code>int cbWinArrayToBuf32(ULONG*, HGLOBAL, long, long)</code>
     */
    static native int cbWinArrayToBuf32(IntBuffer DataArray, HGLOBAL MemHandle, NativeLong FirstPoint,
            NativeLong Count);

    /**
     * Original signature :
     * <code>int cbScaledWinArrayToBuf(double*, HGLOBAL, long, long)</code>
     */
    static native int cbScaledWinArrayToBuf(DoubleBuffer DataArray, HGLOBAL MemHandle, NativeLong FirstPoint,
            NativeLong Count);

    /**
     * Original signature : <code>HGLOBAL cbWinBufAlloc(long)</code>
     */
    static native HGLOBAL cbWinBufAlloc(NativeLong NumPoints);

    /**
     * Original signature : <code>HGLOBAL cbWinBufAlloc32(long)</code>
     */
    static native HGLOBAL cbWinBufAlloc32(NativeLong NumPoints);

    /**
     * Original signature : <code>HGLOBAL cbWinBufAlloc64(long)</code>
     */
    static native HGLOBAL cbWinBufAlloc64(NativeLong NumPoints);

    /**
     * Original signature : <code>int cbWinBufFree(HGLOBAL)</code>
     */
    static native int cbWinBufFree(HGLOBAL MemHandle);

    /**
     * Original signature : <code>int cbInByte(int, int)</code>
     */
    static native int cbInByte(int BoardNum, int PortNum);

    /**
     * Original signature : <code>int cbOutByte(int, int, int)</code>
     */
    static native int cbOutByte(int BoardNum, int PortNum, int PortVal);

    /**
     * Original signature : <code>int cbInWord(int, int)</code>
     */
    static native int cbInWord(int BoardNum, int PortNum);

    /**
     * Original signature : <code>int cbOutWord(int, int, int)</code>
     */
    static native int cbOutWord(int BoardNum, int PortNum, int PortVal);

    /**
     * Original signature : <code>int cbGetConfig(int, int, int, int, int*)</code>
     */
    static native int cbGetConfig(int InfoType, int BoardNum, int DevNum, int ConfigItem, IntByReference ConfigVal);

    /**
     * Original signature :
     * <code>int cbGetConfigString(int, int, int, int, char*, int*)</code>
     */
    static native int cbGetConfigString(int InfoType, int BoardNum, int DevNum, int ConfigItem, ByteBuffer ConfigVal,
            IntByReference maxConfigLen);

    /**
     * Original signature : <code>int cbSetConfig(int, int, int, int, int)</code>
     */
    static native int cbSetConfig(int InfoType, int BoardNum, int DevNum, int ConfigItem, int ConfigVal);

    /**
     * Original signature :
     * <code>int cbSetConfigString(int, int, int, int, char*, int*)</code>
     */
    static native int cbSetConfigString(int InfoType, int BoardNum, int DevNum, int ConfigItem, String ConfigVal,
            IntByReference configLen);

    /**
     * Original signature : <code>int cbToEngUnits(int, int, USHORT, float*)</code>
     */
    static native int cbToEngUnits(int BoardNum, int Range, short DataVal, FloatByReference EngUnits);

    /**
     * Original signature :
     * <code>int cbToEngUnits32(int, int, ULONG, double*)</code>
     */
    static native int cbToEngUnits32(int BoardNum, int Range, NativeLong DataVal, DoubleByReference EngUnits);

    /**
     * Original signature :
     * <code>int cbFromEngUnits(int, int, float, USHORT*)</code>
     */
    static native int cbFromEngUnits(int BoardNum, int Range, float EngUnits, ShortByReference DataVal);

    /**
     * Original signature : <code>int cbGetBoardName(int, char*)</code>
     */
    static native int cbGetBoardName(int BoardNum, ByteBuffer BoardName);

    /**
     * Original signature : <code>int cbDeclareRevision(float*)</code>
     */
    private static native int cbDeclareRevision(FloatByReference RevNum);

    /**
     * Original signature : <code>int cbSetTrigger(int, int, USHORT, USHORT)</code>
     */
    static native int cbSetTrigger(int BoardNum, int TrigType, short LowThreshold, short HighThreshold);

    /**
     * Original signature :
     * <code>int cbEnableEvent(int, unsigned, unsigned, EVENTCALLBACK, void*)</code>
     */
    static native int cbEnableEvent(int BoardNum, int EventType, int Count, EVENTCALLBACK CallbackFunc,
            Pointer UserData);

    /**
     * Original signature : <code>int cbDisableEvent(int, unsigned)</code>
     */
    static native int cbDisableEvent(int BoardNum, int EventType);

    /**
     * Original signature : <code>int cbSelectSignal(int, int, int, int, int)</code>
     */
    static native int cbSelectSignal(int BoardNum, int Direction, int Signal, int Connection, int Polarity);

    /**
     * Original signature :
     * <code>int cbGetSignal(int, int, int, int, int*, int*)</code>
     */
    static native int cbGetSignal(int BoardNum, int Direction, int Signal, int Index, IntByReference Connection,
            IntByReference Polarity);

    /**
     * Original signature :
     * <code>int cbSetCalCoeff(int, int, int, int, int, int, int)</code>
     */
    static native int cbSetCalCoeff(int BoardNum, int FunctionType, int Channel, int Range, int Item, int Value,
            int Store);

    /**
     * Original signature :
     * <code>int cbGetCalCoeff(int, int, int, int, int, int*)</code>
     */
    static native int cbGetCalCoeff(int BoardNum, int FunctionType, int Channel, int Range, int Item,
            IntByReference Value);

    /**
     * Original signature : <code>int cbLogSetPreferences(int, int, int)</code>
     */
    static native int cbLogSetPreferences(int timeFormat, int timeZone, int units);

    /**
     * Original signature : <code>int cbLogGetPreferences(int*, int*, int*)</code>
     */
    static native int cbLogGetPreferences(IntByReference timeFormat, IntByReference timeZone, IntByReference units);

    /**
     * Original signature : <code>int cbLogGetFileName(int, char*, char*)</code>
     */
    static native int cbLogGetFileName(int fileNumber, String path, ByteBuffer filename);

    /**
     * Original signature : <code>int cbLogGetFileInfo(char*, int*, int*)</code>
     */
    static native int cbLogGetFileInfo(String filename, IntByReference version, IntByReference fileSize);

    /**
     * Original signature :
     * <code>int cbLogGetSampleInfo(char*, int*, int*, int*, int*)</code>
     */
    static native int cbLogGetSampleInfo(String filename, IntByReference sampleInterval, IntByReference sampleCount,
            IntByReference startDate, IntByReference startTime);

    /**
     * Original signature : <code>int cbLogGetAIChannelCount(char*, int*)</code>
     */
    static native int cbLogGetAIChannelCount(String filename, IntByReference aiCount);

    /**
     * Original signature : <code>int cbLogGetAIInfo(char*, int*, int*)</code>
     */
    static native int cbLogGetAIInfo(String filename, IntBuffer channelNumbers, IntBuffer units);

    /**
     * Original signature : <code>int cbLogGetCJCInfo(char*, int*)</code>
     */
    static native int cbLogGetCJCInfo(String filename, IntByReference cjcCount);

    /**
     * Original signature : <code>int cbLogGetDIOInfo(char*, int*)</code>
     */
    static native int cbLogGetDIOInfo(String filename, IntByReference dioCount);

    /**
     * Original signature :
     * <code>int cbLogReadTimeTags(char*, int, int, int*, int*)</code>
     */
    static native int cbLogReadTimeTags(String filename, int startSample, int count, IntBuffer dateTags,
            IntBuffer timeTags);

    /**
     * Original signature :
     * <code>int cbLogReadAIChannels(char*, int, int, float*)</code>
     */
    static native int cbLogReadAIChannels(String filename, int startSample, int count, FloatBuffer analog);

    /**
     * Original signature :
     * <code>int cbLogReadCJCChannels(char*, int, int, float*)</code>
     */
    static native int cbLogReadCJCChannels(String filename, int startSample, int count, FloatBuffer cjc);

    /**
     * Original signature :
     * <code>int cbLogReadDIOChannels(char*, int, int, int*)</code>
     */
    static native int cbLogReadDIOChannels(String filename, int startSample, int count, IntBuffer dio);

    /**
     * Original signature :
     * <code>int cbLogConvertFile(char*, char*, int, int, int)</code>
     */
    static native int cbLogConvertFile(String srcFilename, String destFilename, int startSample, int count,
            int delimiter);

    /**
     * Original signature :
     * <code>int cbDaqInScan(int, short*, short*, short*, int, long*, long*, long*, HGLOBAL, int)</code>
     */
    static native int cbDaqInScan(int BoardNum, ShortBuffer ChanArray, ShortBuffer ChanTypeArray, ShortBuffer GainArray,
            int ChanCount, NativeLongByReference Rate, NativeLongByReference PretrigCount,
            NativeLongByReference TotalCount, HGLOBAL MemHandle, int Options);

    /**
     * Original signature :
     * <code>int cbDaqSetTrigger(int, int, int, int, int, int, float, float, int)</code>
     */
    static native int cbDaqSetTrigger(int BoardNum, int TrigSource, int TrigSense, int TrigChan, int ChanType, int Gain,
            float Level, float Variance, int TrigEvent);

    /**
     * Original signature :
     * <code>int cbDaqSetSetpoints(int, float*, float*, float*, int*, int*, float*, float*, float*, float*, int)</code>
     */
    static native int cbDaqSetSetpoints(int BoardNum, FloatBuffer LimitAArray, FloatBuffer LimitBArray,
            FloatBuffer Reserved, IntBuffer SetpointFlagsArray, IntBuffer SetpointOutputArray, FloatBuffer Output1Array,
            FloatBuffer Output2Array, FloatBuffer OutputMask1Array, FloatBuffer OutputMask2Array, int SetpointCount);

    /**
     * Original signature :
     * <code>int cbDaqOutScan(int, short*, short*, short*, int, long*, long, HGLOBAL, int)</code>
     */
    static native int cbDaqOutScan(int BoardNum, ShortBuffer ChanArray, ShortBuffer ChanTypeArray,
            ShortBuffer GainArray, int ChanCount, NativeLongByReference Rate, NativeLong Count, HGLOBAL MemHandle,
            int Options);

    /**
     * Original signature :
     * <code>int cbGetTCValues(int, short*, short*, int, HGLOBAL, int, long, int, float*)</code>
     */
    static native int cbGetTCValues(int BoardNum, ShortBuffer ChanArray, ShortBuffer ChanTypeArray, int ChanCount,
            HGLOBAL MemHandle, int FirstPoint, NativeLong Count, int Scale, FloatBuffer TempValArray);

    /**
     * Original signature : <code>int cbVIn(int, int, int, float*, int)</code>
     */
    static native int cbVIn(int BoardNum, int Chan, int Gain, FloatByReference DataValue, int Options);

    /**
     * Original signature : <code>int cbVIn32(int, int, int, double*, int)</code>
     */
    static native int cbVIn32(int BoardNum, int Chan, int Gain, DoubleByReference DataValue, int Options);

    /**
     * Original signature : <code>int cbVOut(int, int, int, float, int)</code>
     */
    static native int cbVOut(int BoardNum, int Chan, int Gain, float DataValue, int Options);

    /**
     * Original signature : <code>int cbDeviceLogin(int, char*, char*)</code>
     */
    static native int cbDeviceLogin(int BoardNum, String AccountName, String Password);

    /**
     * Original signature : <code>int cbDeviceLogout(int)</code>
     */
    static native int cbDeviceLogout(int BoardNum);

    /**
     * Original signature : <code>int cbTEDSRead(int, int, BYTE*, long*, int)</code>
     */
    static native int cbTEDSRead(int BoardNum, int Chan, ByteBuffer DataBuffer, NativeLongByReference Count,
            int Options);

    /**
     * Original signature : <code>int cbAInputMode(int, int)</code>
     */
    static native int cbAInputMode(int BoardNum, int InputMode);

    /**
     * Original signature : <code>int cbAChanInputMode(int, int, int)</code>
     */
    static native int cbAChanInputMode(int BoardNum, int Chan, int InputMode);

    /**
     * Original signature : <code>int cbIgnoreInstaCal()</code>
     */
    static native int cbIgnoreInstaCal();

    /**
     * Original signature :
     * <code>int cbCreateDaqDevice(int, DaqDeviceDescriptor)</code>
     */
    static native int cbCreateDaqDevice(int BdNum, DaqDeviceDescriptor.ByValue DeviceDescriptor);

    /**
     * Original signature :
     * <code>int cbGetDaqDeviceInventory(DaqDeviceInterface, DaqDeviceDescriptor*, INT*)</code>
     */
    static native int cbGetDaqDeviceInventory(int InterfaceType, DaqDeviceDescriptor Inventory,
            IntByReference NumberOfDevices);

    /**
     * Original signature : <code>int cbReleaseDaqDevice(int)</code>
     */
    static native int cbReleaseDaqDevice(int BdNum);

    /**
     * Original signature : <code>int cbGetBoardNumber(DaqDeviceDescriptor)</code>
     */
    static native int cbGetBoardNumber(DaqDeviceDescriptor.ByValue DeviceDescriptor);

    /**
     * Original signature :
     * <code>int cbGetNetDeviceDescriptor(CHAR*, INT, DaqDeviceDescriptor*, INT)</code>
     */
    static native int cbGetNetDeviceDescriptor(String Host, int Port, DaqDeviceDescriptor DeviceDescriptor,
            int Timeout);

}
