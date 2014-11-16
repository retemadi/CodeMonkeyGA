package plugin.generated.sept1;

import edu.ccil.ec.SurvivalSelectionStrategy;
import edu.ccil.ec.selection.*; 
import edu.ccil.ec.selection.transform.*;

public class mySurvivalSelectionStrategy extends SurvivalSelectionStrategy {

	@Override 
	protected void registration() {
		combinedPopOutputSize = 90;
		combinedPopSelection = new Truncation(150,90,false);
		elitePopSize = 5;
		randomPopSize = 5;
		return;
	}


}
