package edu.ccil.ec.genotype.gep.math;

import edu.ccil.ec.genotype.gep.INodeFactory;
import edu.ccil.ec.genotype.gep.Node;

/**
 * Operator for Trigonometric CoSine operation. Arity of this operation is 1 always 
 * @author Reza Etemadi
 *
 */
public class Cosine extends Node<Double> implements INodeFactory<Double> {

	/**
	 * default & only constuctor
	 */
	public Cosine(){
		super(1);
	}
	
	@Override
	public Double evaluate(Object[] args) {
		try {
			return Math.cos(((Number)args[0]).doubleValue());
		} catch (Exception e){
			logException(e);
			return null;
		}
	}

	/* its factory method. same object can be used everywhere */
	@Override
	public Cosine create() {
		return this;
	}

}
