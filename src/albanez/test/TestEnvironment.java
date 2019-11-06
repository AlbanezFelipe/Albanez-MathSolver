package albanez.test;

//import java.util.ArrayList;
//import java.util.List;
//import java.lang.Math;

import albanez.mathsolver.*;

public class TestEnvironment {
	
	public static void main(String[] args) {
		
		/**
		 * "Discipline, hard work, will power.... 
		 * My experience made me so hard that I wasn't even scared of death. 
		 * But one story reflects his desire clearest."
		 * 	– Milkha Singh
		 * 
		 * Push yourself, because no one else is going to do it for you.
		 *
		 * The harder you work for something, the greater you’ll feel when you achieve it.
		 * 
		 * Don’t stop when you’re tired. Stop when you’re done.
		 * 
		 * It’s going to be hard, but hard does not mean impossible.
		 * 
		 * “Be miserable. Or motivate yourself. Whatever has to be done, it’s always your choice.”
		 * 	 – Wayne Dyer
		 * 
		 * “You can never quit. Winners never quit, and quitters never win.”
		 *   – Ted Turner
		 * 
		 */
		
		//Create a Variable
		Variable x = new Variable('x');
		
		//Create Expressions for both sides;
		Expression left = new Expression("7*x^2-9*x");
		Expression right = new Expression("373");
		
		//Expression left = new Expression("z^2");
		//Expression right = new Expression("36-z^2");
		
		//Expression left = new Expression("9*z^2+56^(1/2)-99");
		//Expression right = new Expression("16*(2+4*(2-4))+42^3+9^(1/2)*0.1");
		
		//Expression left = new Expression("27^(1/3)+1");
		//Expression right = new Expression("x+(x+x+(x+x+(x*3+(x)+1*x-(-3*x))))");
				
		//Expression left = new Expression("(3*x-1*(5*x * 2))-3");
		//Expression right = new Expression("9^(1/2)");
		
		//Expression left = new Expression("75*4-11+2^6*4");
		//Expression right = new Expression("5*2+x*8+105*9");
		
		//Expression left = new Expression("3*3^x*4*2^x");
		//Expression right = new Expression("12");
		
		//Expression left = new Expression("2*y^2*1*2");
		//Expression right = new Expression("2+2");	
		
		//Expression left = new Expression("0*x");
		//Expression right = new Expression("-0*2*x*0*x+1*0*x");	
		
		//Expression left = new Expression("0*x");
		//Expression right = new Expression("-2*x+2*x");	
		
		//Expression left = new Expression("2/x");
		//Expression right = new Expression("5E10 + 4E10");
		
		//Expression left = new Expression("x+2+2*5");
		//Expression right = new Expression("9^0.5*5+1");
		
		//Expression left = new Expression("2*3^x + 4*3^x");
		//Expression right = new Expression("-1");
		
		//Expression left = new Expression(".8");
		//Expression right = new Expression("2.*x^2 + 2.*x^2");
		
		
		//TODO: **ADD TRIGONOMETRIC FUNCTIONS
		//TODO: **ADD LOG OPERATIONS
		//TODO: **SOLVE ZERO ANOMALY (x^0)
		//TODO: *OPERATION WITH PARENTHESES AND REMOVE PARENTHESES WHEN UNNEED[LIKE: (x+2)-1]
		//TODO: ***LEARN BHASKARA(WITH DENOMINATOR TOO)
		//TODO: *CREATE A CONNECTION WITH OPERATORS AND UNIT IS POSSIBLE TO CALCULATE
		//TODO: *ADDITIONS WITH VARIABLE AND ZERO, LIKE x-0 AND -7x+0
		//TODO: **FINISH EXPONENTIATION AND (V,V OF MULTIPLICATION OPERATORS) AND FIX UNIT IS POSSIBLE CALCULATE TO AVOID IMPOSSIBLE CALCULATION EXCEPTION
		//TODO: *IMPROVE VARIABLES PRINT -1/x when print is -/x
		//TODO: WHEN CANNOT SOLVE HAD CALLED TRY TO SUBSTITUE VARIABLE FOR 0. LIKE (15*x^2)/(4^x)=0. THIS JUST MAY WORKS IF ONE SIDE IS ZERO
		
		//Expression left = new Expression("2*x");
		//Expression right = new Expression("99+1");
		
		//print(String.valueOf(Double.MAX_VALUE));
		//print(String.valueOf(Double.MIN_VALUE));
		
		//Expression left = new Expression("x");
		//Expression right = new Expression("2.0155 + 2.4155 + 1 + 2. - 02.11 - .1");
		
		//Expression left = new Expression("0/x + x+2 + 6/x + 2 - 6*x + 2 + 7*2 - x -1*x - 2 + x/4 -2 + 3/x");
		//Expression right = new Expression("5");		
		
		//Possible bhaskara
		//Expression left = new Expression("2/x + 4*x + 2/x");
		//Expression right = new Expression("5");		
		
		
		//Expression left = new Expression("10+5-10+3*6");
		//Expression right = new Expression("-x+x+2+3+x+x+9-x-x+x+5-1*9");
				
		//Expression left = new Expression("x");
		//Expression right = new Expression("-3-3/2/4+1/2*11");
		
		//Expression left = new Expression("-2+5-3+x-1+9-2+2");
		//Expression right = new Expression("-0+9-3-1-1+1-1+0-0");
		
		
		//Create a Equation with expressions
		Equation e = new Equation(left, right, x);
		
		//Create a Solver with Equation
		Solver solver = new Solver(e);
		
		//Solve
		Solution s = solver.solve();
		
		printSolution(s);
		print("");
		printSolutionEq(s);
		
		if(s.isRealAndFinitySolution) {
			for(double solution : s.solutions) {print("Answer: " + String.valueOf(solution));}
		}else {
			print(s.cannotSolve.error);
		}
		
		//System.out.println(Math.pow(65536.0, 1.0 / 8.0));
		//System.out.println(Math.sqrt(4.0));
		//System.out.println(Math.cbrt(-125.0));
		//System.out.println(Math.pow(Math.E, Math.log(125.0)/3.0));
		/*for(double sRoot : Utils.nthRoot(65536.0, 8.0)) {
			System.out.println("Root: " + String.valueOf(sRoot));
		}*/
		//String expTeste = "2+3*(5+3)+3";
		//print(expTeste.substring(4+1,8));
		//print(String.valueOf(Character.isAlphabetic('(')));
		
		//print(String.valueOf(null instanceof Equation));
		
		//print(String.valueOf(1.0 / 3.0));
		/*List<String> l = new ArrayList<>();
		l.add("0");
		l.add("1");
		l.add("2");
		l.add("3");
		l.add("4");
		
		for(int i =0; i < l.size(); i++) {
			print(String.valueOf(i) + " - " + l.get(i));
		}
		print("\n");
		l.remove(2);
		//l.add(2, "9");
		
		for(int i =0; i < l.size(); i++) {
			print(String.valueOf(i) + " - " + l.get(i));
		}
		*/
		//print(s.solution);
		
		/*StringBuilder a = new StringBuilder("a daw awd");
		print(a.toString());
		a.append('b');
		print(a.toString());*/
	}
	
	private static void print(String s) {
		System.out.println(s);
	}

	private static void printSolution(Solution s) {
		print("Full Steps: \n");
		for(String step : s.fullSteps) {
			print(step);
		}
	}
	
	private static void printSolutionEq(Solution s) {
		print("Steps Equation: \n");
		for(String step : s.steps) {
			print(step);
		}
	}
}
