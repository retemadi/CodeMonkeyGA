package example.ImageProcessingRMS;

import edu.ccil.ec.genotype.arraylist.ArrayListOperator;
import edu.ccil.ec.genotype.arraylist.DynamicListGenotype;
import edu.ccil.ec.genotype.arraylist.DynamicListOperator;
import edu.ccil.ec.tool.Util;
import edu.ccil.ec.genotype.ArrayListGenotype;
import edu.ccil.ec.genotype.VariationOperator;
import external.RuleSetAnalyse;

public class myGenome extends DynamicListGenotype<myGenome.myGenomeUnit> {

	private static final long serialVersionUID = 1L;

	public myGenome() {
		super();
		initialSize=5;
		initSizeLow=2;
		initSizeHigh=20;
	}

	@Override
	public void fillRandom() {
		initialSize = Util.randomEngine.nextInt(initSizeLow, initSizeHigh);
		for (int i=0 ; i<this.initialSize; i++){
			myGenomeUnit unit= new myGenomeUnit();
			unit.fillRandom();
			if (size() <= i) {
				this.add(unit);
			} else {
				this.set(i, unit);
			}
		}
	}



	@VariationOperator (friendlyName="One Point Crossover" )
	public void recombineListOnePoint(myGenome genome, double recombinationRate) {
		DynamicListOperator.recombineListOnePoint(this, genome, recombinationRate);
	}

	@VariationOperator (friendlyName="Two Point Crossover" )
	public void recombineListTwoPoint(myGenome genome, double recombinationRate) {
		DynamicListOperator.recombineListTwoPoint(this, genome, recombinationRate);
	}

	@VariationOperator (friendlyName="Uniform Crossover" )
	public void recombineListUniform(myGenome genome, double recombinationRate) {
		DynamicListOperator.recombineListUniform(this, genome, recombinationRate);
	}
	
	//Experimental
	@VariationOperator (friendlyName="Merge Mutation" )
	public void mutateMerge(myGenome genome, double recombinationRate) {
		DynamicListOperator.mutateMerge(this, genome, recombinationRate);
	}

	
	@VariationOperator (friendlyName="Insert Mutation" )
	public void mutateInsert(double mutationRate) {
		DynamicListOperator.mutateInsert(this, mutationRate);
	}

	@VariationOperator (friendlyName="Delete Mutation" )
	public void mutateDelete(double mutationRate) {
		DynamicListOperator.mutateDelete(this, mutationRate);
	}

	@Override
	public myGenomeUnit newUnit(){
	myGenomeUnit unit= new myGenomeUnit();
	unit.fillRandom();
	return unit;
}


	@Override
	public myGenome create() { 
		return new myGenome();
	}

	@Override
	public myGenome clone() {
		myGenome clone = (myGenome)super.clone();
		for(int i=0 ; i<size(); i++) {
			clone.set(i, get(i).clone());
		}
		return clone;
	}


/* The Heterogeneous Unit of the Homogeneous Genotype */
	class myGenomeUnit extends ArrayListGenotype<ArrayListGenotype> {

		private static final long serialVersionUID = 1L;

		public myGenomeUnit() {
			super();
			this.add(0,new myGenomePart0());
		}

		@Override
		public void fillRandom() {
			for (ArrayListGenotype part : this){
				
//				part.fillRandom();
				
				//As an alternative we can fill the initial population with region of image itself
			
				int[] values = external.RuleSetImageAnalyser.getRandomArea(part.getInitialSize()); 

				for (int j=0 ; j <values.length; j++) {
					if (part.size() <= j) {
						part.add(values[j]);
					} else {			
						part.set(j, values[j]);
					}
				}
			}
		}


		@Override
		public myGenomeUnit create() { 
			return new myGenomeUnit();
		}

		@Override
		public myGenomeUnit clone() {
			myGenomeUnit clone = (myGenomeUnit)super.clone();
			for(int i=0 ; i<size(); i++) {
				clone.set(i, get(i).clone());
			}
			return clone;
		}

	}
}