package albanez.mathsolver;

class Parentheses extends ExpressionBehavior {
	
	Parentheses(String expression){
		this.expression = new StringBuilder(expression.replace(" ", ""));
	}
}
