package example.ackley;


import edu.ccil.ec.Population;
import edu.ccil.ec.TerminationStrategy;
import edu.ccil.ec.tool.Monitor;
import edu.ccil.ec.tool.Util;

public class myTerminationStrategy extends TerminationStrategy<Phenome> {

	/**
	 * logs the statistics of the given generation
	 * @param gen
	 */
	private void log (){
		Phenome indi = current.getFittest(1).get(0);
		Util.log("Generation:"+current.age+","+current.getStats()+", Best fitness:" + indi.getFitness()+ " keep going...");	
		Util.logCvs(current.age+","+indi.getFitness()+","+current.fitnessMean()+","+current.fitnessStandardDeviation());
	}

	@Override
	public boolean check() {
		log();
		return(isFitnessReached(0,0.000001)
			|| isProgressStalledReached(3,1000)
			|| isAgeLimitReached(5000)); //3000
	}

}