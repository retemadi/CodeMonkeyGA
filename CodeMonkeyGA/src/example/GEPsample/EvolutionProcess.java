package example.GEPsample;

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


public class EvolutionProcess extends Evolution<Phenome> {


	public EvolutionProcess(){
		super();
		generationSize=350;
		parentPoolSize=250;
		offspringPoolSize=150;
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
		register(ParentSelectionStrategy.class,	new edu.ccil.ec.selection.Truncation(20, 16, true));
		register(VariationStrategy.class, new myVariationStrategy(false));
		register(TerminationStrategy.class, new myTerminationStrategy());
		register(SurvivalSelectionStrategy.class, new mySurvivalSelectionStrategy());
		register(RandomEngine.class, new RandomEngine());
		//register(FitnessTransformer.class, new edu.ccil.ec.selection.transform.PowerLaw(6));
		return;
	}
	

	/**
	 * The main method to execute the process
	 * @param args
	 */
	public static void main(String[] args) throws ESException {

		EvolutionProcess e = new EvolutionProcess();
		
		Population<Phenome> output = e.evolve();
		
		System.out.println(output.getFittest(1));
		return;
	}

	
}
