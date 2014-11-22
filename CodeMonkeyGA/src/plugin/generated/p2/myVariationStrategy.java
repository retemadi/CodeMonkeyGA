package plugin.generated.p2;

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
		int limit = (result[0].size() < result[1].size())?result[0].size():result[1].size();
	
	 	double chance = Util.randomEngine.nextDouble();
	
		if( chance <0.2) {
	
	    	//result[0].mutateInsert(0.8  );
	    	//result[1].mutateInsert(0.8  );
			for (int i=0; i<limit; i++) {
	    		((myGenomePart0)result[0].get(i).get(0)).recombineBooleanListOnePoint((myGenomePart0)result[1].get(i).get(0),0.6  );
			}
			for (int i=0; i<result[0].size(); i++) {
	    		((myGenomePart0)result[0].get(i).get(0)).mutateBooleanListRandom(0.6);
			}
			for (int i=0; i<result[1].size(); i++) {
	    		((myGenomePart0)result[1].get(i).get(0)).mutateBooleanListRandom(0.6);
			}

			for (int i=0; i<limit; i++) {
	    		((myGenomePart1)result[0].get(i).get(1)).recombineIntegerListTwoPoint((myGenomePart1)result[1].get(i).get(1),0.6  );
			}
		} else if( chance <0.4) {
			//result[0].mutateDelete(0.8);
			//result[1].mutateDelete(0.8);
		} else if( chance <0.8) {
	
	    	result[0].recombineListUniform(result[1],0.6  );
			for (int i=0; i<result[0].size(); i++) {
	    		((myGenomePart0)result[0].get(i).get(0)).mutateBooleanListFlip(0.6  );
			}
			for (int i=0; i<result[1].size(); i++) {
	    		((myGenomePart0)result[1].get(i).get(0)).mutateBooleanListFlip(0.6  );
			}
			for (int i=0; i<result[0].size(); i++) {
	    		((myGenomePart1)result[0].get(i).get(1)).mutateIntegerListOnePosition(0.6  );
			}
			for (int i=0; i<result[1].size(); i++) {
	    		((myGenomePart1)result[1].get(i).get(1)).mutateIntegerListOnePosition(0.6  );
			}
		}
	
		Phenome first = new Phenome(result[0]);
		Phenome second = new Phenome(result[1]);
		Population<Phenome> mated = new Population<Phenome>();
		mated.add(first);
		mated.add(second);
	
		return mated;
	}


}
