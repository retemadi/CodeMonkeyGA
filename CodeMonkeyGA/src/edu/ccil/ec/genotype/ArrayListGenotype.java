package edu.ccil.ec.genotype;

import java.util.ArrayList;

import edu.ccil.ec.Genotype;

/**
 * ideal for Genetic Algorithm representation
 * @author R.E.
 *
 * @param <G>
 */
public abstract class ArrayListGenotype<G> extends ArrayList<G> implements Genotype<G> {

	//This is the Genotype size used for fixed length Genotypes
	protected int initialSize;
	
	
	public int getInitialSize() {
		return initialSize;
	}

	@SuppressWarnings("unchecked")
	public ArrayListGenotype<G> clone() {
		return (ArrayListGenotype<G>) super.clone();
	}
	
	/**
	 * to relay the super class's constructor to the concrete subclasses.
	 */
	public ArrayListGenotype(){
		super();
	}

	/**
	 * to relay the super class's constructor to the concrete subclasses.
	 */
	public ArrayListGenotype(int n){
		super(n);
	}
	
	private static final long serialVersionUID = 1L;

	
	/**
	 * @see edu.ccil.ec.Genotype.toXML()
	 */
	@Override
	public String toXML() {
		StringBuffer out= new StringBuffer(startTag);
		for (G g : this){
			if (g instanceof Genotype) {
				out.append(alleleTagStart +((Genotype<?>)g).toXML()+ alleleTagEnd) ;
			} else {
				out.append(alleleTagStart + g.toString() + alleleTagEnd) ;
			}
		}
		return out.append(endTag).toString();
	}


	/**
	 * @see edu.ccil.ec.Genotype.toJSON()
	 */
	@Override
	public String toJSON() {
		String out= "{\"Genome\":[";
		for (G g : this){
			if (g instanceof Genotype) {
				out += ((Genotype<?>)g).toJSON()+ ",";
			} else {
				out += g.toString() + ",";
			}
		}
		if (out.endsWith(",")){
			out = out.substring(0,out.length()-1);
		}
		return out +"]}";
	}
	
}
