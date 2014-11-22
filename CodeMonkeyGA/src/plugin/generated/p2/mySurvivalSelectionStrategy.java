package plugin.generated.p2;

import edu.ccil.ec.SurvivalSelectionStrategy;
import edu.ccil.ec.selection.*; 
import edu.ccil.ec.selection.transform.*;

public class mySurvivalSelectionStrategy extends SurvivalSelectionStrategy {

	@Override 
	protected void registration() {
		combinedPopOutputSize = EvolutionProcess.getGenerationSize()*90/100;
		combinedPopSelection = new Truncation(450,270,false);
		elitePopSize = 15;
		randomPopSize = 15;
		return;
	}


}
