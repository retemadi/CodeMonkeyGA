package edu.ccil.ec.plugin_template;

import edu.ccil.ec.genotype.arraylist.ArrayListOperator;
import edu.ccil.ec.genotype.ArrayListGenotype;
import edu.ccil.ec.genotype.VariationOperator;

public class myGenome extends ArrayListGenotype<ArrayListGenotype> {

	private static final long serialVersionUID = 1L;

	public myGenome() {
		super();
	}

	@Override
	public void fillRandom() {
		for (int i=0 ; i<this.size(); i++){
			this.get(i).fillRandom();
		}
	}


	@Override
	public myGenome create() { 
		return new myGenome();
	}

	@Override
	public myGenome clone() {
		myGenome clone = (myGenome)super.clone();
		for(int i=0 ; i<size(); i++) {
			clone.set(i, get(i).clone());
		}
		return clone;
	}

}