package albanez.mathsolver;

abstract class AdditionOperator extends Operator {
	abstract Number aloneOperate(Number n);
	abstract Variable aloneOperate(Variable v);
	
	Unit aloneOperate(Unit u) {
		if(u instanceof Number) {
			return aloneOperate((Number) u);
		}
		if(u instanceof Variable) {
			return aloneOperate((Variable) u);
		}
		throw new IllegalArgumentException();
	}
	
	@Override
	int getMathPrecedence() {
		return 0;
	}
	
	@Override
	Unit operate(Variable v, Number n) {
		if(n.value == 0) {
			return v;
		}
		throw new IllegalArgumentException("Impossible Operation");
	}
	
	@Override
	Unit operate(Number n, Variable v) {
		//0 - v error
		if(n.value == 0) {
			return v;
		}
		throw new IllegalArgumentException("Impossible Operation");
	}
	
	static boolean isPossibleCalculate(Variable v1, Variable v2){
		return Variable.hasSameFractionSide(v1, v2) && Variable.hasSameExponentBase(v1, v2) && v1.exponentiationValue == v2.exponentiationValue;
	}
}
