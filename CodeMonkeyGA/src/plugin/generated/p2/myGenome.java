package plugin.generated.p2;

import edu.ccil.ec.genotype.arraylist.ArrayListOperator;
import edu.ccil.ec.genotype.ArrayListGenotype;
import edu.ccil.ec.genotype.VariationOperator;

public class myGenome extends ArrayListGenotype<myGenome.myGenomeUnit> {

	private static final long serialVersionUID = 1L;

	public myGenome() {
		super();
		initialSize=5;
	}

	@Override
	public void fillRandom() {
		for (int i=0 ; i<this.initialSize; i++){
			myGenomeUnit unit= new myGenomeUnit();
			unit.fillRandom();
			if (size() <= i) {
				this.add(unit);
			} else {
				this.set(i, unit);
			}
		}
	}



	@VariationOperator (friendlyName="One Point Crossover" )
	public void recombineListOnePoint(myGenome genome, double recombinationRate) {
		ArrayListOperator.recombineListOnePoint(this, genome, recombinationRate);
	}

	@VariationOperator (friendlyName="Two Point Crossover" )
	public void recombineListTwoPoint(myGenome genome, double recombinationRate) {
		ArrayListOperator.recombineListTwoPoint(this, genome, recombinationRate);
	}

	@VariationOperator (friendlyName="Uniform Crossover" )
	public void recombineListUniform(myGenome genome, double recombinationRate) {
		ArrayListOperator.recombineListUniform(this, genome, recombinationRate);
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


/* The Heterogeneous Unit of the Homogeneous Genotype */
	class myGenomeUnit extends ArrayListGenotype<ArrayListGenotype> {

		private static final long serialVersionUID = 1L;

		public myGenomeUnit() {
			super();
			this.add(0,new myGenomePart0());
			this.add(1,new myGenomePart1());
		}

		@Override
		public void fillRandom() {
			for (int i=0 ; i<this.size(); i++){
				this.get(i).fillRandom();
			}
		}


		@Override
		public myGenomeUnit create() { 
			return new myGenomeUnit();
		}

		@Override
		public myGenomeUnit clone() {
			myGenomeUnit clone = (myGenomeUnit)super.clone();
			for(int i=0 ; i<size(); i++) {
				clone.set(i, get(i).clone());
			}
			return clone;
		}

	}
}