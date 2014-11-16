package edu.ccil.ec.selection.transform;

import edu.ccil.ec.Phenotype;
import edu.ccil.ec.Population;
import edu.ccil.ec.FitnessTransformer;
 
/**
 * The Ranking transformation is the function of fitness rank in the population.
 *  
 * @author Reza Etemadi
 *
 * @param <P>
 */
public class Rank implements FitnessTransformer {

	
	@Override
	public void transform(Population<? extends Phenotype<?,?>> pop) {
		pop.sort(); //sort before setting the ranks
		for (int i=0; i<pop.size(); i++) {
			pop.setProportionalFitness(i, i); //sets the rank as each ones proportional fitness
		}
		return;
	}

}
