package AsyDraft.ui;

public abstract class MathUtils {
	/*
	 * converts from screen coordinates on the drawing plane to normal cartesian coordinates
	 */
	public static double toCartesian(double in, int gridwidth) {
		return gridwidth - in;
	}
	/*
	 * converts number to alphabetical with subscript
	 * 1 indexed
	 */
	public static String toTexAlphabet(int i) {
		return i < 1 ? "" : (char) (65 + (i - 1) % 26) + ((i - 1) / 26 == 0 ? "" : "_{" + (i - 1) / 26 + "}");
	}
}
