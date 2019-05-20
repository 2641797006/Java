public class xx1{
	public static void main(String[] args) throws Exception {
		list<String> L = new list<String>();

		for (int i=0; i<100; ++i)
			L.push_back(""+i);

		L.insert(L.rbegin(), "24k");
		L.pop_back();

		L.reverse();
		L.sort((s1, s2)->{ return s1.compareTo(s2); });

		list<String>.iterator it = L.begin();
		while (it != L.end()) {
			print(it.data + " ");
			it = it.next();
		}
		println();
	}

	static <T> void print(T t) { System.out.print(t); }
	static <T> void println() { System.out.println(); }
	static <T> void println(T t) { System.out.println(t); }
	static void printf(String fmt, Object... args) { System.out.printf(fmt, args); }
}
