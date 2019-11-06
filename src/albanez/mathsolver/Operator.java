package albanez.mathsolver;

abstract class Operator extends Symbol{
	abstract Number operate(Number n1, Number n2);
	abstract Unit operate(Variable v1, Variable v2);
	abstract Unit operate(Variable v, Number n);
	abstract Unit operate(Number n, Variable v);
	
	Unit operate(Unit u1, Unit u2) {
		if(u1 instanceof Number && u2 instanceof Number) {
			return (Unit) operate((Number) u1, (Number) u2);
		}
		if(u1 instanceof Variable && u2 instanceof Variable) {
			return operate((Variable) u1, (Variable) u2);
		}
		if((u1 instanceof Variable || u1 instanceof Number) && (u2 instanceof Variable || u2 instanceof Number)) {
			if(u1 instanceof Variable) {
				return operate( (Variable) u1, (Number) u2);
			}
			return operate( (Number) u1, (Variable) u2);
		}
		/*if((u1 instanceof Variable || u1 instanceof Number) && (u2 instanceof Variable || u2 instanceof Number)) {
			return (Unit) operate( (u1 instanceof Variable ? (Variable) u1 : (Number) u1), 
				(u2 instanceof Variable ? (Variable) u2 : (Number) u2) );
		}*/
		throw new IllegalArgumentException();
	}
	
	abstract int getMathPrecedence();
	
	@Override
	Calculation isPossibleCalculate(HistoryExpression h, boolean hasOperatorPrecedence, Symbol currentSymbol) {
		return null;
	}
}
