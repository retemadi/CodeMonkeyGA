package example.knapsack;

import edu.ccil.ec.SurvivalSelectionStrategy;
import edu.ccil.ec.selection.*; 
import edu.ccil.ec.selection.transform.*;

public class mySurvivalSelectionStrategy extends SurvivalSelectionStrategy {

	@Override 
	protected void registration() {
		combinedPopOutputSize = 48;
		combinedPopSelection = new Proportional(10,4,true);
		//combinedPopSelection.setTransformer(new edu.ccil.ec.selection.transform.Linear(6,8));
		elitePopSize = 2;
		return;
	}


}
