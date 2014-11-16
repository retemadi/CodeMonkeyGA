package edu.ccil.ec.genotype.arraylist;

import edu.ccil.ec.ESException;
import edu.ccil.ec.genotype.ArrayListGenotype;
import edu.ccil.ec.tool.Util;

/**
 * class representing a genome that is a list of booleans
 * @author Reza Etemadi
 *
 */

public abstract class BooleanArrayListGenome extends ArrayListGenotype<Boolean> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//default constructor
	public BooleanArrayListGenome() {
		super();
	}
	
	/**
	 * 
	 * @param size
	 */
	public BooleanArrayListGenome(int size ) {
		super(size);
		this.initialSize = size;
	}

	@Override
	public void fillRandom() {
		if ( initialSize < 1) {
			throw new ESException(ESException.InvalidSize, "Genotype Size is :"+ initialSize);
		}
		for (int j=0 ; j <this.initialSize; j++) {
			if (size() <= j) {
				this.add(new Boolean(Util.randomEngine.nextBoolean()));
			} else {			
				this.set(j, new Boolean(Util.randomEngine.nextBoolean()));
			}
		}
	}

	/**
	 * returns bitString representation of genotype e.g. [true,false,false,true] ==> 1001
	 * @return
	 */
	public String toBitString() {
		StringBuffer bitString = new StringBuffer();
		for (Boolean b:this){
			bitString.append(b?"1":"0");
		}
		return bitString.toString();
	}


	// to simplify the conversion to array
	public Boolean[] toArray() {
		return this.toArray(new Boolean[this.size()]);
	}

}
