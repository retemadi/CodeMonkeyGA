package example.knapsackJGAP;

import edu.ccil.ec.Genotype;
import edu.ccil.ec.Phenotype;
import edu.ccil.ec.Population;
import edu.ccil.ec.VariationStrategy;
import edu.ccil.ec.tool.Util;

public class CopyOfmyVariationStrategy extends VariationStrategy {
	
	public CopyOfmyVariationStrategy(boolean reInsert) {
		super(reInsert); // sets the flag for reInsertion from mating pool to offspring pool at constructor
	}

	@SuppressWarnings("rawtypes")
	 @Override
		public <P extends Phenotype> Population mate(P one, P two) {
		myGenome[] result = new myGenome[2];
		result[0] = (myGenome)((Phenome)one).getGenome().clone();
		result[1] = (myGenome)((Phenome)two).getGenome().clone();
	 
	 	double chance = Util.randomEngine.nextDouble();
		if( chance <0.3) {
	    	//result[0].recombineListOnePoint(result[1],.6);
	    	//result[0].mutateArrayListSwitch(.9);
	    	//result[1].mutateArrayListSwitch(.9);
		}else if( chance <1.0) {
	    	//result[0].recombineListTwoPoint(result[1],.5);
	    	//result[0].mutateArrayListShift(.2);
	    	//result[1].mutateArrayListShift(.2);
		}
	
		Phenome first = new Phenome(result[0]);
		Phenome second = new Phenome(result[1]);
		Population<Phenome> mated = new Population<Phenome>();
		mated.add(first);
		mated.add(second);
	
		return mated;
	}


}
