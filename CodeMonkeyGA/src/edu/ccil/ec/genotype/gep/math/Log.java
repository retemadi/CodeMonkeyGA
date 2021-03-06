package edu.ccil.ec.genotype.gep.math;

import edu.ccil.ec.genotype.gep.INodeFactory;
import edu.ccil.ec.genotype.gep.Node;

/**
 * Logarithmic operator. Arity of this operation is 1 always 
 * @author Reza Etemadi
 *
 */
public class Log extends Node<Double> implements INodeFactory<Double> {

	/**
	 * default & only constuctor
	 */
	public Log(){
		super(1);
	}
	
	@Override
	public Double evaluate(Object[] args) {
		try {
			return Math.log(((Number)args[0]).doubleValue());
		} catch (Exception e){
			logException(e);
			return null;
		}
	}

	/* its factory method. same object can be used everywhere */
	@Override
	public Log create() {
		return this;
	}

}
