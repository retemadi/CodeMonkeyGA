package edu.ccil.ec.genotype.gep;

import edu.ccil.ec.genotype.gep.math.*;


/**
 *  A utility class that construct a tree representaion of NodeArrayListGenome
 * by creating and interconnecting NodeTree elements.
 * It allows the traverasal of the tree in order to evaluate what the genome
 * express. It can be used as a mean for fitness calcualtion of GEP genomes.
 * 
 * @author Reza Etemadi
 * 
 * @param <T> The expected return type of the evaluation
 */
public class TreeEvaluator<T> {

	public T evaluate(NodeArrayListGenome genome){
		try {
			NodeTree<T> treeRoot = buildTree(genome);
			//Util.log(treeRoot);
			return treeRoot.calculate();
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public NodeTree<T> buildTree(NodeArrayListGenome genome){
		NodeArrayListGenome temp = genome.clone();  //since we are disecting the genome lets use its clone
		NodeTree<T> treeRoot = new NodeTree<T>((Node<T>)temp.remove(0));
		create(treeRoot, temp);
		return treeRoot;
	}
	
	/**
	 * used internaly to create the node Tree from node Arraylist
	 * @param nodeTree
	 * @param genome
	 */
	@SuppressWarnings("unchecked")
	private void create (NodeTree<?> nodeTree, NodeArrayListGenome genome) {

		for (int i=0; i< nodeTree.node.arity ; i++) {
			nodeTree.children[i] = new NodeTree(genome.remove(0));
		}
		for (int i=0; i< nodeTree.children.length; i++) {
			create(nodeTree.children[i],genome);
		}
		return;
	}

/*
	public static void main(String args[]) {
		NodeArrayListGenome.function = new INodeFactory<?>[] {new Add(), new Subtract(), new Multiply()};  //, new And(), new Or()};
		Variable<Double> a = new Variable<Double>("a");
		Variable<Integer> b = new Variable<Integer>("b");
		Variable<Boolean> A = new Variable<Boolean>("A");
		NodeArrayListGenome.terminal = new INodeFactory<?>[] {a};//, b, A, new Constant.BooleanFactory(),new Constant.DoubleFactory(1, 10)};
		
		NodeArrayListGenome test = new NodeArrayListGenome(8);
		test.fillRandom();
		a.setValue(3.0);
		b.setValue(4);
		A.setValue(true);
		TreeEvaluator<Double> eval = new TreeEvaluator<Double>();
		System.out.println(eval.evaluate(test));
		return;
	}
*/	
}
