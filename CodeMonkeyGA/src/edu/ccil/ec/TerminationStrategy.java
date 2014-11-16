package edu.ccil.ec;

import edu.ccil.ec.tool.Monitor;

/**
 * Defines the termination strategy.
 * It is combination of three scenarios: Goal Reached, Stagnation Reached, Resources Exhausted
 * @author RezaEtemadi
 *
 */
public abstract class TerminationStrategy<P extends Phenotype<?,?>> {

	private final static String goodHeader = "== GREAT NEWS ======================================================================\n";
	private final static String badHeader  = "== NOT SO GREAT NEWS ===============================================================\n";
	private final static String footer     = "========================================================================================\n";		
	/**
	 * the current population
	 */
	protected Population<P> current;  //it is set by Evolution.isSatisfied()  method
	/**
	 * the monitor object that tracks the progress over generations
	 */
	protected Monitor<P> monitor;

	
	protected StringBuffer message = new StringBuffer();
	
	
	public String getMessage() {
		return message.toString();
	}
	
	/**
	 * Checks for the "Goal Reached" condition
	 * @param target
	 * @param diff
	 * @return
	 */
	protected boolean isFitnessReached(double target, double diff){
		P indi = current.getFittest(1).get(0);
		boolean reached = (Math.abs(indi.getFitness().doubleValue() - target) <= diff); //(target==0?1:target)  <= percent/100);
		if (reached) {
			message.append(goodHeader);
			message.append("The fitness of best individual ("+indi.getFitness()+") reached the desired vicinity ("+diff+") of the target fitness ("+ target +")\n");
			message.append("The Best individual is: "+indi+" at final generation: ("+current.age+")\n");
			message.append(footer);

		}
		return reached;
	}

	/**
	 * 
	 * @param low
	 * @param high
	 * @param norm the percentage
	 * @return
	 */
	protected boolean isNormalizedFitnessReached(double low, double high , double norm){
		P indi = current.getFittest(1).get(0);
		double threshold = (high - low)* (norm/100);
		boolean reached = (indi.getFitness().doubleValue() >= threshold); //(target==0?1:target)  <= percent/100);
		if (reached) {
			message.append(goodHeader);
			message.append("The fitness of best individual ("+indi.getFitness()+") reached the desired normalized vicinity ("+norm+") of the fitness range ("+ low +", "+high+")\n");
			message.append("The Best individual is: "+indi+"\n");
			message.append(footer);

		}
		return reached;
	}


	/**
	 * checks for the "Stagnation Reached" condition
	 * @param limit (threshold in percentage)
	 * @param gen (number of generations)
	 * @return
	 */
	protected boolean isProgressStalledReached(double limit, int gen){
		boolean reached = (monitor.fitnessProgressStalled(limit,gen));
		if (reached) {
			message.append(badHeader);
			message.append("The fitness progress in the last "+gen+" generations did not exceed desired limit of (%"+limit+")\n");
			message.append(footer);
		}
		return reached;
	}

	
	/**
	 * checks for the "Resources Exhausted" (generation age limit) 
	 * @param ageLimit
	 * @return
	 */
	protected boolean isAgeLimitReached(int ageLimit){
		boolean reached = (current.age >= ageLimit);
		if (reached){
			message.append(badHeader);
			message.append("The generation age passed the desired limit of ("+ageLimit+")\n");
			message.append(footer);
		}
		return reached;
	}

	
	/**
	 * checks for the "Resources Exhausted" (running time limit) 
	 * @param timeLimit
	 * @return
	 */
	protected boolean isTimeLimitReached(double timeLimit){
		boolean reached = ((System.currentTimeMillis() - Evolution.startTime) >= timeLimit*1000);
		if (reached){
			message.append(badHeader);
			message.append("The execution time passed the desired limit of ("+timeLimit+" seconds)\n");
			message.append(footer);
		}
		return reached;
	}

	
	/**
	 * checks for the "Resources Exhausted" (fitness calculation limit) 
	 * @param countLimit
	 * @return
	 */
	protected boolean isCalculateLimitReached(int countLimit){
		boolean reached = ( Evolution.fitnessCalCounter >= countLimit);
		if (reached){
			message.append(badHeader);
			message.append("The number of fitness calculation over the course of execution passed the desired limit of ("+countLimit+")\n");
			message.append(footer);
		}
		return reached;
	}
	
		
	/**
	 * returns true if the termination condition is reached, false otherwise 
	 * it can use current and monitor internally. They are set by the framework before calling check()
	 * @return
	 */
	public abstract boolean check();
	
}
