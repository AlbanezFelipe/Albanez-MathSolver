package albanez.mathsolver;

import java.util.ArrayList;
import java.util.List;

abstract class ExpressionBehavior {
	
	protected StringBuilder expression;
	
	protected List<Symbol> symExpression;
	
	/**
	 * Use for reduce to save operations
	 */
	String[] lastSteps;
	
	boolean hasVariable() {
		if(this.symExpression != null) {
			for(int i=0; i < this.symExpression.size(); i++) {
				if(this.symExpression.get(i) instanceof Variable) {
					return true;
				}
				if(this.symExpression.get(i) instanceof ParenthesesSymbol) {
					return ((ParenthesesSymbol) this.symExpression.get(i)).parentheses.hasVariable();
				}
			}
			return false;
		}	
		throw new IllegalArgumentException("start expression first");
	}
	
	/**
	 * Just does calculation, operation with numbers and variables.
	 * 
	 * @return false if failed to reduce, true if something was reduced calculated
	 */
	boolean reduce() {
		boolean calculated = false;
		
		List<String> steps = new ArrayList<>();
		
		HistoryExpression h = new HistoryExpression();
	
		for(int i=0; i < this.symExpression.size(); i++) {
			
			Symbol s = this.symExpression.get(i);
			
			//if parentheses -> reduce parentheses here; if parentheses reduce return true ; calculated = true;
			if(s instanceof ParenthesesSymbol) {
				ParenthesesSymbol p = ((ParenthesesSymbol) s);
				
				if(p.reduce()) {	
					//RESET
					calculated = true;
					h = new HistoryExpression();
					i = -1;
					continue;
				}
				
				if(p.isFreeToRemoveParentheses()) {
					writeAndRemove(p.getFinalSymbol(), i);
				}
				
				if(i==0 && this.symExpression.size() == 1) {
					this.symExpression = p.getParenthesesExpression();
				}
			}
			//let operations with parentheses for later; so jump parentheses of history expression
			
			boolean precedence = !h.isLastOperatorNull() ? hasOperatorPrecedence(h.lastOperator) : false;
			
			Calculation type = s.isPossibleCalculate(h, precedence, s);
			
			if(type != null) {
				Symbol r;
				
				if(type == Calculation.SIMPLE || type == Calculation.LASTTYPESIMPLE) {
					r = h.lastOperator.operate(
							type == Calculation.SIMPLE ? 
									(Unit) h.lastValue : h.getLastSameType((Unit) s), (Unit) s
					);
					writeAndRemove(r, type == Calculation.SIMPLE ? h.lastValueIndex : h.getLastSameTypeIndex((Unit) s), h.lastOperatorIndex, i);
				}
				
				if(type == Calculation.VARIABLESAMEFRACTIONSIDE) {
					r = h.lastOperator.operate((Variable) h.getLastVariableOfSameFractionSide(((Variable) s).isDenominator), (Variable) s);
					writeAndRemove(r,h.getLastVariableOfSameFractionSideIndex(((Variable) s).isDenominator) ,h.lastOperatorIndex, i);
				}
				
				if(type == Calculation.ALONEOPERATOR || type == Calculation.ALONEOPERATORWITHPLUS) {
					//Alone Operator
					r = ((AdditionOperator) h.lastOperator).aloneOperate((Unit) s);
					writeAndRemove(r, h.lastOperatorIndex, i);
					
					if(type == Calculation.ALONEOPERATORWITHPLUS) {
						write(new Plus(), h.lastOperatorIndex);
					}
					
				}
				
				
				calculated = true;
				h = new HistoryExpression();
				i = -1;
				
				steps.add(printSymExpression());
				
				//FULL DEBUG
				//System.out.println(printSymExpression());
				//System.out.println(type.toString() + "\n");
			}else{
				h.read(s, i);
			}
		}
		
		if(this.symExpression.size() == 2) {
			if(this.symExpression.get(0) instanceof Plus && this.symExpression.get(1) instanceof Variable) {
				writeAndRemove(null, 0);
			}
		}
		
		lastSteps = steps.size() > 0 && calculated ? steps.toArray(new String[steps.size()]) : null;
		
		return calculated;
	}
	
	/**
	 * Check for math precedence
	 * 
	 * @param o: any Operator
	 * @return true: able to calculate. false: another operator has precedence
	 */
	private boolean hasOperatorPrecedence(Operator o) {
		for(int i=0; i < this.symExpression.size(); i++) {
			if(this.symExpression.get(i) instanceof Operator) {
				if(o.getMathPrecedence() < ((Operator) this.symExpression.get(i)).getMathPrecedence()) {
					return false;
				}
			}
		}
		return true;
	}
	
