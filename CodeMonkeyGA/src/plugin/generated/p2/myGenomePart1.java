package plugin.generated.p2;

import edu.ccil.ec.genotype.VariationOperator;
import edu.ccil.ec.genotype.arraylist.IntegerArrayListGenome;
import edu.ccil.ec.genotype.arraylist.IntegerBoundary;
import edu.ccil.ec.genotype.arraylist.IntegerOperator;

public class myGenomePart1 extends IntegerArrayListGenome {

	private static final long serialVersionUID = 1L;

	private static IntegerBoundary[] boundary = {
			new IntegerBoundary(3,true,10,true),
			new IntegerBoundary(3,true,10,true),
			new IntegerBoundary(3,true,10,true),
			new IntegerBoundary(3,true,10,true),
			new IntegerBoundary(3,true,10,true)
	};

	public myGenomePart1() {
		super();
		initialSize=5;
	}



	@VariationOperator (friendlyName="One Point Crossover" )
	public void recombineIntegerListOnePoint(myGenomePart1 genome, double recombinationRate) {
		IntegerOperator.recombineIntegerListOnePoint(this, genome, recombinationRate);
	}

	@VariationOperator (friendlyName="Two Point Crossover" )
	public void recombineIntegerListTwoPoint(myGenomePart1 genome, double recombinationRate) {
		IntegerOperator.recombineIntegerListTwoPoint(this, genome, recombinationRate);
	}

	@VariationOperator (friendlyName="Discrete Recombination" )
	public void recombineIntegerListDiscrete(myGenomePart1 genome, double recombinationRate) {
		IntegerOperator.recombineIntegerListDiscrete(this, genome, recombinationRate);
	}

	@VariationOperator (friendlyName="One-position Mutation" )
	public void mutateIntegerListOnePosition(double mutationRate) {
		IntegerOperator.mutateIntegerListOnePosition(this, mutationRate);
	}

	@VariationOperator (friendlyName="Creep Mutation" )
	public void mutateIntegerListCreep(double mutationRate) {
		IntegerOperator.mutateIntegerListCreep(this, mutationRate);
	}


	@Override
	public IntegerBoundary getBoundary(int i){
		return boundary[i];
	}

	@Override
	public IntegerArrayListGenome create() { 
		return new myGenomePart1();
	}

	@Override
	public myGenomePart1 clone() { 
	return (myGenomePart1)super.clone();
	}

}