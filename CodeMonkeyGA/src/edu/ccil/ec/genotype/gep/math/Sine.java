package edu.ccil.ec.genotype.gep.math;

import edu.ccil.ec.genotype.gep.INodeFactory;
import edu.ccil.ec.genotype.gep.Node;

/**
 * Operator for Trigonometric Sine operation. Arity of this operation is 1 always 
 * @author Reza Etemadi
 *
 */
public class Sine extends Node<Double> implements INodeFactory<Double> {

	/**
	 * default & only constuctor
	 */
	public Sine(){
		super(1);
	}
	
	@Override
	public Double evaluate(Object[] args) {
		try {
			return Math.sin(((Number)args[0]).doubleValue()); //casting to (Double) doesn't work for integers hence ...
		} catch (Exception e){
			logException(e);
			return null;
		}
	}

	/* its factory method. same object can be used everywhere */
	@Override
	public Sine create() {
		return this;
	}

}
