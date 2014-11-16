package external;

public class TravelingSalesman {
	
	
	/**
	 * Sample TSP data set from : http://people.sc.fsu.edu/~jburkardt/datasets/tsp/p01.tsp
	 * NAME : 15 cities
	 * TYPE : TSP
	 * DIMENSION : 15
	 * EDGE_WEIGHT_TYPE : EXPLICIT
	 * EDGE_WEIGHT_FORMAT : FULL_MATRIX
	 * NODE_COORD_TYPE : NO_COORDS
	 * DISPLAY_DATA_TYPE : NO_DISPLAY
	 * TOUR_SECTION :   1  13  2  15  9  5  7  3  12  14  10  8  6  4  11 ==> 291.0 
	 */
	
	private static int[][] distance = new int[][]
		{{ 0, 29, 82, 46, 68, 52, 72, 42, 51, 55, 29, 74, 23, 72, 46}, 
		 {29,  0, 55, 46, 42, 43, 43, 23, 23, 31, 41, 51, 11, 52, 21}, 
		 {82, 55,  0, 68, 46, 55, 23, 43, 41, 29, 79, 21, 64, 31, 51}, 
		 {46, 46, 68,  0, 82, 15, 72, 31, 62, 42, 21, 51, 51, 43, 64}, 
		 {68, 42, 46, 82,  0, 74, 23, 52, 21, 46, 82, 58, 46, 65, 23}, 
		 {52, 43, 55, 15, 74,  0, 61, 23, 55, 31, 33, 37, 51, 29, 59}, 
		 {72, 43, 23, 72, 23, 61,  0, 42, 23, 31, 77, 37, 51, 46, 33}, 
		 {42, 23, 43, 31, 52, 23, 42,  0, 33, 15, 37, 33, 33, 31, 37}, 
		 {51, 23, 41, 62, 21, 55, 23, 33,  0, 29, 62, 46, 29, 51, 11}, 
		 {55, 31, 29, 42, 46, 31, 31, 15, 29,  0, 51, 21, 41, 23, 37}, 
		 {29, 41, 79, 21, 82, 33, 77, 37, 62, 51,  0, 65, 42, 59, 61}, 
		 {74, 51, 21, 51, 58, 37, 37, 33, 46, 21, 65,  0, 61, 11, 55}, 
		 {23, 11, 64, 51, 46, 51, 51, 33, 29, 41, 42, 61,  0, 62, 23}, 
		 {72, 52, 31, 43, 65, 29, 46, 31, 51, 23, 59, 11, 62,  0, 59}, 
		 {46, 21, 51, 64, 23, 59, 33, 37, 11, 37, 61, 55, 23, 59,  0}};

	/**
	 * 
	 * @param list
	 * @return
	 */
	public static double evaluate(Integer[] list){
		double totalPath = 0;
		for (int i=0; i<list.length; i++){
			int pointA = list[i];
			int pointB = list[i==list.length-1?0:i+1];
			totalPath+= distance[pointA][pointB];
		}
		
		return totalPath;
	}	
	
	public static void main(String[] args){
		Integer[] set1 = new Integer[]{1, 12, 0, 10, 3, 5, 7, 9, 13, 11, 2, 6, 4, 8, 14};
		Integer[] set2 = new Integer[]{0, 12, 1, 14, 8, 4, 6, 2, 11, 13, 9, 7, 5, 3, 10};
		System.out.println(TravelingSalesman.evaluate(set1));
		System.out.println(TravelingSalesman.evaluate(set2));
	
	}
}
