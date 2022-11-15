package AsyDraft.asyGenerator;

import java.util.ArrayList;

public class AsyExpression {
	/*
	 * args, list of argument objects
	 * expressioname, the name of this expression: name()
	 */
	private ArrayList<Object> args = new ArrayList<>();
	private String expressionname;
	/*
	 * instantiates expression, copies args
	 */
	public AsyExpression(String expname, Object ... args) {
		expressionname = expname;
		for (Object o : args) this.args.add(o);
	}
	/*
	 * places args in parentheses with expression name at beginning;
	 */
	public String generateAsyString() {
		StringBuilder s = new StringBuilder(expressionname + "(");
		for (Object o : args) {
			if (o.getClass().equals(String.class)) {
				s.append(o);
			} else if (o.getClass().equals(AsyExpression.class)) {
				s.append(((AsyExpression) o).generateAsyString());
			}
			s.append(args.indexOf(o) == args.size() - 1 ? "" : ",");
		}
		s.append(")");
		return s.toString();
	}
}
