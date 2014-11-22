package plugin.generated.p2;

import java.io.IOException;
import java.util.ArrayList;

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
		generationSize=300;
		parentPoolSize=200;
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
		register(TerminationStrategy.class, new myTerminationStrategy());
		register(SurvivalSelectionStrategy.class, new mySurvivalSelectionStrategy());
		register(RandomEngine.class, new RandomEngine());
		//register(ParentSelectionStrategy.class,
			//	new edu.ccil.ec.selection.Truncation(2, 1, true));
		register(ParentSelectionStrategy.class,
				new edu.ccil.ec.selection.Truncation(2, 1, true));
		register(VariationStrategy.class, new myVariationStrategy(true));
		register(FitnessTransformer.class,
				new edu.ccil.ec.selection.transform.Rank());
		return;
	}
	

	/**
	 * The main method to execute the process
	 * @param args
	 */
	public static void main(String[] args) throws ESException,IOException {

		EvolutionProcess e = new EvolutionProcess();
		
		e.evolve();
		
		//for testing
		Phenome theBest = e.current.getFittest(1).get(0);
		ArrayList<Boolean[]> ruleSet = new ArrayList<Boolean[]>();
		for (myGenome.myGenomeUnit unit: theBest.getGenome()){
			ruleSet.add(((myGenomePart0)(unit.get(0))).toArray());
		}
		
		external.RuleSetCA.createPGM(ruleSet,"TestOutput."+System.currentTimeMillis()+".pgm");
		
		return;
	}

	
}
