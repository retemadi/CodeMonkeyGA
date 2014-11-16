package example.knapsackJGAP;

import edu.ccil.ec.ESException;
import edu.ccil.ec.Evolution;
import edu.ccil.ec.FitnessTransformer;
import edu.ccil.ec.ParentSelectionStrategy;
import edu.ccil.ec.Phenotype;
import edu.ccil.ec.Population;
import edu.ccil.ec.SurvivalSelectionStrategy;
import edu.ccil.ec.TerminationStrategy;
import edu.ccil.ec.VariationStrategy;
import edu.ccil.ec.tool.*;
import external.KnapsackFitnessFunction;


public class EvolutionProcess extends Evolution<Phenome> {


	public EvolutionProcess(){
		super();
		generationSize=50;
		parentPoolSize=40;
		offspringPoolSize=30;
		return;
	}


	@Override
	public void getNextGen(){
		super.getNextGen();
		/* TODO:
		 * Here you can manipulate the parameters of fitness transformer(if is defined)
		 * if they need to be changed between generations
		 */
		return;
	}
	
	
	@Override 
	public void registration(){
		//Any registration of strategy or type that is used in the process goes here
		register(Phenotype.class,new Phenome());
		register(Population.class, new PhenomePopulation());
		register(ParentSelectionStrategy.class, new edu.ccil.ec.selection.Proportional(3, 1, false));	
		register(VariationStrategy.class, new myVariationStrategy(true));
		register(TerminationStrategy.class, new myTerminationStrategy());
		register(SurvivalSelectionStrategy.class, new mySurvivalSelectionStrategy());
		register(RandomEngine.class, new RandomEngine());
		return;
	}
	

	/**
	 * The main method to execute the process
	 * @param args
	 */
	public static void main(String[] args) throws ESException {

		EvolutionProcess e = new EvolutionProcess();
		
		Population<Phenome> output = e.evolve();
		
		output.logPopulation();
		Phenome answer = output.getFittest(1).get(0);
		//System.out.println("Answer:"+answer);
		double totalVolume = 0.0d;
		for (int i=0; i<answer.getGenome().size(); i++) {
			totalVolume += answer.getGenome().get(i)* KnapsackFitnessFunction.itemVolumes[i];
		}
		Util.log("\nThe volume "+totalVolume+ " ccm =("+answer.express()+")");
		return;
	}

	
}
