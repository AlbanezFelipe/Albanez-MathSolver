package albanez.mathsolver;

public class Variable extends Unit{
	
	private static char defaultSymbol = 'x';
	
	char symbol;
	
	String symbolOutput;
	
	double multiplicationValue = 1;
	
	double exponentiationValue = 1;
	
	boolean isDenominator = false;
	
	boolean isExponent = false;
	
	public Variable() {
		this.symbol = defaultSymbol;
		this.symbolOutput = String.valueOf(defaultSymbol);
	}
	
	private boolean isSymbolValid(char symbol) {
		return symbol != ' ' && Character.isAlphabetic(symbol);
	}
	
	private boolean isSymbolOutputValid(String symbolOutput) {
		return symbolOutput != null && symbolOutput.length() > 0 && Character.isAlphabetic(symbolOutput.charAt(0));
	}
	
	public Variable(char symbol) {
		if(isSymbolValid(symbol)) {
			this.symbol = symbol; 
			this.symbolOutput = String.valueOf(symbol);
		}else {
			throw new IllegalArgumentException("Variable symbol must has one char and be aphabetic");
		}
	}
	
	public Variable(char symbol, String symbolOutput) {
		if(isSymbolValid(symbol) && isSymbolOutputValid(symbolOutput)) {
			this.symbol = symbol; 
			this.symbolOutput = symbolOutput;
		}else {
			throw new IllegalArgumentException();
		}
	}
	
	public Variable(String symbolOutput) {
		if(isSymbolOutputValid(symbolOutput)) {
			this.symbol = defaultSymbol; 
			this.symbolOutput = symbolOutput;
		}else {
			throw new IllegalArgumentException();
		}
	}
	
	Variable(char symbol, String symbolOutput, double multiplicationValue, double exponentiationValue, boolean isDenominator, boolean isExponent){
		this.symbol = symbol;
		this.symbolOutput = symbolOutput;
		if(multiplicationValue == 0) {
			throw new IllegalArgumentException("Invalid Number");
		}
		//TODO IF EXPONENT VALUE == 0; INVALID NUMBER; IF X IS NOT EXPONENT
		this.multiplicationValue = multiplicationValue;
		this.exponentiationValue = exponentiationValue;
		this.isDenominator = isDenominator;
		this.isExponent = isExponent;
	}
	
	boolean isClean() {
		return this.multiplicationValue == 1.0 && this.exponentiationValue == 1.0 && !this.isDenominator && !this.isExponent;
	}
	
	@Override
	String getSymbol() {
		//TODO: ADD BRACKETS WHEN NEGATIVE
		return this.isClean() ? String.valueOf(this.symbolOutput) : 
			//Multiplication
			(this.multiplicationValue == 1 && !this.isDenominator ? "" : 
				(this.multiplicationValue == -1 ? "-" : String.valueOf(this.multiplicationValue))
				 + (this.isDenominator ? "/" : "")) +
			//Exponentiation
			(this.exponentiationValue == 1 ? String.valueOf(this.symbolOutput) :
			(this.isExponent ? "(" + String.valueOf(this.exponentiationValue) + "^" + String.valueOf(this.symbolOutput) + ")" : String.valueOf(this.symbolOutput) + "^" + String.valueOf(this.exponentiationValue)));
	}
	
	static boolean hasSameFractionSide(Variable v1, Variable v2) {
		return v1.isDenominator == v2.isDenominator;
	}
	
	static boolean hasSameExponentBase(Variable v1, Variable v2) {
		return v1.isExponent == v2.isExponent;
	}
}