package edu.ccil.ec;

import edu.ccil.ec.tool.Util;

/**
 * This class creates the next generation population by apply selection algorithms
 * to current and offspring population and a combined population of those two
 * (therefore 3 possible population) while each creates a portion of next generation
 * the selection within each of the three population follows the same logic as Parent Selection
 * 
 * @author RezaEtemadi
 *
 */
public abstract class SurvivalSelectionStrategy {
//P extends Population<? extends Genotype<?>> , 
//							G extends Generation<? extends Genotype<?>> > >{

	protected ParentSelectionStrategy currentPopSelection;
	protected ParentSelectionStrategy offspringPopSelection;
	protected ParentSelectionStrategy combinedPopSelection;
	
	protected int currentPopOutputSize;
	protected int offspringPopOutputSize;
	protected int combinedPopOutputSize;
	protected int elitePopSize;
	protected int randomPopSize;
	protected int nextGenSize;  //normally equivalent to Evoltuion.generationSize
	
	/**
	 * to register different selection windows and set their selection algorithms and transformer (if needed)
	 * and the expected output size 
	 */
	protected abstract void registration();
	
		
	/**
	 * default constructor
	 * it calls registration and validation
	 */
	public SurvivalSelectionStrategy(){
		super();
		nextGenSize = Evolution.generationSize;
		registration();
	}
	
	/**
	 * it returns the next generation from a intermediate pool and current generation based
	 * on the implemented strategy for survival selection
	 * The selection can be done on current and offspring population separately or combined, also by adding Elitism and random injection
	 * @param currentGen , offspringPool
	 * @return
	 */
	public <P extends Phenotype<?,?>> Population<P> apply(Population<P> currentGen, Population<P> offspringPool){

		if (currentPopSelection == null && offspringPopSelection == null && combinedPopSelection == null && elitePopSize <1 && randomPopSize <1) {
			throw new ESException(ESException.SurvivalMissing ,"No survivor selection branch is defined for survival selection");
		}
		int total = currentPopOutputSize+offspringPopOutputSize+combinedPopOutputSize+elitePopSize+randomPopSize;
		if (  total != currentGen.size()) {
			throw new ESException(ESException.SanityCheckError,
					"The sum of survivor selection outputs ("+total+")should be equal to the generation size ("+currentGen.size()+")." +
					"Modify one or more of these sizes in your Survivor Strategy so the sum of all equals the generation size:\ncurrentPopOutputSize ="+currentPopOutputSize+
					"\noffspringPopOutputSize="+offspringPopOutputSize+
					"\ncombinedPopOutputSize="+combinedPopOutputSize+
					"\nelitePopSize="+elitePopSize+
					"\nrandomPopSize="+randomPopSize);
		}
		
		Population<P> nextGen = Util.makeNewInstanceOfType(currentGen);
		nextGen.minimization = currentGen.minimization; // pass the minimization flag
		//for current generation branch
		if (currentPopSelection != null) {
			nextGen.addAll(currentPopSelection.apply(currentGen, currentPopOutputSize));
		}
		//for offspring pool branch
		if (offspringPopSelection != null) {
			nextGen.addAll(offspringPopSelection.apply(offspringPool, offspringPopOutputSize));
		}
		//for combined pool branch
		if (combinedPopSelection != null) {
			Population<P> bigPool = Util.makeNewInstanceOfType(currentGen);
			bigPool.addAll(currentGen);
			bigPool.addAll(offspringPool);
			nextGen.addAll(combinedPopSelection.apply(bigPool, combinedPopOutputSize));
		}
		//for Elitism branch
		if (elitePopSize > 0) {
			nextGen.addAll(currentGen.getFittest(elitePopSize));
		}
		//for random injection branch
		if (randomPopSize > 0) {
			Population<P> randomPop = Util.makeNewInstanceOfType(currentGen);
			for (int i=0; i<randomPopSize; i++){
				@SuppressWarnings("unchecked")
				P phenome = (P)Util.makeNewInstanceOfType(Evolution.obtain(Phenotype.class));  //type safety warning suppressed 
				phenome.getGenome().fillRandom();
				randomPop.add(phenome);
			}
			randomPop.evaluate(); //it is needed for termination criteria in next round
			nextGen.addAll(randomPop);
		}
		
		return nextGen;
	}
}
