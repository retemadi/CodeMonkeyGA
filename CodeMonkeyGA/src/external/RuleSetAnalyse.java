package external;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import edu.ccil.ec.tool.Util;

public class RuleSetAnalyse {
	
	private static final String nl = System.getProperty("line.separator");

	private static String maxColor;
	
	private static int neighorBoxlength = 5; //5x5 array

	//array of images { {input1, target1, finalInput1, finalTarget1} , {....} }
	private static final String[][] imageSet = { 
	 //0
	{"ImageJ_277095\\277095.SnP.175x175.ascii.pgm","ImageJ_277095\\277095.175x175.ascii.pgm", 
	 "ImageJ_277095\\277095.SnP.ascii.pgm", "ImageJ_277095\\277095.ascii.pgm"},
	 //1
	{"ImageJ_113044\\113044_SnP_175.ascii.pgm","ImageJ_113044\\113044_175.ascii.pgm",
	 "ImageJ_113044\\113044_SnP.ascii.pgm","ImageJ_113044\\113044.ascii.pgm" },
	 //2
	{"ImageJ_25098\\25098.ascii.SnP.175.pgm","ImageJ_25098\\25098.ascii.175.pgm",
	 "ImageJ_25098\\25098.ascii.SnP.pgm"    ,"ImageJ_25098\\25098.ascii.pgm" },
     //3
	{"ImageJ_61060\\61060.ascii.SnP.175.pgm","ImageJ_61060\\61060.ascii.175.pgm",
	 "ImageJ_61060\\61060.ascii.SnP.pgm"    ,"ImageJ_61060\\61060.ascii.pgm" },
     //4
	{"ImageJ_68077\\68077.ascii.SnP.175.pgm","ImageJ_68077\\68077.ascii.175.pgm",
	 "ImageJ_68077\\68077.ascii.SnP.pgm"    ,"ImageJ_68077\\68077.ascii.pgm" },
     //5
	{"ImageJ_78019\\78019.ascii.SnP.175.pgm","ImageJ_78019\\78019.ascii.175.pgm",
	 "ImageJ_78019\\78019.ascii.SnP.pgm"    ,"ImageJ_78019\\78019.ascii.pgm" },
     //6
	{"ImageJ_181091\\181091.ascii.SnP.175.pgm","ImageJ_181091\\181091.ascii.175.pgm",
	 "ImageJ_181091\\181091.ascii.SnP.pgm"    ,"ImageJ_181091\\181091.ascii.pgm" },
     //7
	{"ImageJ_353013\\353013.ascii.SnP.175.pgm","ImageJ_353013\\353013.ascii.175.pgm",
	 "ImageJ_353013\\353013.ascii.SnP.pgm"    ,"ImageJ_353013\\353013.ascii.pgm" },
 
	};
	
	private static final String prefix = "..\\..\\Experiments\\ImageProcessing\\";
	protected static final String inputName  = prefix+imageSet[7][0];//"ImageJ_113044\\113044_SnP_175.ascii.pgm";//"277095.SnP.175x175.ascii.pgm";
	protected static final String targetName = prefix+imageSet[7][1];//"ImageJ_113044\\113044_175.ascii.pgm";//"277095.175x175.ascii.pgm";
	protected static final String outputName = "output.pgm";
	
	public static final String finalInputName  = prefix+imageSet[7][2];//"ImageJ_113044\\113044_SnP.ascii.pgm";//"277095.SnP.ascii.pgm";
	public static final String finalTargetName = prefix+imageSet[7][3];//"ImageJ_113044\\113044.ascii.pgm";//"277095.ascii.pgm";
	
	private static int[][] input; //the 2D noisy sub image input array (used for training)
	private static int[][] target; //the 2D clean sub image target array (used for training)
	private static int[][] finalInput; //the 2D noisy full image input array (used for testing)
	private static int[][] finalTarget; //the 2D clean full image target array (used for testing)
	
