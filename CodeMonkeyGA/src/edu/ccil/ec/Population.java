package edu.ccil.ec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Level;

import edu.ccil.ec.tool.Util;


public class Population<P extends Phenotype<?,?>> extends ArrayList<P>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * This is the parameter used to reverse sorting population based on their fitness
	 * by default it is false, meaning after sort the individual 
	 * with highest fitness is at beginning of population collection
	 */
	public boolean minimization = false;
	
	/**
	 * the command to run the external program for fitness calculation
	 * if it is null the internal fitness calculation will be used 
	 * Default is null
	 */
	public String externalCommand = null;
	
	/**
	 * the work directory that will be used by the external program 
	 */
	public String externalWorkDir = null;
	
	/**
	 * an enum of available formats for sending phenotype content to external program 
	 */
	public enum Format { XML , JSON };
	
	/**
	 * the format for sending phenotype contents to the external program
	 * the default is XML
	 */
	public Format form = Format.XML;

	protected static int timeout = 1000*60;// timeout in case of external call per individual ( 1000 ms * 60  = 1 minute )

	
	/**
	 * The Age of the population. 
	 * It is only useful when population is being treated as generation
	 * default is set to -1. Any non-negative value indicates that this is a generation.
	 */
	public int age =-1;
	
	/**
	 * if the raw calculated fitness should not be used as proportion of each individual it has to go through
	 * scaling, re-mapping, ranking, etc. ,use this element to store what will be used is selection process as
	 * proportion of this individual in the population window. 
	 * In other words proportional fitness is not stored in the Phenome but inside the population and parallel to Phenomes
	 * Because proportional fitness of a phenome is dependent on what other Phenomes are inside the population (or window)
	 */
	protected HashMap<Integer,Double> proportionalFitness = new HashMap<Integer,Double>();
	
	
	
	/**
	 * the total fitness of population
	 */
	protected Double totalFitness;
	
	
	/**
	 * the average fitness of population
	 */
	protected Double meanFitness;
	
	
	/**
	 * Default constructor. 
	 * It is necessary for any possible subclass of Population to have no-Arg constructor,
	 * because reflection is used to instantiate Population or its subclasses 
	 * in the strategies implementation using Util.geNewInstance() method 
	 */
 	public Population(){
		super();
	}
 	
 	/**
 	 * The constructor for setting minimization parameter. 
 	 * in default constructor the minimization is false.
 	 * @param minimization
 	 */
 	public Population(boolean minimization){
		super();
		this.minimization = minimization;
	}


 	/**
 	 * returns the proportional fitness that is calculated based on
 	 * transformation. 
 	 * If no transformation is done, then the proportionalFitness is null
 	 * and the original fitness of phenotype is returned.
 	 * the fitness can also be negative (for minimization problems). the absolute value is always used
 	 * @param index
 	 * @return
 	 */
 	public Double getProportionalFitness(int index){
 		if (proportionalFitness.get(index) != null) {
 			return Math.abs((Double)proportionalFitness.get(index));
 		} else { //if there is no proportional value return the raw fitness
 			return Math.abs(get(index).getFitness().doubleValue());
 		}
 	}

 	/**
 	 * 
 	 * @param index
 	 */
 	public void setProportionalFitness(int index, double value){
 		proportionalFitness.put(index, value);
 		return;
 	}
 	
	/**
	 * picks a number of individuals randomly from the input population and return their clones.
	 * if reInsertion is not allowed the selected individuals are removed from the input population
	 * if the output size is the same as population size and no reInsertion is allowed it returns a clone of the population
	 * @param num
	 * @param reInsertion (replacement)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Population<P> pickRandom(int num, boolean reInsertion) {
		if (num > size() && !reInsertion) {
			throw new ESException(ESException.SanityCheckError,"The requested number of individuals ("+num+") is bigger than the population size ("+size()+") and reinsertion is not enabled.");
		}
		//if the output size is the same as input and no reInsertion then return a clone of input
		if (num == size() && !reInsertion) {  
			return (Population<P>)this.clone();
		}
		Population<P> output = Util.makeNewInstanceOfType(this);
		output.minimization = this.minimization;  // pass the minimization flag to window in case sorting in window happens (e.g. Truncation) 
		for(int i=0; i<num ; i++) {
			output.add(pickOne(reInsertion));
		}
	
		return output;
	}
	
	/**
	 * returns one random individual from the population and remove it permanently if reInsertion is not allowed 
	 * Note: if reInsetion is disabled the selection shrinks the population by one 
	 * @param reInsertion the boolean flag to enable/disable replacement,   
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public P pickOne(boolean reInsertion) {
		int pos = Util.randomEngine.pick(size());
		P one = get(pos);
		
		if (!reInsertion) { // no replacement so delete from the population and return the same object
			remove(pos);
			return one;
		}else {			// replacement is allowed so it remains in population, return its clone then.
			return (P)one.clone();
		}
	}
	
	/**
	 * iterates through the population and evaluates each member's fitness
	 * if an external program is set then the external program will be called first 
	 * The external program is expected to return a list of name=value properties in XML or JSON format. 
	 * They will be considered raw fitness. The phenotype evaluate() will be called and if there is any return 
	 * from external program it will be passed to evaluate() for further process.
	 */
	public void evaluate(){
		try {
			if (externalCommand != null && externalCommand.length() > 1) { // run the external code
				for (int i=0 ; i < this.size() ; i ++) {
					HashMap<String,String> externalData = Util.runExternalCommand(externalCommand,externalWorkDir,get(i),form, timeout);
					get(i).evaluate(externalData);
				}
			} else {  // run internal mechanism
				for (int i=0 ; i < this.size() ; i ++) {
					get(i).evaluate(null); //external data is null
				}
			}
			Evolution.fitnessCalCounter++;
		}catch (NullPointerException npe){
			npe.printStackTrace();
			throw new ESException(ESException.FitnessError,"Fitness evaluation return null." +
					"\n Make sure you have defined fitness evaluation code internally (in Phenome) or externally.");
		}
		return;
	}

	/**
	 * Sorts this population based on fitness (CompareTo() implementation in Phenotype)
	 * If minimization flag it true the sorting is descending
	 * meaning the best individual is always on top  
	 */
	public void sort(){
		if (minimization) {
			Collections.sort(this, Collections.reverseOrder());
		} else {
			Collections.sort(this);
		}
		return;
	}
	
	
	/**
	 * returns a number of fittest individuals from the population
	 * @param number
	 * @return
	 */
	public Population<P> getFittest(int number){
		sort();
		Population<P> result = Util.makeNewInstanceOfType(this);
		result.minimization = this.minimization;
		for (int i= this.size()-number ; i<this.size() ; i++) {
			result.add(this.get(i));
		}
		//return (AbstractList)this.subList(this.size()-number, this.size()); //AbstactList is not ArrayList
		return result;
	}
	
	
	public P getFittest() {
		return getFittest(1).get(0);
	}
	
	/**
	 * returns the sum of all fitness values in the population
	 * The summation is applied to the double value of Fitness objects,
	 * using java.lang.Number.doubleValue() 
	 * @return 
	 */
	public Number fitnessTotal() {
		if (totalFitness == null) {  //first time or population content changed through add,remove
			totalFitness = 0.0;
			meanFitness = null;
			for (int i=0 ; i < this.size() ; i ++) {
				totalFitness += this.get(i).getFitness().doubleValue();
			}
		}
		return totalFitness;
	}
	
	/**
	 * returns the average fitness in the population
	 * in double value.
	 * @return
	 */
	public Double fitnessMean() {
		if (meanFitness == null) {
			meanFitness = fitnessTotal().doubleValue() / this.size();
		}
		return meanFitness;
	}
	
	/**
	 * returns the standard deviation (sigma) in the fitness of the population (diversity))
	 */
	public Double fitnessStandardDeviation() {
		Double sigma = 0.0;
		Double average = fitnessMean();
		for (int i=0; i< this.size() ; i++) {
			sigma += Math.pow((get(i).getFitness().doubleValue() - average),2.0);
		}
		return Math.sqrt(sigma / (this.size()-1));
	}
	
	
	public String getStats() {
		return "Pop{size:"+this.size()+", Mean:"+fitnessMean()+", Sigma:"+fitnessStandardDeviation()+"}";
	}
	
	
	/*
	 * To make sure the total and mean fitness are in sync with population
	 * the add method is overridden
	 * @see java.util.ArrayList#add(java.lang.Object)
	 */
	@Override
	public boolean add(P p){
		totalFitness = null;
		meanFitness = null;
		return super.add(p);
	}
	
	
	/*
	 * To make sure the total and mean fitness are in sync with population
	 * the remove method is overridden
	 * @see java.util.ArrayList#remove(int)
	 */
	@Override
    public P remove(int index) {
		totalFitness = null;
		meanFitness = null;
		proportionalFitness = new HashMap<Integer,Double>();
		return super.remove(index);		
	}
	
	
	
	/**
	 * logs all individuals of population using Util.log method
	 */
	public void logPopulation() {
		int i=0;
		for(P p:this){
			Util.log(Level.FINE,i++ +")" +p);
		}
		return;
	}
	
	/*
	 * Returns a String representation of the population
	 * @see java.util.AbstractCollection#toString()
	 */
	public String toString(){
		return "Population Size:"+size()+"["+super.toString()+"]";
		
	}
	
	public String toXML(){
		StringBuffer sb = new StringBuffer("<?xml version=\"1.0\"?>\n<XML>\n");
		for (int i=0; i< size(); i++) {
			sb.append("<"+i+">"+get(i).toXML()+"</"+i+">\n");
		}
		sb.append("</XML>");
		return sb.toString();
	}

	
	public String toJSON(){
		StringBuffer sb = new StringBuffer();
		for (int i=0; i< size(); i++) {
			sb.append(i+":\t"+get(i).toJSON()+"\n");
		}
		return sb.toString();
	}
	
	
	/**
	 * return a clone of the the population
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object clone() {
		//shallow cloning
		return (Population<P>) super.clone();
/*		
 		//or deep cloning
		Population<P> clone = Util.makeNewInstanceOfType(this); //
		for (P p:this){
			clone.add((P) p.clone());
		}
		clone.proportionalFitness = (HashMap<Integer, Double>)this.proportionalFitness.clone();
		clone.minimization = this.minimization;
		return clone;
*/
	}
}
