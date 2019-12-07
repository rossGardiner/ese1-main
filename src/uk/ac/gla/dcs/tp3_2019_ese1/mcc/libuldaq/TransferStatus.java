package uk.ac.gla.dcs.tp3_2019_ese1.mcc.libuldaq;

import java.util.List;

import com.sun.jna.Structure;

/**
 * A structure containing information about the progress of the specified scan operation.
 * 
 * @author Duncan Lowther (2402789L)
 */
public class TransferStatus extends Structure {
    public static final List<String> FIELDS = createFieldsOrder("currentScanCount", "currentTotalCount", "currentIndex", "reserved");

    /** 
     * The number of samples per channel transferred since the scan started. This is the same as {@link #currentTotalCount}
     * for single channel scans.
     */
    public long currentScanCount;

    /** 
     * The total number of samples transferred since the scan started. This is the same as {@link #currentScanCount}
     * multiplied by the number of channels in the scan.
     */
    public long currentTotalCount;

    /** 
     * This marks the location in the buffer where the last scan of data values are stored.
     * For continuous scans, this value increments up to (buffer size - number of channels) and restarts from 0. 
     */
    public long currentIndex;

    /** Reserved for future use */
    public byte[] reserved = new byte[64];
    
    @Override
    protected List<String> getFieldOrder() {
        return FIELDS;
    }
}
