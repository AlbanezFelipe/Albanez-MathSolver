package albanez.mathsolver;

import java.util.List;

//TODO: Extends Unit to make parentheses operations
class ParenthesesSymbol extends Symbol {
	
	Parentheses parentheses;
	
	ParenthesesSymbol(String expression, Variable variable){
		this.parentheses = new Parentheses(expression);
		this.parentheses.start(variable);
	}

	boolean reduce(){
		return this.parentheses.reduce();
	}
	
	boolean isFreeToRemoveParentheses() {
		return this.parentheses.symExpression.size() == 1;
	}
	
	List<Symbol> getParenthesesExpression(){
		return this.parentheses.symExpression;
	}
	
	/**
	 * Just called when isFreeToRemoveParentheses return true
	 * @return the last symbol
	 */
	Symbol getFinalSymbol() {
		return this.parentheses.symExpression.get(0);
	}
	
	@Override
	String getSymbol() {
		this.parentheses.updateExpressionFromSym();
		return "(" + this.parentheses.getCurrentExpression() + ")";
	}

	@Override
	Calculation isPossibleCalculate(HistoryExpression h, boolean hasOperatorPrecedence, Symbol currentSymbol) {
		return null;
	}

}
