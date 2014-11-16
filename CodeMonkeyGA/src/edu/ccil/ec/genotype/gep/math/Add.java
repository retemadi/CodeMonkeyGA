package edu.ccil.ec.genotype.gep.math;

import edu.ccil.ec.genotype.gep.INodeFactory;
import edu.ccil.ec.genotype.gep.Node;

/**
 * The Add operation among two or more double values
 * @author Reza Etemadi
 *
 */
public class Add extends Node<Double> implements INodeFactory<Double> {


	public Add(int arity) {
		super(arity);
	}

	
	/**
	 * default constructor
	 */
	public Add() {
		this(2);
	}

	/**
	 * adds the double value of all elements and return the result.
	 */
	@Override
	public Double evaluate(Object[] args) {
		try{
			Double result= ((Number)args[0]).doubleValue(); //casting to (Double) doesn't work for integers hence ...
			for(int i=1; i<args.length; i++){
				result += ((Number)args[i]).doubleValue();
			}
			return result;
		} catch (ClassCastException e){
			logException(e);
			return null;
		}
	}

	/* its factory method. same object can be used everywhere */
	@Override
	public Add create() {
		return this;
	}

}
