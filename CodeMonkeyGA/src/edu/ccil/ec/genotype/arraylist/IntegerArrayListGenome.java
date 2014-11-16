package edu.ccil.ec.genotype.arraylist;

import edu.ccil.ec.genotype.ArrayListGenotype;
import edu.ccil.ec.tool.Util;

/**
 * class representing a genome that is a list of integer numbers
 * @author Reza Etemadi
 *
 */

public abstract class IntegerArrayListGenome extends ArrayListGenotype<Integer> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4135955835254889860L;
	
	/**
	 * boundary is used for scope of generated values. It is set static since it is the same for all instances
	 * Each element can have separate boundary but in the default implementation only one boundary 
	 * is set that is shared among all elements through getBoundary(i) method.
	 * you need to override the getBoundary(i) method if you want to change behavior in addition to defining new boundary array 
	 */
	private static IntegerBoundary[] boundary = { new IntegerBoundary(0,true,Integer.MAX_VALUE,false) };
	
	
	//default constructor
	public IntegerArrayListGenome() {
		super();
	}
	

	/**
	 * 
	 * @param size
	 */
	public IntegerArrayListGenome(int size){
		super(size);
		this.initialSize = size;
	}	


	
	/**
	 * 
	 * @param size
	 * @param low
	 * @param high

	public IntegerArrayListGenome(int size,	int low , int high) {

		this(size);
		this.min = low;
		this.max = high;		
	}
*/
	
	/**
	 * fills the ArrayList with random integer numbers in the boundary of min/max
	 */
	@Override
	public void fillRandom() {
		for (int j=0 ; j <this.initialSize; j++) {
			if (size() <= j) {
				this.add(new Integer(Util.randomEngine.nextInt(getBoundary(j))));
			} else {			
				this.set(j, new Integer(Util.randomEngine.nextInt(getBoundary(j))));
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
	public IntegerBoundary getBoundary(int i){
		if (i >= boundary.length) return boundary[boundary.length-1];
		return boundary[i];
	}
	
	
	// to simplify the conversion to array
	public Integer[] toArray() {
		return this.toArray(new Integer[this.size()]);
	}
	
	
}
