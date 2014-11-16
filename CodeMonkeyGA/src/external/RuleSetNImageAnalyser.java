package external;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import edu.ccil.ec.tool.Util;

public class RuleSetNImageAnalyser {

		private static int neighorBoxlength = 5; //5x5 array since genome0 length is 25
	
		private static final String nl = System.getProperty("line.separator");
	
		private static String maxPgmColor; // used to read from input image and set into output image
	
		private static String[] filterFolders = new String[] {"despeckle","smooth","outlier", "snp"}; //we add snp to calculate RMS before filter
		private static String originalFolder = "original_pgm";
		private static String snpFolder = "snp";
		private static String outputFolder = "cm_output";
		
		//array of images is filled in initialization from directory list
		private static  String[]imageSet;
		
		
		private static final String prefix = "..\\..\\Experiments\\NewImageProcessing\\";
		
	
		private static int[][][] input;  //array of the 2D noisy image input array (used for training & testing)
		private static int[][][] target; //array of the 2D clean image target array (used for training & testing)


		private static int[] imageWidth;
		private static int[] imageHeight;
		private static HashMap<Integer,HashSet<Integer>>[] coordinates;

		//the number of training image from the set , the N first images are used
		private static int N=3;
		
		private static Random random = new Random();
	
		private static boolean initialized = false;
		
