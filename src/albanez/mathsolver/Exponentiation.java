package albanez.mathsolver;

import java.lang.Math;

class Exponentiation extends ExponentiationOperator {

	@Override
	Number operate(Number n1, Number n2) {
		return new Number(Math.pow(n1.value, n2.value));
	}

	@Override
	Unit operate(Variable v1, Variable v2) {
		throw new IllegalArgumentException("Impossible Operation");//Maybe some logs should help(try solve like x^x=4)
	}

	@Override
	Unit operate(Variable v, Number n) {
		//TODO: FINISH
		return new Variable(v.symbol, v.symbolOutput, v.multiplicationValue, n.value, v.isDenominator, false);
	}

	@Override
	Unit operate(Number n, Variable v) {
		//TODO: FINISH
		return new Variable(v.symbol, v.symbolOutput, v.multiplicationValue, n.value, v.isDenominator, true);
	}

	@Override
	String getSymbol() {
		return "^";
	}
}