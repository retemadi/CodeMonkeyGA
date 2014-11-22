package plugin.generated.p1;

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
	
		
		if( chance <0.8) {

	    	((myGenomePart0)result[0].get(0)).recombineBooleanListOnePoint((myGenomePart0)result[1].get(0),0.6  );
	    	((myGenomePart1)result[0].get(1)).recombineBooleanListOnePoint((myGenomePart1)result[1].get(1),0.6  );
	
	    	((myGenomePart2)result[0].get(2)).recombineBooleanListOnePoint((myGenomePart2)result[1].get(2),0.6  );
	    	((myGenomePart3)result[0].get(3)).recombineBooleanListOnePoint((myGenomePart3)result[1].get(3),0.6  );
/*
	    	((myGenomePart4)result[0].get(4)).recombineBooleanListOnePoint((myGenomePart4)result[1].get(4),0.6  );
	    	((myGenomePart5)result[0].get(5)).recombineBooleanListOnePoint((myGenomePart5)result[1].get(5),0.6  );
	    	((myGenomePart6)result[0].get(6)).recombineBooleanListOnePoint((myGenomePart6)result[1].get(6),0.6  );
	    	((myGenomePart7)result[0].get(7)).recombineBooleanListOnePoint((myGenomePart7)result[1].get(7),0.6  );
	    	((myGenomePart8)result[0].get(8)).recombineBooleanListOnePoint((myGenomePart8)result[1].get(8),0.6  );
	    	((myGenomePart9)result[0].get(9)).recombineBooleanListOnePoint((myGenomePart9)result[1].get(9),0.6  );
*/
	    	((myGenomePart0)result[0].get(0)).mutateBooleanListRandom(0.6  );
	    	((myGenomePart0)result[1].get(0)).mutateBooleanListRandom(0.6  );
	    	((myGenomePart1)result[0].get(1)).mutateBooleanListRandom(0.6  );
	    	((myGenomePart1)result[1].get(1)).mutateBooleanListRandom(0.6  );

	    	((myGenomePart2)result[0].get(2)).mutateBooleanListRandom(0.6  );
	    	((myGenomePart2)result[1].get(2)).mutateBooleanListRandom(0.6  );
	    	((myGenomePart3)result[0].get(3)).mutateBooleanListRandom(0.6  );
	    	((myGenomePart3)result[1].get(3)).mutateBooleanListRandom(0.6  );
/*
	    	((myGenomePart4)result[0].get(4)).mutateBooleanListRandom(0.6  );
	    	((myGenomePart4)result[1].get(4)).mutateBooleanListRandom(0.6  );

	    	((myGenomePart5)result[0].get(5)).mutateBooleanListRandom(0.6  );
	    	((myGenomePart5)result[1].get(5)).mutateBooleanListRandom(0.6  );
	    	((myGenomePart6)result[0].get(6)).mutateBooleanListRandom(0.6  );
	    	((myGenomePart6)result[1].get(6)).mutateBooleanListRandom(0.6  );
	    	((myGenomePart7)result[0].get(7)).mutateBooleanListRandom(0.6  );
	    	((myGenomePart7)result[1].get(7)).mutateBooleanListRandom(0.6  );
	    	((myGenomePart8)result[0].get(8)).mutateBooleanListRandom(0.6  );
	    	((myGenomePart8)result[1].get(8)).mutateBooleanListRandom(0.6  );
	    	((myGenomePart9)result[0].get(9)).mutateBooleanListRandom(0.6  );
	    	((myGenomePart9)result[1].get(9)).mutateBooleanListRandom(0.6  );
*/
		}

	 	
	
		Phenome first = new Phenome(result[0]);
		Phenome second = new Phenome(result[1]);
		Population<Phenome> mated = new Population<Phenome>();
		mated.add(first);
		mated.add(second);
	
		return mated;
	}


}
