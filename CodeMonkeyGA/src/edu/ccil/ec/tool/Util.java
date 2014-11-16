package edu.ccil.ec.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import edu.ccil.ec.Evolution;
import edu.ccil.ec.Phenotype;
import edu.ccil.ec.Population;
import edu.ccil.ec.Population.Format;
import edu.ccil.ec.TerminationStrategy;
import edu.ccil.ec.genotype.ArrayListGenotype;

/**
 * This is a utility class that provides generic variation operators
 * @author R.E.
 *
 */
public class Util {
	
	/** The random engine used in the framework. It is set to RandomEngine() by default
	 * An instance of RandomEngine or its subclass is registered inside Evolution class. 
	 * This field is being set inside Evolution and during registration. 
	 */
	public static RandomEngine randomEngine = new RandomEngine();
	

	public static final Logger logger = Logger.getLogger(Util.class.getName());
	public static final Logger loggerCvs = Logger.getLogger(Util.class.getName()+"csv");

	
	static private FileHandler fileTxt; // is used for logging messages to file
	static private FileHandler fileCvs; // is used for logging comma separated values to file

	static String fname;
	
	static {
	
		logger.setLevel(Level.INFO);
		fname = "PGAG2Log_"+ java.text.DateFormat.getDateInstance().format(new java.util.Date())+".txt";
		try {
			fileTxt = new FileHandler(fname);
			fileCvs = new FileHandler(fname+".csv");
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		fileTxt.setFormatter(new LogFormatter());  //for more detail use: SimpleFormatter()
		logger.addHandler(fileTxt);
		//we disable using parent logger which is a console logger, to avoid using default formatting & add our own
		//Alternatively we could set logger.getParent().getHandlers()[0].setFormatter(new LogFormatter());
		logger.setUseParentHandlers(false);
		ConsoleHandler console = new ConsoleHandler();	
		console.setFormatter(new LogFormatter());
		logger.addHandler(console);
/*		
		for (Object it: System.getProperties().keySet()){
			System.out.println( it +"="+System.getProperty((String)it));
		}
*/		
		logger.info("Log File   : "+ fname); 
		logger.info("Located at : "+ System.getProperty("user.dir"));


		
		fileCvs.setFormatter(new LogFormatter());
		loggerCvs.addHandler(fileCvs);
		loggerCvs.setUseParentHandlers(false); // this will stop logging to console
		loggerCvs.info("Generation,Best Fitness,Mean Fitness,Sigma");
		
		
	}
	
	/**
	 * runs an external program and pass the phenome data to it as part of the
	 * execution command assuming the external program accepts string inputs
	 * @param command The name of the external program or script 
	 * @param workDir (optional) the work directory that external process should run in 
	 * @param phenome the Phenotype instance to be send as parameter to external process 
	 * @param form The format for sending phenotype content to the external program 
	 * @param timeout The wait time for external process to finish in millisecond
	 * @return the output of the command execution as a HashMap of name=value pairs or null if it fails
	 */
	public static HashMap<String,String> runExternalCommand(String command, String workDir, Phenotype<?,?> phenome, Format form ,int timeout ){
		 try {
			String phenomeStr = (form == Format.XML?phenome.toXML():phenome.toJSON());
			phenomeStr = phenomeStr.replaceAll("[\\x00-\\x1f]", ""); //remove all control characters
			//run the command execution in a separate thread
			//ProcessRunner pr = new ProcessRunner(command,phenomeStr);
			//pr.setWorkDir(workDir);
			ProcessExec pr = new ProcessExec(command);  //phenome to be passed
			
			
			pr.start();
			int time = 0; 
			while (!pr.isDone() && time <timeout)  {
				pr.join(1000); //1 ms
				time +=1000;
				System.out.println("waiting ..."+ time);
			}

			
			// process still going on? kill it!
			if(!pr.isDone())  {
				System.err.println("Destroying process " + pr);
				pr.abort();
	        }
			StringBuffer response = pr.getResponse();
			log("response :"+ response.toString());
			switch(form) {
	    	case XML:
	    		return parseXMLExternalResult(response);
	    	case JSON:
	    		return parseJSONExternalResult(response);
	    	default:
	    		log("Invalid format name:"+form+". No parsing is possible");
			}
			
		} catch (Exception e) {  //IOException 
			log("Failed to parse the result of External prgram ");
			e.printStackTrace();
		}
		return null;
	}

	
	static class ProcessExec extends Thread {
		
		String cmd;
		StringBuffer output;
		boolean done;
		Process p;
		ProcessExec(String command) {
			cmd = command;
		}
		
		public void run()   {
		    try {
				String line;
				p = Runtime.getRuntime().exec(cmd); 
				BufferedReader bri = new BufferedReader
				  					(new InputStreamReader(p.getInputStream()));
				BufferedReader bre = new BufferedReader
				  					(new InputStreamReader(p.getErrorStream()));
				line = bri.readLine();
				while ( line != null && line.length() > 0) {
					output.append(line);
					line = bri.readLine();
				}
				
				bri.close();
				while ((line = bre.readLine()) != null) {
					System.out.println(line);
				}
				bre.close();
				p.waitFor();
				System.out.println("Done.");
		    }
		    catch (Exception err) {
		    	err.printStackTrace();
	        } 
		    finally {
	            // any cleanup goes here
	            done = true;
	        }
		}
		
        StringBuffer getResponse() {
        	return output;
        }
        
        boolean isDone()  {
            return done;
        }
        
        void abort()   {
            if(! isDone()) {
               // any clean up goes here
               p.destroy();
            }
         }
        
	}
	
	
	static class ProcessRunner extends Thread  {
		ProcessBuilder pb;
	    Process pr;
	    boolean done = false;
		StringBuffer sb = new StringBuffer();
	    int exitValue = -100;

        ProcessRunner(String... args )  {
           super("ProcessRunner " + args); 
           pb = new ProcessBuilder(args);
        }

        public void run()   {
        	try   {
        		pr = pb.start();

	            // Doing the actual work
				BufferedReader buf = new BufferedReader( new InputStreamReader( pr.getInputStream() ) ) ;
				String line;
				while ( (  line = buf.readLine() )  != null )
				{
					System.out.println(line) ;
					sb.append(line);
				}
				exitValue = pr.waitFor();
				pr.getInputStream().close();
				pr.getOutputStream().close();
				pr.getErrorStream().close();

				// waiting for the process to complete
				exitValue = pr.waitFor();
           }catch(Exception e) {
              System.err.println(e.getMessage());
           }finally {
              // any cleanup goes here
              done = true;
           }
        }

        void setWorkDir(String workDir) {
			if (workDir != null) {
				pb.directory(new File(workDir));
			}
        }

        StringBuffer getResponse() {
        	return sb;
        }
        
        int exitValue() throws IllegalStateException  {
           if(pr != null)  {
              return pr.exitValue();
           }         
           throw new IllegalStateException("Process not started yet");
        }

        boolean isDone()  {
           return done;
        }

        void abort()   {
           if(! isDone()) {
              // any clean up goes here
              pr.destroy();
           }
        }
	}
	
	

	/**
	 * Parses the XML result of calling external program, returns a HashMap that contains name=value pairs
	 * the expected XML format of the result is (XML tag names are case sensitive)
	 * <?xml version=\"1.0\"?>
	 * <XML>
	 * <result name='someName' value='someValue' />
	 * .....
	 * <result name='someName' value='someValue' />
	 * </XML> 
	 * @param sb the stringbuffer collected from external program
	 * @return
	 */
	private static HashMap<String,String> parseXMLExternalResult(StringBuffer sb) throws Exception  {
		HashMap<String,String> externalData = new HashMap<String,String>();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(sb.toString()));

        Document doc = db.parse(is);
        NodeList nodes = doc.getElementsByTagName("result");

        // iterate through data
        for (int i = 0; i < nodes.getLength(); i++) {
           Element element = (Element) nodes.item(i);
           externalData.put(element.getAttribute("name"), element.getAttribute("value"));
        }
		return externalData;
	}


