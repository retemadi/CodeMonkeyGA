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
public class DynamicListOperator {


	/**
	 * performs one point crossover between two given genotypes.
	 * the crossover probability should be handled in the calling code
	 * @param <G>
	 * @param g0
	 * @param g1
	 * @param probability
	 * @return
	 */
	@VariationOperator (friendlyName="One Point Crossover")
	public static <G> void recombineListOnePoint(ArrayListGenotype<G> g0, ArrayListGenotype<G> g1, double probability) {
		
		if (Util.randomEngine.nextDouble() < probability ) {
			Util.OnePointCrossover(g0, g1);
		} //else remains untouched	
		return;
	
	}
		
	
	
	/**
	 * performs two point crossover between two given genotypes.
	 * the crossover probability should be handled in the calling code
	 * @param <G>
	 * @param g0
	 * @param g1
	 * @param probability 
	 * @return
	 */
	@VariationOperator (friendlyName="Two Point Crossover")
	public static <G> void recombineListTwoPoint(ArrayListGenotype<G> g0, ArrayListGenotype<G> g1, double probability) {
		
		if (Util.randomEngine.nextDouble() < probability ) {
			Util.TwoPointCrossover(g0, g1);  //cast exception from Genotype to ArrayListGenotype
		} // else remains untouched
		return;
	}

	
	/**
	 * performs uniform crossover between two given genotypes at the Arraylist level
	 * the crossover probability should be handled in the calling code 
	 * @param <G>
	 * @param g0
	 * @param g1
	 * @param probability 
	 * @return
	 */
	@VariationOperator (friendlyName="Uniform Crossover")
	public static <G> void recombineListUniform(ArrayListGenotype<G> g0, ArrayListGenotype<G> g1, double probability) {
		//TODO: make sure it takes into account dynamic length of g0, g1 and doesn't break size limit in offsprting 
		Util.UniformCrossover(g0, g1,probability);
		return;
	}

	
	/**
	 * it is somewhere between mutation and recombination. 
	 * it takes two individual and append them to replace the first one, the second one remain is only input (it won't change)
	 * @param g0
	 * @param g1
	 * @param probability
	 */
	//@VariationOperator (friendlyName="Merge Mutation")
	public static <G> void mutateMerge(DynamicListGenotype<G> g0, DynamicListGenotype<G> g1, double probability) {
		if (Util.randomEngine.nextDouble() < probability )
			if ( g0.size()+g1.size() < g0.getSizeHigh() ) {
				g0.addAll(g1.clone());
			}
		return;
	}

	
	
	/**
	 * inserts a randomly generated gene unit into genotype 
	 * while not violating the maximum allowed length of variable length genotype
	 * @param <G>
	 * @param g0
	 * @param probability 
	 * @return
	 */
	@VariationOperator (friendlyName="Insert Mutation")
	public static <G> void mutateInsert(DynamicListGenotype<G> g0, double probability) {
		if (Util.randomEngine.nextDouble() < probability && g0.size() < g0.getSizeHigh() ) {
			g0.add(Util.randomEngine.pick(g0.size()),g0.newUnit());
		}
		return;
	}

	
	/**
	 * deletes a randomly selected gene unit from genotype 
	 * while not violating the minimum allowed length of variable length genotype
	 * @param <G>
	 * @param g0
	 * @param probability 
	 * @return
	 */
	@VariationOperator (friendlyName="Delete Mutation")
	public static <G> void mutateDelete(DynamicListGenotype<G> g0, double probability) {
		if (Util.randomEngine.nextDouble() < probability && g0.size() > g0.getSizeLow() ) {
			g0.remove(Util.randomEngine.pick(g0.size()));
		}

		return;
	}

	
}	

