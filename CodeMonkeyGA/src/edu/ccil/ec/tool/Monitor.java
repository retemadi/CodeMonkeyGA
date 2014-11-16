package edu.ccil.ec.tool;

import java.util.Hashtable;

import edu.ccil.ec.Phenotype;
import edu.ccil.ec.Population;

/**
 * Monitors Exploration and Exploitation during search process
 * It stores a history of generations in terms of fitness 
 * so that changes in minimum and average fitness and also diversity can be tracked
 * several selection strategy that are window based (use data from multiple generations)
 * make use of this class information 
 * Fitness improvement and diversity are good monitoring parameters
 * Monitoring can also be related to termination.  
 * e.g. if diversity drops under a threshold or fitness does not improve after a period of time
 *
 * @author Reza Etemadi
 *
 * @param <P>
 */

public class Monitor<P extends Phenotype<?,?>> {

	/**
	 * holds the history of best individual in each generation
	 */
	private Hashtable<Integer,P> topPhenomeTrack = new Hashtable<Integer,P>();

	/**
	 * holds the history of fitness diversity in each generation
	 */
	private Hashtable<Integer,Double> diversityTrack = new Hashtable<Integer, Double>();

	
	
	/**
	 * adds the best individual of given generation into the history
	 * the age of the generation is used as the key and the best phenome as the value
	 * @param gen
	 */
	public void add(Population<P> gen){
		topPhenomeTrack.put(gen.age, gen.getFittest(1).get(0));
		diversityTrack.put(gen.age, gen.fitnessStandardDeviation());
	}

	/**
	 * returns the best individual of generation 
	 * @param gen the generation
	 * @return
	 */
	public P getBestInGen(int gen) {
		return topPhenomeTrack.get(gen);
	}
	
	/**
	 * returns the diversity of fitness in generation 
	 * @param gen the generation
	 * @return
	 */
	public Double getSigmaInGen(int gen) {
		return diversityTrack.get(gen);
	}


	/**
	 * returns the size of the history in terms of number of generations
	 * @return
	 */
	public int historySize() {
		return topPhenomeTrack.size();
	}
	
	/**
	 * returns true if fitness change over the last n generation is less than the %limit of the best fitness of n-th generation ago
	 * otherwise returns false meaning progress is better than the limit.
	 * @param limit (threshold in percentage)
	 * @param n (number of generations)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean fitnessProgressStalled(double limit, int n){
		if (n > topPhenomeTrack.size() ||  n < 2){
			return false;
		}
		Number currentBest = (Number)((P)topPhenomeTrack.get(topPhenomeTrack.size()-1)).getFitness();
		Number pastBest = (Number)((P)topPhenomeTrack.get(topPhenomeTrack.size()-n)).getFitness();
		return (Math.abs((currentBest.doubleValue() - pastBest.doubleValue()) / pastBest.doubleValue()) <= (limit /100) );
	}

	/**
	 * returns true is the diversity over the last n generation is less than limit otherwise true
	 * @param limit
	 * @param n
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean lowDiversityStucked(double limit, int n){
		if (n > topPhenomeTrack.size() || n < 1){
			return false;
		}
		
		boolean flag = true;
		for(int i=0 ; i < n ; i++){ // if the sigma in any past n generation is higher than limit, diversity is not stalled.
			if(diversityTrack.get(diversityTrack.size()-i) > limit) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	

	/**
	 * an exploration measure
	 * @return
	 */
	//public abstract double diversityRatio(); 
	
	/**
	 * an exploitation measure
	 * @return
	 */
	//public abstract double fitnessImprovementRatio();
	
	
	/**
	 * returns the minimum fitness among the last n generation (including current generation)
	 * @param n the number of generation (i.e. the window size)
	 * @return
	 */
	//public abstract Number MultiGenMinFitness(int n);
	

	

}
