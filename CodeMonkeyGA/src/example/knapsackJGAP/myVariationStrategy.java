package example.knapsackJGAP;

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
	
		if( chance <0.6) {
	
	    	result[0].recombineIntegerListOnePoint(result[1],0.7  );
	    	result[0].recombineIntegerListTwoPoint(result[1],0.7  );
		} else if( chance <0.8999999999999999) {
	
	    	result[0].mutateIntegerListCreep(0.1  );
	    	result[1].mutateIntegerListCreep(0.1  );
	    	result[0].mutateIntegerListOnePosition(0.1  );
	    	result[1].mutateIntegerListOnePosition(0.1  );
		}
	
		Phenome first = new Phenome(result[0]);
		Phenome second = new Phenome(result[1]);
		Population<Phenome> mated = new Population<Phenome>();
		mated.add(first);
		mated.add(second);
	
		return mated;
	}


}
