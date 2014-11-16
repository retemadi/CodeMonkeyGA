package edu.ccil.ec.genotype.gep.math;

import edu.ccil.ec.genotype.gep.INodeFactory;
import edu.ccil.ec.genotype.gep.Node;
import edu.ccil.ec.tool.Util;

public class Constant<T> extends Node<T>{

	public final T value;
	
	/** 
	 * for a Constant, the value is mandatory
	 * @param value
	 */
	public Constant(T value) {
		super(0);
		this.value = value;
	}

	/**
	 * Just returns its value, no input args is used
	 */
	@Override
	public T evaluate(Object[] args) {
		return value;
	}
	
	
	public String toString(){
		return value.toString();
	}
	
	
	/**
	 * This is the double factory class for Class Constant since it can not be its own factory.
	 * @author RezaEtemadi
	 *
	 */
	public static class DoubleFactory implements INodeFactory<Double> {
		private double min;
		private double max;
		
		
		public DoubleFactory(double min, double max) {
			this.min = min;
			this.max = max;
		}
		
		public Constant<Double> create(){
			return new Constant<Double>(Util.randomEngine.nextDouble(min, max));
		}

		@Override
		public int getArity() {
			return 0;
		}
	}


	/**
	 * This is the boolean factory class for Class Constant since it can not be its own factory.
	 * @author RezaEtemadi
	 *
	 */
	public static class BooleanFactory implements INodeFactory<Boolean> {
		
		
		public Constant<Boolean> create(){
			return new Constant<Boolean>(Util.randomEngine.nextBoolean());
		}

		@Override
		public int getArity() {
			return 0;
		}
	}

	
	
	
	/**
	 * This is the integer factory class for Class Constant since it can not be its own factory.
	 * @author RezaEtemadi
	 *
	 */
	public static class IntegerFactory implements INodeFactory<Integer> {
		private int min;
		private int max;
		
		
		public IntegerFactory(int min, int max) {
			this.min = min;
			this.max = max;
		}
		
		public Constant<Integer> create(){
			return new Constant<Integer>(Util.randomEngine.nextInt(min, max));
		}

		@Override
		public int getArity() {
			return 0;
		}

	}

	
	
}