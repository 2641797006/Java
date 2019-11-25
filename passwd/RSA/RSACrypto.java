package akm;

import java.util.*;
import java.security.*;
import javax.crypto.*;

public class RSACrypto {

	private Cipher cipher;
	public static final String ALGORITHM = "RSA";

	public RSACrypto() {
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

	private static Map<String, Key> createKeyPairPrivate(int keySize) {
		KeyPairGenerator kpg = null;
		try {
			kpg = KeyPairGenerator.getInstance(ALGORITHM);
		} catch (Exception e) {
			e.printStackTrace();
			exit(1);
		}
		kpg.initialize(keySize);
		var keyPair = kpg.generateKeyPair();
		var keyPairMap = new HashMap<String, Key>();
		keyPairMap.put("publicKey", keyPair.getPublic());
		keyPairMap.put("privateKey", keyPair.getPrivate());
		return keyPairMap;
	}

	public Map<String, Key> createKeyPair(int keySize) {
		return createKeyPairPrivate(keySize);
	}

	public byte[] crypto(byte[] data, Key key, int mode) {
		byte[] ba = null;
		try {
			cipher.init(mode, key);
			ba = cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
			exit(1);
		}
		return ba;
	}

	public byte[] encrypt(byte[] data, Key key) {
		return crypto(data, key, Cipher.ENCRYPT_MODE);
	}

	public byte[] decrypt(byte[] data, Key key) {
		return crypto(data, key, Cipher.DECRYPT_MODE);
	}

	static void exit(int i) { System.exit(i); }
}
