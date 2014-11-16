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
public class IntegerOperator {

	/**
	 * performs one point crossover between two given genotypes.
	 * the crossover probability should be handled in the calling code
	 * @param <G>
	 * @param p0
	 * @param p1
	 * @param probability
	 * @return
	 */
	@VariationOperator (friendlyName="One Point Crossover")
	public static <G> void recombineIntegerListOnePoint(ArrayListGenotype<G> g0, ArrayListGenotype<G> g1, double probability) {
		
		if (Util.randomEngine.nextDouble() < probability ) {
			Util.OnePointCrossover(g0, g1);
		} //else remains untouched	
		return;
	}
		
	
	
	/**
	 * performs twp point crossover between two given genotypes.
	 * the crossover probability should be handled in the calling code
	 * @param <G>
	 * @param p0
	 * @param p1
	 * @param probability 
	 * @return
	 */
	@VariationOperator (friendlyName="Two Point Crossover")
	public static <G> void recombineIntegerListTwoPoint(ArrayListGenotype<G> g0, ArrayListGenotype<G> g1, double probability) {
		
		if (Util.randomEngine.nextDouble() < probability ) {
			Util.TwoPointCrossover(g0, g1);  //cast exception from Genotype to ArrayListGenotype
		} // else remains untouched
		return;
	}

	
	/**
	 * performs discrete recombination (similar to uniform for binary encoding) 
	 * between two given genotypes with Integer numbers. 
	 * As described in Evolutionary Computation book (section 9.3.1)
	 * the crossover probability should be handled in the calling code 
	 * @param <G>
	 * @param p0
	 * @param p1
	 * @param probability 
	 * @return
	 */
	@VariationOperator (friendlyName="Discrete Recombination")
	public static <G> void recombineIntegerListDiscrete(ArrayListGenotype<G> g0, ArrayListGenotype<G> g1, double probability) {
		
		Util.UniformCrossover(g0, g1, probability);
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
	 * Applies Random mutation to each gene in a Integer value genome with the given probability
	 * As described in the Evolutionary Computation book section (9.4.1.1)
	 * The random value for each gene is generated within the boundary [min,max[ that is defined in the genome 
	 * @param g  : the Integer value genome (Double in java)
	 * @param probability : the probability of mutation per each gene
	 * @return
	 */
	@VariationOperator (friendlyName="One-position Mutation")
	public static void mutateIntegerListOnePosition(IntegerArrayListGenome g, double probability){
		for (int i=0; i< g.size()-1 ; i++) {
			if (Util.randomEngine.nextDouble() < probability ) {
				g.set(i, Util.randomEngine.nextInt(g.getBoundary(i)));
			}
		} 
		return;
	}


	/**
	 * perform creep mutation with defined probability on each gene 
	 * the creep value is generated randomly using normal probability distribution with zero mean
	 * and standard deviation (simga) set to 1/10 of range 
	 * @param g
	 * @param probability
	 */
	@VariationOperator (friendlyName="Creep Mutation")
	public static void mutateIntegerListCreep(IntegerArrayListGenome g, double probability){
		for (int i=0; i< g.size()-1 ; i++) {
			if (Util.randomEngine.nextDouble() < probability ) {
				IntegerBoundary gb = g.getBoundary(i);
				Double creep = Util.randomEngine.randomDoubleGaussian()* (gb.max-gb.min)/10;
				int newValue = g.get(i) + (creep.intValue()!=0?creep.intValue():1);
				//keep it in the boundary
				if ( newValue > gb.max ) newValue = gb.max;
				if ( newValue < gb.min ) newValue = gb.min;
				g.set(i, newValue);
			}
		} 
		return;
	}

	
	/**
	 * Switches the content of two elements of ArrayList Genotype on the mutation point (mutation point is a randomly selected element)
	 * if mutation point is the last element in the Arraylist then it will be switched with the element[0] (considers end is connected to start)
	 * @param <G>
	 * @param g
	 * @return
	 */
	//@VariationOperator (friendlyName="Switch Point Mutation", type="Integer")
	public static <G> void mutateIntegerListSwitch(ArrayListGenotype<G> g, double probability){
		if (Util.randomEngine.nextDouble() < probability ) {
			int mutationPoint = Util.randomEngine.pick(g.size()-1);
			int nextPoint = (mutationPoint == g.size()-1?0:mutationPoint+1);
			swap(g, mutationPoint, nextPoint);
		} // else remain unchanged
		return;
	}


	/**
	 * Shifts the content of an ArrayList Genotype forward (circular) 
	 * @param <G>
	 * @param g
	 * @return
	 */
	//@VariationOperator (friendlyName="Shift Point Mutation", type="Integer")
	public static <G> void mutateIntegerListShift(ArrayListGenotype<G> g, double probability){
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
	//@VariationOperator (friendlyName="Reverse Point Mutation", type="Integer")
	public static <G> void mutateIntegerListReverse(ArrayListGenotype<G> g, double probability){
		if (Util.randomEngine.nextDouble() < probability ) {
			for (int i=0; i<= g.size()/2 ; i++) {
				swap(g,i,(g.size()-1)-i);
			}
		} //else remains unchanged
		return;
	}
	
}
