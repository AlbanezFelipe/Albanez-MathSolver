package albanez.mathsolver;

import java.util.ArrayList;
import java.util.List;

class StepsManager {

	private List<String> steps;
	
	StepsManager(){
		this.steps = new ArrayList<>();
	}
	
	void addStep(String step){
		steps.add(step);
	}
	
	void addSteps(String log, String[] arraySteps) {
		if(arraySteps != null) {
			for(String s : arraySteps) {
				steps.add(log + s);
			}
		}
	}
	
	String[] getSteps() {
		return steps.toArray(new String[steps.size()]);
	}
}