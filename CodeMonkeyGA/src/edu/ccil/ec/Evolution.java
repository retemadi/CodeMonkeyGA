package edu.ccil.ec;

import java.util.HashMap;
import java.util.logging.Level;

import edu.ccil.ec.tool.Monitor;
import edu.ccil.ec.tool.RandomEngine;
import edu.ccil.ec.tool.Util;

/**
 * This class holds the main top level logic to execute evolutionary algorithms
 * The class is abstract because no default strategies are not defined
 * Any concrete subclass needs to implement the registration() method 
 * and use register() method to enlist phenotype and strategies classes to be used in the framework 
 *  
 * @author RezaEtemadi
 *
 * @param <P>
 */
public abstract class Evolution<P extends Phenotype<?,?>> {

	protected Population<P> current; // the current generation;
	// monitors the population/generation
	protected Monitor<P> monitor;
	
	protected ParentSelectionStrategy parentSel ;
	protected SurvivalSelectionStrategy survivalSel ;
	protected VariationStrategy variationStrategy ;
	protected TerminationStrategy<P> termStrategy ;
	
	//These are default values but should be updated in subclasses based on application needs
	protected static int generationSize ;//= 100;  
	protected static int parentPoolSize ;//= 50;   
	protected static int offspringPoolSize ;//= 50;  
	
	private static HashMap<Class<?>,Object> theRegistry = new HashMap<Class<?>,Object>();

	/**
	 * the start time of the process. it is set inside the initialization.
	 */
	public static long startTime;
	
	public static long fitnessCalCounter;

	
	// for public consumption e.g. in SurvirorSelectionStrategy
	public static int getOffspringPoolSize() {
		return offspringPoolSize;
	}
	
	// for public consumption e.g. in SurvirorSelectionStrategy
	public static int getGenerationSize() {
		return generationSize;
	}

	
	
	/**
	 * 	/**
	 * register the class that will provide service for a given task
	 * @param <T>
	 * @param clazz : the class object, used as key
	 * @param instance : the instance object, used as value for the key 
	 */
	public static <T> void register(Class<T> clazz, T instance){
		//TODO: find a way to use "instaceof" , this approach only works one level
		if (clazz.equals(instance.getClass().getClassLoader().getParent().getClass())){
			throw new ESException(ESException.SanityCheckError,clazz.getName()+" does not match "+ instance.getClass().getName());
		}
		theRegistry.put(clazz,instance);
		return;
	}
	
	
	/**
	 * unregister if an object is registered for the given class 
	 * @param <T>
	 * @param clazz : the class object, used as key
	 */
	public static <T> void unregister(Class<T> clazz){
		if (theRegistry.containsKey(clazz)){
			theRegistry.remove(clazz);
		}
		return;
	}
	
	
	
	/**
	 * returns the instance that is registered with the given class or null if there is none
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T obtain(Class<T> clazz) {
		return (T)theRegistry.get(clazz);   //the casting warning suppressed
	}

	
	
	/**
	 * Any registration of strategies or types should be performed in  registration()
	 * This method is called inside initialize() method. 
	 * The alternative is to do registration directly by calling register() 
	 * anywhere in the code before the call of initialize() method (directly or through evolve() method).
	 * e.g. register(Phenotype.class,new Phenome());
	 */
	public abstract void registration();

	
	
	/**
	 * This method calls registration and then verifies that necessary classes are registered in the system
	 * then it creates the first generation by calling initialGeneration.
	 * @throws ESException
	 */
	@SuppressWarnings("unchecked")
	public void initialize() throws ESException{
		
		Util.logger.setLevel(Level.INFO);
		
		if (generationSize < 1) {
			throw new ESException(ESException.SanityCheckError,"Population size is not valid. Set 'generationSize' in the constructor of your implementation");
		}
		if (parentPoolSize < 1) {
			throw new ESException(ESException.SanityCheckError,"Parent pool size is not valid. Set 'parentPoolSize' in the constructor of your implementation");
		}
		if (offspringPoolSize < 1) {
			throw new ESException(ESException.SanityCheckError,"Offspring pool size is not valid. Set 'offspringPoolSize' in the constructor of your implementation");
		}
		
		registration();
		
		if (theRegistry.isEmpty()) 
			throw new ESException(ESException.SanityCheckError,"You need to register at least Phenotype class and classes for strategies for Parent Selection, Variation, Survival and Termination");
		
		if (obtain(Phenotype.class) == null) 
			throw new ESException(ESException.NoPhenotype,"No Phenotype class is registered.");
		
		//if no Population is registered use the default 
		if (obtain(Population.class) == null) {
			register(Population.class,new Population<P>());
		}

		String where2Look = "\nCheck the 'registration()' method in your implementation: "+this.getClass();
		parentSel = obtain(ParentSelectionStrategy.class);
		if (parentSel == null) 
			throw new ESException(ESException.PSA_Missing,"No Parent Selection Strategy is registered."+where2Look );
		
		parentSel.setTransformer(obtain(FitnessTransformer.class));  //the optional fitness value transformer. It can be null
		
		survivalSel = obtain(SurvivalSelectionStrategy.class);
		if (survivalSel == null) 
			throw new ESException(ESException.SurvivalMissing,"No Survival Selection Strategy is registered."+where2Look);
		
		variationStrategy = obtain(VariationStrategy.class);
		if (variationStrategy == null) 
			throw new ESException(ESException.VariationMissing,"No Variation Operators Strategy is registered."+where2Look);

		termStrategy = obtain(TerminationStrategy.class);
		if (termStrategy == null) 
			throw new ESException(ESException.TerminationMissing,"No Termination Strategy is registered."+where2Look);

		// if no random engine is resisted then use the default (uniform random engine)
		if (obtain(RandomEngine.class) == null) {
			register(RandomEngine.class,new RandomEngine());
		}
		//if no Monitor is registered use the default 
		if (obtain(Monitor.class) == null) {
			register(Monitor.class,new Monitor<P>());
		}
		
		StringBuffer buf = new StringBuffer("Launching the EA with following setting:\n");
		buf.append("\nGeneration Size:\t"+ getGenerationSize());
		buf.append("\nParent Pool Size:\t"+ parentPoolSize);
		buf.append("\nOffspring Pool Size:\t"+ getOffspringPoolSize());
		buf.append("\n\n----- Registered classes -----\n");
		buf.append("\nPhenotype:\t"+ obtain(Phenotype.class).getClass());
		buf.append("\nPopulation:\t"+ obtain(Population.class).getClass());
		buf.append("\nParent Selection Strategy:\t"+ obtain(ParentSelectionStrategy.class).getClass());
		buf.append("\nFitness Transformation:\t\t"+ (obtain(FitnessTransformer.class) == null?" None":obtain(FitnessTransformer.class).getClass()));
		buf.append("\nSurvival Selection Strategy:\t"+ obtain(SurvivalSelectionStrategy.class).getClass());
		buf.append("\nVariation Strategy:\t\t"+ obtain(VariationStrategy.class).getClass());
		buf.append("\nTermination Strategy:\t\t"+ obtain(TerminationStrategy.class).getClass());

		Util.log(buf.toString());
		
		startTime = System.currentTimeMillis();
		Util.log("\nStart Time: "+new java.util.Date(startTime));
		monitor = obtain(Monitor.class);
		Util.randomEngine = Evolution.obtain(RandomEngine.class);   //set the random engine in the Util class
		current = initialGeneration();
		Util.log("Starting evaluation ...");
		current.evaluate(); // it is needed for termination criteria check before actual evolution process
	}

	
	
