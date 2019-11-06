package albanez.mathsolver;

abstract class Unit extends Symbol {
	
	@Override
	Calculation isPossibleCalculate(HistoryExpression h, boolean hasOperatorPrecedence, Symbol currentSymbol) {
		
		if(!h.isLastOperatorNull()) {
		
			if(!h.isLastValueNull() && hasOperatorPrecedence) {
				
				if(!h.isLastValueOfSameType((Unit) currentSymbol) && (h.lastOperator instanceof MultiplicationOperator || h.lastOperator instanceof ExponentiationOperator)) {
					//Number and Variable Calculation
					return Calculation.SIMPLE;
				}
				
				if(h.isLastValueOfSameType((Unit) currentSymbol)) {
					if(currentSymbol instanceof Variable && h.lastOperator instanceof AdditionOperator) {
						if(AdditionOperator.isPossibleCalculate((Variable) currentSymbol, (Variable) h.lastValue)) {
							return Calculation.SIMPLE;
						}
					}else {
						//Normal Calculation
						return Calculation.SIMPLE;
					}
				}
			}
			
			if((h.isLastValueNull() || !h.isLastValueOfSameType((Unit) currentSymbol)) && h.lastOperator instanceof AdditionOperator) {
				
				if(h.isLastValueNull()) {
					//Alone Operator
					return Calculation.ALONEOPERATOR;
				}
				
				if(!h.isLastValueOfSameType((Unit) currentSymbol) && h.lastOperator instanceof Minus) {
					//Alone Operator with plus
					return Calculation.ALONEOPERATORWITHPLUS;
				}
				
			}
			
			if(!h.isLastSameTypeNull((Unit) currentSymbol) && hasOperatorPrecedence) {
				
				if(currentSymbol instanceof Variable && h.lastOperator instanceof AdditionOperator) {
					if(!AdditionOperator.isPossibleCalculate((Variable) currentSymbol, (Variable) h.getLastSameType((Unit) currentSymbol))) {
						
						//if(!h.isLastVariableOfSameFractionSideNull(((Variable) currentSymbol).isDenominator)) {
						//	return Calculation.VARIABLESAMEFRACTIONSIDE;
						//}
						
						return null;
					}
				}
				return Calculation.LASTTYPESIMPLE;
			}
		}
		return null;
	}
}
