package example.ImageProcessingRMS;

import edu.ccil.ec.genotype.VariationOperator;
import edu.ccil.ec.genotype.arraylist.IntegerArrayListGenome;
import edu.ccil.ec.genotype.arraylist.IntegerBoundary;
import edu.ccil.ec.genotype.arraylist.IntegerOperator;

public class myGenomePart0 extends IntegerArrayListGenome {

	private static final long serialVersionUID = 1L;

	private static IntegerBoundary[] boundary = {
			new IntegerBoundary(0,true,255,true),
			new IntegerBoundary(0,true,255,true),
			new IntegerBoundary(0,true,255,true),
			new IntegerBoundary(0,true,255,true),
			new IntegerBoundary(0,true,255,true),
			new IntegerBoundary(0,true,255,true),
			new IntegerBoundary(0,true,255,true),
			new IntegerBoundary(0,true,255,true),
			new IntegerBoundary(0,true,255,true),
			new IntegerBoundary(0,true,255,true),
			new IntegerBoundary(0,true,255,true),
			new IntegerBoundary(0,true,255,true),
			new IntegerBoundary(0,true,255,true),
			new IntegerBoundary(0,true,255,true),
			new IntegerBoundary(0,true,255,true),
			new IntegerBoundary(0,true,255,true),
			new IntegerBoundary(0,true,255,true),
			new IntegerBoundary(0,true,255,true),
			new IntegerBoundary(0,true,255,true),
			new IntegerBoundary(0,true,255,true),
			new IntegerBoundary(0,true,255,true),
			new IntegerBoundary(0,true,255,true),
			new IntegerBoundary(0,true,255,true),
			new IntegerBoundary(0,true,255,true),
			new IntegerBoundary(0,true,255,true)
	};

	public myGenomePart0() {
		super();
		initialSize=25;
	}



	@VariationOperator (friendlyName="One Point Crossover" )
	public void recombineIntegerListOnePoint(myGenomePart0 genome, double recombinationRate) {
		IntegerOperator.recombineIntegerListOnePoint(this, genome, recombinationRate);
	}

	@VariationOperator (friendlyName="Two Point Crossover" )
	public void recombineIntegerListTwoPoint(myGenomePart0 genome, double recombinationRate) {
		IntegerOperator.recombineIntegerListTwoPoint(this, genome, recombinationRate);
	}

	@VariationOperator (friendlyName="Discrete Recombination" )
	public void recombineIntegerListDiscrete(myGenomePart0 genome, double recombinationRate) {
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
		return new myGenomePart0();
	}

	@Override
	public myGenomePart0 clone() { 
	return (myGenomePart0)super.clone();
	}

}