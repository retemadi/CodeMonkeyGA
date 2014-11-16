package example.TravelingSalesman;

import edu.ccil.ec.Phenotype;
import edu.ccil.ec.Population;
import edu.ccil.ec.VariationStrategy;
import edu.ccil.ec.tool.Util;

public class myVariationStrategy extends VariationStrategy {
	
	public myVariationStrategy(boolean reInsert) {
		super(reInsert); // sets the flag for reInsertion from mating pool to offspring pool at constructor
	}

	@SuppressWarnings("rawtypes")
	@Override
	public <P extends Phenotype> Population mate(P one, P two) {
		myGenome[] result = new myGenome[2];
		result[0] = ((Phenome)one).getGenome().clone();
		result[1] = ((Phenome)two).getGenome().clone();
	
	 	double chance = Util.randomEngine.nextDouble();
	
		if( chance <0.3) {
	
	    	result[0].recombinePermutationListPMX(result[1],0.6  );
	    	result[0].mutatePermutationListSwap(0.2  );
	    	result[1].mutatePermutationListSwap(0.2  );
		} else if( chance <0.6) {
	
	    	result[0].recombinePermutationListOrder(result[1],0.6  );
	    	result[0].mutatePermutationListInsert(0.2  );
	    	result[1].mutatePermutationListInsert(0.2  );
		} else if( chance <0.8999999999999999) {
	
	    	result[0].recombinePermutationListCycle(result[1],0.6  );
	    	result[0].mutatePermutationListInversion(0.2  );
	    	result[1].mutatePermutationListInversion(0.2  );
		}
	
		Phenome first = new Phenome(result[0]);
		Phenome second = new Phenome(result[1]);
		Population<Phenome> mated = new Population<Phenome>();
		mated.add(first);
		mated.add(second);
	
		return mated;
	}


}
