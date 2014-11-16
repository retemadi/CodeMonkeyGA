package edu.ccil.ec.genotype.arraylist;

public class RealBoundary {

	public static double percision = 0.00001;
	
	double min;
	boolean minIncluded;
	
	double max;
	boolean maxIncluded;
	/**
	 *  constructor, sets all 4 parameters
	 * @param lowerBound
	 * @param minIsIn
	 * @param upperBound
	 * @param maxIsIn
	 */
	public RealBoundary(double lowerBound, boolean minIsIn, double upperBound , boolean maxIsIn){

		if (upperBound-(maxIsIn?0:percision) < lowerBound+(minIsIn?0:percision) ) {
			  throw new IllegalArgumentException("provided range is not valid: "+
					  							(minIsIn?"[":"(")+ lowerBound+","+upperBound+(maxIsIn?"]":")"));
		}
		
		this.min = lowerBound;
		minIncluded = minIsIn;
		this.max = upperBound;
		maxIncluded = maxIsIn;
	}

	
	/**
	 * constructor 
	 * sets the min (inclusive) and max (inclusive)
	 * @param lowerBound
	 * @param upperBound
	 */
	public RealBoundary(double lowerBound, double upperBound){
		this(lowerBound,true,upperBound,true);
	}


	public double getMin(){
		return min;
	}
	
	public boolean isMinIncluded(){
		return minIncluded;
	}
	
	public double getMax(){
		return max;
	}
	
	public boolean isMaxIncluded(){
		return maxIncluded;
	}

		
	/**
	 * returns true if the input value goes beyond the accepted upper limit (max)
	 * @param value
	 * @return
	 */
	public boolean overBound(double value){
		return maxIncluded?value>max:value>=max;
	}

	/**
	 * returns true if the input value goes below the accepted lower limit (min)
	 * @param value
	 * @return
	 */
	public boolean underBound(double value){
		return minIncluded?value<min:value<=min;
	}

	/**
	 * returns true if the value is our of the scope of accepted range
	 * @param value
	 * @return
	 */
	public boolean outOfRange(double value){
		if (overBound(value) || underBound(value)) return true;
		return false;
	}
	
}
