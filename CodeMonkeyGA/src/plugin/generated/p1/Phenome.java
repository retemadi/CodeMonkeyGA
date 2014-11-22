package plugin.generated.p1;

import java.util.HashMap;

import edu.ccil.ec.Phenotype;
import edu.ccil.ec.genotype.ArrayListGenotype;
import edu.ccil.ec.genotype.arraylist.BooleanArrayListGenome;
import external.FullyDeceptive;

public class Phenome extends Phenotype<myGenome, Double> {
	private static final long serialVersionUID = 1L;

	/**
	 * use this method to calculates the fitness of the Phenome.
	 * the framework invokes this method on every Phenotype (Phenome) during fitness calculation step.

	 * @param externalData If any external program is registered this parameter is used to pass related data
	 * if no external external program is defined ( @see edu.ccil.ec.Population#evaluate() ) this parameter is null   
	 *  
	 */
	@Override
	public void evaluate(HashMap<String,String> externalData) {
		//Use this.genome & the externalData (if applicable) to set this.fitnessValue. 
		//Optionally use express() method if needed for intermediate process genome + externalData
		
		this.fitnessValue = FullyDeceptive.evaluate((ArrayListGenotype<ArrayListGenotype>)this.genome); 

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
