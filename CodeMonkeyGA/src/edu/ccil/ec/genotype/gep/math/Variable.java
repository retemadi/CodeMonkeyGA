package edu.ccil.ec.genotype.gep.math;

import edu.ccil.ec.genotype.gep.INodeFactory;
import edu.ccil.ec.genotype.gep.Node;


public class Variable<T> extends Node<T> implements INodeFactory<T> {

	public final String name;
	
	/**
	 * value is only used to store data in Terminals 
	 */
	public T value = null;
	
	/**
	 * For variable a name is mandatory
	 * @param name
	 */
	public Variable(String name) {
		super(0);
		this.name = name;
	}

	public void setValue(T value){
		this.value = value;
	}

	/**
	 * Just returns its value, no input args is used
	 */
	@Override
	public T evaluate(Object[] args) {
		return value;
	}

	/** its factory method */
	@Override
	public Variable<T> create() {
		return this;
	}

	
	public String toString(){
		return name+"="+value;
	}
}
