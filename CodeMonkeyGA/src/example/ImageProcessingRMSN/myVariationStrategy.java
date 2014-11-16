package example.ImageProcessingRMSN;

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
	
		if( chance <0.6) {
	
	    	result[0].recombineListOnePoint(result[1],0.9); //0.6

	    	//experimental , it adds input genome to the genome if merge doesn't violates max genome size
	    	//result[0].mutateMerge(result[1],0.1);
	    	//result[1].mutateMerge(result[0],0.1);

	    	result[0].mutateInsert(0.1); 
	    	result[1].mutateInsert(0.1); 

	    	limit = (result[0].size() < result[1].size())?result[0].size():result[1].size();
	    	
			for (int i=0; i<limit; i++) {
	    		((myGenomePart0)result[0].get(i).get(0)).
	    		recombineIntegerListTwoPoint((myGenomePart0)result[1].get(i).get(0),0.9); 
			}
			for (int i=0; i<result[0].size(); i++) {
	    		((myGenomePart0)result[0].get(i).get(0)).mutateIntegerListOnePosition(0.05); 
			}
			for (int i=0; i<result[1].size(); i++) {
	    		((myGenomePart0)result[1].get(i).get(0)).mutateIntegerListOnePosition(0.05); 
			}
		} else if( chance <0.8) {

	    	result[0].recombineListUniform(result[1],0.9); 
	    	result[0].mutateDelete(0.1); 
	    	result[1].mutateDelete(0.1); 
	    	limit = (result[0].size() < result[1].size())?result[0].size():result[1].size();
	    	
			for (int i=0; i<limit; i++) {
	    		((myGenomePart0)result[0].get(i).get(0)).
	    		recombineIntegerListDiscrete((myGenomePart0)result[1].get(i).get(0),0.9); 
			}
			for (int i=0; i<result[0].size(); i++) {
	    		((myGenomePart0)result[0].get(i).get(0)).mutateIntegerListCreep(0.05); 
			}
			for (int i=0; i<result[1].size(); i++) {
	    		((myGenomePart0)result[1].get(i).get(0)).mutateIntegerListCreep(0.05); 
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
