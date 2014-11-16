package external;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import edu.ccil.ec.genotype.ArrayListGenotype;

/*
 * The problem description is defined at : http://rosettacode.org/wiki/Knapsack_problem/Unbounded
 * There is knapsack with volume 0.25 that can hold up to 25 weight (e.g. Kg). 
 * 
 * Item					Explanation						Value (each)	weight	Volume (each)
 * Panacea (vials of)	Incredible healing properties	3000			0.3		0.025
 * Ichor (ampoules of)	Vampire's blood					1800			0.2		0.015
 * Gold (bars)			Shiny shiny						2500			2.0		0.002
 * Knapsack				For the carrying of	-			MAX				<=25	<=0.25 
 */

public class KnapsackUnBounded {
	
	public static final String[] itemNames = new String[]{"Panacea" , "Ichor" ,  "Gold"};
	private static  Map<String, Double[]> itemsMap = new HashMap<String, Double[]>();
	static {
		//        name      ,              		 value,    weight,  volume
		itemsMap.put(itemNames[0]	, new Double[]{ 3000.0, 	0.3, 	0.025});
		itemsMap.put(itemNames[1]	, new Double[]{ 1800.0, 	0.2, 	0.015});
		itemsMap.put(itemNames[2]	, new Double[]{ 2500.0, 	2.0, 	0.002});

	}
	private static double maxAllowedWeight = 25.0;
	private static double maxAllowedVolume = 0.25;
	

	
	/**
	 * assuming the order in the arraylist is Panacea, Ichor and Gold
	 * @param a_subject
	 * @return
	 */
	public static double evaluate(ArrayListGenotype<Integer> list) {
		double rawFitness = 0.0;
		double weight = 0.0;
		double volume = 0.0;
	
		for (int i=0; i< itemsMap.size(); i++) {
			rawFitness 	+= list.get(i)* itemsMap.get(itemNames[i])[0]; // number * value
			weight 		+= list.get(i)* itemsMap.get(itemNames[i])[1];     // number * weight
			volume		+= list.get(i)* itemsMap.get(itemNames[i])[2];     // number * volume
		}
		if (weight > maxAllowedWeight ) return 0.0; // for now 
		if (volume > maxAllowedVolume) return 0.0;  
		
		return rawFitness;
	}

	public static String translate(ArrayListGenotype<Integer> list) {
		double rawFitness = 0.0;
		double weight = 0.0;
		double volume = 0.0;
	
		for (int i=0; i< itemsMap.size(); i++) {
			rawFitness 	+= list.get(i)* itemsMap.get(itemNames[i])[0]; // number * value
			weight 		+= list.get(i)* itemsMap.get(itemNames[i])[1];     // number * weight
			volume		+= list.get(i)* itemsMap.get(itemNames[i])[2];     // number * volume
		}
		return "Total value :"+rawFitness +" with total weight:"+ weight +" in total volume:"+ volume; 
	}
	
	
/* Below code is picked up from : http://rosettacode.org/wiki/Knapsack_problem/Unbounded#Java
 * It is added here to be able to run the deterministic solution and compare the result with 
 * the output of CM generated code.
*/	
	
	protected Item []  items = {
               new Item("panacea", 3000,  0.3, 0.025),
               new Item("ichor"  , 1800,  0.2, 0.015),
               new Item("gold"   , 2500,  2.0, 0.002)
               };
	
	protected final int    n = items.length; // the number of items
	protected Item      sack = new Item("sack"   ,    0, 25.0, 0.250);
	protected Item      best = new Item("best"   ,    0,  0.0, 0.000);
	protected int  []  maxIt = new int [n];  // maximum number of items
	protected int  []    iIt = new int [n];  // current indexes of items
	protected int  [] bestAm = new int [n];  // best amounts
	
	public KnapsackUnBounded() {
	// initializing:
	for (int i = 0; i < n; i++) {
		maxIt [i] = Math.min(
	           (int)(sack.getWeight() / items[i].getWeight()),
	           (int)(sack.getVolume() / items[i].getVolume())
	        );
	} // for (i)
	
	// calc the solution:
	calcWithRecursion(0);
	
	// Print out the solution:
	NumberFormat nf = NumberFormat.getInstance();
	System.out.println("Maximum value achievable is: " + best.getValue());
	System.out.print("This is achieved by carrying (one solution): ");
	for (int i = 0; i < n; i++) {
		System.out.print(bestAm[i] + " " + items[i].getName() + ", ");
	}
	System.out.println();
	System.out.println("The weight to carry is: " + nf.format(best.getWeight()) +
	           "   and the volume used is: " + nf.format(best.getVolume())
	          );
	
	}
	
	// calculation the solution with recursion method
	// item : the number of item in the "items" array
	public void calcWithRecursion(int item) {
		for (int i = 0; i <= maxIt[item]; i++) {
			iIt[item] = i;
			if (item < n-1) {
				calcWithRecursion(item+1);
			} else {
				int    currVal = 0;   // current value
				double currWei = 0.0; // current weight
				double currVol = 0.0; // current Volume
				for (int j = 0; j < n; j++) {
					currVal += iIt[j] * items[j].getValue();
					currWei += iIt[j] * items[j].getWeight();
					currVol += iIt[j] * items[j].getVolume();
				}
			
				if (currVal > best.getValue()
					&&
					currWei <= sack.getWeight()
					&&
					currVol <= sack.getVolume()
					)
				{
					best.setValue (currVal);
					best.setWeight(currWei);
					best.setVolume(currVol);
					for (int j = 0; j < n; j++) bestAm[j] = iIt[j];
				} // if (...)
			} // else
		} // for (i)
	} // calcWithRecursion()
	
	// the main() function:
	public static void main(String[] args) {
		new KnapsackUnBounded();
	} // main()
		
	
	
	
	public class Item {
	    protected String name = "";
	    protected int value = 0;
	    protected double weight = 0;
	    protected double volume = 0;
	 
	    public Item() {
	    }
	 
	    public Item(String name, int value, double weight, double volume) {
	        setName(name);
	        setValue(value);
	        setWeight(weight);
	        setVolume(volume);
	    }
	 
	    public int getValue() {
	        return value;
	    }
	 
	    public void setValue(int value) {
	        this.value = Math.max(value, 0);
	    }
	 
	    public double getWeight() {
	        return weight;
	    }
	 
	    public void setWeight(double weight) {
	        this.weight = Math.max(weight, 0);
	    }
	 
	    public double getVolume() {
	        return volume;
	    }
	 
	    public void setVolume(double volume) {
	        this.volume = Math.max(volume, 0);
	    }
	 
	    public String getName() {
	        return name;
	    }
	 
	    public void setName(String name) {
	        this.name = name;
	    }
	 
	} // class
}
