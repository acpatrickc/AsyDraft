package AsyDraft.ui;

public abstract class MathUtils {
	/*
	 * converts from screen coordinates on the drawing plane to normal cartesian coordinates
	 */
	public static double toCartesian(double in, int gridwidth) {
		return gridwidth - in;
	}
}
