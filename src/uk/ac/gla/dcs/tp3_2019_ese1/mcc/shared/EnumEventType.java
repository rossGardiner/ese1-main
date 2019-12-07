package uk.ac.gla.dcs.tp3_2019_ese1.mcc.shared;

import java.util.EnumSet;

public enum EnumEventType {
    /** Defines an event trigger condition that occurs when a specified
     * number of samples are available. */
    ON_DATA_AVAILABLE(1 << 0, 0x0008),

    /** Defines an event trigger condition that occurs when an input scan error occurs. */
    ON_INPUT_SCAN_ERROR(1 << 1, 0x0001),

    /** Defines an event trigger condition that occurs upon completion of an input scan operation
     * such as ulAInScan(). */
    ON_END_OF_INPUT_SCAN(1 << 2, 0x0010),

    /** Defines an event trigger condition that occurs when an output scan error occurs. */
    ON_OUTPUT_SCAN_ERROR(1 << 3, 0x0001),

    /**  Defines an event trigger condition that occurs upon completion of an output scan operation
     * such as ulAOutScan(). */
    ON_END_OF_OUTPUT_SCAN(1 << 4, 0x0020),
    
    ON_EXTERNAL_INTERRUPT(0, 0x0002),
    ON_PRETRIGGER(0, 0x0004),
    ON_CHANGE_DI(0, 0x0040);
    
    public static final EnumSet<EnumEventType> ON_SCAN_ERROR = EnumSet.of(ON_INPUT_SCAN_ERROR, ON_OUTPUT_SCAN_ERROR);
    public static final EnumSet<EnumEventType> ALL_EVENT_TYPES = EnumSet.allOf(EnumEventType.class);
    
    private final int _uldaq;
    private final int _cbw;
    
    private EnumEventType(int uldaq, int cbw) {
        _uldaq = uldaq;
        _cbw = cbw;
    }
    
    public static int asLibuldaqFlags(EnumSet<EnumEventType> set) {
        return set.stream().mapToInt(e -> e._uldaq).reduce(0, (a,b) -> a|b);
    }
    
    public static int asLibcbwFlags(EnumSet<EnumEventType> set) {
        return set.stream().mapToInt(e -> e._cbw).reduce(0, (a,b) -> a|b);
    }
    
    public static EnumEventType forLibuldaqFlag(int flag) {
        return EnumSet.allOf(EnumEventType.class).stream().filter(e -> 0 != (e._uldaq&flag)).findAny().orElse(null);
    }
    
    public static EnumEventType forLibcbwFlag(int flag) {
        return EnumSet.allOf(EnumEventType.class).stream().filter(e -> 0 != (e._cbw&flag)).findAny().orElse(null);
    }

}
