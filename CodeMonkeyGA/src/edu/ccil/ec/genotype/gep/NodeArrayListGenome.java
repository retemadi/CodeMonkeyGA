package edu.ccil.ec.genotype.gep;

import edu.ccil.ec.genotype.ArrayListGenotype;
import edu.ccil.ec.genotype.gep.math.*;
import edu.ccil.ec.tool.Util;

public abstract class NodeArrayListGenome extends ArrayListGenotype<Node<?>> {

	private static final long serialVersionUID = 1L;

	// for head elements from both array can be used. For tail only terminal array
	protected static INodeFactory<?>[] function = {new Add(), new Subtract()};
	protected static INodeFactory<?>[] terminal = {};  //e.g. { new Variable<Double>("a"), new Constant.DoubleFactory(1,100)}

	/**
	 * the size of the head portion of gene in GEP representaion 
	 */
	protected int headSize;
	/**
	 * the size of tail portion, it is calcualted by ((max_arity - 1)* headsize) + 1
	 */
	protected int tailSize;
	
	public NodeArrayListGenome(){
		super();
	}
	
	
	public NodeArrayListGenome(int headSize){
		super();		
		this.headSize = headSize;
		//get the max arity amoung functions
		int maxArity = function[0].getArity();
		for (int i=1 ; i<function.length; i++){
			if (function[i].getArity() > maxArity){
				maxArity = function[i].getArity();
			}
		}
		//calculate tail size
		tailSize = ((maxArity -1)* headSize)+1;
	}


	public NodeArrayListGenome clone(){
		return (NodeArrayListGenome)super.clone();
	}
	

	/**
	 * returns the head portion of this genome
	 * although it is returned as NodeArrayListGenome but is not complete genome
	 * @return
	 */
	protected NodeArrayListGenome headClone(){
		NodeArrayListGenome head = this.clone();
		for (int i=size()-1 ; i>=headSize; i--){
			head.remove(i);
		}
		return head;
	}

	
	/**
	 * returns the tail portion of this genome
	 * although it is returned as NodeArrayListGenome but is not complete genome
	 * @return
	 */
	protected NodeArrayListGenome tailClone(){
		NodeArrayListGenome tail = this.clone();
		for (int i=headSize-1 ; i>=0; i--){
			tail.remove(i);
		}
		return tail;
	}

	
	@Override
	public void fillRandom() {
		// fill the head of functions & terminals
		for (int i =0 ; i<headSize; i++){
			int rand = Util.randomEngine.nextInt(0, function.length+terminal.length);
			if (rand < function.length) {// pick from function
				this.add(function[rand].create());
			}else {  //pick from terminal
				this.add(terminal[rand - function.length].create());
			}
		}
		// fill the tail just with terminals
		for (int i=0; i<tailSize; i++){
			this.add(terminal[Util.randomEngine.nextInt(0, terminal.length)].create());
		}
	}



}
