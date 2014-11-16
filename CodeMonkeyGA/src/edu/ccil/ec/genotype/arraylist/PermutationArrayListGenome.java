package edu.ccil.ec.genotype.arraylist;

import edu.ccil.ec.ESException;
import edu.ccil.ec.genotype.ArrayListGenotype;
import edu.ccil.ec.tool.Util;

/**
 * class representing a genome that is a list of booleans
 * @author Reza Etemadi
 *
 */

public abstract class PermutationArrayListGenome extends ArrayListGenotype<Integer> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//default constructor
	public PermutationArrayListGenome() {
		super();
	}
	
	/**
	 * 
	 * @param size
	 */
	public PermutationArrayListGenome(int size ) {
		super(size);
		this.initialSize = size;
	}

	/**
	 * fills in the permutation list with numbers from 0 to size-1 ordered randomly (size being the length of list)
	 */
	@Override
	public void fillRandom() {
		if ( initialSize < 1) {
			throw new ESException(ESException.InvalidSize, "Invalid genotype Size for permutation  :"+ initialSize);
		}
		java.util.ArrayList<Integer> base = new java.util.ArrayList<Integer>(initialSize);
		for (int i=0; i<initialSize; i++){
			base.add(i);
		}
		for (int j=0 ; j <this.initialSize; j++) {
			//randomly remove from base (ordered) list and add to Genotype
			int gene = base.remove(
							Util.randomEngine.nextInt(0,base.size()));
			if (size() <= j) {
				this.add(gene);
			} else {			
				this.set(j,gene);
			}
		}
	}
	
	
	// to simplify the conversion to array
	public Integer[] toArray() {
		return this.toArray(new Integer[this.size()]);
	}
	
}