	void start(Variable v) {
		createSymExpression(v);
	}
	
	private void createSymExpression(Variable v) {
		this.symExpression = new ArrayList<>();
		
		for(int i = 0; i < this.expression.length(); i++) {
			char c = this.expression.charAt(i);
			SymbolList s = SymbolReader.possibleSymbol(c);
			
			if(s == SymbolList.VARIABLE) {
				if(c == v.symbol)
					this.symExpression.add(v);
				else
					throw new IllegalArgumentException("Unknown symbol on expression: \"" + c + "\"");
			}
			
			if(s == SymbolList.OPERATOR) {
				
				OperatorList o = SymbolReader.getOperator(c);
				
				if(o == OperatorList.PLUS) {
					this.symExpression.add(new Plus());
				}
				
				if(o == OperatorList.MINUS) {
					this.symExpression.add(new Minus());
				}
				
				if(o == OperatorList.MULTIPLICATION) {
					this.symExpression.add(new Multiplication());
				}
				if(o == OperatorList.DIVISION) {
					this.symExpression.add(new Division());
				}
				if(o == OperatorList.EXPONENTIATION) {
					this.symExpression.add(new Exponentiation());
				}
			}
			
			if(s == SymbolList.NUMBER || s == SymbolList.DECIMAL) {
				
				String number = String.valueOf(c);
				
				int j;
				boolean hasDecimal = number.charAt(0) == '.';
				
				for(j = 1; i+j < this.expression.length(); j++) {
				
					char nextC = this.expression.charAt(i+j);
					
					if(SymbolReader.possibleSymbol(nextC) == SymbolList.NUMBER || nextC == 'E') {
						
						number += String.valueOf(nextC);
					}
					else if(SymbolReader.possibleSymbol(nextC) == SymbolList.DECIMAL) {
						if(hasDecimal) {
							throw new IllegalArgumentException("Invalid Number");
						}
						number += String.valueOf(nextC);
						hasDecimal = true;
					}
					else {
						break;
					}
				}
				i += j-1;
				
				if(number.charAt(number.length()-1) == '.') {
					number += "0";
				}
				
				if(number.charAt(0) == '.') {
					number = "0" + number;
				}
				
				System.out.println(": " + number);
				
				this.symExpression.add(new Number(number.toString()));
			}
			
			if(s == SymbolList.PARENTHESES) {
				int parenthesesCounter = 0;
				boolean parenthesesFinded = false;
				for(int j = 1; i+j < this.expression.length(); j++) {
					
					char nextC = this.expression.charAt(i+j);
					
					if(SymbolReader.possibleSymbol(nextC) == SymbolList.PARENTHESES) {
						if(SymbolReader.isParenthesesEnd(nextC)) {
							
							if(parenthesesCounter == 0) {
								this.symExpression.add(new ParenthesesSymbol(this.expression.toString().substring(i+1, i+j), v));
								i += j;
								parenthesesFinded = true;
								break;
							}else {parenthesesCounter--;}
						} else {parenthesesCounter++;}
					}
				}
				if(!parenthesesFinded) {
					throw new IllegalArgumentException("Invalid Parentheses");
				}
			}
		}
	}
	
	String getCurrentExpression() {
		return this.expression.toString();
	}
	
	protected void write(Symbol s, int index) {
		this.symExpression.add(index, s);
	}
	
	/**
	 * Replace many symbols for a symbol, always insert symbol on smaller index passed
	 * 
	 * Called by reduce and move
	 * 
	 * @param result, Symbol to insert. Null for not insert
	 * @param indexes, indexes to remove
	 */
	protected void writeAndRemove(Symbol result, int ... indexes) {
		indexes = Utils.sortDescending(indexes);
		for(int index : indexes) {
			this.symExpression.remove(index);
		}
		if(result != null) {
			this.symExpression.add(indexes[indexes.length-1], result);
		}
	}
	
	void updateExpressionFromSym() {
		//TODO: USE STRING BUILDER TO IMPROVE
		String e = "";
		for(int i=0; i < this.symExpression.size(); i++) {
			e += this.symExpression.get(i).getSymbol();
		}
		this.expression = new StringBuilder(e);
	}
	
	String printSymExpression() {
		//System.out.println();
		String ex = "";
		for(int i=0; i < this.symExpression.size(); i++ ) {
			
			Symbol s = this.symExpression.get(i);
			//System.out.print(s.getSymbol());
			//TODO:Use String Builder to improve
			ex += s.getSymbol();
		}
		//System.out.println();
		return ex;
	}
}
