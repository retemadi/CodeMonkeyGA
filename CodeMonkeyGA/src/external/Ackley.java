package external;

/**
 * This is the class to calculate fitness based on Ackley's function
 * http://tracer.lcc.uma.es/problems/ackley/ackley.html
 * using the representation of the function here  http://www.aridolan.com/ga/gaa/gaa.html#Ackley
 * the goal is to minimize this formula
 * Minimize f(x) = 20+e-20*exp(-0.2*(sqrt((1/n)*sum(x(i)^2))-exp((1/n)*sum(cos(2*Pi*x(i)))  (i from 0 to n)
 * @author Reza Etemadi
 *
 */
public class Ackley {

	/**
	 * 
	 * @param list
	 * @return
	 */
	public static double evaluate(Double[] list){
		int n = list.length;
		return Math.abs(20 + Math.E  
				- 20 * Math.exp(-0.2*(Math.sqrt((1.0/n)*firstZigma(list)))) 
				- Math.exp((1.0/n)*secondZigma(list)));
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
