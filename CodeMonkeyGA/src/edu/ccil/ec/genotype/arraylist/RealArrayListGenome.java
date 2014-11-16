package edu.ccil.ec.genotype.arraylist;

import edu.ccil.ec.genotype.ArrayListGenotype;
import edu.ccil.ec.tool.Util;

/**
 * class representing a genome that is a list of real (java double) numbers
 * @author Reza Etemadi
 *
 */
public abstract class RealArrayListGenome extends ArrayListGenotype<Double> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public double min = Double.MIN_VALUE;
	public double max = Double.MAX_VALUE;
	
	/**
	 * boundary is used for scope of generated values. It is set static since it is the same for all instances
	 * Each element can have separate boundary but in the default implementation only one boundary 
	 * is set that is shared among all elements through getBoundary(i) method.
	 * you need to override the getBoundary(i) method if you want to change behavior in addition to defining new boundary array 
	 */
	private static RealBoundary[] boundary = { new RealBoundary(0,true,Double.MAX_VALUE,false) };
	
	
	
	//default constructor
	public RealArrayListGenome() {
		super();
	}
	
	/**
	 * 
	 * @param size
	 */
	public RealArrayListGenome(int size ) {
		super(size);
		this.initialSize = size;
	}
	/**
	 * 
	 * @param size
	 * @param low
	 * @param high
	 */
	public RealArrayListGenome(int size, 	double low, double high) {
		this(size);
		this.min = low;
		this.max = high;
	}	


	/**
	 * fills the ArrayList with random double numbers in the boundary of min/max
	 */
	@Override
	public void fillRandom() {
		for (int j=0 ; j <this.initialSize; j++) {
			if (size() <= j) {
				this.add(new Double(Util.randomEngine.nextDouble(getBoundary(j))));
			} else {
				this.set(j, new Double(Util.randomEngine.nextDouble(getBoundary(j))));
			}
		}
	}

	/**
	 * return the boundary for element i. 
	 * if boundary array length is smaller than genome the last element in boundary array is returned
	 * default boundary array has only one element.
	 * @param i
	 * @return
	 */
	public RealBoundary getBoundary(int i) {
		if (i >= boundary.length) return boundary[boundary.length-1];
		return boundary[i];
	}

	
	// to simplify the conversion to array
	public Double[] toArray() {
		return this.toArray(new Double[this.size()]);
	}
	
}