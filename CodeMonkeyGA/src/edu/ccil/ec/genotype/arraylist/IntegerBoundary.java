package edu.ccil.ec.genotype.arraylist;

public class IntegerBoundary {

	int min;
	boolean minIncluded;
	
	int max;
	boolean maxIncluded;
	
	/**
	 * constructor, sets all 4 parameters
	 * @param lowerBound
	 * @param minIsIn
	 * @param upperBound
	 * @param maxIsIn
	 */
	public IntegerBoundary(int lowerBound, boolean minIsIn, int upperBound , boolean maxIsIn){

		if (upperBound-(maxIsIn?0:1) < lowerBound+(minIsIn?0:1) ) {
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
	public IntegerBoundary(int lowerBound, int upperBound){
		this(lowerBound,true,upperBound,true);
	}


	public int getMin(){
		return min;
	}
	
	public boolean isMinIncluded(){
		return minIncluded;
	}
	
	public int getMax(){
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
	public boolean overBound(int value){
		return maxIncluded?value>max:value>=max;
	}

	/**
	 * returns true if the input value goes below the accepted lower limit (min)
	 * @param value
	 * @return
	 */
	public boolean underBound(int value){
		return minIncluded?value<min:value<=min;
	}

	/**
	 * returns true if the value is our of the scope of accepted range
	 * @param value
	 * @return
	 */
	public boolean outOfRange(int value){
		if (overBound(value) || underBound(value)) return true;
		return false;
	}
	
}
