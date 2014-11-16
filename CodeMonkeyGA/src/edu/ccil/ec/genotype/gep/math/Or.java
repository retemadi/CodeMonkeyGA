package edu.ccil.ec.genotype.gep.math;

import edu.ccil.ec.genotype.gep.INodeFactory;
import edu.ccil.ec.genotype.gep.Node;

public class Or extends Node<Boolean> implements INodeFactory<Boolean> {

	public Or(int arity) {
		super(arity);
	}

	/**
	 * default constuctor
	 */
	public Or(){
		this(2);
	}
	
	@Override
	public Boolean evaluate(Object[] args) {
		try {
			Boolean result = (Boolean)args[0];
			for (int i=1; i<args.length; i++){
				result |= (Boolean)args[i];
			}
			return result;
		} catch (Exception e){
			logException(e);
			return null;
		}
	}

	/* its factory method. same object can be used everywhere */
	@Override
	public Or create() {
		return this;
	}

}
