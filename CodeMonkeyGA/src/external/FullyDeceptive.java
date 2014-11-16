package external;

import edu.ccil.ec.Genotype;
import edu.ccil.ec.genotype.ArrayListGenotype;
import edu.ccil.ec.genotype.arraylist.BooleanArrayListGenome;

/**
 * This is the class to calculate fitness based on Fully Deceptive function below
 * which is a piecewise liner function
 * 
 * f(x) = n - sum(0.92/4 * (4 - x[i])   if   x[i] <=4
 *        n - sum(2.00/4 * (x[i] - 4)   if   x[i] > 4
 * 
 * where 
 * n is the number of dimensions
 * each dimension (gene i) is a six-bit number 
 * x[i] is  the number of 1's in gene i
 * The goal is to find global optima which is when all bits are 1. 
 * However is a local optima where are bits are 0.
 * 
 * @author Reza Etemadi
 *
 */
public class FullyDeceptive {

	/**
	 * 
	 * @param list
	 * @return
	 */
	public static double evaluate(Integer[] list){
		double result = list.length;
		for(int i:list){
			int xi = count1s(i);
			if (xi > 4) {
				result -= (2.00/4) * (xi - 4);
			}else {
				result -= (0.92/4) * (4 - xi);
			}
		}
		return result;
	}
	
	public static int count1s(int num) {
	    int count = 0;
	    String rep = Integer.toBinaryString(num);
	    for (int i=0; i < rep.length(); i++)
	    {
	        if (rep.charAt(i) == '1')
	        {
	             count++;
	        }
	    }
	    return count;
	}	

	
	public static double evaluate(ArrayListGenotype<ArrayListGenotype> hetroGenome){
		double result = hetroGenome.size();
		for (ArrayListGenotype genome: hetroGenome) {
			int xi = 0;
			for(boolean gene:(BooleanArrayListGenome)genome) {
				if (gene) xi++;
			}
			if (xi > 4) {
				result -= (2.00/4) * (xi - 4);
			}else {
				result -= (0.92/4) * (4 - xi);
			}
		}
		return result;
	}
	
}
