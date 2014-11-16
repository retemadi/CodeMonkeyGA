package edu.ccil.ec.genotype.gep.math;

import edu.ccil.ec.genotype.gep.INodeFactory;
import edu.ccil.ec.genotype.gep.Node;


public class Subtract extends Node<Double>  implements INodeFactory<Double> {


	public Subtract(int arity) {
		super(arity);
	}

	
	/**
	 * default constructor
	 */
	public Subtract() {
		this(2);
	}

	
	@Override
	public Double evaluate(Object[] args) {
		try{
			Double result= ((Number)args[0]).doubleValue();
			for(int i=1; i<args.length; i++){
				result -= ((Number)args[i]).doubleValue();
			}
			return result;
		} catch (ClassCastException e){
			logException(e);
			return null;
		}
	}

	/** its factory method */
	@Override
	public Subtract create() {
		return this;
	}
	

}
