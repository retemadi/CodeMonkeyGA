package edu.ccil.ec.genotype.gep;
/**
 * A NodeTree is a unit for tree representation (or expression) of a Node ArrayList.
 * Each NodeTree can contain an array of other NodeTrees that are not nececcarily same type
 * but the result of calculation should match the type T of the NodeTree
 * Most often the input types match the output (e.g. add, etc.) but in other cases no
 * (e.g. the Equal function can accept any input types but always returns boolean)
 * 
 * @author Reza Etemadi
 *
 * @param <T>
 */
public class NodeTree<T> {
	
	protected Node<T> node;
	
	protected NodeTree<?>[] children;
	
	
	public NodeTree(Node<T> node) {
		this.node = node;
		children = new NodeTree[node.arity];
	}


	public T calculate(){
		if (children == null){
			return node.evaluate(null);
		}else{
			Object[] param = new Object[children.length];
			for (int i=0; i<param.length; i++){
				param[i]= children[i].calculate();
			}
			return node.evaluate(param);
		}
	}

	
	@Override
	public String toString(){
		StringBuffer output = new StringBuffer(node.toString());
		for(NodeTree<?> child: children){
			output.append("("+child.toString()+")");
		}
		return output.toString();
	}

}
