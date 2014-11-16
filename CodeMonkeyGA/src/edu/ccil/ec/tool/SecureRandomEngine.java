package edu.ccil.ec.tool;


/**
 * This class uses java.security.SecureRandom class internally
 * but the implementation can be overridden to use other random engines such as HotBits, Ecuyer,org.uncommons.maths.random.MersenneTwisterRNG etc.
 * To change the Random engine in the framework, implement a subclass of RandomEngine
 * and register the new instance into your implementation of Evolution class.
 * 
 * @author Reza Etemadi 
 *
 */
public class SecureRandomEngine extends RandomEngine {

	private java.security.SecureRandom random;

	
	public SecureRandomEngine() {
		super();
		random = new java.security.SecureRandom();
	}

	public SecureRandomEngine(byte[] seed) {
		super();
		random = new java.security.SecureRandom(seed);
	}

	public SecureRandomEngine(long seed) {
		super();
		random = new java.security.SecureRandom(long2Bytes(seed));
	}
	
	// factory method
	@Override
	protected java.util.Random getRandom() {
		return random;
	}

	
	private final byte[] long2Bytes(long v) {
	    byte[] writeBuffer = new byte[ 8 ];

	    writeBuffer[0] = (byte)(v >>> 56);
	    writeBuffer[1] = (byte)(v >>> 48);
	    writeBuffer[2] = (byte)(v >>> 40);
	    writeBuffer[3] = (byte)(v >>> 32);
	    writeBuffer[4] = (byte)(v >>> 24);
	    writeBuffer[5] = (byte)(v >>> 16);
	    writeBuffer[6] = (byte)(v >>>  8);
	    writeBuffer[7] = (byte)(v >>>  0);

	    return writeBuffer;
	}

}
