package plugin.generated.p2;

import edu.ccil.ec.genotype.VariationOperator;
import edu.ccil.ec.genotype.arraylist.BooleanArrayListGenome;
import edu.ccil.ec.genotype.arraylist.BooleanOperator;

public class myGenomePart0 extends BooleanArrayListGenome {

	private static final long serialVersionUID = 1L;

	public myGenomePart0() {
		super();
		initialSize=5;
	}



	@VariationOperator (friendlyName="One Point Crossover" )
	public void recombineBooleanListOnePoint(myGenomePart0 genome, double recombinationRate) {
		BooleanOperator.recombineBooleanListOnePoint(this, genome, recombinationRate);
	}

	@VariationOperator (friendlyName="Two Point Crossover" )
	public void recombineBooleanListTwoPoint(myGenomePart0 genome, double recombinationRate) {
		BooleanOperator.recombineBooleanListTwoPoint(this, genome, recombinationRate);
	}

	@VariationOperator (friendlyName="Uniform Crossover" )
	public void recombineBooleanListUniform(myGenomePart0 genome, double recombinationRate) {
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
		return new myGenomePart0();
	}

	@Override
	public myGenomePart0 clone() { 
	return (myGenomePart0)super.clone();
	}

}