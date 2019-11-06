package uk.ac.gla.dcs.tp3_2019_ese1.libcbw;

import java.nio.ByteBuffer;

/**
 * An exception stemming from a libcbw error code.
 * 
 * @author Duncan Lowther (2402789L)
 */
public class LibcbwException extends Exception {
	private static final long serialVersionUID = -4606611449633302285L;

	private LibcbwException(String s) {
		super(s);
	}

	/**
	 * Creates an exception object to wrap an integer error code returned by the native library.
	 * 
	 * @param errCode - the error code returned by the native library.
	 * @return the new exception object.
	 */
	public static LibcbwException fromErrorCode(int errCode) {
		ByteBuffer msgBuf = ByteBuffer.allocateDirect(LibcbwJNA.ERRSTRLEN);
		
		int err2 = LibcbwJNA.cbGetErrMsg(errCode, msgBuf);
		if(err2 != LibcbwJNA.ErrorCode.NOERRORS) return fromErrorCode(err2);
		
		return new LibcbwException(new String(msgBuf.array()));
	}
}
