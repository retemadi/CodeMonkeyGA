package external;
import edu.ccil.ec.genotype.ArrayListGenotype;

/*
 * This file is a modified version of the existing class that is part of JGAP.
 * It is being used to calculate the fitness of individuals the same way 
 * that JGAP Knapsack example is calculating.
 * R. Etemadi
 * 
 * JGAP offers a dual license model containing the LGPL as well as the MPL.
 *
 * For licensing information please see the file license.txt included with JGAP
 * or have a look at the top of class org.jgap.Chromosome which representatively
 * includes the JGAP license policy applicable for any file delivered with JGAP.
 */


/**
 * Fitness function for the knapsack example.
 *
 * @author Klaus Meffert
 * @since 2.3
 */
public class KnapsackFitnessFunction {


  /** Volumes of arbitrary items in ccm*/
  public final static double[] itemVolumes = {
      50.2d, 14.8d, 27.5d, 6800.0d, 25.0d, 4.75d, 95.36d, 1500.7d, 18365.9d,
      83571.1d};

  /** Names of arbitrary items, only for outputting something imaginable*/
  public final static String[] itemNames = {
      "Torch", "Banana", "Miniradio", "TV", "Gameboy", "Small thingie",
      "Medium thingie", "Big thingie", "Huge thingie", "Gigantic thingie"};

  private final double m_knapsackVolume;

  public static final double MAX_BOUND = 1000000000.0d;

  private static final double ZERO_DIFFERENCE_FITNESS = Math.sqrt(MAX_BOUND);

  public KnapsackFitnessFunction(double a_knapsackVolume) {
    if (a_knapsackVolume < 1 || a_knapsackVolume >= MAX_BOUND) {
      throw new IllegalArgumentException(
          "Knapsack volumen must be between 1 and " + MAX_BOUND + ".");
    }
    m_knapsackVolume = a_knapsackVolume;
  }

  /**
   * Determine the fitness of the given Chromosome instance. The higher the
   * return value, the more fit the instance. This method should always
   * return the same fitness value for two equivalent Chromosome instances.
   *
   * @param a_subject the Chromosome instance to evaluate
   * @return a positive double reflecting the fitness rating of the given
   * Chromosome
   *
   * @author Klaus Meffert
   * @since 2.3
   */
  public double evaluate(ArrayListGenotype<Integer> a_subject) {
    // The fitness value measures both how close the value is to the
    // target amount supplied by the user and the total number of items
    // represented by the solution. We do this in two steps: first,
    // we consider only the represented amount of change vs. the target
    // amount of change and return higher fitness values for amounts
    // closer to the target, and lower fitness values for amounts further
    // away from the target. Then we go to step 2, which returns a higher
    // fitness value for solutions representing fewer total items, and
    // lower fitness values for solutions representing more total items.
    // ------------------------------------------------------------------
    double totalVolume = getTotalVolume(a_subject);
    int numberOfItems = getTotalNumberOfItems(a_subject);
    double volumeDifference = Math.abs(m_knapsackVolume - totalVolume);
    double fitness = 0.0d;
    // Step 1: Determine distance of amount represented by solution from
    // the target amount. If the change difference is greater than zero we
    // will divide one by the difference in change between the
    // solution amount and the target amount. That will give the desired
    // effect of returning higher values for amounts closer to the target
    // amount and lower values for amounts further away from the target
    // amount.
    // In the case where the change difference is zero it means that we have
    // the correct amount and we assign a higher fitness value
    // -----------------------------------------------------------------
    fitness += volumeDifferenceBonus(MAX_BOUND, volumeDifference);
    // Step 2: We divide the fitness value by a penalty based on the number of
    // items. The higher the number of items the higher the penalty and the
    // smaller the fitness value.
    // And inversely the smaller number of items in the solution the higher
    // the resulting fitness value.
    // -----------------------------------------------------------------------
    fitness -= computeItemNumberPenalty(MAX_BOUND, numberOfItems);
    // Make sure fitness value is always positive.
    // -------------------------------------------
    return  Math.max(1.0d, fitness); //fitness;// 
  }

  /**
   * Bonus calculation of fitness value.
   * @param a_maxFitness maximum fitness value applicable
   * @param a_volumeDifference volume difference in ccm for the items problem
   * @return bonus for given volume difference
   *
   * @author Klaus Meffert
   * @since 2.3
   */
  protected double volumeDifferenceBonus(double a_maxFitness,
                                         double a_volumeDifference) {
    if (a_volumeDifference == 0) {
      return a_maxFitness;
    }
    else {
      // we arbitrarily work with half of the maximum fitness as basis for non-
      // optimal solutions (concerning volume difference)
      return a_maxFitness / 2 - (a_volumeDifference * a_volumeDifference);
    }
  }

  /**
   * Calculates the penalty to apply to the fitness value based on the amount
   * of items in the solution.
   *
   * @param a_maxFitness upper boundary for fitness value possible
   * @param a_items number of items in the solution
   * @return a penalty for the fitness value based on the number of items
   *
   * @author Klaus Meffert
   * @since 2.3
   */
  protected double computeItemNumberPenalty(double a_maxFitness, int a_items) {
    if (a_items == 0) {
      // We know the solution cannot have less than zero items.
      // ------------------------------------------------------
      return 0;
    }
    else {
      // The more items the more penalty, but not more than the maximum fitness
      // value possible. Let's avoid linear behavior and use
      // exponential penalty calculation instead.
      // ----------------------------------------------------------------------
      return (Math.min(a_maxFitness, a_items * a_items));
    }
  }

  /**
   * Calculates the total amount of change (in cents) represented by
   * the given potential solution and returns that amount.
   *
   * @param a_potentialSolution the potential solution to evaluate
   * @return the total amount of change (in cents) represented by the
   * given solution
   *
   * @author Klaus Meffert
   * @since 2.3
   */
  public static double getTotalVolume(ArrayListGenotype<Integer> a_potentialSolution) {
    double volume = 0.0d;
    for (int i = 0; i < a_potentialSolution.size(); i++) {
      volume += getNumberOfItemsAtGene(a_potentialSolution, i) *
          KnapsackFitnessFunction.itemVolumes[i];
    }
    return volume;
  }

  /**
   * Retrieves the number of items represented by the given potential
   * solution at the given gene position.
   *
   * @param a_potentialSolution the potential solution to evaluate
   * @param a_position the gene position to evaluate
   * @return the number of items represented by the potential solution
   * at the given gene position
   *
   * @author Klaus Meffert
   * @since 2.3
   */
  public static int getNumberOfItemsAtGene(ArrayListGenotype<Integer> a_potentialSolution,
                                           int a_position) {
    //Integer numItems =
        //(Integer) a_potentialSolution.getGene(a_position).getAllele();
   // return numItems.intValue();
	  return a_potentialSolution.get(a_position);
  }

  /**
   * Returns the total number of items represented by all of the genes in
   * the given potential solution.
   *
   * @param a_potentialSolution the potential solution to evaluate
   * @return the total number of items represented by the given Chromosome
   *
   * @author Klaus Meffert
   * @since 2.3
   */
  public static int getTotalNumberOfItems(ArrayListGenotype<Integer> a_potentialSolution) {
    int totalItems = 0;
    int numberOfGenes = a_potentialSolution.size();
    for (int i = 0; i < numberOfGenes; i++) {
      totalItems += getNumberOfItemsAtGene(a_potentialSolution, i);
    }
    return totalItems;
  }
}
