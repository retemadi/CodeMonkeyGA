package example.rastrigin;
import java.util.HashMap;

import edu.ccil.ec.Phenotype;


public class Phenome extends Phenotype<myGenome, Double> {
	private static final long serialVersionUID = 1L;

	
	/**
	 * inherited from Fitness interface, it calculates the fitness object of the Phenome 
	 * based on its expression in the solution space.
	 * if any external program was registered in the population class to be executed as part of fitness calculation
	 * it will be called in the evaluate method of population class and the output of that call 
	 * will be presented here to this evaluate method through the input parameter "externalData".
	 * @param externalData Use this parameter to obtain the result of execution of external program by the framework 
	 * if no external program is defined ( @see edu.ccil.ec.Population#evaluate() ) this parameter will be null   
	 *  
	 */
	@Override
	public void evaluate(HashMap<String,String> externalData) {
		//TODO use the this.genome and externalData (if non null) 
		//to evaluate and set the this.fitnessValue
		Double[] xArr = this.genome.toArray(new Double[genome.size()]);
		this.fitnessValue = external.Rastrigin.evaluate(xArr);

		return; 
	}
	
	
	
	/**
	 * (optional) maps the representation into solution space 
	 * all genotype decoding (if needed) should happen here.
	 * if representation and solution space are the same then this method can simply reflects the genotype.
	 */
	@Override
	public Double express() {
		//TODO: do the interpretation of genotype in the solution space (e.g. binary 1001 expresses decimal 5 ) 
		//the return value of this method can be used in the evaluate() method if needed or in the log. 
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
