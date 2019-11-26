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
			+ " -stdio            Standard input and output";

	public static void main(String[] args) throws Exception {
		int optind = 0, argsLen = args.length;
		int mode = ENCRYPT;
		boolean isStdio = false;
		String in = null, out = null;
		Scanner scan = new Scanner(System.in);

// argument
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
			case "-stdio":
				isStdio = true;
				break;
			}
			++optind;
		}
		} catch (Exception e) {
			println(help);
			exit(1);
		}

		byte[] ibs, obs;
		File fileIn=null, fileOut=null;
		if (in==null && out==null)
			isStdio = true;

	if (isStdio) {
// stdio
		println("Please enter the text: ");
		ibs = scan.nextLine().getBytes();
		if (mode == DECRYPT)
			ibs = Base64.getDecoder().decode(ibs);
		//ibs = System.in.readAllBytes();
	} else {

// in,out file
		if (in == null) {
			println("Missing input file");
			exit(1);
		}
		if (out == null) {
			println("Missing output file");
			exit(1);
		}
		fileIn = new File(in);
		fileOut = new File(out);
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
		ibs = new FileInputStream(fileIn).readAllBytes();
	}

// crypto
		var des = new DESCrypto();
		println("Please enter the password: ");
		String s = scan.nextLine();
		Key finalKey = des.createKey(s);
		if (mode == ENCRYPT)
			obs = des.encrypt(ibs, finalKey);
		else
			obs = des.decrypt(ibs, finalKey);

// output
		if (isStdio) {
			if (mode == ENCRYPT)
				obs = Base64.getEncoder().encode(obs);
			println("\nOutput:");
			println(new String(obs));
		} else {
			var os = new FileOutputStream(fileOut);
			os.write(obs);
		}

// end
		println("\n"+(mode == ENCRYPT ? "Encrypt" : "Decrypt") + " OK");
	}

	static <T> void print(T t) { System.out.print(t); }
	static <T> void println() { System.out.println(); }
	static <T> void println(T t) { System.out.println(t); }
	static void printf(String fmt, Object... args) { System.out.printf(fmt, args); }
	static void exit(int i) { System.exit(i); }
}
