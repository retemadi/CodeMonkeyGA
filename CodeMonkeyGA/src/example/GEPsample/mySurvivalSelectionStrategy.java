package example.GEPsample;

import edu.ccil.ec.SurvivalSelectionStrategy;
import edu.ccil.ec.selection.*; 

public class mySurvivalSelectionStrategy extends SurvivalSelectionStrategy {

	@Override 
	protected void registration() {
		combinedPopOutputSize = 350;
		combinedPopSelection = new Truncation(2,1,true);
		combinedPopSelection.setTransformer(new edu.ccil.ec.selection.transform.Logarithmic(3));
		return;
	}


}
