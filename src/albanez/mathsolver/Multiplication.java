package albanez.mathsolver;

class Multiplication extends MultiplicationOperator {

	@Override
	Number operate(Number n1, Number n2) {
		return new Number(n1.value * n2.value);
	}

	@Override
	String getSymbol() {
		return "*";
	}

	@Override
	Unit operate(Variable v1, Variable v2) {
		//TODO: FOR WHILE IGNORATE ZERO EXPONENT
		if(!v1.isExponent && !v2.isExponent && Variable.hasSameFractionSide(v1, v2)) {
			//a^b * a^c = a^(b+c) <=> x^2 * x^3 = x^(2+3) <=> x^5
			return new Variable(v1.symbol, v1.symbolOutput, v1.multiplicationValue * v2.multiplicationValue, v1.exponentiationValue + v2.exponentiationValue, v1.isDenominator, v1.isExponent);
		}
		if(v1.isExponent && v2.isExponent && Variable.hasSameFractionSide(v1, v2)) {
			//a^m * b^m = (a*b)^m
			return new Variable(v1.symbol, v1.symbolOutput, v1.multiplicationValue * v2.multiplicationValue, v1.exponentiationValue * v2.exponentiationValue, v1.isDenominator, v1.isExponent);
		}
		
		//TODO: FINISH
		
		throw new IllegalArgumentException("Operation now working yet");//not working yet
	}

	@Override
	Unit operate(Variable v, Number n) {
		return n.value == 0 ? new Number(0) : new Variable(v.symbol, v.symbolOutput, v.multiplicationValue * n.value, v.exponentiationValue, v.isDenominator, v.isExponent);
	}

	@Override
	Unit operate(Number n, Variable v) {
		return n.value == 0 ? new Number(0) : new Variable(v.symbol, v.symbolOutput, v.multiplicationValue * n.value, v.exponentiationValue, v.isDenominator, v.isExponent);
	}

}
