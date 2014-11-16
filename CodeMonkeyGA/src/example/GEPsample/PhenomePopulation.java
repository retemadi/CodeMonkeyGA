package example.GEPsample;

import edu.ccil.ec.Population;

public class PhenomePopulation extends Population<Phenome> {

	private static final long serialVersionUID = 1L;
	
	public PhenomePopulation() {
		super();
		minimization = false;
	}
	
	//if there is external program to call for fitness calculation
	//it should be set here.
	
}
