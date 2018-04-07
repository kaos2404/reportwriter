package com.kaos.reportwriter;

/**
 * The interface for creating the report
 * @author Kaos
 * @version 1.5
 * @since 15 January 2018
 */
@SuppressWarnings("rawtypes")
public class ReportWriter {
	private Writer writer;
	/**
	 * Constructor
	 * @param baseClass The class which will be used to parse the annotations
	 * @param objList The map of the objects which will be written to the report
	 * @param writeMode The WriteMode value which specifies how to treat a report that is already present in the source folder (check WriteMode class)
	 * @param fileLocation The absolute path of the file that is to be generated
	 * @param separator The character which would be used to separate consecutive fields in one object
	 * @param printColumns Boolean value to set which would either remove or add column headers in all report files
	 */
	public ReportWriter(Class baseClass, java.util.Map<?,?> objList, int writeMode,
			String fileLocation, char separator, boolean printColumns) {
		writer=new Writer(baseClass,  objList, writeMode,
				fileLocation, separator, printColumns);
	}
	/**
	 * Constructor
	 * @param baseClass The class which will be used to parse the annotations
	 * @param objList The map of the objects which will be written to the report
	 * @param writeMode The WriteMode value which specifies how to treat a report that is already present in the source folder (check WriteMode class)
	 * @param fileLocation The absolute path of the file that is to be generated
	 * @param separator The string which would be used to separate consecutive fields in one object
	 * @param printColumns Boolean value to set which would either remove or add column headers in all report files
	 */
	public ReportWriter(Class baseClass, java.util.Map<?,?> objList, int writeMode,
			String fileLocation, String separator, boolean printColumns) {
		writer=new Writer(baseClass,  objList, writeMode,
				fileLocation, separator, printColumns);
	}
	/**
	 * Constructor
	 * @param baseClass The class which will be used to parse the annotations
	 * @param objList The list of the objects which will be written to the report
	 * @param writeMode The WriteMode value which specifies how to treat a report that is already present in the source folder (check WriteMode class)
	 * @param fileLocation The absolute path of the file that is to be generated
	 * @param separator The character which would be used to separate consecutive fields in one object
	 * @param printColumns Boolean value to set which would either remove or add column headers in all report files
	 */
	public ReportWriter(Class baseClass, java.util.ArrayList<?> objList, int writeMode,
			String fileLocation, char separator, boolean printColumns) {
		writer=new Writer(baseClass,  objList, writeMode,
				fileLocation, separator, printColumns);
	}
	/**
	 * Constructor
	 * @param baseClass The class which will be used to parse the annotations
	 * @param objList The list of the objects which will be written to the report
	 * @param writeMode The WriteMode value which specifies how to treat a report that is already present in the source folder (check WriteMode class)
	 * @param fileLocation The absolute path of the file that is to be generated
	 * @param separator The string which would be used to separate consecutive fields in one object
	 * @param printColumns Boolean value to set which would either remove or add column headers in all report files
	 */
	public ReportWriter(Class baseClass, java.util.ArrayList<?> objList, int writeMode,
			String fileLocation, String separator, boolean printColumns) {
		writer=new Writer(baseClass,  objList, writeMode,
				fileLocation, separator, printColumns);
	}
	/**
	 * To limit the size of each report file
	 * @param sizeInBytes Maximum size of each file (in bytes)
	 * @param nameAppender The appender to add to the file name before the file sequence number
	 * @param appenderLength The number of digits to be used for the sequence number
	 * @param tempFileLocation Absolute path of a temporary file that would be used
	 */
	public void setSizeLimit(long sizeInBytes, String nameAppender, int appenderLength, String tempFileLocation){
		writer.setSizeLimit(sizeInBytes, nameAppender, appenderLength, tempFileLocation);
	}
	/**
	 * Returns the absolute path of the reports that were generated
	 * @return ArrayList of string values of the file paths that have been generated
	 */
	public java.util.ArrayList<String> getFilePaths(){
		return writer.getFilePaths();
	}
	/**
	 * Method to generate the report for the current ReportWriter object
	 * @return Total number of lines that were written to the file(s)
	 * @throws Exception
	 */
	public void generateReport() throws Exception{
		writer.generateReport();
	}
	/**
	 * Set to true if a empty file is to be generated if the map/list is empty
	 * @param writeIfEmpty If true, empty file will be generated
	 */
	public void writeEmpty(boolean writeIfEmpty){
		writer.writeEmpty(writeIfEmpty);
	}
	/**
	 * Set the separator for successive bean objects
	 * @param recordSeparator String value that will be used for every new bean object
	 * @default Default value for separator is windows line separator (CRLF i.e. \r\n)
	 */
	public void setRecordSeparator(String recordSeparator){
		writer.setRecordSeparator(recordSeparator);
	}
	
	/**
	 * By default, in append mode if file is present, columns wont be added again. Set to true to force printing of columns
	 * @param printColumns
	 */
	public void alwaysPrintColumns(boolean printColumns){
		writer.alwaysPrintColumns(printColumns);
	}
}