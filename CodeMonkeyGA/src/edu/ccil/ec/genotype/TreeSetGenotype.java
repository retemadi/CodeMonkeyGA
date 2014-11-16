package edu.ccil.ec.genotype;

import java.util.TreeSet;

import edu.ccil.ec.Genotype;

/**
 * ideal for non-duplicated Tree representation
 * @author R.E.
 *
 * @param <G>
 */
public abstract class TreeSetGenotype<G> extends TreeSet<G> implements Genotype<G> {

	private static final long serialVersionUID = 1L;

	public double mutationRate;
	public double recombinationRate;

	
	@SuppressWarnings("unchecked")
	public TreeSetGenotype<G> clone() {
		return (TreeSetGenotype<G>) super.clone();
	}	
	
	/**
	 * to relay the super class's constructor to the concrete subclasses.
	 */
	public TreeSetGenotype() {
		super();
	}

	/**
	 * to relay the super class's constructor to the concrete subclasses.
	 */
	public TreeSetGenotype(TreeSetGenotype<G> s) {
		super(s);
	}
}
