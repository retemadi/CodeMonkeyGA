package example.GEPsample;

import edu.ccil.ec.Genotype;
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
		if( chance <0.4) {
	    	result[0].recombineArrayListTwoPoint(result[1],.9);
	    	result[0].recombineArrayListOnePoint(result[1],.6);
	    	result[0].mutateArrayListShift(.5);
	    	result[1].mutateArrayListShift(.5);
		}else if( chance <1.0) {
	    	result[0].recombineArrayListUniform(result[1],.4);
	    	result[0].mutateArrayListSwitch(.3);
	    	result[1].mutateArrayListSwitch(.3);
		}
	
		Phenome first = new Phenome(result[0]);
		Phenome second = new Phenome(result[1]);
		Population<Phenome> mated = new Population<Phenome>();
		mated.add(first);
		mated.add(second);
	
		return mated;
	}



}
