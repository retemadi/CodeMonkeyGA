package example.GEPsample;

import edu.ccil.ec.Population;
import edu.ccil.ec.TerminationStrategy;
import edu.ccil.ec.tool.Monitor;
import edu.ccil.ec.tool.Util;

public class myTerminationStrategyCopy extends TerminationStrategy<Phenome> {


	/**
	 * logs the statistics of the given generation
	 * @param gen
	 */
	private void log (Population<Phenome> gen){
		Phenome indi = gen.getFittest(1).get(0);
		Util.log("Generation:"+gen.age+","+gen.getStats()+", Best fit:" + indi.getFitness()+ " keep going...");	
	}

	
	private boolean isFitnessReached(){
		Phenome indi = current.getFittest(1).get(0);
		return (Math.abs(indi.getFitness() - 1000)/100  <= 6);
	}


	private boolean isProgressStalledReached(){
		return (monitor.fitnessProgressStalled(20,250));
	}


	@Override
	public boolean check() {
		log(current);
		return(isFitnessReached()
			|| isProgressStalledReached());
	}

}