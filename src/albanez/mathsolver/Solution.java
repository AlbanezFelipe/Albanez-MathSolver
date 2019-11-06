package albanez.mathsolver;

public class Solution {
	
	public double[] solutions;
	public String[] steps;
	public String[] fullSteps;
	
	public boolean isRealAndFinitySolution;
	
	public CannotSolve cannotSolve;
	
	Solution(double[] solutions, String[] steps, String[] fullSteps){
		this.solutions = solutions;
		this.steps = steps;
		this.fullSteps = fullSteps;
		this.isRealAndFinitySolution = true;
	}
	
	Solution(CannotSolve cs, String[] steps, String[] fullSteps){
		this.cannotSolve = cs;
		this.steps = steps;
		this.fullSteps = fullSteps;
		this.isRealAndFinitySolution = false;
	}

}
