package example.ImageProcessingRMS;

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
		parentPoolSize=150;
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
		register(ParentSelectionStrategy.class,
				new edu.ccil.ec.selection.Proportional(20, 15, true));
		register(FitnessTransformer.class,
				new edu.ccil.ec.selection.transform.Rank());
		register(VariationStrategy.class, new myVariationStrategy(false));
		return;
	}
	

	/**
	 * The main method to execute the process
	 * @param args
	 */
	public static void main(String[] args) throws ESException, IOException {

		EvolutionProcess e = new EvolutionProcess();
		
		e.evolve();
		
		Phenome fittest = e.current.getFittest();
		
		ArrayList<Integer[]> fittestRuleSet = new ArrayList<Integer[]>();
		for (myGenome.myGenomeUnit unit: fittest.getGenome()) {
			fittestRuleSet.add(((myGenomePart0)unit.get(0)).toArray());
		}
		long time = System.currentTimeMillis();
		//external.RuleSetAnalyse.output2PGM(fittestRuleSet,  "Fittest.Training."+time+".pgm");
		//external.RuleSetAnalyse.printStat(fittestRuleSet);
		//external.RuleSetAnalyse.outputFinal2PGM(fittestRuleSet, "Fittest.Test."+time+".pgm");
		external.RuleSetImageAnalyser.output2PGM(fittestRuleSet, "Fittest.Test."+time+".pgm");
		external.RuleSetImageAnalyser.printStats();
		external.RuleSetImageAnalyser.readApplyCalculateRMS4All(fittestRuleSet);
		return;
	}

	
}
