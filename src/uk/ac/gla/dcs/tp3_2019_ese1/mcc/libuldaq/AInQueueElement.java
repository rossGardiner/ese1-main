package uk.ac.gla.dcs.tp3_2019_ese1.mcc.libuldaq;

import java.util.List;

import com.sun.jna.Structure;

public class AInQueueElement extends Structure {
    public static final List<String> FIELDS = createFieldsOrder("channel", "inputMode", "range", "reserved");

    /** The analog input channel number for the queue element. */
    int channel;
    
    /** The input mode to use for the specified channel for the queue element. {@link LibuldaqJNA.AInMode} */
    int inputMode;

    /** The range to use for the specified channel for the queue element. {@link LibuldaqJNA.ADCRange} */
    int range;

    /** Reserved for future use */
    public byte[] reserved = new byte[64];
    
    @Override
    protected List<String> getFieldOrder() {
        return FIELDS;
    }
}
