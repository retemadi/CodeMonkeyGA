package example.knapsackJGAP;
import edu.ccil.ec.genotype.VariationOperator;
import edu.ccil.ec.genotype.arraylist.IntegerArrayListGenome;
import edu.ccil.ec.genotype.arraylist.IntegerBoundary;
import edu.ccil.ec.genotype.arraylist.IntegerOperator;

public class myGenome extends IntegerArrayListGenome {

	private static final long serialVersionUID = 1L;

	private static IntegerBoundary[] boundary = {
										 new IntegerBoundary(0,4),
										 new IntegerBoundary(0,14),
										 new IntegerBoundary(0,8),
										 new IntegerBoundary(0,1),
										 new IntegerBoundary(0,8),
										 new IntegerBoundary(0,43),
										 new IntegerBoundary(0,3),
										 new IntegerBoundary(0,1),
										 new IntegerBoundary(0,1),
										 new IntegerBoundary(0,1),
										};
	public myGenome() {
		super();
		initialSize=10;
		//min=0;
		//max=40;
		
		return;
	}



	@VariationOperator (friendlyName="One Point Crossover" )
	public void recombineIntegerListOnePoint(myGenome genome, double recombinationRate) {
		IntegerOperator.recombineIntegerListOnePoint(this, genome, recombinationRate);
	}

	@VariationOperator (friendlyName="Integer Two Point Crossover" )
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
		return (myGenome) super.clone();
	}

	
	
}