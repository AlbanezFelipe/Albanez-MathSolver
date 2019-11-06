package albanez.mathsolver;

abstract class ExponentiationOperator extends Operator {

	@Override
	int getMathPrecedence() {
		return 2;
	}
}