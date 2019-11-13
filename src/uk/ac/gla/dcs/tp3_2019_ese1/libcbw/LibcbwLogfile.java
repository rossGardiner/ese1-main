package uk.ac.gla.dcs.tp3_2019_ese1.libcbw;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import com.sun.jna.ptr.IntByReference;

import uk.ac.gla.dcs.tp3_2019_ese1.libcbw.LibcbwException.ErrorCode;

/**
 * Frontend for the cbLogXXX() functions.
 * 
 * @author Duncan Lowther (2402789L)
 */
public class LibcbwLogfile {

    private final String _filename;
    private final int _version, _size, _interval, _sample_count, _ain_count, _cjc_count, _dio_count;
    private final LocalDateTime _start;

    public LibcbwLogfile(String filename) throws LibcbwException {
        _filename = filename;

        IntByReference ver_ref = new IntByReference();
        IntByReference size_ref = new IntByReference();
        int err = LibcbwJNA.cbLogGetFileInfo(_filename, ver_ref, size_ref);
        if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);

        _version = ver_ref.getValue();
        _size = size_ref.getValue();

        IntByReference interval_ref = new IntByReference();
        IntByReference count_ref = new IntByReference();
        IntByReference date_ref = new IntByReference();
        IntByReference time_ref = new IntByReference();
        err = LibcbwJNA.cbLogGetSampleInfo(_filename, interval_ref, count_ref, date_ref, time_ref);
        if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);
        _interval = interval_ref.getValue();
        _sample_count = count_ref.getValue();
        int date = date_ref.getValue();
        int time = time_ref.getValue();
        int hour = (time & 0x00FF0000) >> 16;
        switch(time >> 24) {
            case 0:
                if(hour == 12) hour = 0;
                break;
            case 1:
                if(hour < 12) hour += 12;
        }
        _start = LocalDateTime.of(date >> 16, (date & 0xFF00) >> 8, date & 0xFF, hour, (time & 0xFF00) >> 8,
                time & 0xFF);

        err = LibcbwJNA.cbLogGetAIChannelCount(_filename, count_ref);
        if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);
        _ain_count = count_ref.getValue();

        err = LibcbwJNA.cbLogGetCJCInfo(_filename, count_ref);
        if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);
        _cjc_count = count_ref.getValue();

        err = LibcbwJNA.cbLogGetDIOInfo(_filename, count_ref);
        if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);
        _dio_count = count_ref.getValue();
    }

    public static void setPreferences(int timeFormat, int timeZone, int units) throws LibcbwException {
        int err = LibcbwJNA.cbLogSetPreferences(timeFormat, timeZone, units);
        if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);
    }

    public static int[] getPreferences() throws LibcbwException {
        IntByReference fmt_ref = new IntByReference();
        IntByReference zone_ref = new IntByReference();
        IntByReference unit_ref = new IntByReference();
        int err = LibcbwJNA.cbLogGetPreferences(fmt_ref, zone_ref, unit_ref);
        if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);
        return new int[] { fmt_ref.getValue(), zone_ref.getValue(), unit_ref.getValue() };
    }

    public static String getFileName(int fileNumber, String path) throws LibcbwException {
        ByteBuffer buf = ByteBuffer.allocateDirect(256);
        int err = LibcbwJNA.cbLogGetFileName(fileNumber, path, buf);
        if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);
        return new String(StandardCharsets.UTF_8.decode(buf).toString());
    }

    public int[][] getAIInfo() throws LibcbwException {
        IntBuffer numbers = IntBuffer.allocate(_ain_count);
        IntBuffer units = IntBuffer.allocate(_ain_count);
        int err = LibcbwJNA.cbLogGetAIInfo(_filename, numbers, units);
        if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);
        return new int[][] { numbers.array(), units.array() };
    }

    public LocalDateTime[] readTimeTags() throws LibcbwException {
        IntBuffer dates = IntBuffer.allocate(_sample_count);
        IntBuffer times = IntBuffer.allocate(_sample_count);
        int err = LibcbwJNA.cbLogReadTimeTags(_filename, 0, _sample_count, dates, times);
        if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);

        LocalDateTime[] ret = new LocalDateTime[_sample_count];
        for(int i = 0; i < _sample_count; i++) {
            int date = dates.get(i);
            int time = times.get(i);
            int hour = (time & 0x00FF0000) >> 16;
            switch(time >> 24) {
                case 0:
                    if(hour == 12) hour = 0;
                    break;
                case 1:
                    if(hour < 12) hour += 12;
            }
            ret[i] = LocalDateTime.of(date >> 16, (date & 0xFF00) >> 8, date & 0xFF, hour, (time & 0xFF00) >> 8,
                    time & 0xFF);
        }
        return ret;
    }

    public float[][] readAIChannels() throws LibcbwException {
        FloatBuffer buffer = ByteBuffer.allocateDirect(4 * _sample_count * _ain_count).order(ByteOrder.nativeOrder())
                .asFloatBuffer();

        int err = LibcbwJNA.cbLogReadAIChannels(_filename, 0, _sample_count, buffer);
        if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);

        float[] raw = buffer.array();
        float[][] ret = new float[_ain_count][_sample_count];

        for(int chan = 0; chan < _ain_count; chan++) {
            for(int sample = 0; sample < _sample_count; sample++) {
                ret[chan][sample] = raw[sample * _ain_count + chan];
            }
        }
        return ret;
    }

    public float[][] readCJCChannels() throws LibcbwException {
        FloatBuffer buffer = ByteBuffer.allocateDirect(4 * _sample_count * _cjc_count).order(ByteOrder.nativeOrder())
                .asFloatBuffer();

        int err = LibcbwJNA.cbLogReadCJCChannels(_filename, 0, _sample_count, buffer);
        if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);

        float[] raw = buffer.array();
        float[][] ret = new float[_cjc_count][_sample_count];

        for(int chan = 0; chan < _cjc_count; chan++) {
            for(int sample = 0; sample < _sample_count; sample++) {
                ret[chan][sample] = raw[sample * _cjc_count + chan];
            }
        }
        return ret;
    }

    public int[][] readDIOChannels() throws LibcbwException {
        IntBuffer buffer = ByteBuffer.allocateDirect(4 * _sample_count * _dio_count).order(ByteOrder.nativeOrder())
                .asIntBuffer();

        int err = LibcbwJNA.cbLogReadDIOChannels(_filename, 0, _sample_count, buffer);
        if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);

        int[] raw = buffer.array();
        int[][] ret = new int[_dio_count][_sample_count];

        for(int chan = 0; chan < _dio_count; chan++) {
            for(int sample = 0; sample < _sample_count; sample++) {
                ret[chan][sample] = raw[sample * _dio_count + chan];
            }
        }
        return ret;
    }

    public void toCSV(String path, int first, int count, int delimiter) throws LibcbwException {
        int err = LibcbwJNA.cbLogConvertFile(_filename, path, first, count, delimiter);
        if(err != ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);
    }

    public int getVersion() {
        return _version;
    }

    public int getSize() {
        return _size;
    }

    public int getInterval() {
        return _interval;
    }

    public LocalDateTime getStart() {
        return _start;
    }
}
