package edu.ccil.ec.selection;

import edu.ccil.ec.ESException;
import edu.ccil.ec.ParentSelectionStrategy;
import edu.ccil.ec.Phenotype;
import edu.ccil.ec.Population;
import edu.ccil.ec.selection.transform.Rank;
import edu.ccil.ec.tool.Util;

/**
	This is implementation of roulette wheel selection. 
	If fitnesses (or proportional fitnesses) are all positive and the goal is maximization of it then then everything is good.
	If there are mix of positive and negative fitnesses are involved then we shift them all into positive side before rolling
	If the goal is minimization then we certainly need fitness transformation so that smaller fitness value gets bigger proportion 
	for example by using rank of individual in the population or using the reverse value of fitness as proportion (watch out for 0.0 fitness).
	If minimization is set and no transformer is registered we automatically use normalized and reverse value of fitness   
*/
public class Proportional extends ParentSelectionStrategy {

	public Proportional(int p_windowSize, int p_windowOutputSize , boolean p_reInsertion) {
		super(p_windowSize,p_windowOutputSize , p_reInsertion);
	}

	/**
	 * Applies roulette wheel selection proportionally. 
	 * @param window is the randomly selection input window from population
	 * @param output is the output population that widow's output goes in to
	 * @param reInsertion is the boolean flag to allow re-insertion or not 
	 * if true the implication is that the copy of selected individuals goes to output and itself remain in population to potentially show up in some subsequent window (if any) 
	 * if false the implication is that the selected individual is cut from input and goes to output 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <P extends Phenotype<?,?>> void select(Population<P> window, Population<P> output, boolean reInsertion) {
		
		// if some fitness have negative value then all fitness have to be shifted to positive side 
		//otherwise the total fitness value of all individual will not be correct in relation to proportion of each individual 
		
		window.sort();
		double lowestFitness = window.getProportionalFitness(0);
		// if there is negative fitness, we need to shift all to positive side
		if (lowestFitness<0.0) {
			for (int i=0; i<window.size(); i++){
				window.setProportionalFitness(i, window.getProportionalFitness(i)+lowestFitness);
			}
		}

/*
		//for minimization Roulette Wheel can not be used unless the transformed fitness is used so that proportion for smaller fitnesses (the better ones) are bigger.
		//if no transformer is registered it means no transformation applied to window before it is passed to this method, 
		//one approach was to set the transformer of this class to Rank() and call it on window.
		if (window.minimization && transformer == null) { 
			Util.log("Proportional selection with fitness minimization needs transformation to give bigger proportion to lower fitness (using Ranking by default).");
			this.transformer = new Rank();
			transformer.transform(window);
		}
		//but we have better solution below that is for minimization the proportion is set to (1.0 - normalized fitness)
*/
		
		double windowTotalFitness = 0.0;
		for(int i=0; i<window.size();i++){  //calculating total fitness of the window
			windowTotalFitness +=  window.getProportionalFitness(i); 
		}
		
		//if minimization, normalizing and subtracting from 1 to calculate fitness proportions so the smaller fitness gets higher proportion
		//alternative is to use ranking transformation with reversed order.
		if (window.minimization) {
			for(int i=0; i<window.size();i++) {  
				window.setProportionalFitness(i, 
						1- (window.getProportionalFitness(i)/windowTotalFitness));
			}
			windowTotalFitness = 0.0;
			for(int i=0; i<window.size();i++){  //recalculating total fitness now that proportions have been transformed by (1 - f/total)
				windowTotalFitness +=  window.getProportionalFitness(i); 
			}
		}

		//apply roulette wheel		
		//Population<P> output = Util.makeNewInstanceOfType(window);
		for (int k=0; k<winOutSize; k++) {
			double roll = Util.randomEngine.roll(windowTotalFitness);
			double temp = 0.0;
			int i = 0;
			for( ; i<window.size() && roll > 0.0; ++i){  
				temp += window.getProportionalFitness(i);
				if (temp >= roll) break;
			}
			output.add((P)window.get(i).clone());   //suppressed warning is for casting here

			//if no reInsertion then remove it from the window so it won't picked up again
			if (!reInsertion) { 
				window.remove(i);
				//since window has changed we need to recalculate the proportional fitness
				if (transformer != null) {
					transformer.transform(window);
				}
				windowTotalFitness = 0.0;
				for(int j=0; j<window.size();j++){  //calculating total fitness of the window
					windowTotalFitness +=  window.getProportionalFitness(j); //p.getFitness().doubleValue();
				}
			}
		}
		return; //output;
	}

}
