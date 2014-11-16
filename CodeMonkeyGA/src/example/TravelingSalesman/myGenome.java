package example.TravelingSalesman;
import edu.ccil.ec.genotype.VariationOperator;
import edu.ccil.ec.genotype.arraylist.PermutationArrayListGenome;
import edu.ccil.ec.genotype.arraylist.PermutationOperator;

public class myGenome extends PermutationArrayListGenome {

	private static final long serialVersionUID = 1L;

	public myGenome() {
		super();
		initialSize=15;
	}



	@VariationOperator (friendlyName="PMX Crossover" )
	public void recombinePermutationListPMX(myGenome genome, double recombinationRate) {
		PermutationOperator.recombinePermutationListPMX(this, genome, recombinationRate);
	}

	@VariationOperator (friendlyName="Order Crossover" )
	public void recombinePermutationListOrder(myGenome genome, double recombinationRate) {
		PermutationOperator.recombinePermutationListOrder(this, genome, recombinationRate);
	}

	@VariationOperator (friendlyName="Cycle Crossover" )
	public void recombinePermutationListCycle(myGenome genome, double recombinationRate) {
		PermutationOperator.recombinePermutationListCycle(this, genome, recombinationRate);
	}

	@VariationOperator (friendlyName="Swap Mutation" )
	public void mutatePermutationListSwap(double mutationRate) {
		PermutationOperator.mutatePermutationListSwap(this, mutationRate);
	}

	@VariationOperator (friendlyName="Insert Mutation" )
	public void mutatePermutationListInsert(double mutationRate) {
		PermutationOperator.mutatePermutationListInsert(this, mutationRate);
	}

	@VariationOperator (friendlyName="Inversion Mutation" )
	public void mutatePermutationListInversion(double mutationRate) {
		PermutationOperator.mutatePermutationListInversion(this, mutationRate);
	}


	@Override
	public PermutationArrayListGenome create() { 
		return new myGenome();
	}

	@Override
	public myGenome clone() { 
	return (myGenome)super.clone();
	}

}