package edu.ccil.ec.selection;

import edu.ccil.ec.Phenotype;
import edu.ccil.ec.Population;
import edu.ccil.ec.SurvivalSelectionStrategy;
import edu.ccil.ec.tool.Util;

public class BestofBreed extends SurvivalSelectionStrategy {


	@Override
	public <P extends Phenotype<?,?>>  Population<P> apply(Population<P> currentGen, Population<P> intermediatePool) {
		
		Population<P> bigPool = Util.makeNewInstanceOfType(currentGen);
		bigPool.addAll(currentGen);
		intermediatePool.evaluate();
		bigPool.addAll(intermediatePool);
		return bigPool.getFittest(currentGen.size());
	}

	@Override
	protected void registration() {
		// TODO Auto-generated method stub
		
	}

}
