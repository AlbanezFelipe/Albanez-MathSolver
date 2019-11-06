package albanez.mathsolver;

class Plus extends AdditionOperator {

	@Override
	Number operate(Number n1, Number n2) {
		return new Number(n1.value + n2.value);
	}

	@Override
	Number aloneOperate(Number n) {
		return n;
	}

	@Override
	String getSymbol() {
		return "+";
	}

	@Override
	Unit operate(Variable v1, Variable v2) {
		if(AdditionOperator.isPossibleCalculate(v1, v2)) {
			return v1.multiplicationValue + v2.multiplicationValue == 0 ? new Number(0) : new Variable(v1.symbol, v1.symbolOutput, v1.multiplicationValue + v2.multiplicationValue, v1.exponentiationValue, v1.isDenominator, v1.isExponent);
		}
		throw new IllegalArgumentException("Impossible Operation");
	}

	@Override
	Variable aloneOperate(Variable v) {
		return v;
	}
}