	static {
		try {
			input = readPGM(inputName);
			target = readPGM(targetName);
			finalInput = readPGM(finalInputName);
			finalTarget = readPGM(finalTargetName);

			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// to have alternative noisy input
	public static void changeInputAndFinal(String inFN, String finalInFN) {
		try {
			input = readPGM(inFN);
			finalInput = readPGM(finalInFN);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) throws IOException {
		System.out.println("pass the fullpath of denoised pgm and reference pgm to calculate RMS of denoised one.");

		if (args == null || args.length <2 ) {
			args = new String[] {prefix+"ImageJ_353013\\353013.ascii.SnP - Copy.using-despeckle.pgm", finalTargetName};
		}
//RMS : 33.29993728640261  of noisy image 7
//RMS : 4.911485216228293  of de-noised version by evolution of rules
//RMS : 30.48997397020754  of de-noised using wavelet 
//RMS : 6.465216417289356  of de-noised using NL fitler (alpha trimmed mean, alpha=1.0, Radius=1.0)
//RMS : 7.049125046685495  of de-noised using despeckle (adaptive, recursive, black=0, white=255, radius=1)
		
		int [][] denoised = readPGM(args[0]);
		int [][] reference = readPGM(args[1]);
		
		System.out.println("RMS : "+calculateRMS(denoised, reference));
		return;

	}
	
/*	
	public static void main(String[] args) throws IOException {

		int[][] rules = new int[][]{
		{229, 61, 140, 0, 8, 174, 99, 223, 42, 6, 68, 63, 0, 207, 18, 50, 128, 157, 17, 0, 51, 87, 0, 4, 51}, 
		{129, 176, 55, 122, 247, 195, 154, 0, 83, 110, 44, 247, 255, 73, 67, 33, 180, 210, 102, 60, 0, 97, 191, 34, 238}, 
		{141, 66, 119, 31, 0, 160, 23, 73, 34, 36, 0, 0, 255, 73, 0, 135, 24, 149, 100, 0, 19, 115, 0, 100, 44}
		};//E:13139
		
		ArrayList<Integer[]> testRuleSet = new ArrayList<Integer[]>();
		for (int[] rule: rules) {
			Integer[] dest = new Integer[rule.length];
			for (int i=0; i<rule.length; i++) {
				dest[i] = rule[i];
			}
			//System.arraycopy(rule, 0, dest, 0, rule.length);
			testRuleSet.add(dest);
		}

		printStat(testRuleSet);
		output2PGM(testRuleSet, System.currentTimeMillis()+outputName);

		//changeInputAndFinal("277095.gus.100x100.ascii.pgm","277095.gus.ascii.pgm");

		//printStat(testRuleSet);
		//output2PGM(testRuleSet, System.currentTimeMillis()+outputName);

	}
*/
	public static void printStat(ArrayList<Integer[]> testRuleSet) throws IOException{
		Util.log("input mean/sigma:"+ getMean(input)/getSigma(input));
		int[][] output = generateOutput(testRuleSet);
		Util.log("output mean/sigma:"+ getMean(output)/getSigma(output));
		int baseError = countError(input, target, true);
		double originalRMS = calculateRMS(input,target);
		int error = countError(output,target, true);
		Util.log("Raw input  Error:"+ baseError+", Raw output Error:"+ error );
		Util.log("Input Image RMS:"+ originalRMS);
		double ratio = (double)error/baseError;
		Util.log("Error Value Ratio:"+ ratio + ", That is improvement of %"+(1.0-ratio)*100 );

		baseError = countError(input, target, false);
		error = countError(output,target, false);
		double outputRMS = calculateRMS(output,target);
		Util.log("# of input  Error:"+ baseError+", # of output Error:"+ error+ ", Diff:"+(baseError-error) );
		Util.log("Output Image RMS:"+outputRMS);
		double ratio2 = Math.abs((double)(baseError-error))/baseError;
		Util.log("Error Number Ratio:"+ratio2 +", That is improvement of %"+(1.0-ratio2)*100 );
		
		
	}
	
	/**
	 * evaluates the rule set and returns the error count
	 * @param ruleSet
	 * @return
	 */
	public static double evaluate(ArrayList<Integer[]> ruleSet){
		try {
			
			int[][] output = generateOutput(ruleSet);

			//int[][] target = readPGM(targetName);
			return countError(target, output, true);
			
		}catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * applies the ruleSet and generates the output and writes it into given file as PGM
	 * used at the end of process to demonstrate the result of best ruleSet
	 * @param ruleSet
	 * @param pathFilename
	 * @throws IOException
	 */
	public static void output2PGM(ArrayList<Integer[]> ruleSet, String pathFilename) throws IOException {
		writePGM(generateOutput(ruleSet), pathFilename);
	}

	public static void outputFinal2PGM(ArrayList<Integer[]> ruleSet, String pathFilename) throws IOException {
		input = finalInput; //change input to the complete image (because generateOuput reads input array
		int[][] finalOutput = generateOutput(ruleSet);
		int baseError = countError(finalTarget, input, true);
		int error = countError(finalTarget, finalOutput, true);

		double ratio = (double)error/baseError;
		Util.log("Full Error Ratio:"+ ratio + " That is improvement of %"+(1.0-ratio)*100 );

		baseError = countError(finalTarget, input, false);
		error = countError(finalTarget, finalOutput, false);
		Util.log("Full # of input  Error:"+ baseError+", Full # of output Error:"+ error + ", Diff:"+(baseError-error));
		double ratio2 = Math.abs((double)(baseError-error))/baseError;
		Util.log("Full Error Number Ratio:"+ratio2 +", That is improvement of %"+(1.0-ratio2)*100 );
		
        double targetSNR = getMean(finalTarget)/getSigma(finalTarget);
        double inputSNR = getMean(finalInput)/getSigma(finalInput);
        double outputSNR = getMean(finalOutput)/getSigma(finalOutput);
        
		Util.log("Full Target S/N:"+ targetSNR +", Full Input S/N:"+inputSNR +", Full output S/N:"+outputSNR);
		Util.log("SNR reduction :"+ (outputSNR-inputSNR)*100 +", Full Input S/N:"+inputSNR +", Full output S/N:"+outputSNR);
		
		
		
		writePGM(finalOutput, pathFilename);
	
		input = readPGM(inputName); //set input back to part of image

	}

	
	// reads the input, scan it and find match and fills the output
	private static int[][] generateOutput(ArrayList<Integer[]> ruleSet) throws IOException{

		//int[][] target = readPGM(targetName);
		//int[][] input  = readPGM(inputName);
		int[][] output = new int[input.length][input[0].length];
				
		//scan the input, find the match rule, set the output value	
		//i acts as Y, j acts as X
		for (int i=0; i<input.length; i++) {
			for (int j=0; j<input[i].length; j++){
				
				int	matchRule = getBestMatch(ruleSet,getNeighbors(input,i,j));
						//isRoughMatch(ruleSet, getNeighbors(input,i,j));
						//isFoundMatch(ruleSet, getNeighbors(input,i,j));

				if (matchRule > -1) {
					output[i][j] = 
					applyRule(input[i][j], ruleSet.get(matchRule)); 
				} else {
					output[i][j] = input[i][j];
					
				}
			}
		}
		return output;
	}
	
	// returns the first matching rule in the ruleSet. if no match returns -1
	private static int isExactMatch(ArrayList<Integer[]> ruleSet, int[] neighbors) {
		for (int r=0; r<ruleSet.size(); r++) {
			Integer[] rule = ruleSet.get(r);
			boolean match = true;
			for (int i=0; i<neighbors.length; i++) {
				if (neighbors[i] > -1 && neighbors[i] != rule[i].intValue()) {
					match = false;
					break;
				}
			}
			//if match is still true, the rule didn't find any mismatch so it's a match
			if (match) return r;
		}
		return -1;
	}

	
	// returns the best matching rule in the ruleSet with respect to neighborhood. 
	//the center has hard match but neighbors soft match. if no match found returns -1
	private static int getBestMatch(ArrayList<Integer[]> ruleSet, int[] neighbors) {
		int center = neighbors[neighbors.length/2];
		int bestMatch = -1;
		Integer lowestDiff = null;
		for (int r=0; r<ruleSet.size(); r++) {
			Integer[] rule = ruleSet.get(r);
			int ruleCenter = rule[rule.length/2];
			//center must match
			if (center != ruleCenter) {
				continue;
			}
			//if we are hear the center is a match so calculate the diff with neighborhood
			int diffSum = 0;
			for (int i=0; i<neighbors.length; i++) {
				if (neighbors[i] > -1) {
					diffSum += Math.abs(neighbors[i] - rule[i].intValue()); 
				}
			}
			if (lowestDiff == null //first one
					|| diffSum < lowestDiff) {//another but better
				bestMatch = r;
				lowestDiff = diffSum;
			}
		}
		return bestMatch;
	}
	

	// returns the first matching rule in the ruleSet. if no match returns -1
	private static int isRoughMatch(ArrayList<Integer[]> ruleSet, int[] neighbors) {
		
		int dot = neighbors[neighbors.length/2]; 
		if (dot !=0 && dot!=255) { //we are only look for rules for Salt and Pepper dots
			return -1;
		}
		for (int r=0; r<ruleSet.size(); r++) {
			Integer[] rule = ruleSet.get(r);
			boolean match = true;
			for (int i=0; i<neighbors.length; i++) {
				if (neighbors[i] > -1 &&  Math.abs(neighbors[i] - rule[i].intValue()) >125 ) {
					match = false;
					break;
				}
			}
			//if match is still true, the rule didn't find any mismatch so it's a match
			if (match) return r;
		}
		return -1;
	}


	private static double getSum(int[][] array) {
		int sum=0;
		for (int i=0;i<array.length; i++) {
			for (int j=0; j<array[i].length;j++){
				sum += array[i][j];
			}
		}
		return sum;
	}

	
	private static double getMean(int[][] array) {
		return getSum(array)/(array.length*array[0].length);
	}
	
	private static double getVariance(int[][] array) {
		double mean = getMean(array);
		double bigSigma = 0;
		int num=0;
		for (int i=0;i<array.length; i++) {
			for (int j=0; j<array[i].length;j++){
				bigSigma += Math.pow((array[i][j]- mean),2);
				num++;
			}
		}
		return bigSigma/num;
	}
	
	private static double getSigma(int[][] array) {
		return Math.sqrt(getVariance(array));
	}
	
	
	
	// returns the first matching rule in the ruleSet. if no match returns -1
	private static int isFoundMatch(ArrayList<Integer[]> ruleSet, int[] neighbors) {
		for (int r=0; r<ruleSet.size(); r++) {
			Integer[] rule = ruleSet.get(r);
			boolean match = true;
			for (int i=0; i<neighbors.length; i++) {
				if (neighbors[i] > -1 &&  !foundInArray(rule,neighbors[i])) {
					match = false;
					break;
				}
			}
			//if match is still true, the rule didn't find any mismatch so it's a match
			if (match) {
				return r;
			}
		}
		return -1;
	}
	
	private static boolean foundInArray(Integer[] arr, int num) {
		for (int i=0; i<arr.length; i++) {
			if (arr[i] == num) return true;
		}
		return false;
	}
	
	/*
	 * returns the neighboring pixel around pixel X (at coordinate i,j)
	 * an example of 3x3 neighbors is below 
	 * | 0 | 1 | 2 |
	 * | 3 | X | 4 |
	 * | 5 | 6 | 7 |
	 * if Point X is at border the missing neighbor locations are considered as don't care 
	 */
	 private static int[] getNeighbors(int[][] input, int x, int y) {
		 int[] neighbors = new int[neighorBoxlength * neighorBoxlength];
		 int distance = neighorBoxlength/2;
		 int k=0;
		 for (int i=x-distance; i<= x+distance; i++){
			 for (int j=y-distance; j<= y+distance; j++){
				 if (i >= 0 && j>=0 && i<input.length && j<input[x].length) {
					 neighbors[k++] = input[i][j];
				 } else {
					 neighbors[k++] = -1; //don't care
				 }
			 }
		 }
		 return neighbors;
	 }

	   //used for genotype rule set initialization
		public static int[] getRandomArea(int len){
			int x = Util.randomEngine.nextInt(2, input.length-2);
			int y = Util.randomEngine.nextInt(2, input[0].length-2);
			return getNeighbors(input,x,y); 
		}
	 
	 
	 /* applies the rule to generate output (which is returning average of rules values)*/
	 private static int applyRule(int input, Integer[] rule) {
		 int sum =0;
		 for (int r : rule) sum +=r;
		 return sum/rule.length;
	 }
	 
	/**
	 * Count the number of mismatch between input parameter and target
	 * @param input  the image to compare 
	 * @param target the image to compare with
	 * @param countDistance the boolean flag to set whether count distances or just number of mismatch
	 * @return
	 */
	public static int countError(int[][] input, int[][] target, boolean countDistance) {
		int error = 0;
		for(int i=0; i<input.length; i++){
			for(int j=0; j<input[i].length; j++){
				if (input[i][j] != target[i][j]) {
					if (!countDistance) {  // add up number of mismatches
						error++;
					} else { // add up the error distances 
						error += Math.abs(input[i][j] - target[i][j]); 
					}
				}
			}
		}
		return error;
	}
	
	/*
	 * calculates the root mean square value. useful for measuring the error rate
	 */
	public static double calculateRMS(int[][] input, int[][] target) {
		double squareSum = 0.0;
		int n =0;
		for (int i=0; i<input.length; i++) {
			for(int j=0; j<input[i].length; j++){
				squareSum += Math.pow(Math.abs(input[i][j] - target[i][j]),2); 
				n++;
			}
		}
		return Math.sqrt(squareSum/n);
	}

	/**
	 * reads a pgm file and return it as 2d array
	 * @param pathFilename
	 * @return
	 * @throws IOException
	 */
	public static int[][] readPGM(String pathFilename) throws IOException{
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(pathFilename));	
		
			String line = reader.readLine(); //the first line is header
			if (!"P2".equalsIgnoreCase(line)) {
				throw new IOException("File's header must match PGM signature: P2");
			}
			//skip comment lines
			while ((line = reader.readLine()) != null  && line.startsWith("#")) {
					continue;
			}
			//the second line is dimensions of PGM
			String[] dim = line.split("\\s");
			int width = Integer.parseInt(dim[0]);
			int height = Integer.parseInt(dim[1]);
			//the third line is max color value in pgm file
			maxColor = reader.readLine();
			
			int output[][] = new int[height][width];
			int counter = 0;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split("\\s");
				for(String part:parts) {
					output[counter/width][counter%width] = Integer.parseInt(part);
					counter++;
				}
			}
			return output;

		} finally {
			if (reader != null) reader.close(); 
		}
	}
	
	
	/**
	 * create pgm file using data from input parameter array
	 * @param input the 2d array
	 * @param pathFilename the output file
	 * @throws IOException
	 */
	public static void writePGM(int[][] input, String pathFilename) throws IOException {
		if (pathFilename == null) {
			throw new IOException("No output path filename is provided");
		}
		FileOutputStream stream = null;
		Writer writer = null;
		try {
			StringBuffer sb = new StringBuffer("P2"+nl+"#"+pathFilename+" Created by me." );
			sb.append(nl+input[0].length+" "+input.length);
			sb.append(nl+maxColor+nl);  //for now 15 but we should obtain highest value from parameter array
			for(int i=0; i<input.length; i++){
				for(int j=0; j<input[i].length; j++){
					sb.append(input[i][j]+" ");
				}
				sb.append(nl);
			}
	
			stream = new FileOutputStream(pathFilename);
			writer = new OutputStreamWriter(stream);
			writer.write(sb.toString());
		} finally {
			if (writer != null) writer.close();
			if (stream != null) stream.close();
		}
	}
	

}
