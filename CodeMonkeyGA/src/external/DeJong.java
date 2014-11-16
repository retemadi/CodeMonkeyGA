package external;

/**
 * This is the class to calculate fitness based on De Jong function
 * the goal is to minimize this formula
 * Minimize f(x) = sum(x(i)^2))  (i from 0 to n)
 * @author Reza Etemadi
 *
 */
public class DeJong {

	/**
	 * 
	 * @param list
	 * @return
	 */
	public static double evaluate(Double[] list){
		double result = 0;
		for(double xi:list){
			result += Math.pow(xi, 2);
		}
		return result;
	}

}
