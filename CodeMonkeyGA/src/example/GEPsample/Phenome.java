package example.GEPsample;
import java.util.HashMap;

import edu.ccil.ec.Phenotype;
import edu.ccil.ec.genotype.gep.TreeEvaluator;
import edu.ccil.ec.tool.Util;

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
		
		//genome has n parts;
		//genome.get(0); //is part one which is a list of m binary numbers or ...
		
		
		double error =0.001;
		for (double i=0; i<10; i++){
			genome.a.value = i;
			error += Math.abs(express() - ((genome.a.value*2)+10));
		}
	
		fitnessValue = 1/error;
		if (Double.isNaN(fitnessValue)) {  //when error is NaN the reverse is NaN and ends up in top of sort therefore eliminating by setting it negative 
			fitnessValue = -1000.0;
		}
		TreeEvaluator<Double> tree = new TreeEvaluator<Double>();
		Util.log(tree.buildTree(genome)+"=>(Err:"+error+")F:"+ fitnessValue);
	}

	
	
	/**
	 * TODO: map the representation into solution space 
	 * all genotype decoding happens here.
	 * the output value should be used for evaluation of fitness.
	 * if representation and solution space are the same then this 
	 * method simply reflects the genotype.
	 */
	@Override
	public Double express() {
		TreeEvaluator<Double> tree = new TreeEvaluator<Double>();
		return tree.evaluate(genome);
	}
	
/* for sample GA		
	public Integer express() {
		Integer rawVal = 0;
		for (int i=0 ; i<genome.size() ; i++) {
			if (genome.get(i)) { 
				rawVal += (int)Math.pow(2,i);
			}
		}
		return rawVal;
	}
*/	
	

	
	
	
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
		fitnessValue = 0.0;
	}


	
	//we override the getGenome to narrow down the return type
	//so that we use get(), set() methods
	@Override
	public myGenome getGenome(){
		return genome;
	}



		
	public static void main(String[] args){
		Phenome me = new Phenome();
		me.genome.fillRandom();
		System.out.println(	me.toString());
		System.out.println(me.toXML());
		System.out.println(me.toJSON());
		//String res =Util.runExternalCommand("c:/thesis/code/PGAF2/src/runExternal.bat","c:\\temp",me,edu.ccil.ec.Population.Format.XML);
		//me.setFitnessFromStr(res);
		return;
	}









}
