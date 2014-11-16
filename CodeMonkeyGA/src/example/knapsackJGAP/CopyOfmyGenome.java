package example.knapsackJGAP;
import edu.ccil.ec.Genotype;
import edu.ccil.ec.genotype.ArrayListGenotype;
import edu.ccil.ec.genotype.VariationOperator;
import edu.ccil.ec.genotype.arraylist.UtilGenome;
import edu.ccil.ec.genotype.arraylist.BooleanArrayListGenome;

public class CopyOfmyGenome extends BooleanArrayListGenome {

	private static final long serialVersionUID = 1L;

	public CopyOfmyGenome() {
		super();
		initialSize=10;
		return;
	}

	@VariationOperator (friendlyName="One Point Crossover", type="boolean")
	public void recombineArrayListOnePoint(CopyOfmyGenome genome, double recombinationRate) {
		UtilGenome.recombineArrayListOnePoint(this, genome, recombinationRate);
	}

	@VariationOperator (friendlyName="Two Point Crossover", type="boolean")
	public void recombineArrayListTwoPoint(CopyOfmyGenome genome, double recombinationRate) {
		UtilGenome.recombineArrayListTwoPoint(this, genome, recombinationRate);
	}
	
	@VariationOperator (friendlyName="Switch Point Mutation", type="boolean")
	public void mutateArrayListSwitch(double mutationRate) {
		UtilGenome.mutateArrayListSwitch(this, mutationRate);
	}
	
	@VariationOperator (friendlyName="Shift Point Mutation", type="boolean")
	public void mutateArrayListShift(double mutationRate) {
		UtilGenome.mutateArrayListShift(this, mutationRate);
	}


	@Override
	public BooleanArrayListGenome create() { 
		return new CopyOfmyGenome();
	}

	@Override
	public CopyOfmyGenome clone() { 
	return (CopyOfmyGenome)super.clone();
	}

}