	/**
	 * Parses the JSON result of calling external program, returns a HashMap that contains name=value pairs
	 * the expected JSON format of the result is:
	 * {
	 * "somename" : "somevalue",
	 * ...
	 * "somename" : "somevalue"
	 * }
	 *  
	 * @param sb the stringbuffer collected from external program
	 * @return
	 */
	private static HashMap<String,String> parseJSONExternalResult(StringBuffer sb) {
		HashMap<String,String> externalData = new HashMap<String,String>();
    	String result  = sb.substring(1,sb.length()-1).replaceAll("[\"]","");
    	String[] elements = result.split("[,]");
    	for (int i=0 ; i <elements.length ; i++) {
    		String[] data = elements[i].split("[:]");
    		externalData.put(data[0].trim(), data[1].trim());
    	}
		return externalData;
	}

	/**
	 * returns the shorter arraylist between two inputs
	 * @param g0
	 * @param g1
	 * @return
	 */
	private static <G> ArrayListGenotype<G> shorter(ArrayListGenotype<G> g0, ArrayListGenotype<G> g1){
		return (g0.size()<g1.size()?g0:g1);
	}


	/**
	 * performs one point crossover between two given mutable arraylist genotypes.
	 * the crossover probability should be handled in the calling code
	 * no sublevel crossover is being called on the crossover point
	 * @param <G>
	 * @param p0
	 * @param p1
	 * @return
	 */
	public static <G> void OnePointCrossover(ArrayListGenotype<G> g0, ArrayListGenotype<G> g1) {
		if (g0.size() != g1.size()) {
			OnePointCrossDynamic(g0,g1);
			return;
		}
		int crossPoint = randomEngine.pick(g0.size()-1)+1; // size-1 location for crossover ,starting from 1
	    //swap them from crossover point onward
		for(int i=crossPoint; i< g0.size(); i++){
			G temp = g0.get(i);
			g0.set(i, g1.get(i));
			g1.set(i, temp);
		}
		return;
	}
	
