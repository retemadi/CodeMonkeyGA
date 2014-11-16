package example.de_jong;

import edu.ccil.ec.SurvivalSelectionStrategy;
import edu.ccil.ec.selection.*; 
import edu.ccil.ec.selection.transform.*;

public class mySurvivalSelectionStrategy extends SurvivalSelectionStrategy {

	@Override 
	protected void registration() {
		combinedPopOutputSize = 270;
		combinedPopSelection = new Proportional(450,270,true);
		//combinedPopSelection = new Truncation(450,270,false);
		elitePopSize = 25;
		randomPopSize = 5;
		return;
	}


}
