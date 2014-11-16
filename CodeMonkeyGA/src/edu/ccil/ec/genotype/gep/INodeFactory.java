package edu.ccil.ec.genotype.gep;


/**
 * the interface for creating instances of Nodes
 * @author RezaEtemadi
 *
 */
public interface INodeFactory<T> {
	
	/**
	 * The factory method for creating a Node.
	 * For function Nodes, one instance can be used everywhere
	 * For each variable also one instance suffice
	 * For Constants that are mutable , eachone should be unique
	 * @return
	 */
	public Node<T> create();
	
	/**
	 * retuns the arity of the Node that it will generate.
	 * @return arity, a value >= 0 
	 */
	public int getArity();
	
	

}
