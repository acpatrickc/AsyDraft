package AsyDraft.asyGenerator;

import java.util.ArrayList;

public class AsyLinkedPairs {
	/*
	 * pairs, stored pairs in this series of pairs
	 * cyclic, if the pairs loop
	 */
	private ArrayList<AsyPair> pairs = new ArrayList<>();
	private boolean cyclic;
	
	public AsyLinkedPairs(boolean cyclic, AsyPair ... pairs) {
		this.cyclic = cyclic;
		for (AsyPair p : pairs) this.pairs.add(p);
	}
	/*
	 * generates pairs linked by "--"
	 */
	public String generateAsyString() {
		StringBuilder s = new StringBuilder();
		for (AsyPair p : pairs) {
			s.append(p.getAsyExpression().generateAsyString());
			s.append(pairs.indexOf(p) == pairs.size() - 1 ? "" : "--");
		}
		if (cyclic) s.append("--cycle");
		return s.toString();
	}
}
