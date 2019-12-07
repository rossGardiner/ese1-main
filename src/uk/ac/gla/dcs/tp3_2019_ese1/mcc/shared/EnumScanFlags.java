package uk.ac.gla.dcs.tp3_2019_ese1.mcc.shared;

import java.util.EnumSet;

public enum EnumScanFlags {
    /** Transfers one packet of data at a time. */
    SINGLEIO(1 << 0, 0x0020),

    /** Transfers A/D data in blocks. */
    BLOCKIO(1 << 1, 0x0060),

    /** Transfers A/D data from the FIFO after the scan completes.
     * Allows maximum rates for finite scans up to the full capacity of the FIFO. Not recommended for slow acquisition rates. */
    BURSTIO(1 << 2, 0x10000),

    /** Scans data in an endless loop. The only way to stop the operation is with ulAInScanStop(). */
    CONTINUOUS(1 << 3, 0x0002),

    /** Data conversions are controlled by an external clock signal. */
    EXTCLOCK(1 << 4, 0x0004),

    /** Sampling begins when a trigger condition is met. */
    EXTTRIGGER(1 << 5, 0x4000),

    /** Re-arms the trigger after a trigger event is performed. */
    RETRIGGER(1 << 6, 0x20000),

    /** Enables burst mode sampling, minimizing the channel skew. */
    BURSTMODE(1 << 7, 0x1000),

    /** Enables or disables the internal pacer output on a DAQ device. */
    PACEROUT(1 << 8, 0),

    /** Changes the internal clock's timebase to an external timebase source. This can allow synchronization of multiple DAQ devices. */
    EXTTIMEBASE(1 << 9, 0),

    /** Enables or disables the internal timebase output on a DAQ device. */
    TIMEBASEOUT(1 << 10, 0),
    
    /** Run in background, return immediately (default on libuldaq) */
    BACKGROUND(0, 0x0001),
    
    /** DMA transfer */
    DMAIO(0, 0x0040),
    
    /** Non-streamed D/A output */
    NONSTREAMEDIO(0, 0x040000),
    
    /** Output operation is triggered on ADC clock */
    ADCCLOCKTRIG(0, 0x080000),
    
    /** Output operation is paced by ADC clock */
    ADCCLOCK(0, 0x100000),
    
    /** Use high resolution rate */
    HIGHRESRATE(0, 0x200000),
    
    /** Enable Shunt Calibration */
    SHUNTCAL(0, 0x400000),
    
    /** Digital IN/OUT a word at a time */
    WORDXFER(0, 0x0100),
    /** Digital IN/OUT a double word at a time */
    DWORDXFER(0, 0x0200),
    /** Simultaneous D/A output */
    SIMULTANEOUS(0, 0x0200);
    
    
    /** Transfers A/D data based on the board type and sampling speed. */
    public static final EnumSet<EnumScanFlags> DEFAULTIO = EnumSet.noneOf(EnumScanFlags.class);
    
    private final int _uldaq;
    private final int _cbw;
    
    private EnumScanFlags(int uldaq, int cbw) {
        _uldaq = uldaq;
        _cbw = cbw;
    }
    
    public static int asLibuldaqFlags(EnumSet<EnumScanFlags> set) {
        return set.stream().mapToInt(e -> e._uldaq).reduce(0, (a,b) -> a|b);
    }
    
    public static int asLibcbwFlags(EnumSet<EnumScanFlags> set) {
        return set.stream().mapToInt(e -> e._cbw).reduce(0, (a,b) -> a|b);
    }
}
