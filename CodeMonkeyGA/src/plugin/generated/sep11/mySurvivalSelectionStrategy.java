package plugin.generated.sep11;

import edu.ccil.ec.SurvivalSelectionStrategy;
import edu.ccil.ec.selection.*; 
import edu.ccil.ec.selection.transform.*;

public class mySurvivalSelectionStrategy extends SurvivalSelectionStrategy {

	@Override 
	protected void registration() {
		combinedPopOutputSize = 180;
		combinedPopSelection = new Truncation(300,180,true);
		elitePopSize = 10;
		randomPopSize = 10;
		return;
	}


}
