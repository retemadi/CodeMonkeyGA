package plugin.generated.Aug31;

import edu.ccil.ec.SurvivalSelectionStrategy;
import edu.ccil.ec.selection.*; 
import edu.ccil.ec.selection.transform.*;

public class mySurvivalSelectionStrategy extends SurvivalSelectionStrategy {

	@Override 
	protected void registration() {
		combinedPopOutputSize = 90;
		combinedPopSelection = new Truncation(150,90,true);
		elitePopSize = 5;
		randomPopSize = 5;
		return;
	}


}
