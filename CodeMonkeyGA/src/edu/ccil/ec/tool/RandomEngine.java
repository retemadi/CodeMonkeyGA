package edu.ccil.ec.tool;

import edu.ccil.ec.genotype.ArrayListGenotype;
import edu.ccil.ec.genotype.arraylist.IntegerBoundary;
import edu.ccil.ec.genotype.arraylist.RealBoundary;

/**
 * This class acts as the default random generator. By default it uses java.util.Random class internally
 * SecureRandom provides a cryptographic-ally strong random number generator  
 *  
 * @author Reza Etemadi 
 *
 */
public class RandomEngine {

	private java.util.Random random =null;

	
	public RandomEngine() {
		super();
		random = new java.util.Random();
	}

	public RandomEngine(long seed) {
		super();
		random = new java.util.Random(seed);
	}
	
	// factory method
	protected java.util.Random getRandom() {
		return random;
	}
	
	/**
	 * uses @see java.util.Random#nextInt(int n) internaly
	 * this method is made final because it is used in selection inside population and genotypes
	 * return a random integer between 0(inclusive) and n(exclusive) selected uniformly i.e. [0,n[
	 * and those selection must remain uniform. 
	 * if any non-uniform random integer is needed, either nextInt() or randomBoundedInteger() can be overridden
	 * @param n the bound on random number. 
	 * @return
	 */
	public final int pick(int n) {
		return getRandom().nextInt(n);
	}
	
	
	/**
	 * uses @see java.util.Random#nextInt(int n) internaly
	 * returns two points with in a defined length (in an array, the smaller number is first always)
	 * @param n
	 * @return an int[] the first element is start point and second element is end point
	 */
	public final int[] pickDualPoint(int length){
		int start = getRandom().nextInt(length-1); //making sure start never falls at last element
		int end = getRandom().nextInt(length);
		if (end == start) end = length-1;  // or alternatively =start+1 but this one gives bigger distance
		if (start > end) {
			int temp = start;
			start = end;
			end = temp;
		}
		return new int[]{start, end};
	}
	
	
	

	/**
	 * the roll picks a random number between 0.0 and limit size 
	 * can be used in proportional (roullette wheel) among a collection of objection
	 * @param limit can be positive or negative (absolute value is always used)
	 * @return
	 */
	public final double roll(double limit) {
		return (getRandom().nextDouble()* Math.abs(limit));
	}
	
	
	/**
	 * return a random boolean value, selected uniformly
	 * @return
	 */
	public boolean nextBoolean(){
		return getRandom().nextBoolean();
	}

	
	/**
	 * returns a random integer among all 2^32 possible integers ,selected uniformly
	 * uses @see java.util.Random#nextInt()
	 * @return
	 */
	public int nextInt(){
		return getRandom().nextInt();
	}

	
	/**
	 * Returns a pseudo random, uniformly distributed int value between lowerBound (inclusive) and 
	 * the upperBound (exclusive), drawn from this random number generator's sequence
	 * using java.util.Random 
	 * @param lowerBound the lower boundary (inclusive)
	 * @param upperBound the upper boundary (exclusive)
	 * @return
	 */
	
	public int nextInt(int lowerBound, int upperBound){
		if (upperBound <lowerBound ) {
			  throw new IllegalArgumentException(upperBound +" must be bigger than "+ lowerBound);
		}
		return lowerBound + (getRandom().nextInt(upperBound));
	}

	
	public int nextInt(IntegerBoundary bound){
		int upperbound = bound.isMaxIncluded()?bound.getMax()+1:bound.getMax() ;
		int lowerbound = bound.isMinIncluded()?bound.getMin():bound.getMin()+1 ;
		int result = lowerbound + getRandom().nextInt(upperbound);
		while (bound.outOfRange(result)){
			result = lowerbound + getRandom().nextInt(upperbound);
		}
		return result;
	}
	
	
	/**
	 * returns a random long integer all possible values provided by underlying engine, selected uniformly
	 * uses @see java.util.Random#nextLong()
	 * @return
	 */
	public long nextLong(){
		return getRandom().nextLong();
	}

	
	
	/**
	 * returns a  uniformly distributed double value in range 0.0 (inclusive) and 1.0 (exclusive)  using random object 
	 * uses @see java.util.Random#nextDouble() 
	 * @return 
	 */
	public double nextDouble(){
		return getRandom().nextDouble();
	}

	
	/**
	 * Returns the next pseudo random, uniformly distributed double value 
	 * between lowerBound and higherBound  
	 * from this random number generator's sequence using java.util.Random
	 * @param lowerBound the lower boundary (inclusive)
	 * @param upperBound the upper boundary (exclusive)
	 * @return
	 */
	public double nextDouble(double lowerBound, double upperBound){
		if (upperBound <lowerBound ) {
			  throw new IllegalArgumentException(upperBound +" must be bigger than "+ lowerBound);
		}
		return lowerBound + (getRandom().nextDouble()* (upperBound-lowerBound));
	}
	
	
	public double nextDouble(RealBoundary bound ){
		double upperBound = bound.isMaxIncluded()?bound.getMax()+RealBoundary.percision:bound.getMax() ;
		double lowerBound = bound.isMinIncluded()?bound.getMin():bound.getMin()+RealBoundary.percision ;
		double result = lowerBound + (getRandom().nextDouble()* (upperBound-lowerBound));
		while (bound.outOfRange(result)){
			result = lowerBound + (getRandom().nextDouble()* (upperBound-lowerBound));
		}
		return result;		
	}
	
	
	/**
	 * Returns the next pseudo random, Gaussian ("normally") distributed double value with mean 0.0 
	 * and standard deviation 1
	 * from this random number generator's sequence using java.util.Random
	 * @return
	 */
	public double randomDoubleGaussian(){
		return getRandom().nextGaussian();
	}

}
