package akm;

import java.io.*;
import java.util.*;
import java.security.*;

public class xx1{
	static final int ENCRYPT = 0, DECRYPT = 1;

	static final String help = "Usage:    java me [options] [-in IN_FILE -out OUT_FILE]\n"
			+ "Valid options are:\n"
			+ " -e              Encrypt\n"
			+ " -d              Decrypt\n"
			+ " -in in_file     Input file\n"
			+ " -out out_file   Output file\n"
			+ " -keygen         Generate key\n"
			+ " -b/-bits %d     Specify key length, the default is 2048 bits";

	public static void generateKey(int keySize) {
		try {
		String s, path="id_rsa";
		print("Generating public/private rsa key pair.\nEnter file in which to save the key (./id_rsa): ");
		Scanner sc, scan = new Scanner(System.in);
		sc = new Scanner(scan.nextLine());
		try { s = sc.next(); }
		catch (Exception e) { s = path; }
		path = s;
		File fileKeyPri = new File(path);
		File fileKeyPub = new File(path+".pub");
		if ( fileKeyPri.exists() || fileKeyPub.exists() ) {
			println(path + " already exists.");
			print("Overwrite (y/n)? ");
			sc = new Scanner(scan.nextLine());
			try { s = sc.next(); }
			catch (Exception e) { exit(1); }
			char c = s.charAt(0);
			if (c!='y' && c!='Y')
				exit(1);
		}
		var rsa = new RSACrypto();
		var keyPairMap = rsa.createKeyPair(keySize);
		Key keyPub = keyPairMap.get("publicKey");
		Key keyPri = keyPairMap.get("privateKey");
		var base64 = Base64.getEncoder();
		var out = new FileOutputStream(fileKeyPri);
		out.write(base64.encode(keyPri.getEncoded()));
		out = new FileOutputStream(fileKeyPub);
		out.write(base64.encode(keyPub.getEncoded()));
		} catch (Exception e) {
			e.printStackTrace();
			println("fail to generating public/private rsa key pair");
			exit(1);
		}
	}

	public static void main(String[] args) throws Exception {
		int optind = 0, argsLen = args.length;
		int mode = ENCRYPT, keySize = 2048;
		boolean keyGen = false;
		String in = null, out = null;
		try {
		if (argsLen == 0)
			throw new Exception();
		while (optind < argsLen) {
			switch (args[optind]) {
			case "-e":
				mode = ENCRYPT;
				break;
			case "-d":
				mode = DECRYPT;
				break;
			case "-in":
				in = args[++optind];
				break;
			case "-out":
				out = args[++optind];
				break;
			case "-keygen":
				keyGen = true;
				break;
			case "-b": case "-bits":
				keySize = Integer.parseInt(args[++optind]);
				break;
			}
			++optind;
		}
		} catch (Exception e) {
			println(help);
			exit(1);
		}
		if (keyGen) {
			if (keySize<768 || keySize>2048)
				println("The keysize must be between 768 and 2048");
			else
				generateKey(keySize);
			exit(0);
		}
	}

	static <T> void print(T t) { System.out.print(t); }
	static <T> void println() { System.out.println(); }
	static <T> void println(T t) { System.out.println(t); }
	static void printf(String fmt, Object... args) { System.out.printf(fmt, args); }
	static void exit(int i) { System.exit(i); }
}
