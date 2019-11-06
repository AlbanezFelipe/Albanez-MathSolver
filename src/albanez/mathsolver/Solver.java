package albanez.mathsolver;

import java.util.List;

public class Solver {
	
	private Equation equation;
	private StepsManager steps = new StepsManager();
	private StepsManager fullSteps = new StepsManager();
	
	private static Side defaultAnswerSide = Side.LEFT; //In case of var in both sides
	private Side answerSide;
	
	public Solver(Equation equation) {
		this.equation = equation;
		this.steps = new StepsManager();
	}
	
	public Solution solve() {
		
		System.out.println("Left: "  + equation.expressions.get(Side.LEFT).getCurrentExpression());
		System.out.println("Right: " + equation.expressions.get(Side.RIGHT).getCurrentExpression());
		System.out.println("Equation: " + equation.expressions.get(Side.LEFT).getCurrentExpression() + " = " + equation.expressions.get(Side.RIGHT).getCurrentExpression());
		//fullSteps.addStep("Left: "  + equation.expressions.get(Side.LEFT).getCurrentExpression());
		//fullSteps.addStep("Right: " + equation.expressions.get(Side.RIGHT).getCurrentExpression());
		
		System.out.println("Left: "  + equation.expressions.get(Side.LEFT).hasVariable(equation.unknown));
		System.out.println("Right: " + equation.expressions.get(Side.RIGHT).hasVariable(equation.unknown));
		
		boolean areVarsInLeft = equation.expressions.get(Side.LEFT).hasVariable(equation.unknown);
		boolean areVarsInRight = equation.expressions.get(Side.RIGHT).hasVariable(equation.unknown);
		
		//Expressions starts to generate a data of string
		equation.expressions.get(Side.LEFT).start(equation.unknown);
		equation.expressions.get(Side.RIGHT).start(equation.unknown);
		
		System.out.println("\nDEBUG\n");
		
		/*
		 * First Case: x in both sides
		 */
		if(areVarsInLeft && areVarsInRight) {
			this.answerSide = defaultAnswerSide; 
		}
		/*
		 * Second Case: x just in left
		 */
		else if(areVarsInLeft && !areVarsInRight) {
			this.answerSide = Side.LEFT;
		}
		/*
		 * Third Case: x just in right 
		 */
		else if(areVarsInRight && !areVarsInLeft) {
			this.answerSide = Side.RIGHT;
		}
		
		//Original Equation (First step)
		//TODO: Do not update to get original equation
		saveSteps();
		printEquation();
		
		//DEBUG
		//System.out.println(getEquation());
			
		int limit = 7;
		int j = 0;
		//Equation Process
		while(
				!equation.expressions.get(answerSide).isFinished(true) ||
				!equation.expressions.get(Side.getOtherSide(this.answerSide)).isFinished(false)
		) {
			j++;
			if(j > limit) {
				System.out.println("break forced");
				throw new IllegalArgumentException("Cannot solve");
			}
		
			//Reduce
			while(true) {
				if(
					!equation.expressions.get(Side.getOtherSide(this.answerSide)).reduce() &&
					!equation.expressions.get(answerSide).reduce()
				) break;
				//Save Steps
				fullSteps.addSteps((this.answerSide != Side.LEFT ? "Left: " : "Right: "), equation.expressions.get(Side.getOtherSide(this.answerSide)).lastSteps);
				fullSteps.addSteps((this.answerSide == Side.LEFT ? "Left: " : "Right: "), equation.expressions.get(answerSide).lastSteps);
			}
			
			//Check for anomalies(when does not have variable in both side)
			if(!equation.expressions.get(answerSide).hasVariable() && !equation.expressions.get(Side.getOtherSide(this.answerSide)).hasVariable()) {
				saveSteps();
				return new Solution(new CannotSolve(isInfinityOrNoSolution() ? "Infinity Solutions" : "No Real Solution"), steps.getSteps(), fullSteps.getSteps());
			}
			
			//Move
			List<Symbol> numberMove = equation.expressions.get(answerSide).move(true);
			List<Symbol> variableMove = equation.expressions.get(Side.getOtherSide(this.answerSide)).move(false);
			
			if(numberMove != null) {
				equation.expressions.get(Side.getOtherSide(this.answerSide)).receive(false, Expression.invert(numberMove));
			}
			
			if(variableMove != null) {
				equation.expressions.get(answerSide).receive(false, Expression.invert(variableMove));
			}
			
			//Pass Multiplication Value
			if(equation.expressions.get(answerSide).isMultiplicationIsolated() && 
					equation.expressions.get(Side.getOtherSide(this.answerSide)).isFinished(false)) {
				equation.expressions.get(Side.getOtherSide(this.answerSide)).receive(equation.expressions.get(answerSide).isDenominator(),
						equation.expressions.get(answerSide).getVariableMultiplicationValue());
			}
			
			//Special Cases
			
			//x^(number)=(number)
			if(equation.expressions.get(answerSide).isExponentIsolated() && 
					equation.expressions.get(Side.getOtherSide(this.answerSide)).isFinished(false)) {
				
				saveSteps();
				
				double[] solutions = Utils.nthRoot(equation.expressions.get(Side.getOtherSide(this.answerSide)).getAnswer(), 
						equation.expressions.get(answerSide).getExponentIsolated());
				
				if(solutions != null) {return new Solution(solutions, steps.getSteps(), fullSteps.getSteps());}
					
				return new Solution(new CannotSolve("Negative Root"), steps.getSteps(), fullSteps.getSteps());
			}
			
			//(number)^x = (number)
			
			//Bhaskara -> (number)*x^2 (+\-) (number)*x (+\-) = (number) 
			if(equation.expressions.get(answerSide).isPossibleBhaskaraSolution() && 
					equation.expressions.get(Side.getOtherSide(this.answerSide)).isFinished(false)) {
				double[] ab = equation.expressions.get(answerSide).getBhaskaraConstants();
				double c = equation.expressions.get(Side.getOtherSide(this.answerSide)).getAnswer() * -1;
				return new Solution(Utils.bhaskara(ab[0], ab[1], c), steps.getSteps(), fullSteps.getSteps());
			}
			
			saveSteps();
			
			//DEBUG
			printEquation();
		}
		
		return new Solution(new double[] {equation.expressions.get(Side.getOtherSide(this.answerSide)).getAnswer()}, steps.getSteps(), fullSteps.getSteps());
	}
	
	private void saveSteps() {
		updateEquation();
		steps.addStep(getEquation());
	}
	
	private void updateEquation() {
		equation.expressions.get(this.answerSide).updateExpressionFromSym();
		equation.expressions.get(Side.getOtherSide(this.answerSide)).updateExpressionFromSym();
	}
	
	private String getEquation() {
		return equation.expressions.get(Side.LEFT).getCurrentExpression() + " = " + equation.expressions.get(Side.RIGHT).getCurrentExpression();
	}
	
	/**
	 * Just Called when anomaly has found
	 * @return true=Infinity ; false=No Solution
	 */
	private boolean isInfinityOrNoSolution() {
		return equation.expressions.get(Side.LEFT).getAnswer() == equation.expressions.get(Side.RIGHT).getAnswer();
	}
	
	// **********
	// DEBUG
	// **********
	private void printEquation() {
		System.out.println("Eq: " + getEquation() + "\n");
	}
}