	// performs one point crossover between two non-equal size lists
	private static <G> void OnePointCrossDynamic(ArrayListGenotype<G> g0, ArrayListGenotype<G> g1) {
		ArrayListGenotype<G> shorter = shorter(g0,g1);
		int crossPoint = randomEngine.pick(shorter.size()-1)+1; // size-1 location for crossover ,starting from 1
		for(int i=crossPoint; i< shorter.size(); i++){
			if ( i<g0.size() && i<g1.size() ) {
				G temp = g0.get(i);
				g0.set(i, g1.get(i));
				g1.set(i, temp);

			} else if ( i<g0.size() && i >=g1.size()) {
				G temp = g0.get(i);
				g0.set(i, null);
				g1.add(temp);
			} else if ( i>=g0.size() && i<g1.size()) {
				G temp = g1.get(i);
				g0.add(temp);
				g1.set(i, null);
			}
		}
		//eliminates all null
		g0.removeAll(Collections.singleton(null));
		g1.removeAll(Collections.singleton(null));

		return;
		
	}
	
	/**
	 * performs two point crossover between two given mutable arraylist genotypes.
	 * the crossover probability should be handled in the calling code
	 * @param <G>
	 * @param p0
	 * @param p1
	 * @return
	 */
	public static <G> void TwoPointCrossover(ArrayListGenotype<G> g0, ArrayListGenotype<G> g1) {
		if (g0.size() != g1.size()) {
			TwoPointCrossDynamic(g0,g1);
			return;
		}
		if (g0.size() < 3) {  //maximum one corss point can be found
			OnePointCrossover(g0,g1);
			return;
		}
	
		int crossPoint1 = randomEngine.pick(g0.size()-2) + 1; // size-2 location for the first point starting from 1, (reserving at least one for second point)
		int crossPoint2 = crossPoint1 +1 + randomEngine.pick((g0.size()-1)-crossPoint1); // between firstpoint+1 and length-1  

		//swap them within the 2 cross points
		for (int i=crossPoint1; i<crossPoint2; i++) {
			G temp = g0.get(i);
			g0.set(i, g1.get(i));
			g1.set(i, temp);
		}
		
		return;
	}

