package edu.ccil.ec.genotype;

import java.util.LinkedList;

import edu.ccil.ec.Genotype;

/**
 * ideal for Genetic Programming representation
 * @author R.E.
 *
 * @param <G>
 */
public abstract class LinkedListGenotype<G> extends LinkedList<G> implements Genotype<G> {

	private static final long serialVersionUID = 1L;

	public double mutationRate;
	
	public double recombinationRate;

	@SuppressWarnings("unchecked")
	public LinkedListGenotype<G> clone() {
		return (LinkedListGenotype<G>)super.clone();
	}	
	
	/**
	 * to relay the super class's constructor to the concrete subclasses.
	 */
	public LinkedListGenotype(){
		super();
	}

	/**
	 * to relay the super class's constructor to the concrete subclasses.
	 */
	public LinkedListGenotype(LinkedListGenotype<G> g){
		super(g);
	}
}
