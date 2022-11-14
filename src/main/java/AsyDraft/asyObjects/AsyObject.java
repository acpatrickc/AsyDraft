package AsyDraft.asyObjects;

import AsyDraft.AsyProperties.AsyPen;

public interface AsyObject {
	/*
	 * returns asymptote code for this Object
	 */
	public String generateAsyString();
	/*
	 * returns the pen of this AsyObject
	 */
	public AsyPen getAsyPen();
}
