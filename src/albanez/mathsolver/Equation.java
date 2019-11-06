package albanez.mathsolver;

import java.util.Map;
import java.util.HashMap;

public class Equation {

	Map<Side, Expression> expressions;
	Variable unknown;
	
	public Equation(Expression exp1, Expression exp2, Variable unknown) {
		this.expressions = new HashMap<Side, Expression>();
		this.expressions.put(Side.LEFT, exp1);
		this.expressions.put(Side.RIGHT, exp2);
		this.unknown = unknown;
	}
}
