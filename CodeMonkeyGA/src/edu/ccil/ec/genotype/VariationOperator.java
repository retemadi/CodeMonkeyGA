package edu.ccil.ec.genotype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)

/**
 * This Annotation is used to identify what methods in a Genome class are variation operators
 * This is useful for the Variation Wizard UI to list all available diversification methods.
 * 
 * @author Reza Etemadi
 *
 */
public @interface VariationOperator {
	/**
	 * the friendly name of operator ,useful for UI  
	 */
	public String friendlyName();

	/**
	 * the type of genome that this operator can operate on 
	 */
	public String type() default "Collection";
	
	
}