	/**
	 * creates the initial population using default Population class 
	 * or any registered Population class.
	 * fills the population to the generationSize value by creating instances of registered Phenotype class
	 * and calling fillRandom() method of their Genome. 
	 * @param <P>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <P extends Phenotype<?,?>> Population<P> initialGeneration(){
		Util.log("Creating initial generation ....."); // five . is used as hint in log formatter not to add new line
		//we can use the same registered instance as the first generation
		Population<P> genZero = obtain(Population.class);
		genZero.age=0;
		// randomly generates the first population by calling fillRandom on each individual
		Phenotype<?,?> instance = obtain(Phenotype.class);
		for(int i=0; i<generationSize; i++) {
			//we replicate the instance that is registered as we need many of them
			P phenome = (P)Util.makeNewInstanceOfType(instance);  //type safety warning suppressed 
			phenome.getGenome().fillRandom();
			genZero.add(phenome);
			Util.log(Level.FINE,i+":"+phenome.getGenome()); //
		}
		Util.log(" Done.");
		return genZero;
	}

	
	
	/**
	 * applies the evolutionary operators on current generation
	 * and replace the current generation.
	 * @return
	 */
	public void getNextGen(){
		
		Population<P> parentPool = parentSel.apply(current, parentPoolSize);  
		Population<P> offspring = variationStrategy.apply(parentPool, offspringPoolSize);
		offspring.evaluate(); //it is needed for survival selection (except few cases where all go automatically to next generation) and for termination criteria check at next round.
		int age = current.age;
		current = survivalSel.apply(current, offspring);
		current.age = age +1; //increase the age
	}		


	
	/**
	 * Returns the result of checking termination condition
	 * 
	 * @return
	 */
	public boolean isSatisfied(){
//		current.evaluate(); //if first generation, it is already evaluated, in subsequent gens, offspring and re-init pop needs them on their own
		monitor.add(current);
		termStrategy.current = current;
		termStrategy.monitor = monitor;
		return termStrategy.check();
	}

	
	/**
	 * The iterative process of evolving the population.
	 * The process initialize the population and continues creating next generation
	 * until the termination condition is met
	 * @return
	 * @throws ESException
	 */
	public Population<P> evolve() throws ESException {

		try {
			
			initialize();
			while (!isSatisfied()){
				getNextGen();
				// maybe we can check if anything is registered to manipulate the fitness transformer parameters
				//if they need to be changed between generations.
				//even better have an optional method in transformer that is called here.
			}
		} catch (Exception e) {
			e.printStackTrace();
			Util.log(Level.SEVERE,"Exception happened at generation "+ current.age );
			System.exit(-1);
		}

		Util.logFooter(current,termStrategy);

		return current;
	}
	
	
	/**
	 * The main method to execute the process
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		
		Util.log(Level.SEVERE, "\nNOTE: " +Evolution.class.getName()+" is the abstract class of the Evolutionary process.");
		Util.log(Level.SEVERE,"A concrete subclass of this class that implemented registration() method can run.");
		return;
	}
	
	
	//INITIALIZE population randomly
	//EVALUATE each candidate
	//REPEAT UNTIL ( TERMINATION CONDITION is reached ) DO
	  //SELECT parents
	  //RECOMBINE the parents (with probability)
	  //MUTATE the resulting offspring (with probability)
	  //EVALUATE new candidates
	  //SELECT individuals for next generation  //sometimes offsprings advance automatically
	//END REPEAT
	
	 /*
	  * get some idea
	 public Population evolve(Population initial, StoppingCondition condition) {
        Population current = initial;
        while (!condition.isSatisfied(current)) {
            current = nextGeneration(current);
        }
        return current;
    }

	  */
	
	// idea of 
	// evolve , netxGen method , add method to pool, 
	//class for recombination, mutation and termination condition 
	
	
}
