package example.ackley;

import edu.ccil.ec.SurvivalSelectionStrategy;
import edu.ccil.ec.selection.*; 
import edu.ccil.ec.selection.transform.*;

public class mySurvivalSelectionStrategy extends SurvivalSelectionStrategy {

	@Override 
	protected void registration() {
		combinedPopOutputSize = 270;
		//combinedPopSelection = new Proportional(450,270,false);
		combinedPopSelection = new Truncation(450,270,false);  //faster convergence
		elitePopSize = 15;
		randomPopSize = 15;
		return;
	}


}
