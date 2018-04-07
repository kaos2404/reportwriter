package com.kaos.reportwriter;
/**
 * The annotation definition
 * @author Kaos
 * @version 1.0
 * @since 15 June 2017
 */
import java.lang.annotation.*;
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WriteField{
	/**
	 * The name of the column
	 * @return
	 */
	public String columnName();
	/**
	 * The order of the column from the left most
	 * @return
	 */
	public int intoColumn();
}
