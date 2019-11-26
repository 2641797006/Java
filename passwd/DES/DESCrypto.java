package akm;

import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public class DESCrypto {

	private Cipher cipher;
	public static final String ALGORITHM = "DES";

	public DESCrypto() {
		try {
			cipher = Cipher.getInstance(ALGORITHM);
		} catch (Exception e) {
			e.printStackTrace();
			exit(1);
		}
	}

	public String getAlgorithm() {
		return ALGORITHM;
	}

	public Key createKey(String s) {
		Key key = null;
		try {
			var dks = new DESKeySpec( Digest.md5( s.getBytes() ) );
			var keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
			key = keyFactory.generateSecret(dks);
		} catch (Exception e) {
			e.printStackTrace();
			exit(1);
		}
		return key;
	}

	private byte[] crypto(byte[] data, Key key, int mode) {
		byte[] res = null;
		try {
			cipher.init(mode, key);
			res = cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
			exit(1);
		}
		return res;
	}

	public byte[] encrypt(byte[] data, Key key) {
		return crypto(data, key, Cipher.ENCRYPT_MODE);
	}

	public byte[] decrypt(byte[] data, Key key) {
		return crypto(data, key, Cipher.DECRYPT_MODE);
	}

	static void exit(int i) { System.exit(i); }
}
