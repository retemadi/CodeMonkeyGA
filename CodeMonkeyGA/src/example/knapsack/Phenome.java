package example.knapsack;
import java.util.HashMap;

import edu.ccil.ec.Phenotype;
import external.KnapsackUnBounded;


public class Phenome extends Phenotype<myGenome, Double> {
	private static final long serialVersionUID = 1L;

	
	/**
	 * TODO: inherited from Fitness interface, it calculates the fitness object of the phenotype 
	 * based on its expression in the solution space.
	 * if any external program was registered in the population class to be executed as part of fitness calculation
	 * it will be called in the evaluate method of population class and the output of that call 
	 * will be presented here to this evaluate method through the input parameter "externalData".
	 * @param externalData Use this parameter to obtain the result of execution of external prgram by the framework 
	 * if no external program is executed by the framework this parameter will be null   
	 *  
	 */
	@Override
	public void evaluate(HashMap<String,String> externalData) {
	
		fitnessValue = KnapsackUnBounded.evaluate(this.genome);
		return; 
	}
	
	
	/**
	 * TODO: map the representation into solution space 
	 * all genotype decoding happens here.
	 * the output value should be used for evaluation of fitness.
	 * if representation and solution space are the same then this 
	 * method simply reflects the genotype.
	 */

	@Override
	public String express() {
		StringBuffer sb = new StringBuffer();
		for (int i=0 ; i<genome.size() ; i++) {
			sb.append(genome.get(i)+" "+KnapsackUnBounded.itemNames[i]+", ");
		}
		return sb.toString();
	}
	
	
	
	/**
	 * default constructor
	 */
	public Phenome() {
		this(new myGenome());
	}

	
	/**
	 * constructor
	 */
	public Phenome(myGenome gen) {
		genome = gen;
	}


	
	//we override the getGenome to narrow down the return type
	//so that we use get(), set() methods
	@Override
	public myGenome getGenome(){
		return genome;
	}






}
