package plugin.generated.p0;

import java.util.HashMap;

import edu.ccil.ec.Phenotype;

public class Phenome extends Phenotype<myGenome, Double> {
	private static final long serialVersionUID = 1L;

	/**
	 * Important: Write the code for body of 'evaluate' method to calculates & set the fitness.
	 * the framework invokes this method on every Phenotype during fitness calculation step.

	 * @param externalData If any external program is called this parameter contains the result
	 * otherwise it is null ( @see edu.ccil.ec.Population#evaluate() )    
	 *  
	 */
	@Override
	public void evaluate(HashMap<String,String> externalData) {
		//Use this.genome & the externalData (if applicable) to set this.fitnessValue. 
		//Optionally use express() method if is needed for intermediate process

		//TODO: this.fitnessValue =    //set the fitness value after calculation 

		return; 
	}
	
	
	/**
	 * (optional) maps the representation into solution space 
	 * all genotype decoding (if needed) should happen here.
	 * if representation and solution space are the same then this method can simply reflects the genotype.
	 */
	@Override
	public Double express() {
		//provide interpretation of genotype in the solution space 
		//(e.g. if binary is used as representation while decimal is solution then 1001 in search space expresses 5 in solution space) 
		//the return value of this method can be used in the evaluate() method if needed or in the log messages. 
		return null;

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
