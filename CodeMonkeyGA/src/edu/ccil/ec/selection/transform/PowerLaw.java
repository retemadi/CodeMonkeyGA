package edu.ccil.ec.selection.transform;

import edu.ccil.ec.Phenotype;
import edu.ccil.ec.Population;
import edu.ccil.ec.FitnessTransformer;
 
/**
 * The power law scaling transformation is the function S,
 * 		S(x) = x^u  (x raised to the power of u)
 * where u is a real number.
 * Usually, u is chosen close to 1.
 * 
 * @author Reza Etemadi
 *
 * @param <P>
 */
public class PowerLaw implements FitnessTransformer {

	/**
	 * the power 
	 * the public access is provided in case it should change during the search process
	 */
	public static double u; 
	
	
	public PowerLaw(double u) {
		PowerLaw.u = u;
	}
	
	@Override
	public void transform(Population<? extends Phenotype<?,?>> pop) {
		for (int i=0; i<pop.size(); i++) {
			double x = pop.get(i).getFitness().doubleValue();
			x = java.lang.Math.pow(x,u);
			pop.setProportionalFitness(i, x);
		}
		return;
	}

}
