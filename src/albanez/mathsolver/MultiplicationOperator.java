package albanez.mathsolver;

abstract class MultiplicationOperator extends Operator{
	
	@Override
	int getMathPrecedence() {
		return 1;
	}
}
