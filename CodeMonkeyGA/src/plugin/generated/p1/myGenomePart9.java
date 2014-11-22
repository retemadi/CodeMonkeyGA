package plugin.generated.p1;

import edu.ccil.ec.genotype.VariationOperator;
import edu.ccil.ec.genotype.arraylist.BooleanArrayListGenome;
import edu.ccil.ec.genotype.arraylist.BooleanOperator;

public class myGenomePart9 extends BooleanArrayListGenome {

	private static final long serialVersionUID = 1L;

	public myGenomePart9() {
		super();
		initialSize=5;
	}



	@VariationOperator (friendlyName="One Point Crossover" )
	public void recombineBooleanListOnePoint(myGenomePart9 genome, double recombinationRate) {
		BooleanOperator.recombineBooleanListOnePoint(this, genome, recombinationRate);
	}

	@VariationOperator (friendlyName="Two Point Crossover" )
	public void recombineBooleanListTwoPoint(myGenomePart9 genome, double recombinationRate) {
		BooleanOperator.recombineBooleanListTwoPoint(this, genome, recombinationRate);
	}

	@VariationOperator (friendlyName="Uniform Crossover" )
	public void recombineBooleanListUniform(myGenomePart9 genome, double recombinationRate) {
		BooleanOperator.recombineBooleanListUniform(this, genome, recombinationRate);
	}

	@VariationOperator (friendlyName="Flip Mutation" )
	public void mutateBooleanListFlip(double mutationRate) {
		BooleanOperator.mutateBooleanListFlip(this, mutationRate);
	}

	@VariationOperator (friendlyName="One-position Mutation" )
	public void mutateBooleanListRandom(double mutationRate) {
		BooleanOperator.mutateBooleanListRandom(this, mutationRate);
	}


	@Override
	public BooleanArrayListGenome create() { 
		return new myGenomePart9();
	}

	@Override
	public myGenomePart9 clone() { 
	return (myGenomePart9)super.clone();
	}

}