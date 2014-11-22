package plugin.generated.p1;

import edu.ccil.ec.genotype.arraylist.ArrayListOperator;
import edu.ccil.ec.genotype.ArrayListGenotype;
import edu.ccil.ec.genotype.VariationOperator;

public class myGenome extends ArrayListGenotype<ArrayListGenotype> {

	private static final long serialVersionUID = 1L;

	public myGenome() {
		super();
		this.add(0,new myGenomePart0());
		this.add(1,new myGenomePart1());

		this.add(2,new myGenomePart2());
		this.add(3,new myGenomePart3());
/*
		this.add(4,new myGenomePart4());
		this.add(5,new myGenomePart5());
		this.add(6,new myGenomePart6());
		this.add(7,new myGenomePart7());
		this.add(8,new myGenomePart8());
		this.add(9,new myGenomePart9());
*/
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