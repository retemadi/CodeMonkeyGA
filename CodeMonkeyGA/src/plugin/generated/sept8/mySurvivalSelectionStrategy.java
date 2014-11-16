package plugin.generated.sept8;

import edu.ccil.ec.SurvivalSelectionStrategy;
import edu.ccil.ec.selection.*; 
import edu.ccil.ec.selection.transform.*;

public class mySurvivalSelectionStrategy extends SurvivalSelectionStrategy {

	@Override 
	protected void registration() {
		combinedPopOutputSize = 180;
		combinedPopSelection = new Proportional(25,18,true);
		elitePopSize = 10;
		randomPopSize = 10;
		return;
	}


}
