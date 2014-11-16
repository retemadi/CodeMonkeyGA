package edu.ccil.ec.tool;

/**
 * This class uses Guassian method from java.util.Random class to generate a normal distribution of values
 * by default the mean =0 and sigma=1 .but using constructor both mean and sigma can be shifted
 *  
 * @author RezaEtemadi
 *
 */
public class RandomEngineNormal extends RandomEngine {

	private static java.util.Random random = null;


	public RandomEngineNormal() {
		super();
		random = new java.util.Random();
	}

	public RandomEngineNormal(double mean, double sigma) {
		super();
		random = new java.util.Random();
		this.mean = mean;
		this.sigma = sigma;
	}

	public RandomEngineNormal(long seed) {
		super();
		random = new java.util.Random(seed);
	}
	
	/**
	 * the normal distribution is around this value 
	 */
	private double mean = 0.0;
	
	/**
	 *  %70 of generated values are in sigma vicinity of mean value
	 *  %95 are in the twice sigma vicinity
	 *  %99.7 are in the three sigma vicinity
	 */
	private double sigma = 1.0;
	/**
	 * uses default values for normal distribution mean=0 and sigma=1 
	 */
	
	public double getMean() {
		return mean;
	}

	public void setMean(double mean) {
		this.mean = mean;
	}

	public double getSigma() {
		return sigma;
	}

	public void setSigma(double sigma) {
		this.sigma = sigma;
	}

	
	/**
	 * Returns a random integer number generated with normal distribution around mean with standard deviation sigma 
	 */
	@Override
	public int nextInt(){
		return (int)Math.round(random.nextGaussian()* sigma + mean);
	}
	
	/**
	 * Returns a random integer number selected with normal distribution probability around the 'mean' and with the standard deviation 'sigma'
	 * and inside the vicinity of 'lowerbound' and upperbound'.
	 * The lower and upper bound must be on equal distance from 'mean' and at least 2 sigma apart from the 'mean'.
	 */
/*
	@Override
	public int nextInt(int lowerBound, int upperBound){
		if (upperBound <lowerBound ) {
			  throw new IllegalArgumentException(upperBound +" must be bigger than "+ lowerBound);
		}
		if ((upperBound -lowerBound)/2 + lowerBound != mean) {
			throw new IllegalArgumentException("The mean="+ mean +" must in the middle of "+upperBound +" and "+ lowerBound);
		}
		if ((upperBound -lowerBound) < 4*sigma) {
			throw new IllegalArgumentException( "The distance between "+upperBound +" and "+ lowerBound +" must be greater than 4 times of sigma="+ sigma); 
		}
		int outcome = (int)Math.round(random.nextGaussian()* sigma + mean);
		while (outcome < lowerBound || outcome > upperBound) {
			outcome = (int)Math.round(random.nextGaussian()* sigma + mean);
		}
		return outcome;
	}
*/
	
	
	/**
	 * Returns a random long integer number generated with normal distribution around mean with standard deviation sigma 
	 */
	@Override
	public long nextLong(){
		return (long)Math.round(random.nextGaussian()* sigma + mean);
	}

	
	/*
	 * returns a random double between 0.0 and 1.0 with normal distribution around 0.5 (1/2).
	 * since %99.9 of outcomes are within -3sigma and +3sigma the sigma is chosen sigma=1/6
	 */
	@Override
	public double nextDouble(){
		double outcome = random.nextGaussian()* 0.17 + 0.5;
		while (outcome >= 1.0 || outcome < 0.0) {
			outcome = random.nextGaussian()* 0.17 + 0.5;
		}
		return outcome;
	}
	
	/**
	 * Returns a random double number selected with normal distribution probability around the 'mean' and with the standard deviation 'sigma'
	 * and inside the vicinity of 'lowerbound' and upperbound'.
	 * The lower and upper bound must be on equal distance from 'mean' and at least 2 sigma apart from the 'mean'.
	 */
	public double nextDouble(double lowerBound, double upperBound){
		if (upperBound <lowerBound ) {
			  throw new IllegalArgumentException(upperBound +" must be bigger than "+ lowerBound);
		}
		if ((upperBound -lowerBound)/2 + lowerBound != mean) {
			throw new IllegalArgumentException("The mean="+ mean +" must in the middle of "+upperBound +" and "+ lowerBound);
		}
		if ((upperBound -lowerBound) < 4*sigma) {
			throw new IllegalArgumentException( "The distance between "+upperBound +" and "+ lowerBound +" must be greater than 4 times of sigma="+ sigma); 
		}
		double outcome = random.nextGaussian()* sigma + mean;
		while (outcome < lowerBound || outcome > upperBound) {
			outcome = random.nextGaussian()* sigma + mean;
		}
		return outcome;
		
	}
	
}
