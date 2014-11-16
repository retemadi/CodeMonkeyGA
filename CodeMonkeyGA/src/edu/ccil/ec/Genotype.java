package edu.ccil.ec;

import java.io.Serializable;
import java.util.Collection;


/**
 * The Generic interface for representing Genotype
 * The probability of variation operators (crossover and mutation) are defined inside genome as epi-genetic data
 * It is represented as an interface and not an abstract class so it allows a concrete genotype class to extend existing collection 
 * subclasses (e.g. ArrayList, Vector, LinkedList etc.) while implementing the Genotype interface.
 * 
 * @author RezaEtemadi
 *
 * @param <G>
 */
public interface Genotype<G> extends Collection<G> , Cloneable , Serializable {

	
	public String startTag = "<Genome>";
	public String endTag = "</Genome>";

	public String alleleTagStart = "<Allele>";
	public String alleleTagEnd = "</Allele>";

	/**
	 * the factory method. 
	 * to remove dependency to class name that constructors have. We use this method
	 * in other classes when instantiating subclasses of Genotype
	 * @return
	 */
	public Genotype<G> create();
	
	
	/**
	 * Fills the Genotype<G> with randomly generated alleles (full length of Genome)
	 * this is useful when building the first generation
	 * @return
	 */
	public void fillRandom();
	
	
	/**
	 * return a clone of this Genotype
	 * @return
	 */
	public Genotype<G> clone();
	
	
	/**
	 * Because not all types of Evolutionary Algorithms use both crossover and mutation for diversification
	 * no predefined name of mutation and crossover methods are considered in Genotype & this interface
	 * does not force the implementor classes for specific variation operations. But plenty of Crossover/mutation
	 * tools are provided in the UtilGenome classes in the genotype sub-packages.
	 * As an example you can easily add code to an ArrayListGenotype subclass like this:
	 * 	
	 * public void recombine1(Genotype genome, double recombinationRate) {
	 *	  UtilGenome.recombineArrayListOnePoint(this, genome, recombinationRate);
	 * }
	 *
	 * public void mutateArrayListSwitch(double mutationRate) {
	 *	  UtilGenome.mutateArrayListSwitch(this, mutationRate);
	 * }
     * 
	 * For diversification, any number of variation operators (crossovers and/or mutations) can be defined inside the Genotype implementor classes.
	 * The actual strategy for diversification based on variation operators should be implemented inside variation strategy.
     *
     * Note that crossover and mutation utility methods do not produce a new genome object but modify the same object
     * so if current object must remain intact, clone it in your variation strategy implementation & apply on clones. 

	 * 
	 * The Abstract class VariationStrategy.apply() method is called by the framework. A subclass of this is needed to implement
	 * VariationStrategy.mate() method based on the operators defined in the Genotype implementation.
	 * The concrete subclass then can be registered into framework through registration() of Evolution class.
	 *  
	 */

	
	/**
	 * returns a string representation of the Genotype, 
	 * useful for logging purposes
	 * @return
	 */
	public String toString();
	

	/**
	 * returns a XML based string representation of the Genotype, 
	 * e.g. <genome>...<allele>...</allele> ...</genome>
	 * useful for communication with external programs who parse XML
	 * @return
	 */
	public String toXML();


	/**
	 * returns a JSON based string representation of the Genotype, 
	 * useful for logging and communicating with external programs who parse JSON format
	 * @return
	 */
	public String toJSON();

	
}
