package edu.ccil.ec.selection;

import edu.ccil.ec.ParentSelectionStrategy;
import edu.ccil.ec.Phenotype;
import edu.ccil.ec.Population;

/**
 * This class is implementation of PSA based on unbiased random selection.
 * this type of selection are used in Evolutionary Strategies.
 * 
 * @author Reza Etemadi
 *
 * @param <P>
 */
//public class UniformRandom<P extends Phenotype<?,?>> implements ParentSelectionStrategy<Population<P>>  {
public class UniformRandom extends ParentSelectionStrategy  {


	
	public UniformRandom(int p_windowSize, int p_windowOutputSize , boolean p_reInsertion) {
		super(p_windowSize,p_windowOutputSize , p_reInsertion);
	}


	/**
	 * Applies selection completely randomly. 
	 * @param window is the randomly selection input window from population
	 * @param output is the output population that widow's output goes in to
	 * @param reInsertion is the boolean flag to allow re-insertion or not 
	 * if true the implication is that the copy of selected individuals goes to output and itself remain in population to potentially show up in some subsequent window (if any) 
	 * if false the implication is that the selected individual is cut from input and goes to output 
	 */
	@Override
	public <P extends Phenotype<?,?>> void select(Population<P> window, Population<P> output, boolean reInsertion)  {
		// the pickRandom method will make use of reInsertion to eliminate from window or not
		output.addAll(window.pickRandom(winOutSize, reInsertion));
		return;
	}

}
