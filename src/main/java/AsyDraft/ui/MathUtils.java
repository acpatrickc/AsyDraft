package AsyDraft.ui;

public abstract class MathUtils {
	/*
	 * converts from screen coordinates on the drawing plane to normal cartesian coordinates
	 */
	public static double toCartesian(double in, int gridwidth) {
		return gridwidth - in;
	}
	/*
	 * converts number to alphabetical
	 * basically a base converter b10 to b26, store remainder, repeat with quotient
	 */
	public static String toAlphabet(int i) {
		return i < 1 ? "" : toAlphabet((i - 1) / 26) + (char) (65 + (i - 1) % 26);
	}
}
