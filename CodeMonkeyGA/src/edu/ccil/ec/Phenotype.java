package edu.ccil.ec;

import java.io.Serializable;

/**
 * The super class representing Phenotype. It is an abstract class since 
 * the implementation of some methods is left to the concrete sub classes.
 *  
 * @author Reza Etemadi
 *
 * @param <G>
 * @param <F>
 */
public abstract class Phenotype	<G extends Genotype<?>,F extends Number & Comparable<F>> 
						implements Fitness<F>, Cloneable , Serializable {

	
	private static final long serialVersionUID = 1L;

	/**
	 *  the genotype representation of this phenotype
	 */
	protected G genome = null;
	
	/**
	 *  the Object value associated with this phenotype (optional)
	 */
	protected Object data = null;
	
	/**
	 *  the fitness value of this phenotype (based on its data and genome)
	 */
	protected F fitnessValue = null;
	
	/**
	 * To control re-insertion option.
	 * Default is -1 (unlimited re-insertion), meaning there is no limit on how many clones of Phenotype appear in a Population
	 * Any > 0 number will be considered as limit and will be decreased after each copy.
	 * once the number reaches 0, no more copy (re-insertion) of the Phenotype is allowed. 
	 */
	protected int copyCounter = -1;
	
	/**
	 * An optional variable to track the age of the Phenotype as it moves from one generation to another.
	 * In certain strategies the older individuals are gradually eliminated from Population.
	 * default is zero & remain zero unless some process (e.g. in survival selection increase it)
	 */
	protected int age=0;
	 	
	/**
	 * gets the genome object of this Phenotype
	 * @return
	 */
	public G getGenome(){
		return genome;
	}
	
	/**
	 * sets the input parameter into the genome of this Phenotype and calls update() to update the value this Phenotype
	 * the refresh method should be implemented in subclasses to reflect changes in the genome.
	 * @param gen
	 */
	public void setGenome(G gen){
		genome = gen;
	}
	
	/**
	 * returns the epi-genetic portion of this phenotype
	 * @return
	 */
	public Object getData(){
		return data;
	}

	/**
	 * sets the epi-genetic portion of this phenotype
	 * @param value
	 */
	public void setData(Object value){
		data = value;
	}

	
	/**
	 * expresses the phenotype in the solution space
	 * it is the result of decoding genotype and the epi-genetic data
	 * @return
	 */
	public abstract Object express();
	
	
	/**
	 * returns the fitness of this phenotype
	 */
	@Override
	public F getFitness() {
		if (fitnessValue == null) {
			throw new NullPointerException("Fitness value is not calculated");
		}
		return fitnessValue;
	}

	

	/**
	 * provides compareTo implementation based on "F fitness" compareTo method
	 */
	@Override
	public int compareTo(Fitness<F> o) {
		if (fitnessValue == null) {
			throw new ESException(ESException.FitnessError,
					"Fitness value is not calculated. " +
					"\nMake sure 'evaluate' method in your Phenotype implementation is not empty." +
					"\nIf you generated the code using Code Monkey App, verify 'evaluate' method inside Phenome class");
		}
		return fitnessValue.compareTo(o.getFitness());
	}

	
	/**
	 * sets the fitness value from a string 
	 * it is used in population class when an external program is called for fitness evaluation.
	 * it requires that the <F> class to have a string based constructor.
	 * @param value
	 * @throws ESException
	 */
	@SuppressWarnings("unchecked")
	public final void setFitnessFromStr(String value) {
		try {
			fitnessValue = (F) fitnessValue.getClass().
							getConstructor(String.class).
							newInstance(value);  //all of the Number implementors have string based constructor except for AtomicInt and AtomicLong
		}catch (Exception e) {
			e.printStackTrace();
			throw new ESException(ESException.FitnessError,"Failed to construct fitness value from input string. Make sure fitness class has a string based constructor");
		}
	}

	
	
	@SuppressWarnings("unchecked")
	public Object clone() {
		try{
			Phenotype<G,F> clone = (Phenotype<G,F>) super.clone();
			clone.genome = (G)this.genome.clone();
			clone.fitnessValue = this.fitnessValue;
			clone.data = this.data; //how to clone the data
			return clone;
		} catch (CloneNotSupportedException e) {
		    // this shouldn't happen, since we are Clone-able
		    throw new InternalError();
		}
	}


	/**
	 * returns a string representation of the phenotype
	 * used for log purposes.
	 */
	public String toString(){
		return "P:{Fitness="+(fitnessValue!=null?fitnessValue.toString():"null")
				+",Genotype="+genome.toString() 
				+",ExternalData="+(data!=null?data.toString():"null")
				+",Represented Value="+express()+"}";

	}
	
	
	/**
	 * returns a XML based string representation of phenome.
	 * @return
	 */
	public String toXML() {
		return "<Phenome>" + genome.toXML() 
				+"<Fitness>"+(fitnessValue!=null?"<![CDATA["+fitnessValue+"]]>":"")+"</Fitness>"
				+"<Data>"+(data!=null?"<![CDATA["+ data.toString()+"]]>":"")+"</Data>"
				+"</Phenome>";
	}
	
	
	
	/**
	 * returns a JSON based string representaion of the phenotype
	 */
	public String toJSON(){
		return "{\"Phenome\":{\t"+genome.toJSON()+"," 
				+"\t\"Fitness\":"+(fitnessValue!=null?fitnessValue.toString()+",":"null,") 
				+"\t\"Data\":"+(data!=null?"\""+data.toString()+"\"":"null") 
				+"\t}}";
	}


}
