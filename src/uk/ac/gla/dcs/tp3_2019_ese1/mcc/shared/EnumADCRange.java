package uk.ac.gla.dcs.tp3_2019_ese1.mcc.shared;

public enum EnumADCRange {
    /** -60 to +60 Volts */
    BIP60VOLTS(1, 20),
    /** -30 to +30 Volts */
    BIP30VOLTS(2, 23),
    /** -15 to +15 Volts */
    BIP15VOLTS(3, 21),
    /** -20 to +20 Volts */
    BIP20VOLTS(4, 15),
    /** -10 to +10 Volts */
    BIP10VOLTS(5, 1),
    /** -5 to +5 Volts */
    BIP5VOLTS(6, 0),
    /** -4 to +4 Volts */
    BIP4VOLTS(7, 16),
    /** -2.5 to +2.5 Volts */
    BIP2PT5VOLTS(8, 2),
    /** -2.0 to +2.0 Volts */
    BIP2VOLTS(9, 14),
    /** -1.25 to +1.25 Volts */
    BIP1PT25VOLTS(10, 3),
    /** -1 to +1 Volts */
    BIP1VOLTS(11, 4),
    /** -.625 to +.625 Volts */
    BIPPT625VOLTS(12, 5),
    /** -.5 to +.5 Volts */
    BIPPT5VOLTS(13, 6),
    /** -0.25 to +0.25 Volts */
    BIPPT25VOLTS(14, 12),
    /** -0.125 to +0.125 Volts */
    BIPPT125VOLTS(15, 22),
    /** -0.2 to +0.2 Volts */
    BIPPT2VOLTS(16, 13),
    /** -.1 to +.1 Volts */
    BIPPT1VOLTS(17, 7),
    /** -0.078 to +0.078 Volts */
    BIPPT078VOLTS(18, 19),
    /** -.05 to +.05 Volts */
    BIPPT05VOLTS(19, 8),
    /** -.01 to +.01 Volts */
    BIPPT01VOLTS(20, 9),
    /** -.005 to +.005 Volts */
    BIPPT005VOLTS(21, 10),
    /** -3.0 to +3.0 Volts */
    BIP3VOLTS(22, -1 /* XXX */),
    /** -.312 to +.312 Volts */
    BIPPT312VOLTS(23, 17),
    /** -.156 to +.156 Volts */
    BIPPT156VOLTS(24, 18),
    /** -1.67 to +1.67 Volts */
    BIP1PT67VOLTS(-1, 11),

    /** 0 to +60 Volts */
    UNI60VOLTS(1001, -1 /* XXX */),
    /** 0 to +30 Volts */
    UNI30VOLTS(1002, -1 /* XXX */),
    /** 0 to +15 Volts */
    UNI15VOLTS(1003, -1 /* XXX */),
    /** 0 to +20 Volts */
    UNI20VOLTS(1004, -1 /* XXX */),
    /** 0 to +10 Volts */
    UNI10VOLTS(1005, 100),
    /** 0 to +5 Volts */
    UNI5VOLTS(1006, 101),
    /** 0 to +4 Volts */
    UNI4VOLTS(1007, 114),
    /** 0 to +2.5 Volts */
    UNI2PT5VOLTS(1008, 102),
    /** 0 to +2.0 Volts */
    UNI2VOLTS(1009, 103),
    /** 0 to +1.25 Volts */
    UNI1PT25VOLTS(1010, 104),
    /** 0 to +1 Volts */
    UNI1VOLTS(1011, 105),
    /** 0 to +.625 Volts */
    UNIPT625VOLTS(1012, -1 /* XXX */),
    /** 0 to +.5 Volts */
    UNIPT5VOLTS(1013, 110),
    /** 0 to +0.25 Volts */
    UNIPT25VOLTS(1014, 111),
    /** 0 to +0.125 Volts */
    UNIPT125VOLTS(1015, -1 /* XXX */),
    /** 0 to +0.2 Volts */
    UNIPT2VOLTS(1016, 112),
    /** 0 to +.1 Volts */
    UNIPT1VOLTS(1017, 106),
    /** 0 to +0.078 Volts */
    UNIPT078VOLTS(1018, -1 /* XXX */),
    /** 0 to +.05 Volts */
    UNIPT05VOLTS(1019, 113),
    /** 0 to +.01 Volts */
    UNIPT01VOLTS(1020, 107),
    /** 0 to +.005 Volts */
    UNIPT005VOLTS(1021, -1 /* XXX */),
    /** 0 to 1.67 Volts */
    UNI1PT67VOLTS(-1, 109),
    /** 0 to .02 Volts */
    UNIPT02VOLTS(-1, 108),

    /** 0 to 20 Milliamps */
    MA0TO20(2000, 204),
    /** 4 to 20 mA */
    MA4TO20(-1, 200), 
    /** 2 to 10 mA */
    MA2TO10(-1, 201), 
    /** 1 to 5 mA */
    MA1TO5(-1, 202),
    /** 0 to 20 mA */
    MAPT5TO2PT5(-1, 203), 
    /** -0.025 to 0.025 mA */
    BIPPT025AMPS(-1, 205);

    private final int _uldaq, _cbw;

    private EnumADCRange(int uldaq, int cbw) {
        _uldaq = uldaq;
        _cbw = cbw;
    }

    public int getValLibuldaq() {
        return _uldaq;
    }

    public int getValLibcbw() {
        return _cbw;
    }
}
