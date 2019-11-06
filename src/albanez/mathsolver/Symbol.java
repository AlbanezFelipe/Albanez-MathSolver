package albanez.mathsolver;

abstract class Symbol {
	abstract String getSymbol();
	abstract Calculation isPossibleCalculate(HistoryExpression h, boolean hasOperatorPrecedence, Symbol currentSymbol);
}
