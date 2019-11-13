package uk.ac.gla.dcs.tp3_2019_ese1.libcbw;

import com.sun.jna.ptr.IntByReference;

import uk.ac.gla.dcs.tp3_2019_ese1.libcbw.LibcbwException.ErrorCode;
import uk.ac.gla.dcs.tp3_2019_ese1.libcbw.LibcbwJNA.ConfigInfo;

/**
 * Java-friendly global configuration functions.
 * 
 * @author Duncan Lowther (2402789L)
 */
public class LibcbwConfig {
    private LibcbwConfig() {
    }

    /**
     * Loads configuration data from a file.
     * 
     * @param path - the path to the configuration file
     * @throws LibcbwException if an error occurs.
     */
    public static void loadConfig(String path) throws LibcbwException {
        int err = LibcbwJNA.cbLoadConfig(path);
        if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);
    }

    /**
     * Saves configuration data to a file.
     * 
     * @param path - the path to the configuration file
     * @throws LibcbwException if an error occurs.
     */
    public static void saveConfig(String path) throws LibcbwException {
        int err = LibcbwJNA.cbSaveConfig(path);
        if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);
    }

    /**
     * @return the configuration format version number.
     * @throws LibcbwException if an error occurs.
     */
    public static int getVersion() throws LibcbwException {
        IntByReference val_ref = new IntByReference();
        int err = LibcbwJNA.cbGetConfig(ConfigInfo.GLOBALINFO, 0, 0, ConfigInfo.GIVERSION, val_ref);
        if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);
        return val_ref.getValue();
    }

    /**
     * @return the maximum number of boards that can be connected at any one time.
     * @throws LibcbwException if an error occurs.
     */
    public static int getMaxBoards() throws LibcbwException {
        IntByReference val_ref = new IntByReference();
        int err = LibcbwJNA.cbGetConfig(ConfigInfo.GLOBALINFO, 0, 0, ConfigInfo.GINUMBOARDS, val_ref);
        if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);
        return val_ref.getValue();
    }

    /**
     * @return the maximum number of expansion boards that can be connected at any
     *         one time.
     * @throws LibcbwException if an error occurs.
     */
    public static int getMaxExpBoards() throws LibcbwException {
        IntByReference val_ref = new IntByReference();
        int err = LibcbwJNA.cbGetConfig(ConfigInfo.GLOBALINFO, 0, 0, ConfigInfo.GINUMEXPBOARDS, val_ref);
        if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);
        return val_ref.getValue();
    }
}
