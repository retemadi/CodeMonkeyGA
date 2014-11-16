package edu.ccil.ec.genotype.arraylist;
import java.util.ArrayList;

import edu.ccil.ec.genotype.ArrayListGenotype;
import edu.ccil.ec.genotype.VariationOperator;
import edu.ccil.ec.tool.Util;

/**
 * A Utility class providing different recombination & mutation methods at collection level
 * 
 * @author RezaEtemadi
 * 
 */
public class PermutationOperator {
	         
	/**
	 * performs Partially Mapped Crossover (PMX) between two given genotypes.
	 * As described in Introduction to Evolutionary Computing book (section 3.5.4, p.52)
	 * the crossover probability should be handled in the calling code
	 * @param <Integer>
	 * @param p0
	 * @param p1
	 * @param probability
	 * @return
	 */
	@VariationOperator (friendlyName="PMX Crossover", type="Permutation")
	public static void recombinePermutationListPMX(ArrayListGenotype<Integer> g0, ArrayListGenotype<Integer> g1, double probability) {
		
		if (Util.randomEngine.nextDouble() < probability ) {
			int len = g0.size();
			int[] pp = Util.randomEngine.pickDualPoint(g0.size());
			int start = pp[0];
			int end = pp[1];

			// create temporary place holders for offspring till they are shaped and then copy over input genotypes
			ArrayList<Integer> off0 = new ArrayList<Integer>(len);
			ArrayList<Integer> off1 = new ArrayList<Integer>(len);
			for (int i=0; i<len; i++){
				off0.add(null);
				off1.add(null);
			}

			//step.1 - copy the segment between two points into offsprings respectively
			for (int i=start ; i<=end; i++) {
				off0.set(i, g0.get(i));
				off1.set(i, g1.get(i));
			}
			//step.2 - lookup the segment from each parent in opposite offspring
			for (int i=start; i<=end; i++){
				if (off0.indexOf(g1.get(i)) < 0) { // not there
					int newLoc = g1.indexOf(off0.get(i));
					while (off0.get(newLoc) != null) {
						newLoc = g1.indexOf(off0.get(newLoc));
					}
					off0.set(newLoc, g1.get(i));
				}
				if (off1.indexOf(g0.get(i)) < 0) { // not there
					int newLoc = g0.indexOf(off1.get(i));
					while (off1.get(newLoc) != null) {
						newLoc = g0.indexOf(off1.get(newLoc));
					}
					off1.set(newLoc, g0.get(i));
				}
			}
			//step.3 - copy the remaining from alternate parent. 
			//In this case we overwrite the original genotypes with created parts of offsprings
			for (int i=0; i<len; i++){
				if (off0.get(i) != null) {
					g1.set(i, off0.get(i));
				}
				if (off1.get(i) != null) {
					g0.set(i, off1.get(i));
				}
			}
			
		} //else remains untouched	
		return;
	
	}
	
	
	/**
	 * performs Order crossover between two given genotypes.
	 * As described in Introduction to Evolutionary Computing book (section 3.5.4, p.54,55)
	 * the crossover probability should be handled in the calling code 
	 * @param <Integer>
	 * @param p0
	 * @param p1
	 * @param probability 
	 * @return
	 */
	@VariationOperator (friendlyName="Order Crossover", type="Permutation")
	public static void recombinePermutationListOrder(ArrayListGenotype<Integer> g0, ArrayListGenotype<Integer> g1, double probability) {
		
		if (Util.randomEngine.nextDouble() < probability ) {
			int len = g0.size();
			int[] pp = Util.randomEngine.pickDualPoint(len);
			int start = pp[0];
			int end = pp[1];

			// create temporary place holders for offspring till they are shaped and then copy over input genotypes
			ArrayList<Integer> off0 = new ArrayList<Integer>(len);
			ArrayList<Integer> off1 = new ArrayList<Integer>(len);
			for (int i=0; i<len; i++){
				off0.add(null);
				off1.add(null);
			}
			//step.1 - copy the segment between two points into offsprings respectively
			for (int i=start ; i<=end; i++) {
				off0.set(i, g0.get(i));
				off1.set(i, g1.get(i));
			}

			//step.2 - copy from other parent in order starting from second point
			
			for (int i = (end == len-1?0:end+1), j=i; i != start; j = (j==len-1?0:j+1)){
				if (off0.indexOf(g1.get(j)) < 0) { //it is not there, so copy to ith location
					off0.set(i, g1.get(j));
					i = (i==len-1?0:i+1);
				}
				
				
			}
			//do the same for other
			for (int i = (end == len-1?0:end+1), j=i; i != start; j = (j==len-1?0:j+1)){
				if (off1.indexOf(g0.get(j)) < 0) { //it is not there, so copy to ith location
					off1.set(i, g0.get(j));
					i = (i==len-1?0:i+1);
				}
			}
			// copy over original
			for (int i=0; i<len; i++){
				if (off0.get(i) == null || off1.get(i)== null) {
					System.out.println(off0 +"\n "+off1);
					throw new NullPointerException();
				}
				g0.set(i,off0.get(i));
				g1.set(i,off1.get(i));
			}
			
		} // else remains untouched
		return;
	}
		
	
	
