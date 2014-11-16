package example.knapsack2;

import edu.ccil.ec.genotype.VariationOperator;
import edu.ccil.ec.genotype.arraylist.IntegerArrayListGenome;
import edu.ccil.ec.genotype.arraylist.IntegerBoundary;
import edu.ccil.ec.genotype.arraylist.IntegerOperator;

public class myGenome extends IntegerArrayListGenome {

	private static final long serialVersionUID = 1L;

	private static IntegerBoundary[] boundary = {
			new IntegerBoundary(0,true,10,true),
			new IntegerBoundary(0,true,16,true),
			new IntegerBoundary(0,true,12,true)
	};

	public myGenome() {
		super();
		initialSize=3;
	}



	@VariationOperator (friendlyName="One Point Crossover" )
	public void recombineIntegerListOnePoint(myGenome genome, double recombinationRate) {
		IntegerOperator.recombineIntegerListOnePoint(this, genome, recombinationRate);
	}

	@VariationOperator (friendlyName="Two Point Crossover" )
	public void recombineIntegerListTwoPoint(myGenome genome, double recombinationRate) {
		IntegerOperator.recombineIntegerListTwoPoint(this, genome, recombinationRate);
	}

	@VariationOperator (friendlyName="Discrete Recombination" )
	public void recombineIntegerListDiscrete(myGenome genome, double recombinationRate) {
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
		return new myGenome();
	}

	@Override
	public myGenome clone() { 
	return (myGenome)super.clone();
	}

}