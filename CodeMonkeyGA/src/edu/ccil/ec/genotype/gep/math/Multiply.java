package edu.ccil.ec.genotype.gep.math;

import edu.ccil.ec.genotype.gep.INodeFactory;
import edu.ccil.ec.genotype.gep.Node;


public class Multiply extends Node<Double> implements INodeFactory<Double> {


	public Multiply(int arity) {
		super(arity);
	}

	
	/**
	 * default constructor
	 */
	public Multiply() {
		this(2);
	}

	
	@Override
	public Double evaluate(Object[] args) {
		try{
			Double result= ((Number)args[0]).doubleValue();
			for(int i=1; i<args.length; i++){
				result *= ((Number)args[i]).doubleValue();
			}
			return result;
		} catch (ClassCastException e){
			logException(e);
			return null;
		}
	}

	/* its factory method. same object can be used everywhere */
	@Override
	public Multiply create() {
		return this;
	}


}
