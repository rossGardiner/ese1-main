package uk.ac.gla.dcs.tp3_2019_ese1.mcc.libuldaq;

import java.util.List;

import com.sun.jna.Structure;

/**
 * A structure that defines an output channel and its properties. Used with ulDaqOutScan().
 *
 * @author Duncan Lowther (2402789L)
 */
public class DaqOutChanDescriptor extends Structure {
    public static final List<String> FIELDS = createFieldsOrder("channel", "type", "range", "reserved");

    /** Analog output */
    public static final int DAQO_ANALOG = 1 << 0;
    /** Digital output */
    public static final int DAQO_DIGITAL = 1 << 1;

    /** The channel number. */
    int channel;

    /**
     * The type of input for the specified channel, such as analog or digital. DAQO_
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
