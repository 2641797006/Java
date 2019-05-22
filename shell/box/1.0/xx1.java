import java.util.*;

public class xx1{
	public static void main(String[] args) throws Exception {
		int i, n=1;
		Box box = new Box();
		String s =  new String();
		if ( args.length > 0 ) {
			Scanner scan = new Scanner(args[0]);
			if (scan.hasNextInt())
				n = scan.nextInt();
			if ( n<= 0 )
				n = 1;
		}
//		if ( System.in.available() != 0 ) {
			byte[] b = System.in.readAllBytes();
			s = new String(b);
//		}
		for (i=0; i<n; ++i) {
			box.set(s);
			s = box.toString();
		}
		print(box);
	}

	static <T> void print(T t) { System.out.print(t); }
	static <T> void println(T t) { System.out.println(t); }
	static void printf(String fmt, Object... args) { System.out.printf(fmt, args); }
}
