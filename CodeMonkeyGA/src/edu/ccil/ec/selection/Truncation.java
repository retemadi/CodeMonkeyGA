package edu.ccil.ec.selection;

import java.util.Collections;

import edu.ccil.ec.ESException;
import edu.ccil.ec.ParentSelectionStrategy;
import edu.ccil.ec.Phenotype;
import edu.ccil.ec.Population;
import edu.ccil.ec.tool.Util;


//public class Truncation<P extends Phenotype<?,?>> implements ParentSelectionStrategy<Population<P>> {
public class Truncation extends ParentSelectionStrategy {

	public Truncation(int p_windowSize, int p_windowOutputSize , boolean p_reInsertion) {
		super(p_windowSize,p_windowOutputSize , p_reInsertion);
	}

	/**
	 * Applies the truncation wheel selection deterministically. 
	 * @param window is the randomly selection input window from population
	 * @param output is the output population that widow's output goes in to
	 * @param reInsertion is the boolean flag to allow re-insertion or not 
	 * if true the implication is that the copy of selected individuals goes to output and itself remain in population to potentially show up in some subsequent window (if any) 
	 * if false the implication is that the selected individual is cut from input and goes to output 
	 */
	
	@SuppressWarnings("unchecked")
	@Override
	public <P extends Phenotype<?,?>> void select(Population<P> window, Population<P> output, boolean reInsertion) {

		window.sort();
		//Population<P> output = Util.makeNewInstanceOfType(window);
		//for (int i=0; i<outputSize ; i++) {  // add top "outputSize" # of individuals to the output pop
		//	output.add((P)window.get(window.size()-(i+1)).clone());
		//}

		Population<P> theChosen = (Population<P>)window.getFittest(winOutSize);
		output.addAll((Population<P>)theChosen.clone());
		
		
		//if no reInsertion then remove it from the window since we add the remaining of window back to input
		//by removing them here they won't get a chance to be picked up again by another window from input
		if (!reInsertion) { 
			for (P theOne: theChosen) {
				window.remove(theOne);
			}
		}

		return ;
	}

}
