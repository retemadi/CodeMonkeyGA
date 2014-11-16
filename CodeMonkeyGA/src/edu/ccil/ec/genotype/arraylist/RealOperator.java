package edu.ccil.ec.genotype.arraylist;

import edu.ccil.ec.genotype.ArrayListGenotype;
import edu.ccil.ec.genotype.VariationOperator;
import edu.ccil.ec.tool.Util;

/**
 * A Utility class providing different recombination & mutation methods at collection level
 * 
 * @author RezaEtemadi
 * 
 */
public class RealOperator {


	/**
	 * performs discrete recombination (similar to uniform for binary encoding) 
	 * between two given genotypes with Real numbers. 
	 * As described in Evolutionary Computation book (section 9.3.1)
	 * the crossover probability should be handled in the calling code 
	 * @param <G>
	 * @param p0
	 * @param p1
	 * @param probability 
	 * @return
	 */
	@VariationOperator (friendlyName="Discrete Recombination")
	public static <G> void recombineRealListDiscrete(ArrayListGenotype<G> g0, ArrayListGenotype<G> g1, double probability) {
		
		Util.UniformCrossover(g0, g1, probability);
		return;
	}
	
	
	/**
	 * performs Continuous recombination between two given genotypes with Real values.
	 * As described in Evolutionary Computation book (section 9.3.2)
	 * This is similar to average recombination (Davis, 1990) but both offspring are set 
	 * the crossover probability should be handled in the calling code
	 * @param <G>
	 * @param p0
	 * @param p1
	 * @param probability
	 * @return
	 */
	@VariationOperator (friendlyName="Continuous Recombination")
	public static void recombineRealListContinuous(ArrayListGenotype<Double> g0, ArrayListGenotype<Double> g1, double probability) {
	
		for (int i=0; i<g0.size(); i++) {
			if (Util.randomEngine.nextDouble() < probability) { //do arithmetical average
				Double average = (g0.get(i) + g1.get(i)) /2;
				g0.set(i, average);
				g1.set(i, average);
			}
		}
		return;
	}
	
	
	/**
	 * performs Convex (intermediate) recombination between two given genotypes with Real values.
	 * As described in Evolutionary Computation book (section 9.3.4.3)
	 * The coefficient alpha is chosen randomly for the whole genome and not per Allele
	 * the crossover probability should be handled in the calling code
	 * @param <G>
	 * @param p0
	 * @param p1
	 * @param probability 
	 * @return
	 */
	@VariationOperator (friendlyName="Convex Recombination (random alpha)")
	public static void recombineRealListConvex(ArrayListGenotype<Double> g0, ArrayListGenotype<Double> g1, double probability) {
	
		Double alpha = Util.randomEngine.nextDouble();
		for (int i=0; i<g0.size(); i++) {
			if (Util.randomEngine.nextDouble() < probability) { 
				g0.set(i, g0.get(i) * alpha    + g1.get(i) * (1-alpha));
				g1.set(i, g0.get(i) *(1-alpha) + g1.get(i) * alpha);
			}
		}
		return;
	}


	/**
	 * performs Local Crossover ( a sub-type of type Convex recombination) 
	 * between two given genotypes with Real values.
	 * As described in Evolutionary Computation book (section 9.3.4.4)
	 * The coefficient alpha is chosen randomly for each Allele separately
	 * the crossover probability should be handled in the calling code
	 * @param <G>
	 * @param p0
	 * @param p1
	 * @param probability 
	 * @return
	 */
	@VariationOperator (friendlyName="Local Crossover")
	public static void recombineRealListLocal(ArrayListGenotype<Double> g0, ArrayListGenotype<Double> g1, double probability) {
	
		for (int i=0; i<g0.size(); i++) {
			Double alpha = Util.randomEngine.nextDouble();
			if (Util.randomEngine.nextDouble() < probability) { 
				g0.set(i, g0.get(i) * alpha    + g1.get(i) * (1-alpha));
				g1.set(i, g0.get(i) *(1-alpha) + g1.get(i) * alpha);
			}
		}
		return;
	}
	
	

	/**
	 * swaps the contents of two elements in an ArrayList
	 * it assumes both locations are valid
	 * @param <G>
	 * @param g
	 * @param p1
	 * @param p2
	 */
	protected static <G> void swap(ArrayListGenotype<G> g, int p1, int p2){
		G temp =g.get(p1);
		g.set(p1, g.get(p2));
		g.set(p2, temp);
		return;
	}
	


	/**
	 * Applies Random mutation to each gene in a Real value genome with the given probability
	 * As described in the Evolutionary Computation book section (9.4.1.1)
	 * The random value for each gene is generated within the boundary [min, max[ that is defined for each gene 
	 * @param g  : the Real value genome (Double in java)
	 * @param probability : the probability of mutation per each gene
	 * @return
	 */
	@VariationOperator (friendlyName="One-position Mutation")
	public static void mutateRealListOnePosition(RealArrayListGenome g, double probability){
		for (int i=0; i< g.size() ; i++) {
			if (Util.randomEngine.nextDouble() < probability ) {
				g.set(i, Util.randomEngine.nextDouble(g.getBoundary(i)));
			}
		} 
		return;
	}
	
	/**
	 * perform creep mutation with probability of One  
	 * but using mutation probability for calculating the standard deviation
	 * as described in the Introduction to Evolutionary Computing 
	 * Nonuniform Mutation with a Fixed Distribution, section 3.4.
	 * @param g
	 * @param probability (used as standard deviation (sigma) for creep generator
	 */
	@VariationOperator (friendlyName="Creep Mutation (sigma=rate)")
	public static void mutateRealListCreep(RealArrayListGenome g, double probability){
		for (int i=0; i< g.size() ; i++) {
			if (Util.randomEngine.nextDouble() < probability ) {
				double creep = Util.randomEngine.randomDoubleGaussian()* probability/100;
				double newValue = g.get(i)+ creep;
				//keep it in the boundary
				if ( newValue > g.max ) newValue = g.max;
				if ( newValue < g.min ) newValue = g.min;
				g.set(i, newValue);
			}
		}
	}

	/**
	 * Shifts the content of an ArrayList Genotype forward (circular) 
	 * @param <G>
	 * @param g
	 * @return
	 */
	//@VariationOperator (friendlyName="Shift Point Mutation")
	public static <G> void mutateRealListShift(ArrayListGenotype<G> g, double probability){
		if (Util.randomEngine.nextDouble() < probability ) {
			G temp =g.get(0);
			for (int i=0; i< g.size()-1 ; i++) {
				g.set(i, g.get(i+1));
			}
			g.set(g.size()-1, temp);
		} // else remains unchanged
		return;
	
	}

	

	/**
	 * Reverses the content of an ArrayList Genotype 
	 * @param <G>
	 * @param g
	 * @return
	 */
	//@VariationOperator (friendlyName="Reverse Point Mutation")
	public static <G> void mutateRealListReverse(ArrayListGenotype<G> g, double probability){
		if (Util.randomEngine.nextDouble() < probability ) {
			for (int i=0; i<= g.size()/2 ; i++) {
				swap(g,i,(g.size()-1)-i);
			}
		} //else remains unchanged
		return;
	}
	
}
