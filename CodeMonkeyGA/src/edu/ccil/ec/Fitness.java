package edu.ccil.ec;

import java.util.HashMap;

/**
 * This interface defines what method are expected from a Fitness object
 * any implementation of this interface can act as Fitness in the process
 * This interface is implemented by Phenotype class. 
 * 
 * @author Reza Etemadi
 *
 * @param <F> can be any implementation of java.lang.Number interface that also implements java.lang.Comparable
 * This excludes java.util.concurrent.atomic.AtomicInteger and java.util.concurrent.atomic.AtomicLong
 * The rest of the Number implementors are implementing java.lang.Comparable interface 
 * 
 * In most cases java.lang.Double is sufficient for fitness but since java.lang.Double is a final class
 * and can not be extended, we choose java.lang.Number to provide more flexibility to advanced users
 * should they choose to come up with their own implementation of java.lang.Number and Comparable
 */
public interface Fitness<F extends Number & Comparable<F>> extends Comparable<Fitness<F>>{
	
	/**
	 * It evaluates the fitness value. 
	 * It mostly relies on internal data to calculate but if there is any external data should be used it can be passed 
	 * through the input parameter. 
	 * @param externalData An optional parameter than contains all external data needed for fitness evaluation, null otherwise. 
	 * Each The external elements is a pair of name=value that must be stored in this HashMap. 
	 */
	public abstract void evaluate(HashMap<String,String> externalData);
	
	/**
	 * retrieves the fitness
	 * @return
	 */
	public F getFitness();
	
	
}