		private static void intialize() {
			try {
				input = new int[N][][];
				target = new int[N][][];
				imageWidth = new int[N];
				imageHeight = new int[N];
				coordinates = new HashMap[N];

				//list all files in the original_pgm folder
				File originalDir = new File (prefix+originalFolder);
				imageSet = originalDir.list();
				Arrays.sort(imageSet);
				
				// read the first N images from image set for training
				for (int n=0; n< N; n++) {
					input[n] = readPGM(prefix+snpFolder+"\\"+imageSet[n]);
					imageHeight[n] = input[n].length;
					imageWidth[n] = input[n][0].length;
					//fill coordinated with 1/16 of available coordinates in the image
					coordinates[n] = fillCoordinatesRandom(16,n);
					target[n] = readPGM(prefix+originalFolder+"\\"+imageSet[n]);
				}

				initialized = true;

			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return;
		}
	
		
		public static void main(String[] args) throws IOException {
			

			if (args != null && args.length ==2 ) {
				System.out.println("Given PGM:"+ args[0]);
				System.out.println("Reference PGM:"+ args[0]);
				
				int [][] denoised = readPGM(args[0]);
				int [][] reference = readPGM(args[1]);
				writePGM(denoised,args[0]+".output.pgm");
				writePGM(reference,args[1]+".output.pgm");
				
				System.out.println("RMS : "+calculateRMS(denoised, reference));
				return;
			}
			
			System.out.println("Optionaly you can pass the fullpath of denoised pgm and reference pgm to calculate RMS of denoised one.");
			//loop through original images and their filtered ones to calculate average RMS
			for (String filterFolder : filterFolders) {
				System.out.println("Comparing file from:"+ filterFolder);
				double TotalRMS = 0.0;
				File dir1 = new File(prefix+filterFolder);
				String[] filtered = dir1.list();
				double[] RMSs = new double[filtered.length];
				int counter =0;
				
				Arrays.sort(filtered);
				for (String fileName : filtered) {
					int [][] denoised = readPGM(prefix+filterFolder+"\\"+fileName);
					int [][] reference = readPGM(prefix+originalFolder+"\\"+fileName);
					RMSs[counter] =  calculateRMS(denoised, reference);
					TotalRMS +=RMSs[counter];
					System.out.println("RMS for file: "+fileName+" ="+RMSs[counter]);
					counter++;
				}
				double meanRMS = TotalRMS/RMSs.length;
				System.out.println("Average RMS for files in "+ filterFolder +" folder ="+meanRMS);
				double sigma = 0.0;
				for (double RMS : RMSs) {
					sigma += Math.pow(RMS - meanRMS, 2);
				}
				System.out.println("Standard Diviation of  RMS for files in "+ filterFolder +" folder ="+sigma/RMSs.length);

			}
			//args = new String[] {"41004.snp.gimp-ascii.pgm","41004.snp.gimp-raw.pgm"};

			return;			

		}
	
		
		/**
		 * evaluates the rule set and returns the error count
		 * @param ruleSet
		 * @return
		 */		
		public static double evaluate(ArrayList<Integer[]> ruleSet){
			if (!initialized) {
				intialize();
			}
			//for every coordinate in the map, get the neighbors, finds the best matched rule, apply the rule if there is match or keep the point unchanged
			//calculate error by comparing the output with point same coordinate of target, raise the error to power 2 and add to sum of squares, 
			//after the loop calculate Root Mean Squares
			double errorSum = 0.0;
			for (int n=0; n<N; n++) {
				//For every coordinate in the map
				for (Integer i : coordinates[n].keySet()) {
					for (Integer j : coordinates[n].get(i)) {
						//Get the neighborhood
						int[] neighbors = getNeighbors(input[n], i, j);
						//Find the best matching rule (if there is one)
						int bestRuleMatchIndex = getBestMatch(ruleSet, neighbors);
						//Set the output from input 
						int output = input[n][i][j];
						//If there is matching rule, apply it to update output
						if (bestRuleMatchIndex > -1 ) { // Match found, apply rule to get new value of output
							output = applyRule(output, ruleSet.get(bestRuleMatchIndex));
						}
						//Compare the output to point in target to calculate error
						int error = Math.abs(output - target[n][i][j]);
						errorSum += error;
							
					}
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
		
		

		public static void readApplyCalculateRMS4All(ArrayList<Integer[]> ruleSet) throws IOException {
			int n =0;
			double totalRMS = 0.0;
			double[] newRMSs = new double[imageSet.length];
			
			for (String image : imageSet) {
				//read all images one by one and apply the rule and calculate RMS before and after 
				int[][] _input = readPGM(prefix+snpFolder+"\\"+image);
				int[][] _target = readPGM(prefix+originalFolder+"\\"+image);
				if (n< N) {
					Util.log(image +" is training image");
				}
				Util.log("RMS for image "+image+" before rule set : "+calculateRMS(_input,_target));
				//apply
				int[][] _result = generateOutput(ruleSet, _input);
				//calculate=
				newRMSs[n] = calculateRMS(_result,_target);

				Util.log("RMS for image "+image+" after rule set : "+newRMSs[n]);
				totalRMS +=newRMSs[n];
				writePGM(_result, prefix+outputFolder+"\\"+image+".output.pgm");
				n++;
			}
			double meanRMS = totalRMS/imageSet.length;
			Util.log("Average of RMSs: "+meanRMS);
			double sigma = 0.0;
			for (double RMS : newRMSs ) {
				sigma += Math.pow(RMS - meanRMS,2);
			}
			Util.log("Standard Deviation of RMSs: "+sigma/newRMSs.length);

		}
		
		//is used for Genotype rule set initialization
		//pick a training image (0 to N ) randomly then pick a point in it randomly & get neighbors
		public static int[] getRandomArea(int len){
			if (!initialized) {
				intialize();
			}			
			int n = Util.randomEngine.nextInt(0,N);
			int x = Util.randomEngine.nextInt(2, input[n].length-2);
			int y = Util.randomEngine.nextInt(2, input[n][0].length-2);
			return getNeighbors(input[n],x,y); 
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
			DataInputStream reader2 = null;
			try {
				reader = new BufferedReader(new FileReader(pathFilename));	
				
				//for binary case
	            reader2 =  new DataInputStream(new BufferedInputStream(new FileInputStream(pathFilename)));
	            
	            String EOL =System.getProperties().getProperty("line.separator");
	            
				String line = reader.readLine(); //the first line is header
				reader2.skip((line+EOL).getBytes().length); // move the second reader forward
				
				boolean ascii = false;
				if ("P2".equalsIgnoreCase(line)) {
					ascii = true;
				} else if ("P5".equalsIgnoreCase(line)) {
					ascii = false;
				} else { // neither ascii nor raw
					throw new IOException(pathFilename+" File's header must match PGM signature: P2(ascii) or P5(raw)");
				}
				//skip comment lines
				while ((line = reader.readLine()) != null  && line.startsWith("#")) {
					reader2.skip((line+EOL).getBytes().length); // move the second reader forward
					continue;
				}
				// move the second reader one more byte for the the line read after comment line
				reader2.skip(("\n").getBytes().length); 

				//the second line is dimensions of PGM
				String[] dim = line.split("\\s");
				int imageWidth = Integer.parseInt(dim[0]);
				int imageHeight = Integer.parseInt(dim[1]);

				//the third line is max color value in PGM file
				maxPgmColor = reader.readLine();
				reader2.skip((line+EOL).getBytes().length); // move the second reader forward

				
				int output[][] = new int[imageHeight][imageWidth];
				int counter = 0;
				if (ascii) {
					while ((line = reader.readLine()) != null) {
					//P2 is spaced between numbers, use a splitter to get each point as it is
						String[] parts = line.split("\\s");
						for(String part:parts) {
							output[counter/imageWidth][counter%imageWidth] = Integer.parseInt(part);
							counter++;
						}
					}
				} else { //raw
					 // Now parse the file as binary data
					while(counter <imageHeight*imageWidth) {
						output[counter/imageWidth][counter%imageWidth] = reader2.readUnsignedByte();
						counter++;
							
						}
				}
					
				
				return output;
	
			} finally {
				if (reader != null) reader.close(); 
				if (reader2 != null) reader2.close(); 

			}
		}
		
		//Fills the coordinates Map with random I and associated random Js 
		//up to 1/ratio of image (divided between width and height) 
		private static HashMap<Integer,HashSet<Integer>> fillCoordinatesRandom(int ratio, int n) {
			//since it is a 2D space, each D get's square root of ratio
			int share = ((Double)Math.sqrt(ratio)).intValue();	
			HashMap<Integer,HashSet<Integer>> coordinates = new HashMap<Integer,HashSet<Integer>>();
			for (int i=0; i<imageHeight[n]/share;) {
				int I =random.nextInt(imageHeight[n]-2)+1; // not to include first and last points
				HashSet<Integer> Js = coordinates.get(I);
				if (Js == null) { // it's a new I that doesn't have J yet. If it wasn't we loop back to generate new I
					i++;
					Js = new HashSet<Integer>();
					//put the I and its J set into Map, the J set is filled in loop below
					coordinates.put(I, Js);
				
					for (int j=0; j<imageWidth[n]/share;) {
						int J = random.nextInt(imageWidth[n]-2)+1;
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

		
		// reads the given input scan it and find match and return the result
		private static int[][] generateOutput(ArrayList<Integer[]> ruleSet, int[][] _input) throws IOException{

			int[][] _result = new int[_input.length][_input[0].length];
					
			//scan the input, find the match rule, set the output value	
			for (int i=0; i<_input.length; i++) {
				for (int j=0; j<_input[i].length; j++){
					
					int	matchRule = getBestMatch(ruleSet,getNeighbors(_input,i,j));

					if (matchRule > -1) {
						_result[i][j] = 
						applyRule(_input[i][j], ruleSet.get(matchRule)); 
					} else {
						_result[i][j] = _input[i][j];
						
					}
				}
			}
			return _result;
			
		}

		
		
}
