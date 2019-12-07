package uk.ac.gla.dcs.tp3_2019_ese1.mcc.libuldaq;

import java.util.List;

import com.sun.jna.Structure;

/**
 * A structure that defines an input channel and its properties. Used with ulDaqInScan().
 *
 * @author Duncan Lowther (2402789L)
 */
public class DaqInChanDescriptor extends Structure {
    public static final List<String> FIELDS = createFieldsOrder("channel", "type", "range", "reserved");

    /** Analog input channel, differential mode */
    public static final int DAQI_ANALOG_DIFF = 1 << 0;
    /** Analog input channel, single-ended mode */
    public static final int DAQI_ANALOG_SE = 1 << 1;
    /** Digital channel */
    public static final int DAQI_DIGITAL = 1 << 2;
    /** 16-bit counter channel. */
    public static final int DAQI_CTR16 = 1 << 3;
    /** 32-bit counter channel. */
    public static final int DAQI_CTR32 = 1 << 4;
    /** 48-bit counter channel. */
    public static final int DAQI_CTR48 = 1 << 5;
    /** DAQI_CTR64 */
    public static final int DAQI_DAC = 1 << 7;

    
    /** The channel number. */
    int channel;

    /**
     * The type of input for the specified channel, such as analog, digital, or
     * counter. DAQI_
     */
    int type;

    /**
     * The range to be used for the specified channel; ignored if not analog.
     * {@link LibuldaqJNA.ADCRange}
     */
    int range;

    /** Reserved for future use */
    public byte[] reserved = new byte[64];

    @Override
    protected List<String> getFieldOrder() {
        return FIELDS;
    }
}
