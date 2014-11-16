package edu.ccil.ec;

import edu.ccil.ec.tool.Util;


public abstract class VariationStrategy {

	/**
	 * the boolean flag to indicate if individual are allowed to 
	 * be selected multiple times from mating pool for mating (re-selection). default is false.
	 */
	protected boolean reSelection = false;

	public VariationStrategy(boolean reSelect){
		reSelection = reSelect;
	}
	
	/**
	 * this method applies the variation operators on the input parent pool population
	 * and return an offspring population of offspringPoolSize.
	 * The parents are picked randomly from the parent pool. there is no selection window as PSA.
	 * If re-insertion (reSelection) is disabled the parents are removed from parent pool otherwise their clone will be used
	 * and they remain in parent pool for possible further random pickup.
	 * Internally the process relies on mate() method for each selected individual from mating pool
	 * The real crossover and mutation between the selected individuals is defined inside their mate() method.
	 * @param parentPool  the parent pool, the result of parent selection process
	 * @param offspringSize  the desired size of offspring population
	 * @return
	 */	
	public <P extends Phenotype<?,?>> Population<P> apply(Population<P> parentPool, int offspringPoolSize) {
		//sanity check
		if (!reSelection ) {
			if( parentPool.size() < offspringPoolSize ) {
				throw new ESException(ESException.SanityCheckError,"without reInsertion, The offspringPoolSize ("+offspringPoolSize+") can not be bigger than mating pool("+parentPool.size()+").");
			} else if (offspringPoolSize ==  parentPool.size() && offspringPoolSize % 2 == 1){ //if odd and equal to mating pool size
				throw new ESException(ESException.SanityCheckError,"without reInsertion, The offspringPoolSize ("+offspringPoolSize+") can be eqaul to mating pool("+parentPool.size()+"), if both are an even number.");
			}
		}
		
		Population<P> offspring = Util.makeNewInstanceOfType(parentPool);
		offspring.minimization = parentPool.minimization; // pass minimization flag
		//randomly pick 2 individuals, mate them and add to offspring and repeat till the offspring size is met
		while (offspring.size() < offspringPoolSize) {
			offspring.addAll(mate(parentPool.pickOne(reSelection),parentPool.pickOne(reSelection)));
		}
		if (offspring.size() > offspringPoolSize) { //if offspringSize is an odd number, this can happen
			offspring.remove(offspring.size()-1);
		}
		return offspring;
	}

	/**
	 * This is the method defined what type crossover and/or mutation should be applied between 2 individuals and with what sequence and probability
	 * ideally there are several variation methods are defined in the genotype class that can be called from the genotype interface
	 * An implementation of VariationStrategy class can define any parallel or sequential combination of crossover and mutation here  
	 * @param one
	 * @param two
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public abstract <P extends Phenotype> Population<P> mate(P one, P two);
}

