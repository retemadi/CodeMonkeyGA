package edu.ccil.ec;

public class ESException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8350843931231454543L;

	//General Error Codes 
	public final static int NoPhenotype = 10;
	public static final int WindowMissing = 100;
	public final static int PSA_Missing = 200;
	public final static int SurvivalMissing = 300;
	public final static int VariationMissing = 400;
	public final static int TerminationMissing = 500;
	public final static int InvalidSize = 600;
	public final static int FitnessError = 700;
	public final static int SanityCheckError = 1001;


	
	
	/**
	 * Throws exception with specified error code and message
	 * @param errorCode
	 * @param message
	 */
	public ESException(int errorCode, String message) {
		super("(Error:"+errorCode+") "+ message);
	}
	
	/**
	 * Throws exception with the message and the default error code for sanity check 
	 * @param message
	 */
	public ESException(String message) {
		this(SanityCheckError,message);
	}


}
