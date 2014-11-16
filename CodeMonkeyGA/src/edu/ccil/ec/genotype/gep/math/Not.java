package edu.ccil.ec.genotype.gep.math;

import edu.ccil.ec.genotype.gep.INodeFactory;
import edu.ccil.ec.genotype.gep.Node;

/**
 * Operator for boolean NOT operation. Arity of this operation is 1 always 
 * @author Reza Etemadi
 *
 */
public class Not extends Node<Boolean> implements INodeFactory<Boolean> {

	/**
	 * default & only constuctor
	 */
	public Not(){
		super(1);
	}
	
	@Override
	public Boolean evaluate(Object[] args) {
		try {
			return !(Boolean)args[0];
		} catch (Exception e){
			logException(e);
			return null;
		}
	}

	/* its factory method. same object can be used everywhere */
	@Override
	public Not create() {
		return this;
	}

}
