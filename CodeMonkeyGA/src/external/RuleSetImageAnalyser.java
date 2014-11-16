package external;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import edu.ccil.ec.tool.Util;

public class RuleSetImageAnalyser {

		private static int neighorBoxlength = 5; //5x5 array since genome0 length is 25
	
		private static final String nl = System.getProperty("line.separator");
	
		private static String maxPgmColor; // used to read from input image and set into output image
		private static int imageWidth;
		private static int imageHeight;
	
		
		//array of images { {noisyImage-0, targetImage-0} , {....} }
		private static final String[][] imageSet = { 
			 //0
			{ "ImageJ_277095\\277095.SnP.ascii.pgm" , "ImageJ_277095\\277095.ascii.pgm" },
			 //1
			{ "ImageJ_113044\\113044_SnP.ascii.pgm" , "ImageJ_113044\\113044.ascii.pgm" },
			 //2
			{ "ImageJ_25098\\25098.ascii.SnP.pgm"   , "ImageJ_25098\\25098.ascii.pgm" },
		     //3
			{ "ImageJ_61060\\61060.ascii.SnP.pgm"   , "ImageJ_61060\\61060.ascii.pgm" },
		     //4
			{ "ImageJ_68077\\68077.ascii.SnP.pgm"   , "ImageJ_68077\\68077.ascii.pgm" },
		     //5
			{ "ImageJ_78019\\78019.ascii.SnP.pgm"   , "ImageJ_78019\\78019.ascii.pgm" },
		     //6
			{ "ImageJ_181091\\181091.ascii.SnP.pgm" , "ImageJ_181091\\181091.ascii.pgm" },
		     //7
			{ "ImageJ_353013\\353013.ascii.SnP.pgm" , "ImageJ_353013\\353013.ascii.pgm" },
			
			//8 to 12 animals
			{ "selected\\41004.snp.pgm" , "selected\\41004.pgm" },
			{ "selected\\41025.snp.pgm" , "selected\\41025.pgm" },
			{ "selected\\66075.snp.pgm" , "selected\\66075.pgm" },
			{ "selected\\69015.snp.pgm" , "selected\\69015.pgm" },
			{ "selected\\100080.snp.pgm" , "selected\\100080.pgm" },
			
			
	 
		};
		
		
		private static final String prefix = "..\\..\\Experiments\\ImageProcessing\\";
		protected static final String inputName  = prefix+imageSet[8][0]; 
		protected static final String targetName = prefix+imageSet[8][1]; 
		protected static final String outputName = inputName.substring(inputName.indexOf('\\')+1)+".output.pgm";
		
		private static HashMap<Integer,HashSet<Integer>> coordinates;
	
		private static int[][] input;  //the 2D noisy image input array (used for training & testing)
		private static int[][] target; //the 2D clean image target array (used for training & testing)
		private static int[][] result; //the 2D array to store output of applying rules
		
		private static Random random = new Random();
	
