package edu.ccil.ec.selection.transform;

import edu.ccil.ec.Phenotype;
import edu.ccil.ec.Population;
import edu.ccil.ec.FitnessTransformer;
 
/**
 * This logarithmic scaling transformation is based on
 * Boltzmann selection that uses this function
 * 		tf(x) = e^(f(x)/T)
 * where the parameter T, controlling the selection pressure, decreases
 * during the search process.
 * 
 * @author Reza Etemadi
 *
 * @param <P>
 */
public class Logarithmic implements FitnessTransformer {

	/**
	 * the T (selection pressure) parameter
	 * It is public so it can be modified from outside 
	 * (normally to decrease as search process continues)
	 */
	public static double T; 
	
	public Logarithmic(double T) {
		Logarithmic.T = T;
	}
	
	
	@Override
	public void transform(Population<? extends Phenotype<?,?>> pop) {
		for (int i=0; i<pop.size(); i++) {
			double x = pop.get(i).getFitness().doubleValue();
			x = java.lang.Math.log(x/T);
			pop.setProportionalFitness(i, x);
		}
		return;
	}

	
}
