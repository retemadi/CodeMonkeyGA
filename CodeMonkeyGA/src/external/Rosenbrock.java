package external;

/**
 * This is the class to calculate fitness based on Rosenbrock function
 * http://en.wikipedia.org/wiki/Rosenbrock_function
 * the goal is to minimize this formula
 * Minimize f(x) = sum( 100 *(x(i)^2 - x(i+1))^2 + (1- x(i))^2 ) (i from 0 to n-1)
 * @author Reza Etemadi
 *
 */
public class Rosenbrock {

	/**
	 * 
	 * @param x
	 * @return
	 */
	public static double evaluate(Double[] x){
		int n = x.length;
		double result = 0;
		for (int i=0; i<n-1; i++){
			result += ( 100 * Math.pow((Math.pow(x[i],2)- x[i+1]),2) ) + Math.pow((1 - x[i]),2);
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
