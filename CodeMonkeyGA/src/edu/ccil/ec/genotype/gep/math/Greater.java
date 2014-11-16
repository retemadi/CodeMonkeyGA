package edu.ccil.ec.genotype.gep.math;

import edu.ccil.ec.genotype.gep.INodeFactory;
import edu.ccil.ec.genotype.gep.Node;


public class Greater extends Node<Boolean> implements INodeFactory<Boolean> {


	public Greater(int arity) {
		super(arity);
	}

	
	/**
	 * default constructor
	 */
	public Greater() {
		this(2);
	}

	/**
	 * returns true if the double value of first element is greater than the rest 
	 */
	@Override
	public Boolean evaluate(Object[] args) {
		try{
			Double first = ((Number)args[0]).doubleValue(); //casting to (Double) doesn't work for integers hence ...
			for(int i=1; i<args.length; i++){
				if (first <= ((Number)args[i]).doubleValue()) return false;
			}
			return true;
		} catch (ClassCastException e){
			logException(e);
			return null;
		}
	}

	/* its factory method. same object can be used everywhere */
	@Override
	public Greater create() {
		return this;
	}

}
