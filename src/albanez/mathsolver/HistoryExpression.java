package albanez.mathsolver;

class HistoryExpression {
	
	Unit lastValue = null;
	Operator lastOperator = null;
	
	Number lastNumber = null;
	Variable lastVariable = null;
	
	Variable lastDenominatorVariable = null;
	Variable lastNumeratorVariable = null;
	
	int lastValueIndex;
	int lastOperatorIndex;
	
	int lastNumberIndex;
	int lastVariableIndex;
	
	int lastDenominatorVariableIndex;
	int lastNumeratorVariableIndex;

	void read(Symbol s, int index){
		if(s instanceof Number || s instanceof Variable) {
			
			if(s instanceof Number) {
				this.lastNumber = (Number) s;
				this.lastNumberIndex = index;
			}
			
			if(s instanceof Variable) {
				
				this.lastVariable = (Variable) s;
				this.lastVariableIndex = index;
				
				if(((Variable) s).isDenominator) {
					this.lastDenominatorVariable = (Variable) s;
					this.lastDenominatorVariableIndex = index;
				}else {
					this.lastNumeratorVariable = (Variable) s;
					this.lastNumeratorVariableIndex = index;
				}
			}
			
			this.lastValue = (Unit) s;
			this.lastValueIndex = index;
		}
		
		if(s instanceof Operator) {
			this.lastOperator = (Operator) s;
			this.lastOperatorIndex = index;
		}
	}
	
	Unit getLastSameType(Unit u) {
		if(u instanceof Number) {
			return (Unit) this.lastNumber;
		}
		if(u instanceof Variable) {
			return (Unit) this.lastVariable;
		}
		throw new IllegalArgumentException();
	}
	
	int getLastSameTypeIndex(Unit u) {
		if(u instanceof Number) {
			return this.lastNumberIndex;
		}
		if(u instanceof Variable) {
			return this.lastVariableIndex;
		}
		throw new IllegalArgumentException();
	}
	
	boolean isAllNull() {
		return this.lastValue == null && this.lastOperator == null;
	}
	
	boolean isAnyNull() {
		return this.lastValue == null || this.lastOperator == null;
	}
	
	boolean isLastValueNull() {
		return this.lastValue == null;
	}
	
	boolean isLastOperatorNull() {
		return this.lastOperator == null;
	}
	
	boolean isLastValueANumber() {
		return this.lastValue instanceof Number;
	}
	
	boolean isLastValueAVariable() {
		return this.lastValue instanceof Variable;
	}
	
	boolean isLastNumberNull() {
		return this.lastNumber == null;
	}
	
	boolean isLastVariableNull() {
		return this.lastVariable == null;
	}
	
	boolean isLastDenominatorVariableNull() {
		return this.lastDenominatorVariable == null;
	}
	
	boolean isLastNumeratorVariableNull() {
		return this.lastNumeratorVariable == null;
	}
	
	Variable getLastVariableOfSameFractionSide(boolean isDenominator) {
		return isDenominator ? this.lastDenominatorVariable : this.lastNumeratorVariable;
	}
	
	int getLastVariableOfSameFractionSideIndex(boolean isDenominator) {
		return isDenominator ? this.lastDenominatorVariableIndex : this.lastNumeratorVariableIndex;
	}
	
	boolean isLastVariableOfSameFractionSideNull(boolean isDenominator) {
		return isDenominator ? this.lastDenominatorVariable == null : this.lastNumeratorVariable == null;
	}
	
	boolean isLastValueOfSameType(Unit u) {

		if(this.lastValue != null) {
			return (this.lastValue instanceof Variable ? SymbolList.VARIABLE : SymbolList.NUMBER) == 
					(u instanceof Variable ? SymbolList.VARIABLE : SymbolList.NUMBER);
		}
		return false;
	}
	
	boolean isLastSameTypeNull(Unit u) {
		SymbolList s = u instanceof Variable ? SymbolList.VARIABLE : SymbolList.NUMBER;
		
		if(s == SymbolList.VARIABLE) {
			return this.isLastVariableNull();
		}
		if(s == SymbolList.NUMBER) {
			return this.isLastNumberNull();
		}
		return false;
	}
}
