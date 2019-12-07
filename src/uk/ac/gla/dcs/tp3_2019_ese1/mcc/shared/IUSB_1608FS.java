package uk.ac.gla.dcs.tp3_2019_ese1.mcc.shared;

import java.util.EnumSet;

public interface IUSB_1608FS extends AutoCloseable {
    /**
     * Read one of the ADC channels and return the result in volts.
     * 
     * @param channel - the ADC channel number (0 to 7, inclusive)
     * @param range   - the voltage range for the reading. This board
     *                only supports the following ranges:
     *                BIP1VOLTS, BIP2VOLTS, BIP5VOLTS, or BIP10VOLTS
     * @return the measurement in volts, as a double-precision float.
     * @throws MCCException if an error occurs.
     */
    double analogueInVolts(int channel, EnumADCRange range) throws MCCException;

    /**
     * Begin an asynchronous scan, reading at regular intervals from one or more the
     * ADC channels.
     * 
     * @param lowchan           - the ADC channel number of the first channel to
     *                          poll (0 to 7, inclusive)
     * @param numChannels       - the number of consecutive channels to poll
     * @param range             - the voltage range for the reading. This board
     *                          only supports the following ranges:
     *                          BIP1VOLTS, BIP2VOLTS, BIP5VOLTS, or BIP10VOLTS
     * @param samplesPerChannel - the number of samples to take from each channel
     * @param freq              - the sampling frequency (Hz) for each individual
     *                          channel. Must be between 1 and 50000, inclusive, and
     *                          <code>freq*numChannels</code> should not exceed
     *                          200000.
     * @throws MCCException if an error occurs.
     */
    void analogueInStartAsync(int lowchan, int numChannels, EnumADCRange range, int samplesPerChannel, int freq, EnumSet<EnumScanFlags> flags) throws MCCException;

    /**
     * End an asynchronous scan, and return all samples that have yet to be
     * processed.
     * 
     * @return a two-dimensional array indexed as
     *         <code>array[channel][sample]</code> of <code>double</code>s or
     *         <code>null</code> if no asynchronous scan is active.
     */
    double[][] analogueInStopAsync();

    /**
     * Reads from a single DIO line.
     * 
     * @param bit - the index of the DIO line to read
     * @return the logic value of the DIO line
     * @throws MCCException if an error occurs.
     */
    boolean digitalIn(int pin) throws MCCException;

    /**
     * Simultaneously reads from all eight DIO lines.
     * 
     * @return the logic values of the DIO lines taken together as a
     *         <code>byte</code>.
     * @throws MCCException if an error occurs.
     */
    byte digitalIn() throws MCCException;

    /**
     * Writes to a single DIO line.
     * 
     * @param bit   - the index of the DIO line to write to
     * @param value - the logic value to write
     * @throws MCCException if an error occurs.
     */
    void digitalOut(int pin, boolean value) throws MCCException;

    /**
     * Simultaneously writes to all eight DIO lines.
     * 
     * @param value - a <code>byte</code> with each bit representing the value to
     *              write to a different DIO line.
     * @throws MCCException if an error occurs.
     */
    void digitalOut(byte value) throws MCCException;
    
    /**
     * Registers an event handler for the specified interrupt type on this board.
     * 
     * @param type     - the type of event to handle. Must be one of the following:
     *                 ON_SCAN_ERROR, ON_DATA_AVAILABLE, ON_END_OF_INPUT_SCAN
     * @param callback - the event handler to call when the specified event occurs
     * @throws MCCException if an error occurs.
     */
    void enableEvent(EnumEventType type, JavaCallback callback) throws MCCException;
    
    /**
     * Registers an event handler for a set of specified interrupt types on this board.
     * 
     * @param type     - the types of event to handle. Must contain one or more
     *                 of the following constants and no others:
     *                 ON_SCAN_ERROR, ON_DATA_AVAILABLE, ON_END_OF_INPUT_SCAN.
     * @param callback - the event handler to call when the specified event occurs
     * @throws MCCException if an error occurs.
     */
    void enableEvent(EnumSet<EnumEventType> type, JavaCallback callback) throws MCCException;
    
    /**
     * Deregisters any event handler for the specified interrupt type on this
     * board.
     * 
     * @param type - the type of event to stop handling. Must be one of the following:
     *             ON_SCAN_ERROR, ON_DATA_AVAILABLE, ON_END_OF_INPUT_SCAN.
     * @throws MCCException if an error occurs.
     */
    void disableEvent(EnumEventType type) throws MCCException;
    
    /**
     * Deregisters any event handler for the specified interrupt types on this
     * board.
     * 
     * @param type - the type of event to stop handling. Must contain one or more
     *             of the following constants and no others:
     *             ON_SCAN_ERROR, ON_DATA_AVAILABLE, ON_END_OF_INPUT_SCAN.
     *             
     * @throws MCCException if an error occurs.
     */
    void disableEvent(EnumSet<EnumEventType> type) throws MCCException;
    
    /**
     * Causes the LED on the board to flash.
     * 
     * @throws MCCException if an error occurs.
     */
    void flashLED() throws MCCException;

    /**
     * Clears the board's event counter.
     * 
     * @throws MCCException if an error occurs.
     */
    void clearCounter() throws MCCException;
    
    /**
     * Reads the board's event counter.
     * 
     * @return the current count value as a 32-bit unsigned integer, zero extended
     *         to fit a <code>long</code>
     * @throws MCCException if an error occurs.
     */
    long readCounter() throws MCCException;
    
    @Override
    void close() throws MCCException;
    
    /**
     * A functional interface defining a Java-friendly event callback.
     * 
     * @author Duncan Lowther (2402789L)
     */
    @FunctionalInterface
    public static interface JavaCallback {
        void apply(IUSB_1608FS dev, EnumEventType evtType, long data);
    }
}
