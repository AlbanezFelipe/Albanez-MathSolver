package albanez.mathsolver;

class Division extends MultiplicationOperator {

	//TODO: BE CAREFUL WITH INFINITY NUMBERS. TRY TO BETTER THIS LATER
	
	@Override
	Number operate(Number n1, Number n2) {
		//TODO -1/y = 0; THIS SHOULD BE NO SOLUTION
		return new Number(n1.value / n2.value);
	}

	@Override
	String getSymbol() {
		return "/";
	}

	@Override
	Unit operate(Variable v1, Variable v2) {
		//TODO: FINISH
		throw new IllegalArgumentException("Operation now working yet");//not working yet
	}

	@Override
	Unit operate(Variable v, Number n) {
		if(n.value == 0) {
			//TODO: I DO NOT KNOW HOW TO DEAL WITH THAT. BUT LET'S KEEP THIS WAY
			return new Number(v.multiplicationValue > 0 ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY);
		}
		return new Variable(v.symbol, v.symbolOutput, v.multiplicationValue / n.value, v.exponentiationValue, v.isDenominator, v.isExponent);
	}

	@Override
	Unit operate(Number n, Variable v) {
		//TODO ZERO DIVISIED BY VARIABLE RETURN ZERO. IT'S WRONG (MAYBE CONSIDER THAT VARIABLE CAN BE ZERO), x!=0, FIX THAT LATER
		return n.value == 0 ? new Number(0) : new Variable(v.symbol, v.symbolOutput, n.value / v.multiplicationValue, v.exponentiationValue, !v.isDenominator, v.isExponent);
	}
}
