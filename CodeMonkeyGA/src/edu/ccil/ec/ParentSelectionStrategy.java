package edu.ccil.ec;

import edu.ccil.ec.tool.Util;

//public interface ParentSelectionStrategy<P extends Population<?>> {
/**
 * The Abstract class representing Parent Selection Algorithm 
 * Any concrete subclass needs to implement the "select" method 
 * @author Reza Etemadi
 * 
 */
public abstract class ParentSelectionStrategy {	

	/**
	 * the size of selection window used during the selection (default : 2)
	 */
	protected int winInSize = 2;

	/**
	 * the size of output of selection window used during the selection (default : 1)
	 */
	protected int winOutSize = 1;


	/**
	 * the boolean flag to indicate if individual are allowed to 
	 * appear in multiple windows (re-insertion). default is true.
	 */
	protected boolean reInsertion = true;

	
	/**
	 * the fitness transformer function that applied inside the window to the fitness values of input population.
	 * This transformation will affect the proportional selection bases PSAs.
	 */
	protected FitnessTransformer transformer;

	
	/**
	 * constructor
	 * @param p_windowSize
	 * @param p_windowOutputSize
	 * @param p_matingPoolSize
	 */
	public ParentSelectionStrategy(int p_windowSize, int p_windowOutputSize , boolean p_reInsertion){
		winInSize = p_windowSize;
		winOutSize = p_windowOutputSize;
		reInsertion = p_reInsertion;	
	}

	
	/**
	 * sets the fitness transformer used inside the window apply() method.
	 * transformation is optional and only affects the outcome of proportional selection algorithms.
	 * @param trans
	 */
	public void setTransformer(FitnessTransformer trans) {
		transformer = trans;
		return;
	}

  
	/**
	* Applies the selection algorithm to current population and returns the selected pool
	* The re-insertion can be applied inside the window (when creating its output) not for the filling of the window.
	* there should be no re-insertion for filling the window (otherwise one might ends up in tournament with itself for example)
	* the window is where the actual selection takes place and that's where the option of re-selection should be applied.
	* Window is always bunch of unique individuals picked randomly from input (no duplicates, so window size can not be bigger than input size) 
	* After selection from the window we send the individual's clone to window's output and depending on reInsersion we 
	* either remove the individual from the window or keep it in the window for possible re-selection.
	* Once the window's output size is reached we return the remaining of window goes back to input population 
	* (in case of reInsertion nothing is eliminated from window so everything goes back to input population that will be used for subsequent windows). 
	* the output of window can exceed the window size only when replacement(reInsertion) is enabled.
	* without replacement there is no duplicate of any individual in the parent pool or anywhere.
 
	* @param inputPop 
	* @param outputSize 
	* @return
	*/
	public <P extends Phenotype<?,?>> Population<P> apply(Population<P> inputPop, int outputSize){
		  
		if (winInSize <= 1 || winInSize > inputPop.size()) {  //set the windowSize to the whole input population size
			Util.log("Window input size out is of bound ("+winInSize+"),setting it to the size of input population ("+inputPop.size()+")");
			winInSize = inputPop.size();
		}
		
		if (winOutSize <=0) { // set the windowOutputSize according to windowSize
			Util.log("Window output size out of bound ("+winOutSize+"),setting it to the window input size -1  ("+inputPop.size()+")");
			winOutSize = winInSize-1;
		}
		
		if (winOutSize > winInSize ) {
			throw new ESException(ESException.SanityCheckError,"The winOutSize ("+winOutSize+") can not be bigger than winInSize ("+winInSize+").");
		}
		
		if (!reInsertion) {
			//sanity check
			if (outputSize > inputPop.size()) {
				throw new ESException(ESException.SanityCheckError,"The outputSize ("+outputSize+") can not be bigger than given input population size " +
																	"("+inputPop.size()+") if re-insertion (replacement) is not enabled.");
			}
			
			int numberOfWindows = (int) (Math.ceil(outputSize/new Double(winOutSize)));
			//in each window the winOutSize is reduced from inputPop, this repeats numberOfWindows time, 
			//there should be enough left in input pop for creation of last window since there is no reInsertion, let's calculate that.
			if (inputPop.size()- ((numberOfWindows-1)* winOutSize) < winInSize) {
				throw new ESException(ESException.SanityCheckError,"The input size ("+inputPop.size()+") can not give ("+numberOfWindows+") windows of size ("+winInSize+") without reInsertion.");
				
			}
			/*
			int neededWindows = (int) (Math.ceil(outputSize/new Double(winOutSize)));
			if (neededWindows * winInSize > inputPop.size()) {
				throw new ESException(ESException.SanityCheckError,"It will take ("+neededWindows+") windows of each ("+winInSize+") to generate desired output size ("+outputSize+"). " +
												"This will be bigger than the given input population size ("+inputPop.size()+"). Enable re-insertion or change output or window size.");
			}
			*/
		}

		if (outputSize <= 0) { // choose default which is current pop size
			outputSize  = inputPop.size();
		}

		//always using clone of input pop since we manipulate it by removing from it for window and then putting back what remains from window.
		@SuppressWarnings("unchecked")
		Population<P> input = (Population<P>) inputPop.clone(); 

		Population<P> outputPop = Util.makeNewInstanceOfType(inputPop);
		outputPop.minimization = inputPop.minimization;  // pass the minimization flag to output in case sorting happens (e.g. Truncation) 
	
		
		//using the mechanism in "select" method choose from window
		//but apply fitness transformation before selection if there is one
		while (outputPop.size() < outputSize ) {
			
			Population<P> window = input.pickRandom(winInSize, false);// we don't have any duplicate in the window therefore reInsertion parameter is false
			//TODO: (VERIFY) should we remove the picked individuals from the input, since we add the remaining of window back into input below
			if (transformer != null) {
				transformer.transform(window);
			}
			//the actual selection from the window will be done in implementation of select() method
			select(window, outputPop, reInsertion);

			//add whatever remains in window back to input
			//if reInsetion is true it means nothing was removed from window during select() method and all goes back to input
			input.addAll(window);
/*
			for (P unlucky:window){ //return the remaining back into input for further use
				input.add(unlucky);
			}
*/
			
		}
		//sometime due to winOutSize the output goes beyond the desired output size. we need to truncate the output
		while (outputPop.size() > outputSize) {
			outputPop.remove(outputPop.size()-1);
		}
		
		return outputPop;
		
	}
	
	/**
	* It applies the selection algorithm to the window of individuals from input population
	* and add the selected individuals (known from winOutSize) to output population.
	* If selection is based on calculated fitness value then any type of deterministic or probabilistic selection algorithm can be applied inside this method
	* If however selection is purely based tournament then the actual competition should be implemented inside this method
	 * @param window is the randomly selection input window from population
	 * @param output is the output population that widow's output goes in to
	 * @param reInsertion is the boolean flag to allow re-insertion or not 
	 * if true the implication is that the copy of selected individuals goes to output and itself remain in population to potentially show up in some subsequent window (if any) 
	 * if false the implication is that the selected individual is cut from input and goes to output 
	*/
	protected abstract <P extends Phenotype<?,?>> void select(Population<P> window, Population<P> output, boolean reInsertion);

	  
}
