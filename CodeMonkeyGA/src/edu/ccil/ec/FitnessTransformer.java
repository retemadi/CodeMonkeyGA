package edu.ccil.ec;


/**
 * This interface should be implemented if the raw fitness is not ideal for porportional selection
 * and a transformed version of fitness (e.g. scaled, rank, etc) must be used
 * An implementor of this interface must register itself through Factory to be recognized
 * for calculating the selection proportion of each individual
 * the calculated value for each individual must be stored in the poplulation's proportionalFitness
 * HashMap using the individual's population ID as the key.
 *  
 * @author Reza Etemadi
 *
 * @param <P>
 */
public interface FitnessTransformer {
	
	/**
	 * iterates through all individuals in the given population and
	 * calculate the transformed value of the fitness and store 
	 * in the proportional fitness for that individual.
	 * 
	 * @param pop : the population that has to go through fitness transformation
	 * @return
	 */
	public void transform(Population<? extends Phenotype<?,?>> pop);

}