		static {
			try {
				input = readPGM(inputName);
				imageHeight = input.length;
				imageWidth = input[0].length;
				//fill coordinated with 1/9 of available coordinates in the image
				coordinates = fillCoordinatesRandom(9);
				target = readPGM(targetName);
					
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
		
		public static void main(String[] args) throws IOException {

			if (args == null || args.length <2 ) {
				System.out.println("pass the fullpath of denoised pgm and reference pgm to calculate RMS of denoised one.");
				args = new String[] {prefix+imageSet[0][0], prefix+imageSet[0][1]};
			}
			System.out.println("Given PGM:"+ args[0]);
			System.out.println("Reference PGM:"+ args[0]);
			
			int [][] denoised = readPGM(args[0]);
			int [][] reference = readPGM(args[1]);
			
			System.out.println("RMS : "+calculateRMS(denoised, reference));
			return;			

		}
	
		
		/**
		 * evaluates the rule set and returns the error count
		 * @param ruleSet
		 * @return
		 */		
		public static double evaluate(ArrayList<Integer[]> ruleSet){
			
			//for every coordinate in the map, get the neighbors, finds the best matched rule, apply the rule if there is match or keep the point unchanged
			//calculate error by comparing the output with point same coordinate of target, raise the error to power 2 and add to sum of squares, 
			//after the loop calculate Root Mean Squares
			double errorSum = 0.0;
			//For every coordinate in the map
			for (Integer i : coordinates.keySet()) {
				for (Integer j : coordinates.get(i)) {
					//Get the neighborhood
					int[] neighbors = getNeighbors(input, i, j);
					//Find the best matching rule (if there is one)
					int bestRuleMatchIndex = getBestMatch(ruleSet, neighbors);
					//Set the output from input 
					int output = input[i][j];
					//If there is matching rule, apply it to update output
					if (bestRuleMatchIndex > -1 ) { // Match found, apply rule to get new value of output
						output = applyRule(output, ruleSet.get(bestRuleMatchIndex));
					}
					//Compare the output to point in target to calculate error
					int error = Math.abs(output - target[i][j]);
					errorSum += error;
						
				}
			}
			return errorSum;
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
		
		
		public static void printStats() {
			Util.log("Input RMS:"+ calculateRMS(input, target));
			if (result == null) {
				System.err.println("Result array is not generated.");
				return;
			}
			Util.log("Result RMS:"+ calculateRMS(result, target));
			
		}

		public static void readApplyCalculateRMS4All(ArrayList<Integer[]> ruleSet) throws IOException {
			for (String[] image : imageSet) {
				//read
				input = readPGM(prefix+image[0]);
				target = readPGM(prefix+image[1]);
				Util.log("RMS for image "+image[0]+" before rule set : "+calculateRMS(input,target));
				//apply
				generateOutput(ruleSet);
				//calculate
				Util.log("RMS for image "+image[0]+" after rule set : "+calculateRMS(result,target));
				writePGM(result, image[0].substring(image[0].indexOf('\\')+1)+".output.pgm");
			}
			
		}
		
		//used for Genotype rule set initialization
		public static int[] getRandomArea(int len){
			int x = Util.randomEngine.nextInt(2, input.length-2);
			int y = Util.randomEngine.nextInt(2, input[0].length-2);
			return getNeighbors(input,x,y); 
		}
		 
		 
		/* applies the rule to generate output (it returns average of the rule values)*/
		private static int applyRule(int input, Integer[] rule) {
			int sum =0;
			for (int r : rule) sum +=r;
			return sum/rule.length;
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
		
		
		
		
		/**
		 * reads a PGM file and return it as 2d array
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
				int imageWidth = Integer.parseInt(dim[0]);
				int imageHeight = Integer.parseInt(dim[1]);

				//the third line is max color value in PGM file
				maxPgmColor = reader.readLine();
				
				int output[][] = new int[imageHeight][imageWidth];
				int counter = 0;
				while ((line = reader.readLine()) != null) {
					String[] parts = line.split("\\s");
					for(String part:parts) {
						output[counter/imageWidth][counter%imageWidth] = Integer.parseInt(part);
						counter++;
					}
				}
				return output;
	
			} finally {
				if (reader != null) reader.close(); 
			}
		}
		
		//Fills the coordinates Map with random I and associated random Js 
		//up to 1/ratio of image (divided between width and height) 
		private static HashMap<Integer,HashSet<Integer>> fillCoordinatesRandom(int ratio) {
			//since it is a 2D space, each D get's square root of ratio
			int share = ((Double)Math.sqrt(ratio)).intValue();	
			HashMap<Integer,HashSet<Integer>> coordinates = new HashMap<Integer,HashSet<Integer>>();
			for (int i=0; i<imageHeight/share;) {
				int I =random.nextInt(imageHeight-2)+1; // not to include first and last points
				HashSet<Integer> Js = coordinates.get(I);
				if (Js == null) { // it's a new I that doesn't have J yet. If it wasn't we loop back to generate new I
					i++;
					Js = new HashSet<Integer>();
					//put the I and its J set into Map, the J set is filled in loop below
					coordinates.put(I, Js);
				
					for (int j=0; j<imageWidth/share;) {
						int J = random.nextInt(imageWidth-2)+1;
						// if J is added successfully then increase j otherwise loop back to generating new J
						if (Js.add(J)) {
							j++;
						}
					}
				}
			}
			return coordinates;
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
				sb.append(nl+maxPgmColor+nl);  //for now 15 but we should obtain highest value from parameter array
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

		
		// reads the input[][], scan it and find match and fills the result
		private static void generateOutput(ArrayList<Integer[]> ruleSet) throws IOException{

			result = new int[input.length][input[0].length];
					
			//scan the input, find the match rule, set the output value	
			for (int i=0; i<input.length; i++) {
				for (int j=0; j<input[i].length; j++){
					
					int	matchRule = getBestMatch(ruleSet,getNeighbors(input,i,j));

					if (matchRule > -1) {
						result[i][j] = 
						applyRule(input[i][j], ruleSet.get(matchRule)); 
					} else {
						result[i][j] = input[i][j];
						
					}
				}
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
			generateOutput(ruleSet);
			writePGM(result, pathFilename);
		}
		
		
}
