package example.ImageProcessingRMSN;

import edu.ccil.ec.SurvivalSelectionStrategy;
import edu.ccil.ec.selection.*; 
import edu.ccil.ec.selection.transform.*;

public class mySurvivalSelectionStrategy extends SurvivalSelectionStrategy {

	@Override 
	protected void registration() {
		combinedPopOutputSize = 270;
		combinedPopSelection = new Proportional(45,27,true);
		//combinedPopSelection = new Truncation(45,27,false);
		elitePopSize = 30;
		//randomPopSize = 5;
		return;
	}


}
