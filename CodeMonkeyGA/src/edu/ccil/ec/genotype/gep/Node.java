package edu.ccil.ec.genotype.gep;

/** an abstract class representing the node in a GEP Gene
 * A node can be a Function (arity >0) or a Terminal (arity=0)
 * Arity of a Node never changes.
 * 
 * @author Reza Etemadi
 *
 */
public abstract class Node<T> {
	
	protected final int arity;  //is set in the constructor and remain unchanged

	
	public Node(int arity){
		this.arity = arity;
	}

	/** 
	 * evaluates the input parameters.
	 * The method accepts any type of Node but only returns a value of type T
	 * for all incorrect inputs the evaluration returns Null 
	 * @param args (array of Nodes)
	 * @return T the result of evaluation
	 */
	public abstract T evaluate(Object[] args);
	

	public int getArity() {
		return arity;
	}
	
	public String toString(){
		return this.getClass().getSimpleName();
	}
	
	/**
	 * a logging method to be used for debuging only 
	 * @param e
	 */
	protected void logException(Exception e){
		e.printStackTrace(System.err);
	}
}
