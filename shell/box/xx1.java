public class xx1{
	public static void main(String[] args) throws Exception {
		String s =  new String();
		if ( System.in.available() != 0 ) {
			byte[] b = System.in.readAllBytes();
			s = new String(b);
		}
		Box box = new Box();
		box.set(s);
		print(box);
	}

	static <T> void print(T t) { System.out.print(t); }
	static <T> void println(T t) { System.out.println(t); }
	static void printf(String fmt, Object... args) { System.out.printf(fmt, args); }
}
