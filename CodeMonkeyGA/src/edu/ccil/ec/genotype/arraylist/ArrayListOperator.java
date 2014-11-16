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
public class ArrayListOperator {


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
		
		Util.UniformCrossover(g0, g1,probability);
		return;
	}
		

}
