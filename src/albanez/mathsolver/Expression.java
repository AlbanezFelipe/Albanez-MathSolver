package albanez.mathsolver;

import java.util.ArrayList;
import java.util.List;

public class Expression extends ExpressionBehavior {
	
	public Expression(String expression) {
		this.expression = new StringBuilder(expression.replace(" ", ""));
	}

	boolean hasVariable(Variable v) {
		for(int i = 0; i < this.expression.length(); i++) {
			if(v.symbol == this.expression.charAt(i)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * check if expression does not have any other symbol to calculate
	 * 
	 * @return true if expression only have one symbol.
	 */
	boolean isFinished(boolean isAnswerSide) {
		//printSymExpression();
		return this.symExpression.size() == 1 && (isAnswerSide ? 
				this.symExpression.get(0) instanceof Variable && 
				((Variable) this.symExpression.get(0)).isClean()
				: this.symExpression.get(0) instanceof Number);
		
	}
	
	private boolean isVariableIsolated() {
		return this.symExpression.size() == 1 && this.symExpression.get(0) instanceof Variable;
	}
	
	/**
	 * Just for answer side
	 * @return true for be able to pass multiplication value
	 */
	boolean isMultiplicationIsolated() {
		//System.out.println("M: " + ((Variable) this.symExpression.get(0)).multiplicationValue);
		return isVariableIsolated() && 
				(((Variable) this.symExpression.get(0)).multiplicationValue != 1.0 || (((Variable) this.symExpression.get(0)).isDenominator));
	}
	
	/**
	 * Just called when isMultiplicationIsolated return true
	 */
	boolean isDenominator() {
		return ((Variable) this.symExpression.get(0)).isDenominator;
	}
	
	/**
	 * Just when finished and for number side
	 * @return
	 */
	double getAnswer() {
		return ((Number) this.symExpression.get(0)).value;
	}
	
	/**
	 * Just called when isMultiplicationIsolated return true
	 * @return
	 */
	List<Symbol> getVariableMultiplicationValue(){
		List<Symbol> v = new ArrayList<>();
		v.add(new Division());
		v.add(new Number(
				((Variable) this.symExpression.get(0)).multiplicationValue
				));
		//v = ((Variable) this.symExpression.get(0)).isDenominator ? Utils.invertList(v) : v;
		((Variable) this.symExpression.get(0)).multiplicationValue = 1.0;
		((Variable) this.symExpression.get(0)).isDenominator = false;
		return v;
	}
	
	/**
	 * Just for answer side
	 * @return true if variable only has a exponent number. like x^2 or x^0.5, not -x^2 or 1/x^3
	 */
	boolean isExponentIsolated() {
		return isVariableIsolated() && 
				!((Variable) this.symExpression.get(0)).isExponent &&
				!((Variable) this.symExpression.get(0)).isDenominator &&
				((Variable) this.symExpression.get(0)).multiplicationValue == 1.0 &&
				((Variable) this.symExpression.get(0)).exponentiationValue != 1.0;
	}
	
	/**
	 * Just called if isExponentIsolated return true
	 * @return
	 */
	double getExponentIsolated() {
		return ((Variable) this.symExpression.get(0)).exponentiationValue;
	}
	
	/**
	 * Move numbers or variables to other side
	 * 
	 * @param keepVariable. True for move numbers, false for move variable
	 * @return Symbolic Expression to be pass to another, null if nothing have to be passed 
	 */
	List<Symbol> move(boolean keepVariable) {
		
		HistoryExpression h = new HistoryExpression();
			
		List<Symbol> symToPass = new ArrayList<>();
		
		for(int i=0; i < this.symExpression.size(); i++) {
			Symbol s = this.symExpression.get(i);
			
			if((s instanceof Number && keepVariable) || (s instanceof Variable && !keepVariable)) {
				
				if(!h.isLastOperatorNull()) {
					symToPass.add(h.lastOperator);
					symToPass.add(s);
					
					writeAndRemove(null, h.lastOperatorIndex, i);	
					
				}else {
					symToPass.add(new Plus());//this is for cases like (-5)+x For work this must be +(-5)+x
					symToPass.add(s);
					writeAndRemove(null, i);
				}
				i = -1;
			}
			h.read(s, i);
		}
		
		if(symExpression.size() == 0) {
			write(new Number(0), 0);
		}

		return symToPass.isEmpty() ? null : symToPass;
		
	}
	
	/**
	 * Receive symbols to expression
	 * 
	 * @param expression to be added
	 */
	void receive(boolean onStart, List<Symbol> expression){
		
		for(int i=0; i < expression.size(); i++) {
			if(!onStart)
				this.symExpression.add(expression.get(i));
			else
				this.symExpression.add(0, expression.get(i));
		}
	}
	
	/**
	 * Just works for var side
	 * @return
	 */
	boolean isPossibleBhaskaraSolution() {
		if(this.symExpression.size() != 3) {
			return false;
		}
		boolean a = false;
		boolean b = false;
		for(int i=0; i < this.symExpression.size(); i++) {
			Symbol s = this.symExpression.get(i);
			if(s instanceof Variable || s instanceof AdditionOperator) {
				if(s instanceof Variable) {
					Variable v = ((Variable) s);
					if(!v.isDenominator && !v.isExponent) {
						if(v.exponentiationValue == 1) b = !b; else 
						if(v.exponentiationValue == 2) a = !a; else
						return false;
						continue;
					}
					return false;
				}
			} else return false;
		}
		return a && b;
	}
	
	/**
	 * just called when isPossibleBhaskaraSolution return true
	 * @return
	 */
	double[] getBhaskaraConstants() {
		//TODO check if bx+ax^2
		return new double[] {((Variable) this.symExpression.get(0)).multiplicationValue,
				((Variable) this.symExpression.get(2)).multiplicationValue * (this.symExpression.get(1) instanceof Minus ? -1 : 1)};
	}
	
	/**
	 * For while it's just work with plus and minus
	 * @param l
	 * @return
	 */
	static List<Symbol> invert(List<Symbol> l){
		List<Symbol> r = new ArrayList<>();
		for(int i=0; i < l.size(); i++) {
			if(l.get(i) instanceof AdditionOperator) {
				if(l.get(i) instanceof Plus) {
					r.add(i, new Minus());
				}else{
					r.add(i, new Plus());
				}
			}
			if(l.get(i) instanceof Number || l.get(i) instanceof Variable) {	
				r.add(i, l.get(i));
				//r.add(i, new Number(String.valueOf(Double.valueOf(((Number) l.get(i)).number) * -1))); 
				//r.add(i, new Plus());
			}
		}
		
		return r;
	}
}