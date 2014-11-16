package plugin.generated.Aug17;

import edu.ccil.ec.genotype.VariationOperator;
import edu.ccil.ec.genotype.arraylist.RealArrayListGenome;
import edu.ccil.ec.genotype.arraylist.RealBoundary;
import edu.ccil.ec.genotype.arraylist.RealOperator;

public class myGenome extends RealArrayListGenome {

	private static final long serialVersionUID = 1L;

	private static RealBoundary[] boundary = {
			new RealBoundary(-5.12,true,5.12,true),
			new RealBoundary(-5.12,true,5.12,true),
			new RealBoundary(-5.12,true,5.12,true),
			new RealBoundary(-5.12,true,5.12,true),
			new RealBoundary(-5.12,true,5.12,true),
			new RealBoundary(-5.12,true,5.12,true),
			new RealBoundary(-5.12,true,5.12,true),
			new RealBoundary(-5.12,true,5.12,true),
			new RealBoundary(-5.12,true,5.12,true),
			new RealBoundary(-5.12,true,5.12,true)
	};

	public myGenome() {
		super();
		initialSize=10;
	}



	@VariationOperator (friendlyName="Discrete Recombination" )
	public void recombineRealListDiscrete(myGenome genome, double recombinationRate) {
		RealOperator.recombineRealListDiscrete(this, genome, recombinationRate);
	}

	@VariationOperator (friendlyName="Continuous Recombination" )
	public void recombineRealListContinuous(myGenome genome, double recombinationRate) {
		RealOperator.recombineRealListContinuous(this, genome, recombinationRate);
	}

	@VariationOperator (friendlyName="Convex Recombination (random alpha)" )
	public void recombineRealListConvex(myGenome genome, double recombinationRate) {
		RealOperator.recombineRealListConvex(this, genome, recombinationRate);
	}

	@VariationOperator (friendlyName="One-position Mutation" )
	public void mutateRealListOnePosition(double mutationRate) {
		RealOperator.mutateRealListOnePosition(this, mutationRate);
	}

	@VariationOperator (friendlyName="Creep Mutation (sigma=rate)" )
	public void mutateRealListCreep(double mutationRate) {
		RealOperator.mutateRealListCreep(this, mutationRate);
	}


	@Override
	public RealBoundary getBoundary(int i){
		return boundary[i];
	}

	@Override
	public RealArrayListGenome create() { 
		return new myGenome();
	}

	@Override
	public myGenome clone() { 
	return (myGenome)super.clone();
	}

}