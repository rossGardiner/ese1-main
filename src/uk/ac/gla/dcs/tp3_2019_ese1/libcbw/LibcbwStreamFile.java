package uk.ac.gla.dcs.tp3_2019_ese1.libcbw;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

import com.sun.jna.NativeLong;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.NativeLongByReference;
import com.sun.jna.ptr.ShortByReference;

import uk.ac.gla.dcs.tp3_2019_ese1.libcbw.LibcbwBoard.USB_1608FS;
import uk.ac.gla.dcs.tp3_2019_ese1.libcbw.LibcbwException.ErrorCode;
import uk.ac.gla.dcs.tp3_2019_ese1.libcbw.LibcbwJNA.ADCRange;

/**
 * A way of reading a file that has been created with
 * {@link USB_1608FS#analogueInToFile(String, int, int, int, int, int, int)} or
 * similar.
 * 
 * @author Duncan Lowther (2402789L)
 */
public class LibcbwStreamFile {

	private final String _filename;
	private final int _first_channel;
	private final int _channel_count;
	private final int _pretrig_count;
	private final int _sample_count;
	private final int _sample_rate;
	private final int _range;

	/**
	 * Opens the file and reads the identifying information.
	 * 
	 * @param filename - the path to the file.
	 * @throws LibcbwException if an error occurs.
	 */
	public LibcbwStreamFile(String filename) throws LibcbwException {
		_filename = filename;

		ShortByReference low = new ShortByReference();
		ShortByReference high = new ShortByReference();
		NativeLongByReference pretrig = new NativeLongByReference();
		NativeLongByReference total = new NativeLongByReference();
		NativeLongByReference rate = new NativeLongByReference();
		IntByReference range = new IntByReference();

		int err = LibcbwJNA.cbFileGetInfo(_filename, low, high, pretrig, total, rate, range);
		if (err != ErrorCode.NOERRORS)
			throw LibcbwException.fromErrorCode(err);

		_first_channel = low.getValue();
		_channel_count = high.getValue() - _first_channel + 1;
		_pretrig_count = pretrig.getValue().intValue();
		_sample_count = total.getValue().intValue();
		_sample_rate = rate.getValue().intValue();
		_range = range.getValue();
	}

	/**
	 * Reads <code>count</code> samples from an arbitrary position in the file.
	 * 
	 * @param first - the index of the first sample to read.
	 * @param count - the number of samples to read.
	 * @return a two-dimensional array indexed as
	 *         <code>array[channel][sample]</code> of 16-bit unsigned measurements,
	 *         zero-extended to fit <code>int</code>s
	 * @throws LibcbwException if an error occurs.
	 */
	public int[][] read(int first, int count) throws LibcbwException {
		ShortBuffer buffer = ByteBuffer.allocateDirect(2 * count * _channel_count).order(ByteOrder.nativeOrder())
				.asShortBuffer();
		NativeLongByReference count_ref = new NativeLongByReference(new NativeLong(count));

		int err = LibcbwJNA.cbFileRead(_filename, new NativeLong(first), count_ref, buffer);
		if (err != ErrorCode.NOERRORS)
			throw LibcbwException.fromErrorCode(err);

		int actual_count = count_ref.getValue().intValue();

		short[] raw = buffer.array();
		int[][] ret = new int[_channel_count][actual_count];

		for (int chan = 0; chan < _channel_count; chan++) {
			for (int sample = 0; sample < actual_count; sample++) {
				ret[chan][sample] = raw[sample * _channel_count + chan];
			}
		}
		return ret;
	}

	/**
	 * @return the first channel from which this file holds data.
	 */
	public int getFirstChannel() {
		return _first_channel;
	}

	/**
	 * @return the number of channels from which this file holds data.
	 */
	public int getChannelCount() {
		return _channel_count;
	}

	/**
	 * @return the number of pre-trigger samples this file holds.
	 */
	public int getPretrigCount() {
		return _pretrig_count;
	}

	/**
	 * @return the total number of samples (in each channel) this file holds.
	 */
	public int getSampleCount() {
		return _sample_count;
	}

	/**
	 * @return the rate at which the samples in this file were taken.
	 */
	public int getSampleRate() {
		return _sample_rate;
	}

	/**
	 * @return a constant from {@link ADCRange} defining the voltage range of the
	 *         samples.
	 */
	public int getRange() {
		return _range;
	}

}
