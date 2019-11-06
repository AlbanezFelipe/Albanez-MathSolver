package albanez.mathsolver;

import java.util.Arrays;
//import java.util.List;
//import java.util.Collections;

import java.lang.Math;

class Utils {
	
	static int[] sortDescending(int[] array) {
		
		int[] r = new int[array.length];
		Arrays.sort(array);
		
		int j=0;
		for(int i = array.length-1; i >= 0; i--) {
			r[j] = array[i];
			j++;
		}
		
		return r;
	}
	
	/**
	 * 
	 * @param base
	 * @param n
	 * @return array of solutions. If return null, no solutions
	 */
	static double[] nthRoot(Double base, Double n) {
		if(base < 0 && n % 2 == 0) {
			return null;
		}
		
		//Double solution = Math.pow(Math.E, Math.log(Math.abs(base))/Math.abs(n));
		Double solution = Math.pow(Math.abs(base), 1.0 / Math.abs(n));
		
		return n % 2 == 0 ? new double[] {solution, solution * -1.0} : 
			new double[] {base < 0 ? solution * -1.0 : solution};
	}
	
	static double[] bhaskara(double a, double b, double c) {
		double[] droot = nthRoot(Math.pow(b, 2) - 4.0 * a * c, 2.0);
		if(droot == null) return null;
		return new double[] {(-b + droot[0])/(2.0 * a), (-b + droot[1])/(2.0 * a)};
		
	}
	
	/*
	static List<Symbol> invertList(List<Symbol> list) {
		Collections.reverse(list);
		return list;
	}*/
}
