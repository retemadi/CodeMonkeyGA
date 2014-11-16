package edu.ccil.ec.genotype.arraylist;


import edu.ccil.ec.Genotype;
import edu.ccil.ec.genotype.ArrayListGenotype;

/**
 * ideal for Genetic Algorithm representation
 * @author R.E.
 *
 * @param <G>
 */
public abstract class DynamicListGenotype<G> extends ArrayListGenotype<G> {

	//This is the Genotype size used for fixed length Genotypes
	protected int initialSize;
	
	//These are Genotype size limits used for variable length Genotypes
	protected int initSizeLow;
	protected int initSizeHigh;
	
	public DynamicListGenotype<G> clone() {
		return (DynamicListGenotype<G>) super.clone();
	}
	
	/**
	 * to relay the super class's constructor to the concrete subclasses.
	 */
	public DynamicListGenotype(){
		super();
	}

	/**
	 * to relay the super class's constructor to the concrete subclasses.
	 */
	public DynamicListGenotype(int n){
		super(n);
	}
	
	private static final long serialVersionUID = 1L;

	

	public int getSizeHigh() {
		return initSizeHigh;
	}
	
	public int getSizeLow() {
		return initSizeLow;
	}
	
	/**
	 * abstract method . its implementation is used for insert mutation
	 * @return
	 */
	public abstract G newUnit();

	
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
	
	//in case of dynamic genotype the length varies and it is note worthy for toString()
	@Override 
	public String toString() {
		return "(size:"+this.size()+")"+super.toString();
	}
}
