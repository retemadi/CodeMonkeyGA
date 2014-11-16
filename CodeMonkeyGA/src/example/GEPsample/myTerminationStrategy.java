package example.GEPsample;


import edu.ccil.ec.Population;
import edu.ccil.ec.TerminationStrategy;
import edu.ccil.ec.tool.Monitor;
import edu.ccil.ec.tool.Util;

public class myTerminationStrategy extends TerminationStrategy<Phenome> {


	/**
	 * logs the statistics of the given generation
	 * @param gen
	 */
	private void log (Population<Phenome> gen){
		Phenome indi = gen.getFittest(1).get(0);
		Util.log("Generation:"+gen.age+","+gen.getStats()+", Best fit:" + indi.getFitness()+ " keep going...");	
	}

	@Override
	public boolean check() {
		log(current);
		return(isFitnessReached(998,5)
			|| isProgressStalledReached(21,251)
			|| isAgeLimitReached(401));
	}

}