package edu.ccil.ec.genotype;

import java.util.Vector;
import edu.ccil.ec.Genotype;

/**
 * ideal for Evolutionary Strategies and Evolutionary Programming representation
 * @author R.E.
 *
 * @param <G>
 */
public abstract class VectorGenotype<G> extends Vector<G> implements Genotype<G> {


	private static final long serialVersionUID = 1L;

	public double mutationRate;
	
	public double recombinationRate;


	@SuppressWarnings("unchecked")
	public VectorGenotype<G> clone() {
		return (VectorGenotype<G>) super.clone();
	}	
	
	/**
	 * to relay the super class's constructor to the concrete subclasses.
	 */
	public VectorGenotype(){
		super();
	}

	/**
	 * to relay the super class's constructor to the concrete subclasses.
	 */
	public VectorGenotype(int i){
		super(i);
	}
	

}
