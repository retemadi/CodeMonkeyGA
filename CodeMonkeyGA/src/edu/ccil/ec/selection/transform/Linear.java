package edu.ccil.ec.selection.transform;

import edu.ccil.ec.Phenotype;
import edu.ccil.ec.Population;
import edu.ccil.ec.FitnessTransformer;
 
/**
 * The linear scaling transformation is a one-variable real linear function S,
 * 		S(x) = a.x + b
 * where the coefficients a and b are real numbers.
 * The coefficients a and b do not depend on the problem in an explicit way.
 * The x represents the fitness of the individual.
 * 
 * @author Reza Etemadi
 *
 * @param <P>
 */
public class Linear implements FitnessTransformer {

	/**
	 * the coefficient a.
	 * the public access is provided in case it should change during the search process
	 */
	public static double a;

	/**
	 * the coefficient b
	 * the public access is provided in case it should change during the search process
	 */
	public static double b;
	
	
	public Linear(double a, double b) {
		Linear.a = a;
		Linear.b = b;
	}
	
	@Override
	public void transform(Population<? extends Phenotype<?,?>> pop) {
		for (int i=0; i<pop.size(); i++) {
			double x = pop.get(i).getFitness().doubleValue();
			x = a*x + b;
			pop.setProportionalFitness(i, x);
		}
		return;
	}

}
