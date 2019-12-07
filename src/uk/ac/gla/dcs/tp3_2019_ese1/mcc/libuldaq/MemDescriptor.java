package uk.ac.gla.dcs.tp3_2019_ese1.mcc.libuldaq;

import java.util.List;
import com.sun.jna.Structure;

/** 
 * A structure that defines the location and access properties of the physical
 * memory of a device.
 * 
 * @author Duncan Lowther (2402789L)
 */
public class MemDescriptor extends Structure {
    public static final List<String> FIELDS = createFieldsOrder("region", "address", "size", "accessTypes", "reserved");

    /**
     * Specifies the calibration data region information returned to the
     * MemDescriptor struct
     */
    public static final int MR_CAL = 1 << 0;
    /**
     * Specifies the user data region information returned to the MemDescriptor
     */
    public static final int MR_USER = 1 << 1;
    /**
     * Specifies the data settings region information returned to the MemDescriptor
     */
    public static final int MR_SETTINGS = 1 << 2;
    /**
     * Specifies the first reserved region information returned to the MemDescriptor
     * struct
     */
    public static final int MR_RESERVED0 = 1 << 3;
    
    /** Indicates read access for the location. */
    public static final int MA_READ = 1 << 0;
    /** Indicates write access for the location. */
    public static final int MA_WRITE = 1 << 1;

    
    /** The enumeration indicating the region of the memory. */
    public int region;

    /**
     * A numeric value that specifies the address of the memory; used with
     * ulMemRead() and ulMemWrite().
     */
    public int address;

    /**
     * A numeric value that specifies the size of the memory area at the specified
     * address.
     */
    public int size;

    /**
     * A bitmask indicating the access rights to the memory at the specified address
     * (read, write, or both).
     */
    public int accessTypes;

    /** Reserved for future use */
    public byte[] reserved = new byte[64];

    @Override
    protected List<String> getFieldOrder() {
        return FIELDS;
    }
}
