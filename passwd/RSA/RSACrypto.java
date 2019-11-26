package akm;

import java.io.*;
import java.util.*;
import java.security.*;
import java.security.spec.*;
import java.security.interfaces.*;
import javax.crypto.*;

public class RSACrypto {

	private Cipher cipher;
	public static final String ALGORITHM = "RSA";
	public static final int PUBLIC_KEY = 0, PRIVATE_KEY = 1;

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

	private static KeyPair createKeyPairPrivate(int keySize) {
		KeyPairGenerator kpg = null;
		try {
			kpg = KeyPairGenerator.getInstance(ALGORITHM);
		} catch (Exception e) {
			e.printStackTrace();
			exit(1);
		}
		kpg.initialize(keySize);
		return kpg.generateKeyPair();
	}

	public KeyPair createKeyPair(int keySize) {
		return createKeyPairPrivate(keySize);
	}

	public PublicKey bytesToPublicKey(byte[] b) {
		PublicKey key = null;
		try {
		var keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(b));
		key = KeyFactory.getInstance(ALGORITHM).generatePublic(keySpec);
		} catch (Exception e) { }
		return key;
	}

	public PrivateKey bytesToPrivateKey(byte[] b) {
		PrivateKey key = null;
		try {
		var keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(b));
		key = KeyFactory.getInstance(ALGORITHM).generatePrivate(keySpec);
		} catch (Exception e) { }
		return key;
	}

	public Key bytesToKey(byte[] b) {
		Key key = bytesToPublicKey(b);
		if (key == null)
			key = bytesToPrivateKey(b);
		return key;
	}

	private byte[] crypto(byte[] data, Key key, int mode) {
		var out = new ByteArrayOutputStream();
		int keyLen = ((RSAKey)key).getModulus().bitLength() / 8;
		int maxBlock = (mode == Cipher.ENCRYPT_MODE) ? keyLen - 11 : keyLen;
		int dataLen = data.length;
		int n = dataLen / maxBlock;
		int remain = dataLen % maxBlock;
		try {
			cipher.init(mode, key);
			for (int i=0; i<n; ++i)
				out.write(cipher.doFinal(data, i * maxBlock, maxBlock));
			if (remain != 0)
				out.write(cipher.doFinal(data, n * maxBlock, remain));
		} catch (Exception e) {
			e.printStackTrace();
			exit(1);
		}
		return out.toByteArray();
	}

	public byte[] encrypt(byte[] data, Key key) {
		return crypto(data, key, Cipher.ENCRYPT_MODE);
	}

	public byte[] decrypt(byte[] data, Key key) {
		return crypto(data, key, Cipher.DECRYPT_MODE);
	}

	static void exit(int i) { System.exit(i); }
}
