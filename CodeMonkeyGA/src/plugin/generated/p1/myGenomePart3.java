package plugin.generated.p1;

import edu.ccil.ec.genotype.VariationOperator;
import edu.ccil.ec.genotype.arraylist.BooleanArrayListGenome;
import edu.ccil.ec.genotype.arraylist.BooleanOperator;

public class myGenomePart3 extends BooleanArrayListGenome {

	private static final long serialVersionUID = 1L;

	public myGenomePart3() {
		super();
		initialSize=6;
	}



	@VariationOperator (friendlyName="One Point Crossover" )
	public void recombineBooleanListOnePoint(myGenomePart3 genome, double recombinationRate) {
		BooleanOperator.recombineBooleanListOnePoint(this, genome, recombinationRate);
	}

	@VariationOperator (friendlyName="Two Point Crossover" )
	public void recombineBooleanListTwoPoint(myGenomePart3 genome, double recombinationRate) {
		BooleanOperator.recombineBooleanListTwoPoint(this, genome, recombinationRate);
	}

	@VariationOperator (friendlyName="Uniform Crossover" )
	public void recombineBooleanListUniform(myGenomePart3 genome, double recombinationRate) {
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
		return new myGenomePart3();
	}

	@Override
	public myGenomePart3 clone() { 
	return (myGenomePart3)super.clone();
	}

}