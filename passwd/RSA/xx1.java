package akm;

import java.io.*;
import java.util.*;
import java.security.*;

public class xx1{
	static final int ENCRYPT = 0, DECRYPT = 1;

	static final String help = "Usage:    java me [options] [-in IN_FILE -out OUT_FILE]\n"
			+ "Valid options are:\n"
			+ " -e                Encrypt\n"
			+ " -d                Decrypt\n"
			+ " -in in_file       Input file\n"
			+ " -out out_file     Output file\n"
			+ " -k/-key key_file  Specifying the key file\n"
			+ " -keygen           Generate key\n"
			+ " -b/-bits %d       Specify key length, the default is 2048 bits";

	public static void generateKey(int keySize) {
		try {
		String s, path="./id_rsa";
		print("Generating public/private rsa key pair.\nEnter file in which to save the key ("+path+"): ");
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
		var keyPair = rsa.createKeyPair(keySize);
		Key keyPub = keyPair.getPublic();
		Key keyPri = keyPair.getPrivate();
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
		println("\nGenerating key pair OK");
	}

	public static void main(String[] args) throws Exception {
		int optind = 0, argsLen = args.length;
		int mode = ENCRYPT, keySize = 2048;
		boolean keyGen = false;
		String in = null, out = null, key = null;
		Scanner scan = new Scanner(System.in);
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
			case "-k": case "-key":
				key = args[++optind];
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
		File fileIn = new File(in);
		File fileOut = new File(out);
		if ( ! fileIn.canRead() ) {
			println("Fail to open file "+in);
			exit(1);
		}
		if ( fileOut.exists() ) {
			println("File "+out+" already exists.");
			print("Overwrite (y/n)? ");
			var sc = new Scanner(scan.nextLine());
			String s = null;
			try { s = sc.next(); }
			catch (Exception e) { exit(1); }
			char c = s.charAt(0);
			if (c!='y' && c!='Y')
				exit(1);
		}
		byte[] keyBytes;
		if ( key == null ) {
			println("Please enter the key: ");
			String keyBase64 = scan.next();
			scan.nextLine();
			keyBytes = keyBase64.getBytes();
		} else {
			File fileKey = new File(key);
			if ( ! fileKey.canRead() ) {
				println("File to open file "+key);
				exit(1);
			}
			var keyIs = new FileInputStream(fileKey);
			keyBytes = keyIs.readAllBytes();
		}
		var rsa = new RSACrypto();
		Key finalKey = rsa.bytesToKey(keyBytes);
		if (finalKey == null) {
			println("Key format error");
			exit(1);
		}
		byte[] obs;
		var ibs = new FileInputStream(fileIn).readAllBytes();
		var os = new FileOutputStream(fileOut);
		if (mode == ENCRYPT)
			obs = rsa.encrypt(ibs, finalKey);
		else
			obs = rsa.decrypt(ibs, finalKey);
		os.write(obs);
		println("\n"+(mode == ENCRYPT ? "Encrypt" : "Decrypt") + " OK");
	}

	static <T> void print(T t) { System.out.print(t); }
	static <T> void println() { System.out.println(); }
	static <T> void println(T t) { System.out.println(t); }
	static void printf(String fmt, Object... args) { System.out.printf(fmt, args); }
	static void exit(int i) { System.exit(i); }
}
