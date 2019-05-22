public class Box{
	private StringBuffer sb;
	private int align;
	private char px;
	private String pa;
	private String pyl, pyr;
	private int width;

	public Box() {
		this('/', "////", "/**/  ", "  /**/");
	}

	public Box(char x, String a, String yl, String yr) {
		px = x;
		pa = a;
		pyl = yl;
		pyr = yr;
		align = 0;
		sb = new StringBuffer();
	}

	public void setAlign(int align) {
		this.align = align;
	}

	public void line(int n) {
		int i, w;
		w = n + pyl.length() + pyr.length() - 2 * pa.length();
		sb.append(pa);
		for (i=0; i<w; ++i)
			sb.append(px);
		sb.append(pa).append('\n');
	}

	public void line(String s, int pos1, int pos2) {
		int i, wd, left, right;
		// wd = width - (pos2 - pos1);
		wd = width - widthFix(s, pos1, pos2);
		if (align < 0) {
			left = 0;
			right = wd;
		} else if (align > 0) {
			left = wd;
			right = 0;
		} else {
			left = wd/2;
			right = wd - left;
		}
		sb.append(pyl);
		for (i=0; i<left; ++i)
			sb.append(' ');
		sb.append(s.substring(pos1, pos2));
		for (i=0; i<right; ++i)
			sb.append(' ');
		sb.append(pyr).append('\n');
	}

	void set(String s) {
		int pos1=0, pos2;
		sb.setLength(0);
		stringWidth(s);
		line(width);
		for ( ; pos1<s.length(); ) {
			pos2 = s.indexOf('\n', pos1);
			if (pos2 == -1)
				pos2 = s.length();
			line(s, pos1, pos2);
			pos1 = pos2 + 1;
		}
		line(width);
	}

	int stringWidth(String s) {
		int pos1=0, pos2, subw, width=0;
		for ( ; pos1<s.length(); ) {
			pos2 = s.indexOf('\n', pos1);
			if (pos2 == -1)
				pos2 = s.length();
		//	subw = pos2 - pos1;
			subw = widthFix(s, pos1, pos2);
			if (subw > width)
				width = subw;
			pos1 = pos2 + 1;
		}
		this.width = width;
		return width;
	}

	int widthFix(String s, int pos1, int pos2) {
		int i, width = pos2 - pos1;
		for (i=pos1; i<pos2; ++i)
			if ( isWchar(s.charAt(i)) )
				++width;
		return width;
	}

	boolean isWchar(char c) {
		if ( c > '\u00ff')
			return true;
//		if ( '\u4e00' <= c && c <= '\u9fa5' )
//			return true;
		return false;
	}

	public String toString() {
		return sb.toString();
	}
}
