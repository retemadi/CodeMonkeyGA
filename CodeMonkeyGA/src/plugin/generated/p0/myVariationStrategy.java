package plugin.generated.p0;

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
	

	
		Phenome first = new Phenome(result[0]);
		Phenome second = new Phenome(result[1]);
		Population<Phenome> mated = new Population<Phenome>();
		mated.add(first);
		mated.add(second);
	
		return mated;
	}


}