	/**
	 * performs Cycle crossover between two given genotypes.
	 * As described in Introduction to Evolutionary Computing book (section 3.5.4, p.55,56)
	 * the crossover probability should be handled in the calling code
	 * @param <Integer>
	 * @param p0
	 * @param p1
	 * @param probability 
	 * @return
	 */
	@VariationOperator (friendlyName="Cycle Crossover", type="Permutation")
	public static void recombinePermutationListCycle(ArrayListGenotype<Integer> g0, ArrayListGenotype<Integer> g1, double probability) {
		
		if (Util.randomEngine.nextDouble() < probability ) {
			int len = g0.size();
			ArrayList<Integer> tag = new ArrayList<Integer>(len);
			for (int i=0; i<len; i++){
				tag.add(i,i);
			}
			ArrayList<ArrayList<Integer>> cycleHolder = new ArrayList<ArrayList<Integer>>(len);
			while (!tag.isEmpty()) {
				ArrayList<Integer> cycle = new ArrayList<Integer>();
				int i = tag.remove(0);
				cycle.add(i);
				int j = g0.indexOf(g1.get(i));
				while (j != i) {
					tag.remove(tag.indexOf(j));
					cycle.add(j);
					j = g0.indexOf(g1.get(j));
				}
				cycleHolder.add(cycle);
			}
			// create temporary place holders for offspring till they are shaped and then copy over input genotypes
			ArrayList<Integer> off0 = new ArrayList<Integer>(len);
			ArrayList<Integer> off1 = new ArrayList<Integer>(len);
			for (int i=0; i<len; i++){
				off0.add(null);
				off1.add(null);
			}

			// go over the cycles and copy corresponding element to offsprings
			for (int i=0; i<cycleHolder.size(); i++) {
				ArrayList<Integer> cycle = cycleHolder.get(i);
				if (i%2 == 1) {
					for (int j=0; j<cycle.size(); j++) {
						off0.set(cycle.get(j),g0.get(cycle.get(j)));
						off1.set(cycle.get(j),g1.get(cycle.get(j)));
					}
				} else {
					for (int j=0; j<cycle.size(); j++) {
						off1.set(cycle.get(j),g0.get(cycle.get(j)));
						off0.set(cycle.get(j),g1.get(cycle.get(j)));
					}
				}
			}
			// copy over original
			for (int i=0; i<len; i++){
				g0.set(i,off0.get(i));
				g1.set(i,off1.get(i));
			}
			
		} // else remains untouched
		return;
	}

	
	

	/**
	 * swaps the contents of two elements in an ArrayList
	 * it assumes both locations are valid
	 * @param <Integer>
	 * @param g
	 * @param p1
	 * @param p2
	 */
	private static void swap(ArrayListGenotype<Integer> g, int p1, int p2){
		Integer temp =g.get(p1);
		g.set(p1, g.get(p2));
		g.set(p2, temp);
		return;
	}
	
	
	
	/**
	 * Swaps the content of two elements of ArrayList Genotype on the mutation points (mutation points are randomly selected element)
	 * As described in Introduction to Evolutionary Computing book (section 3.4.4, p.45)
	 * 
	 * If random points coincides, we shift second number by one and if that exceeds the limit is set to zero (literary we end up with first and last points)
	 * @param <Integer>
	 * @param g
	 * @param probability 
	 * @return
	 */
	@VariationOperator (friendlyName="Swap Mutation", type="Permutation")
	public static void mutatePermutationListSwap(ArrayListGenotype<Integer> g, double probability){
		if (Util.randomEngine.nextDouble() < probability ) {
			int[] pp = Util.randomEngine.pickDualPoint(g.size());
			swap(g, pp[0], pp[1]);
		} // else remain unchanged
		return;
	}


	/**
	 * Performs the Insert mutation by picking two random locations in genotype and shifting them toward each other.
	 * As described in Introduction to Evolutionary Computing book (section 3.4.4, p.45,46)
	 * 
	 * If random points are next to each other nothing going to happen
	 * @param <Integer>
	 * @param g
	 * @param probability 
	 * @return
	 */
	@VariationOperator (friendlyName="Insert Mutation", type="Permutation")
	public static void mutatePermutationListInsert(ArrayListGenotype<Integer> g, double probability){
		if (Util.randomEngine.nextDouble() < probability ) {
			int[] pp = Util.randomEngine.pickDualPoint(g.size());
			int start = pp[0];
			int end = pp[1];
			Integer temp =g.get(end);
			for (int i=end; i>start+1; i--){
				g.set(i, g.get(i-1));
			}
			g.set(start+1,temp);
		}// else remains unchanged
		return;
	}


	/**
	 * Performs Inversion Mutation by randomly picking two points and reversing the order in region between two points
	 * As described in Introduction to Evolutionary Computing book (section 3.4.4, p.46)
	 * 
	 * If random points are next to each other or one apart nothing going to happen
	 * @param <Integer>
	 * @param g
	 * @param probability 
	 * @return
	 */
	@VariationOperator (friendlyName="Inversion Mutation", type="Permutation")
	public static void mutatePermutationListInversion(ArrayListGenotype<Integer> g, double probability){
		if (Util.randomEngine.nextDouble() < probability ) {
			int[] pp = Util.randomEngine.pickDualPoint(g.size());
			for (int start=pp[0],end=pp[1]; start<end ; start++, end--) {
				swap(g,start,end);
			}
		} //else remains unchanged
		return;
	}
	

	
}
