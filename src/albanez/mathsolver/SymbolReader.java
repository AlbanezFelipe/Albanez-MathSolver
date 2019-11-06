package albanez.mathsolver;

class SymbolReader {
	
	private static final char plus =  '+';
	private static final char minus = '-';
	private static final char multiplication = '*';
	private static final char division = '/';
	private static final char exponentiation = '^';
	private static final char decimal = '.';
	private static final char parenthesesBegin = '(';
	private static final char parenthesesEnd = ')';
	
	static SymbolList possibleSymbol(char c) {
		
		if(Character.isDigit(c)) {
			return SymbolList.NUMBER;
		}
		
		if(Character.isAlphabetic(c)) {
			return SymbolList.VARIABLE;
		}
		
		if(c == plus || c == minus || c == multiplication || c == division || c == exponentiation) {
			return SymbolList.OPERATOR;
		}
		
		if(c == decimal) {
			return SymbolList.DECIMAL;
		}
		
		if(c == parenthesesBegin || c == parenthesesEnd) {
			return SymbolList.PARENTHESES;
		}
		
		throw new IllegalArgumentException("Invalid symbol in expression");
		
	}
	
	static OperatorList getOperator(char c) {
		if(c == plus) {
			return OperatorList.PLUS;
		}
		if(c == minus) {
			return OperatorList.MINUS;
		}
		if(c == multiplication) {
			return OperatorList.MULTIPLICATION;
		}
		if(c == division) {
			return OperatorList.DIVISION;
		}
		if(c == exponentiation) {
			return OperatorList.EXPONENTIATION;
		}
		throw new IllegalArgumentException("Invalid operator");
	}
	
	static boolean isParenthesesEnd(char c) {
		//TODO: THROW EXPECTION IF c IS NOT PARENTHESES
		return c == parenthesesEnd;
	}
}