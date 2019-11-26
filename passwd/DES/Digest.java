package akm;

import java.security.MessageDigest;

public class Digest {
	private static byte[] digest(byte[] data, String algorithm) {
		byte[] res = null;
		try {
			var md = MessageDigest.getInstance(algorithm);
			md.update(data);
			res = md.digest();
		} catch (Exception e) {
			e.printStackTrace();
			exit(1);
		}
		return res;
	}

	public static byte[] md5(byte[] data) {
		return digest(data, "md5");
	}

	public static String md5(String data) {
		return bytesToHexString( md5(data.getBytes()) );
	}

	public static String bytesToHexString(byte[] b) {
		var sb = new StringBuffer();
		int bLen = b.length;
		for (int i=0; i<bLen; ++i)
			sb.append( String.format("%02x", b[i]) );
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {

	}

	static <T> void print(T t) { System.out.print(t); }
	static <T> void println() { System.out.println(); }
	static <T> void println(T t) { System.out.println(t); }
	static void printf(String fmt, Object... args) { System.out.printf(fmt, args); }
	static void exit(int i) { System.exit(i); }
}
