package edu.ccil.ec.genotype.gep;

import edu.ccil.ec.genotype.ArrayListGenotype;
import edu.ccil.ec.genotype.arraylist.UtilGenome;
import edu.ccil.ec.tool.Util;

/**
 * Subclass of UtilGenome to add mutation methods for GEP genomes
 * 
 * @author RezaEtemadi
 * 
 */
public class UtilGEP extends UtilGenome{

	
	
	public static void mutateArrayListSwitch(NodeArrayListGenome g, double probability){
		if (Util.randomEngine.nextDouble() < probability ) {
			SwitchMutation(g);
		} //else remains unchanged
		return;
	}

	/**
	 * Switches the content of two elements of ArrayList Genotype on the mutation point (mutation point is a randomly selected element)
	 * if mutation point is the last element in the arraylist then it will be switched with the elment[0] (considers end is connected to start)
	 * @param <G>
	 * @param g
	 * @return
	 */
	protected static void SwitchMutation(NodeArrayListGenome g) {
		NodeArrayListGenome hClone = g.headClone();
		int mutationPoint = Util.randomEngine.pick(hClone.size()-1);
		int nextPoint = (mutationPoint == hClone.size()-1?0:mutationPoint+1);
		swap(hClone, mutationPoint, nextPoint);

		NodeArrayListGenome tClone = g.tailClone();
		mutationPoint = Util.randomEngine.pick(tClone.size()-1);
		nextPoint = (mutationPoint == tClone.size()-1?0:mutationPoint+1);
		swap(tClone, mutationPoint, nextPoint);

		hClone.addAll(tClone);
		g = hClone;
		return;
 	}
	

		
	public static void mutateArrayListShift(NodeArrayListGenome g, double probability){
		if (Util.randomEngine.nextDouble() < probability ) {
			ShiftMutation(g);
		} //else remains unchanged
		return;
	
	}

	
	/**
	 * Shifts the content of an ArrayList Genotype forward (circular) 
	 * @param <G>
	 * @param g
	 * @return
	 */
	protected static void ShiftMutation(NodeArrayListGenome g) {
		NodeArrayListGenome hClone = g.headClone();
		Node<?> temp = hClone.get(0);
		for (int i=0; i< hClone.size()-1 ; i++) {
			hClone.set(i, hClone.get(i+1));
		}
		hClone.set(hClone.size()-1, temp);
		
		NodeArrayListGenome tClone = g.tailClone();
		temp = tClone.get(0);
		for (int i=0; i< tClone.size()-1 ; i++) {
			tClone.set(i, tClone.get(i+1));
		}
		tClone.set(tClone.size()-1, temp);

		hClone.addAll(tClone);
		g = hClone;
		return;
 	}
	
	
	
	
	
	public static void mutateArrayListReverse(NodeArrayListGenome g, double probability){
		if (Util.randomEngine.nextDouble() < probability ) {
			ReverseMutation(g);
		} //else remains unchanged
		return;
	}

	
	
	/**
	 * Clones and Reverses the content of an ArrayList Genotype 
	 * @param <G>
	 * @param g
	 * @return
	 */
	protected static void ReverseMutation(NodeArrayListGenome g) {
		NodeArrayListGenome hClone = g.headClone();
		for (int i=0; i<= hClone.size()/2 ; i++) {
			swap(hClone,i,(hClone.size()-1)-i);
		}

		NodeArrayListGenome tClone = g.tailClone();
		for (int i=0; i<= tClone.size()/2 ; i++) {
			swap(tClone,i,(tClone.size()-1)-i);
		}

		hClone.addAll(tClone);
		g = hClone;
		return;
 	}
	
}