	//performs two point crossover between two non-equal size lists (choose the segment based on length of shorter one)
	private static <G> void TwoPointCrossDynamic(ArrayListGenotype<G> g0, ArrayListGenotype<G> g1) {
		ArrayListGenotype<G> shorter = shorter(g0,g1);
		if (shorter.size()<3) { // maximum one cross point can be found
			OnePointCrossDynamic(g0,g1);
			return;
		}
		int crossPoint1 = randomEngine.pick(shorter.size()-2) + 1; // size-2 location for the first point starting from 1, (reserving at least one for second point)
		int crossPoint2 = crossPoint1 +1 + randomEngine.pick((shorter.size()-1)-crossPoint1); // between firstpoint+1 and length-1  

		//swap them within the 2 cross points
		for (int i=crossPoint1; i<crossPoint2; i++) {
			G temp = g0.get(i);
			g0.set(i, g1.get(i));
			g1.set(i, temp);
		}

	}
	
	/**
	 * performs uniform crossover between two given genotypes.
	 * the crossover probability should be handled in the calling code 
	 * @param <G>
	 * @param p0
	 * @param p1
	 * @param p : the probability used for swapping each position 
	 * @return
	 */
	public static <G> void UniformCrossover(ArrayListGenotype<G> g0, ArrayListGenotype<G> g1, double p) {
		if (g0.size() != g1.size()) {
			UniformCrossDynamic(g0,g1,p);
			return;
		}
		for (int i=0; i<g0.size(); i++) {
			if (randomEngine.nextDouble() < p) { //swap
				G temp = g0.get(i);
				g0.set(i, g1.get(i));
				g1.set(i, temp);
			}
		}
		return;
	}
		
	//performs uniform crossover between two non-equal size lists (up to the length of shorter one)
	public static <G> void UniformCrossDynamic(ArrayListGenotype<G> g0, ArrayListGenotype<G> g1, double p) {
		ArrayListGenotype<G> shorter = shorter(g0,g1);
		for (int i=0; i<shorter.size(); i++) {
			if (randomEngine.nextDouble() < p) { //swap
				G temp = g0.get(i);
				g0.set(i, g1.get(i));
				g1.set(i, temp);
			}
		}
	
	}
	
	
	/**
	 * A utility method to create a new instance of a given object.
	 * It expects no-arg constructor in the Class of the given obj 
	 * @param <T>
	 * @param t
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T makeNewInstanceOfType(T t){
		try {
			return (T) t.getClass().newInstance();   //casting is suppressed
		}catch (Exception e) {
			try {
		      t.getClass().getConstructor(); //check if no_args constructor exist
	        } catch (NoSuchMethodException nsme) {
	        	throw new AssertionError("Class " + t.getClass().getName() + " must have a no_args constructor");
	        }
	        e.printStackTrace();  //in other cases print stack trace
		}
		return null;
	}

	/**
	 * the utility function to log the information at INFO level
	 * @param o
	 */
	public static void log(Object o) {
		log(Level.INFO, o);
	}

	/**
	 * the utility function to log the information
	 * @param l log level
	 * @param o object to be logged
	 */
	public static void log(Level l,Object o) {
		logger.log(l, o.toString());
	}
	
	/**
	 * the utility function to log the cvs information
	 * @param o object to be logged
	 */
	public static void logCvs(Object o) {
		loggerCvs.log(Level.INFO, o.toString());
	}

	public static void logFooter(Population<?> current, TerminationStrategy<?> termStrategy){
		log("Terminated, Total time: "+(System.currentTimeMillis()-Evolution.startTime)/1000+"(s)");
		log(termStrategy.getMessage());
		log("Fittest:" +current.getFittest(1).get(0));
		log("CVS File "+fname+".csv is available at : "+System.getProperty("user.dir"));
	}
}
