package external;

/**
 * This is the class to calculate fitness based on Rastrigin function
 * http://en.wikipedia.org/wiki/Rastrigin_function
 * the goal is to minimize this formula 
 * Minimize f(x) = 10*n + sum( (x(i)^2 - 10*cos( 2*pi*x(i)) )  (i from 0 to n)
 * @author Reza Etemadi
 *
 */
public class Rastrigin {

	/**
	 * 
	 * @param x
	 * @return
	 */
	public static double evaluate(Double[] x){
		int n = x.length;
		double result = 10 * n;
		for (int i=0; i<n; i++){
			result += Math.pow(x[i],2)- (10 * Math.cos(2*Math.PI*x[i])) ;
		}
		return Math.abs(result);
	}
	

	/**
	 * 
	 * @param list
	 * @return
	 */
	private static double firstZigma(Double[] list ){
		double result = 0;
		for(double xi:list){
			result += Math.pow(xi, 2);
		}
		return result;
	}

	/**
	 * 
	 * @param list
	 * @return
	 */
	private static double secondZigma(Double[] list ){
		double result = 0;
		for(double xi:list){
			result += Math.cos(2*Math.PI*xi);
		}
		return result;
	}

}
