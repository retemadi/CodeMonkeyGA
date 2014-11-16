package external;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class RuleSetCA {

	private static HashMap<Integer, Integer[][]> LUT = new HashMap<Integer, Integer[][]>();

	/*
	 * The 8 neighboring pixel around the the pixel X is numbered as below 
	 * | 0 | 1 | 2 |
	 * | 3 | X | 4 |
	 * | 5 | 6 | 7 |
	 *
	 * Simplified Look Up table for 3x3 neighborhood of a pixel after eliminating symmetric patterns 
	 * (As described in paper "Training Cellular Automata for Image Processing" by Paul L. Rosin)
	 * They are categorized based on number of 1's (black or marked) pixel
	 */
	static {

		LUT.put(0, new Integer[][]{	{0,0,0,0,0,0,0,0} });

		LUT.put(1, new Integer[][]{	{1,0,0,0,0,0,0,0},{0,1,0,0,0,0,0,0}	});

		LUT.put(2, new Integer[][]{	{1,1,0,0,0,0,0,0},{1,0,1,0,0,0,0,0},{1,0,0,0,1,0,0,0},
								   	{1,0,0,0,0,0,0,1},{0,1,0,0,1,0,0,0},{0,1,0,0,0,0,1,0} });

		LUT.put(3, new Integer[][]{	{1,1,1,0,0,0,0,0},{1,1,0,0,1,0,0,0},{1,1,0,0,0,0,0,1},
				   					{1,1,0,0,0,0,1,0},{1,1,0,0,0,1,0,0},{1,1,0,1,0,0,0,0},
				   					{1,0,1,0,0,0,0,1},{1,0,1,0,0,0,1,0},{1,0,0,0,1,0,1,0}, 
				   					{0,1,0,0,1,0,1,0} });

		LUT.put(4, new Integer[][]{	{1,1,1,0,1,0,0,0},{1,1,1,0,0,0,0,1},{1,1,1,0,0,0,1,0},
									{1,1,0,0,1,0,0,1},{1,1,0,0,1,0,1,0},{1,1,0,0,1,1,0,0},
									{1,1,0,1,0,0,0,1},{1,1,0,0,0,0,1,1},{1,1,0,0,0,1,0,1}, 
									{1,1,0,1,0,0,0,1},{1,1,0,0,0,1,0,1},{1,0,1,0,0,1,0,1},
									{0,1,0,1,1,0,1,0} });

		LUT.put(5, new Integer[][]{	{1,1,1,0,1,0,0,1},{1,1,1,0,1,0,1,0},{1,1,1,0,1,1,0,0},
									{1,1,1,1,1,0,0,0},{1,1,1,0,0,0,1,1},{1,1,1,0,0,1,0,1},
									{1,1,0,0,1,0,1,1},{1,1,0,0,1,1,0,1},{1,1,0,0,1,1,1,0}, 
									{1,1,0,1,1,0,1,0} });

		LUT.put(6, new Integer[][]{	{1,1,1,0,1,0,1,1},{1,1,1,0,1,1,0,1},{1,1,1,0,1,1,1,0},
			   						{1,1,1,1,1,0,1,0},{1,1,1,0,0,1,1,1},{1,1,0,1,1,0,1,1} });
			
		LUT.put(7, new Integer[][]{	{1,1,1,0,1,1,1,1},{1,1,1,1,1,0,1,1}	});

		LUT.put(8, new Integer[][]{	{1,1,1,1,1,1,1,1} });

	}

	/**
	 * returns how many ones are in the given integer array
	 */
	private int getNumberOf1s(int[] neighbor) {
		int counter = 0;
		for (int i : neighbor) {
			if (i == 1) counter++; 
		}
		return counter;
	}

	/**
	 * returns the subset of LUT that has same number of 1s as neighbor input
	 * @param neighbor
	 * @return
	 */
	public Integer[][] getSubLUT(int[] neighbor) {
		return LUT.get(getNumberOf1s(neighbor));
	}
	
/* sample PGM image 
		P2
        # feep.pgm
        26 9
        15
*/        
	public static int[][] target = new int[][]
		{
		{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  0,  0,  0,  0, 0, 0,  0,  0,  0,  0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  0,  0,  0,  0, 0, 0,  0,  0,  0,  0, 0, 0},
        {0, 0, 3, 3, 3, 3, 0, 0, 7, 7, 7, 7, 0, 0, 11, 11, 11, 11, 0, 0, 15, 15, 15, 15, 0, 0},
        {0, 0, 3, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 11,  0,  0,  0, 0, 0, 15,  0,  0, 15, 0, 0},
        {0, 0, 3, 3, 3, 0, 0, 0, 7, 7, 7, 0, 0, 0, 11, 11, 11,  0, 0, 0, 15, 15, 15, 15, 0, 0},
        {0, 0, 3, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 11,  0,  0,  0, 0, 0, 15,  0,  0,  0, 0, 0},
        {0, 0, 3, 0, 0, 0, 0, 0, 7, 7, 7, 7, 0, 0, 11, 11, 11, 11, 0, 0, 15,  0,  0,  0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  0,  0,  0,  0, 0, 0,  0,  0,  0,  0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  0,  0,  0,  0, 0, 0,  0,  0,  0,  0, 0, 0},
		
		{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  0,  0,  0,  0, 0, 0,  0,  0,  0,  0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  0,  0,  0,  0, 0, 0,  0,  0,  0,  0, 0, 0},
        {0, 3, 3, 0, 8, 8, 8, 0, 7, 7, 7, 0, 0, 0, 11, 11, 11,  0, 0, 0, 15, 15, 15, 15, 0, 0},
        {0, 0, 3, 0, 8, 8, 8, 0, 0, 0, 0, 7, 0, 0,  0,  0, 11,  0, 0, 0, 15,  0,  0, 15, 0, 0},
        {0, 0, 3, 0, 8, 8, 8, 0, 0, 7, 7, 0, 0, 0, 11, 11, 11,  0, 0, 0, 15, 15, 15, 15, 0, 0},
        {0, 0, 3, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0,  0,  0, 11,  0, 0, 0, 15,  0,  0, 15, 0, 0},
        {0, 3, 3, 3, 0, 0, 0, 0, 7, 7, 7, 7, 0, 0, 11, 11, 11,  0, 0, 0, 15, 15, 15, 15, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  0,  0,  0,  0, 0, 0,  0,  0,  0,  0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  0,  0,  0,  0, 0, 0,  0,  0,  0,  0, 0, 0},

		};
	

	public static final int[][] input1 = new int[][]
		{
		{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  0,  0,  0,  0, 0, 0,  0,  0,  0,  0, 0, 0},
		{0, 0, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0,  9,  0,  0,  0, 0, 0,  0,  0,  0,  0, 0, 0},
        {0, 0, 3, 3, 3, 3, 0, 0, 7, 7, 7, 7, 0, 0, 11, 11, 11, 11, 0, 0, 15, 15, 15, 15, 0, 0},
        {0, 0, 3, 0, 0, 0, 0, 0, 7, 0, 0, 0, 9, 0, 11,  0,  0,  0, 0, 0, 15,  0,  0, 15, 0, 0},
        {0, 0, 3, 3, 3, 9, 0, 0, 7, 7, 7, 0, 0, 0, 11, 11, 11,  0, 0, 0, 15, 15, 15, 15, 0, 0},
        {0, 0, 3, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 11,  0,  0,  0, 9, 0, 15,  0,  0,  0, 0, 0},
        {0, 0, 3, 0, 9, 0, 0, 9, 7, 7, 7, 7, 0, 0, 11, 11, 11, 11, 0, 0, 15,  0,  9,  0, 0, 0},
        {0, 0, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0,  0,  0,  0,  0, 0, 0,  0,  9,  0,  0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  0,  0,  0,  0, 0, 0,  0,  0,  0,  0, 0, 0},
		
		{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  0,  0,  0,  0, 0, 0,  0,  0,  0,  0, 0, 0},
		{0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  0,  0,  0,  0, 0, 0,  0,  0,  0,  0, 0, 0},
        {0, 3, 3, 0, 8, 8, 8, 0, 7, 7, 7, 0, 0, 0, 11, 11, 11,  0, 0, 0, 15, 15, 15, 15, 0, 0},
        {0, 0, 3, 0, 8, 1, 8, 0, 0, 0, 0, 7, 0, 0,  0,  0, 11,  9, 0, 0, 15,  9,  0, 15, 0, 0},
        {0, 0, 3, 0, 8, 8, 8, 0, 0, 7, 7, 0, 9, 9, 11, 11, 11,  0, 9, 0, 15, 15, 15, 15, 9, 0},
        {0, 0, 3, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0,  0,  0, 11,  0, 0, 9, 15,  0,  0, 15, 0, 0},
        {0, 3, 3, 3, 0, 9, 0, 0, 7, 7, 7, 7, 0, 0, 11, 11, 11,  0, 0, 0, 15, 15, 15, 15, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  0,  0,  0,  0, 0, 0,  0,  0,  0,  0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  0,  0,  0,  0, 0, 0,  0,  0,  0,  0, 0, 0},
		};

	
	public static int[][] input = input1;
	
///this section added to use image from RuleSetAnalyse instead of hard coded arrays
/*
	static {
		try {
			input = RuleSetAnalyse.readPGM(RuleSetAnalyse.inputName);//
			target = RuleSetAnalyse.readPGM(RuleSetAnalyse.targetName);
		} catch (IOException ioe) {
			
		}
	}
*/
	// it is used to keep the outcome of applying rule set to input image
	public static int[][] output = new int[input.length][input[0].length];

	/**
	 * evaluates a list of rule sets by applying them to input image, creating output image 
	 * and comparing it with target image. then using the mismatch as to calculate fitness of rule set 
	 * @return
	 */
	public static double evaluate(ArrayList<Boolean[]> ruleSet)  {
		int rounds =1;
		for (int r=0; r<rounds; r++) {
			//generateOutputBnW(ruleSet);
			//generateOutputGray(ruleSet,16,1,false);
			generateOutputGrayBitwise(ruleSet);
			
			//This section added for images from RuleSetAnalyse
			//generateOutputGray(ruleSet,255,10,true);
			
			// returns number of mismatch as fitness (to be used in minimization)
			//return countError(output,true);
		
			//use output as new input
/*
			input = output.clone();
			for (int i=0; i<output.length; i++) {
				input[i] = output[i].clone();
			}
*/
		}
		//output is updated above
		return countError(output,true);

	}

	public static double evaluate(ArrayList<Boolean[]> ruleSet, ArrayList<Integer> baseLine)  {
		int rounds =1;
		for (int r=0; r<rounds; r++) {
			////generateOutputBaseLine(ruleSet, baseLine);
			//generateOutputGray(ruleSet,16,false);
			generateOutputGrayBitwise(ruleSet);
		
			//use output as new input
/*
			input = output.clone();
			for (int i=0; i<output.length; i++) {
				input[i] = output[i].clone();
			}
*/
		}
		//output is updated above
		return countError(output,true);

	}
	
	
	/*
	 * returns the 8 neighboring pixel around pixel X (at coordinate i,j) of input image as below 
	 * | 0 | 1 | 2 |
	 * | 3 | X | 4 |
	 * | 5 | 6 | 7 |
	 * Point X can not be at border lines of image (it must have all 8 neighbor) 
	 */
	public static int[] getNeighbor(int i, int j) {
		return new int[] {input[i-1][j-1], input[i-1][j], input[i-1][j+1],
						  input[i][j-1],   				  input[i][j+1],
						  input[i+1][j-1], input[i+1][j], input[i+1][j+1] };
	}

	/*
	 * compares the give rule set with neighbors array in integer, using threshold as 0/1 boundary of each neighbor value
	 */
	public static boolean isMatchFound(ArrayList<Boolean[]> ruleSet, int[] neighbor, int threshold) {
		boolean match = true;
		for (Boolean[] rule: ruleSet) {
			match = true;
			for (int k=0; k<rule.length;k++) {
				if (rule[k] != (neighbor[k] >= threshold) ) {
					match = false;
					break;
				}
			}
			if (match) {  //if match is still true after a rule is tried we have a rule match so return true
				return match;
			}
		}
		return match;
	}

	public static int[][] generateOutputGrayBitwise(ArrayList<Boolean[]> ruleSet){
		for (int i=1; i<input.length-1 ; i++) {
			for (int j=1; j<input[0].length-1; j++) {
				
				int[] neighbor = getNeighbor(i,j);
				boolean[][] binary = neighborToBooleans(neighbor,4);
						
				boolean[] out = intToBooleans(input[i][j]);
				for (int n=0; n<4; n++) {
					Boolean[] bitNeighbor = nthElementArray(binary, n);
					for (Boolean[] rule: ruleSet) {
						if (Arrays.equals(rule, bitNeighbor)) {
							out[n]= false;
							//break;
						}
					}
				}
				output[i][j] = booleansToInt(out); ///Integer.parseInt(new String(out),2);
			}
		}
		return output;
	}

	private static Boolean[] nthElementArray(boolean[][] in, int n) {
		Boolean[] out = new Boolean[in.length];
		for (int i=0; i<out.length; i++) {
			out[i]=in[i][n];
		}
		return out;
	}
	
	private static int booleansToInt(boolean[] arr){
	    int n = 0;
	    for (boolean b : arr){
	        n = (n << 1) | (b ? 1 : 0);
	    }
	    return n;
	}
	
	private static boolean[] intToBooleans2(int num, int len) {

		boolean[] bits = new boolean[len];
	    for (int i = bits.length-1; i >= 0; i--) {
	        bits[i] = (num & (1 << i)) != 0;
	    }	
	    return bits;
	}
	
	private static boolean[] intToBooleans(int num) {
		
		switch (num) {
		case 0:  return new boolean[]{false,false,false,false};
		case 1:  return new boolean[]{false,false,false,true};
		case 2:  return new boolean[]{false,false,true,false};
		case 3:  return new boolean[]{false,false,true,true};
		case 4:  return new boolean[]{false,true,false,false};
		case 5:  return new boolean[]{false,true,false,true};
		case 6:  return new boolean[]{false,true,true,false};
		case 7:  return new boolean[]{false,true,true,true};
		case 8:  return new boolean[]{true,false,false,false};
		case 9:  return new boolean[]{true,false,false,true};
		case 10: return new boolean[]{true,false,true,false};
		case 11: return new boolean[]{true,false,true,true};
		case 12: return new boolean[]{true,true,false,false};
		case 13: return new boolean[]{true,true,false,true};
		case 14: return new boolean[]{true,true,true,false};
		case 15: return new boolean[]{true,true,true,true};
		}
		return null;
	}
	
	private static boolean[][] neighborToBooleans(int[] neighbors, int len) {
		boolean[][] neighborInBits = new boolean[neighbors.length][len];
		for (int i=0; i<neighbors.length; i++) {
			neighborInBits[i] = intToBooleans(neighbors[i]);
		}
		return neighborInBits;
	}

	
	
	public static int[][] generateOutputBaseLine(ArrayList<Boolean[]> ruleSet, ArrayList<Integer> baseLine) {
		for (int i=1; i<input.length-1 ; i++) {
			for (int j=1; j<input[0].length-1; j++) {
				if (isMatchFoundBaseLine(ruleSet, getNeighbor(i,j),baseLine)) {
					output[i][j] = 0;
				} else {
					output[i][j] = input[i][j];
				}
			}
		}
		return output;
	}
	
	/*
	 * compares the give rule set with neighbors array in integer, using threshold as 0/1 boundary of each neighbor value
	 */
	public static boolean isMatchFoundBaseLine(ArrayList<Boolean[]> ruleSet, int[] neighbor, ArrayList<Integer> threshold) {
		boolean match = true;
		for (int i=0; i<ruleSet.size(); i++) {
			Boolean[] rule = ruleSet.get(i);
			match = true;
			for (int k=0; k<rule.length;k++) {
				if (rule[k] != (neighbor[k] >= threshold.get(i)) ) {
					match = false;
					break;
				}
			}
			if (match) {  //if match is still true after a rule is tried we have a rule match so return true
				return match;
			}
		}
		return match;
	}
	
	
	/**
	 * using given rule set fill out the output array
	 * can be used as input parameter of createPGM to save the output into file
	 * this is ideal not for PGM but for PBM where there is only 0 & 1
	 *
	 * @param ruleSet
	 * @param baseLine the line to cut between black and white e.g. 1
	 */
	public static int[][] generateOutputBnW(ArrayList<Boolean[]> ruleSet) {
		for (int i=1; i<input.length-1 ; i++) {
			for (int j=1; j<input[0].length-1; j++) {
				if (isMatchFound(ruleSet, getNeighbor(i,j),1)) {
					output[i][j] = 0;
				} else {
					output[i][j] = input[i][j];
				}
			}
		}
		return output;
	}

	/**
	 * using given rule set fill out the output array
	 * The difference with BnW is that here we loop through all thresholds from 1 to threshold  and 
	 * return the threshold value that finds a match as output value (instead of 0)
	 * or returns the average of all matched thresholds (floor value)  
	 * (instead of threshold being 1 in BnW and 0 as output, this extended version & technically should 
	 * cover both salts and peppers whereas BnW can only find peppers)
	 * @param ruleSet
	 * @param maxThreshold
	 * @param average
	 */
	public static int[][] generateOutputGray(ArrayList<Boolean[]> ruleSet, int maxThreshold, int step, boolean average) {
		for (int i=1; i<input.length-1 ; i++) {
			for (int j=1; j<input[0].length-1; j++) {
				output[i][j] = input[i][j];
				if (average) { // try all matches & use average of threshold
					int found =0;
					int sum = 0;
					for (int k=1; k<maxThreshold; k+=step) {
						if (isMatchFound(ruleSet, getNeighbor(i,j),k)) {
							sum+=k-1;
							found++;
						}
					}
					if (found >0) {
						output[i][j] = Math.round(sum/found);
					}
					
				} else { // find the first match and use the threshold
					for (int k=1; k<maxThreshold; k++) {
						if (isMatchFound(ruleSet, getNeighbor(i,j),k)) {
							output[i][j] = k-1;
							break;
						}
					}
				}
			}
		}
		return output;
	}
	
	
	/**
	 * Count the number of mismatch between input parameter and target
	 * @param param  the image to compare 
	 * @return
	 */
	public static int countError(int[][] param, boolean diff) {
		int error = 0;
		for(int i=0; i<param.length; i++){
			for(int j=0; j<param[i].length; j++){
				if (param[i][j] != target[i][j]) {
					if (!diff) {  // add up number of mismatches
						error++;
					} else { // add up the error diffs 
						error += Math.abs(param[i][j] - target[i][j]); 
					}
				}
			}
		}
		return error;
	}
	
	

	
	public static void createPGM(ArrayList<Boolean[]> ruleSet, String filename) throws IOException {
		evaluate(ruleSet); //evaluate fills in the output
		createPGM(output,filename);
		return;
	}
	
	/**
	 * create output.pgm using data from input parameter array
	 * @param param
	 * @throws IOException
	 */
	public static void createPGM(int[][] param, String filename) throws IOException {
		String nl = System.getProperty("line.separator");
		if (filename == null) {
			filename="output.pgm";
		}
		StringBuffer sb = new StringBuffer("P2"+nl+"#"+filename);
		sb.append(nl+param[0].length+" "+param.length);
		sb.append(nl+"15"+nl);  //for now 15 but we should obtain highest value from parameter array
		for(int i=0; i<param.length; i++){
			for(int j=0; j<param[i].length; j++){
				sb.append(param[i][j]+" ");
			}
			sb.append(nl);
		}

		FileOutputStream stream = new FileOutputStream(filename);
		Writer writer = new OutputStreamWriter(stream);
		writer.write(sb.toString());
		writer.close();
		stream.close();		
	}
	
	//For testing 
	public static void main(String[] arg) throws Exception{
		createPGM(target,"target.pgm");
		createPGM(input1,"input.pgm");
		
	}
	
}
