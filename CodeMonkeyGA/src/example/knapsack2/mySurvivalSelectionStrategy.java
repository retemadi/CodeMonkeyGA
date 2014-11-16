package example.knapsack2;

import edu.ccil.ec.SurvivalSelectionStrategy;
import edu.ccil.ec.selection.*; 
import edu.ccil.ec.selection.transform.*;

public class mySurvivalSelectionStrategy extends SurvivalSelectionStrategy {

	@Override 
	protected void registration() {
		combinedPopOutputSize = 48;
		combinedPopSelection = new Proportional(10,4,true);
		elitePopSize = 2;
		return;
	}


}
