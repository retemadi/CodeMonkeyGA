package edu.ccil.ec.genotype.arraylist;

import edu.ccil.ec.genotype.ArrayListGenotype;
import edu.ccil.ec.tool.Util;

/**
 * A Utility class providing different recombination & mutation methods at collection level
 * 
 * @author RezaEtemadi
 * 
 */
public class UtilGenome {



	/**
	 * performs one point crossover between two given genotypes.
	 * the crossover probability should be handled in the calling code
	 * @param <G>
	 * @param p0
	 * @param p1
	 * @param probability
	 * @return
	 */
	public static <G> void recombineArrayListOnePoint(ArrayListGenotype<G> g0, ArrayListGenotype<G> g1, double probability) {
		
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

	public static <G> void recombineArrayListTwoPoint(ArrayListGenotype<G> g0, ArrayListGenotype<G> g1, double probability) {
		
		if (Util.randomEngine.nextDouble() < probability ) {
			Util.TwoPointCrossover(g0, g1);  //cast exception from Genotype to ArrayListGenotype
		} // else remains untouched
		return;
	}

	
	/**
	 * performs uniform crossover between two given genotypes.
	 * the crossover probability should be handled in the calling code 
	 * @param <G>
	 * @param p0
	 * @param p1
	 * @param probability 
	 * @return
	 */
	public static <G> void recombineArrayListUniform(ArrayListGenotype<G> g0, ArrayListGenotype<G> g1, double probability) {
		
		if (Util.randomEngine.nextDouble() < probability ) {
			Util.UniformCrossover(g0, g1, 0.5);
		} // else remains untouched
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
	 * Switches the content of two elements of ArrayList Genotype on the mutation point (mutation point is a randomly selected element)
	 * if mutation point is the last element in the Arraylist then it will be switched with the element[0] (considers end is connected to start)
	 * @param <G>
	 * @param g
	 * @return
	 */
	public static <G> void mutateArrayListSwitch(ArrayListGenotype<G> g, double probability){
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
	public static <G> void mutateArrayListShift(ArrayListGenotype<G> g, double probability){
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

	public static <G> void mutateArrayListReverse(ArrayListGenotype<G> g, double probability){
		if (Util.randomEngine.nextDouble() < probability ) {
			for (int i=0; i<= g.size()/2 ; i++) {
				swap(g,i,(g.size()-1)-i);
			}
		} //else remains unchanged
		return;
	}
	
}
