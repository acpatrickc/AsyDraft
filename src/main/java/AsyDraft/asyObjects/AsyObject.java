package AsyDraft.asyObjects;

import AsyDraft.asyGenerator.AsyPair;
import AsyDraft.asyGenerator.AsyPen;

public interface AsyObject {
	/*
	 * returns asymptote code for this Object
	 */
	public AsyPair[] getAsyPairs();
	/*
	 * returns the pen of this AsyObject
	 */
	public AsyPen getAsyPen();
	/*
	 * returns one string arguments specific to each object
	 */
	public String[] getStringArgs();
}
