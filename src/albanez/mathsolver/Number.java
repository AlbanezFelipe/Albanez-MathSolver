package albanez.mathsolver;

public class Number extends Unit{
	
	double value;
	
	Number(String number){
		this.value = Double.valueOf(number);
	}
	
	Number(double number){
		this.value = number;
	}

	@Override
	String getSymbol() {
		return this.value < 0 ? "(" + String.valueOf(this.value) + ")" : String.valueOf(this.value);
	}
}
