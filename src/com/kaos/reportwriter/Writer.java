package com.kaos.reportwriter;
@SuppressWarnings("rawtypes")
class Writer {
	private java.util.ArrayList<String> filePath;
	private java.util.TreeMap<Integer, Object> output;
	private java.util.TreeMap<Integer, String> columns;
	private java.util.ArrayList<Object> objList;
	private int writeMode;
	private java.io.File file;
	private boolean printColumns;
	private boolean wrote;
	private String separator;
	private boolean sizeLimit;
	private long sizeInBytes;
	private String nameAppender;
	private int appenderLength;
	private String tempFileLocation;
	private boolean writeIfEmpty;
	private String recordSeparator;
	@SuppressWarnings("unused")
	private int lineCount;
	private Class baseClass;
	private boolean forcePrintColumns;
	protected Writer(Class baseClass, java.util.Map<?,?> objList, int writeMode,
			String fileLocation, char separator, boolean printColumns) {
		this.baseClass=baseClass;
		this.filePath=new java.util.ArrayList<String>();
		this.sizeLimit=false;
		this.printColumns=printColumns;
		this.wrote=false;
		this.output=new java.util.TreeMap<>();
		this.columns=new java.util.TreeMap<>();
		this.objList = (java.util.ArrayList<Object>) parseHashMap(objList);
		this.writeMode = writeMode;
		this.separator=String.valueOf(separator);
		this.file = new java.io.File(fileLocation);
		this.recordSeparator="\r\n";
		this.forcePrintColumns=false;
	}
	protected Writer(Class baseClass, java.util.Map<?,?> objList, int writeMode,
			String fileLocation, String separator, boolean printColumns) {
		this.baseClass=baseClass;
		this.filePath=new java.util.ArrayList<String>();
		this.sizeLimit=false;
		this.printColumns=printColumns;
		this.wrote=false;
		this.output=new java.util.TreeMap<>();
		this.columns=new java.util.TreeMap<>();
		this.objList = (java.util.ArrayList<Object>) parseHashMap(objList);
		this.writeMode = writeMode;
		this.separator=separator;
		this.file = new java.io.File(fileLocation);
		this.recordSeparator="\r\n";
		this.forcePrintColumns=false;
	}

	@SuppressWarnings("unchecked")
	protected Writer(Class baseClass, java.util.ArrayList<?> objList, int writeMode,
			String fileLocation, char separator, boolean printColumns) {
		this.baseClass=baseClass;
		this.sizeLimit=false;	
		this.filePath=new java.util.ArrayList<String>();
		this.printColumns=printColumns;
		this.output=new java.util.TreeMap<>();
		this.columns=new java.util.TreeMap<>();
		this.objList = (java.util.ArrayList<Object>) objList;
		this.writeMode = writeMode;
		this.separator=String.valueOf(separator);
		this.file = new java.io.File(fileLocation);
		this.recordSeparator="\r\n";
		this.forcePrintColumns=false;
	}

	@SuppressWarnings("unchecked")
	protected Writer(Class baseClass, java.util.ArrayList<?> objList, int writeMode,
			String fileLocation, String separator, boolean printColumns) {
		this.baseClass=baseClass;
		this.sizeLimit=false;	
		this.filePath=new java.util.ArrayList<String>();
		this.printColumns=printColumns;
		this.output=new java.util.TreeMap<>();
		this.columns=new java.util.TreeMap<>();
		this.objList = (java.util.ArrayList<Object>) objList;
		this.writeMode = writeMode;
		this.separator=separator;
		this.file = new java.io.File(fileLocation);
		this.recordSeparator="\r\n";
		this.forcePrintColumns=false;
	}
	protected void setRecordSeparator(String recordSeparator){
		this.recordSeparator=recordSeparator;
	}
	protected java.util.ArrayList<String> getFilePaths(){
		return filePath;
	}
	private java.util.ArrayList<Object> parseHashMap(java.util.Map<?, ?> hashMap) {
		java.util.ArrayList<Object> arrayList=new java.util.ArrayList<>();
		for(Object obj : hashMap.keySet()){
			arrayList.add(hashMap.get(obj));
		}
		return arrayList;
	}
	private boolean parse(){
		boolean check=false;
		if(wrote==true){
			throw new RuntimeException("Report already written to : "+file.getAbsolutePath());
		}
		if(writeMode!=com.kaos.reportwriter.WriteMode.OVERWRITE && writeMode!=com.kaos.reportwriter.WriteMode.APPEND && writeMode!=com.kaos.reportwriter.WriteMode.PROTECTIVE){
			throw new RuntimeException("Error in writeMode. Please select writeMode from Constants");
		}
		if(writeMode==com.kaos.reportwriter.WriteMode.PROTECTIVE && file.exists()){
			throw new RuntimeException("File already present. Delete and re-run the utility : "+file.getAbsolutePath());
		}
		if(writeMode==com.kaos.reportwriter.WriteMode.OVERWRITE && file.exists() && !file.delete()){
			throw new RuntimeException("File could not be deleted to overwrite");
		}
		if(writeIfEmpty && objList.size()==0){
			java.lang.reflect.Field[] fields=baseClass.getDeclaredFields();
			java.lang.annotation.Annotation annotation=null;
			for(java.lang.reflect.Field f : fields){
				annotation=f.getAnnotation(com.kaos.reportwriter.WriteField.class);
				if(annotation!=null){
					if(columns.get(((com.kaos.reportwriter.WriteField) annotation).intoColumn())!=null){
						throw new RuntimeException("Duplicate value set to column for the annotation : "+annotation.toString());
					}
					else{
						columns.put(((com.kaos.reportwriter.WriteField) annotation).intoColumn(), ((com.kaos.reportwriter.WriteField) annotation).columnName());
					}
				}
			}
		}
		if(objList instanceof java.util.ArrayList && objList.size()>0){
			for(Object obj : objList){
				if(!obj.getClass().getName().equals(baseClass.getName())){
					throw new RuntimeException("Cannot parse objects of multiple classes at the same time");
				}
			}
			java.lang.reflect.Field[] fields=baseClass.getDeclaredFields();
			java.lang.annotation.Annotation annotation=null;
			for(java.lang.reflect.Field f : fields){
				annotation=f.getAnnotation(com.kaos.reportwriter.WriteField.class);
				if(annotation!=null){
					if(columns.get(((com.kaos.reportwriter.WriteField) annotation).intoColumn())!=null){
						throw new RuntimeException("Duplicate value set to column for the annotation : "+annotation.toString());
					}
					else{
						columns.put(((com.kaos.reportwriter.WriteField) annotation).intoColumn(), ((com.kaos.reportwriter.WriteField) annotation).columnName());
					}
				}
			}
			int count=1;
			for(Object obj : objList){
				output.put(count++, obj);
			}
			check=true;
		}
		return check;
	}
	protected void generateReport() throws Exception{
		String line="";
		String columnLine="";
		java.lang.annotation.Annotation annotation=null;
		if(!parse()){
			return;
		}
		java.io.PrintWriter printWriter=null;
		if(printColumns) {
			for(Integer i : columns.keySet()){
				columnLine=columnLine+columns.get(i)+separator;
			}
			columnLine=columnLine.substring(0, columnLine.length()-separator.length());
		}

		if(!sizeLimit){
			String k="";
			printWriter=new java.io.PrintWriter(new java.io.BufferedWriter(new java.io.FileWriter(file, (writeMode==com.kaos.reportwriter.WriteMode.APPEND))));
			filePath.add(file.getAbsolutePath());
			if(printColumns) {
				if(!(writeMode==com.kaos.reportwriter.WriteMode.APPEND && file.length()>=0) || forcePrintColumns){
					printWriter.print(columnLine);
					printWriter.print(recordSeparator);
				}
			}
			for(Integer row : output.keySet())
			{
				line="";
				for(Integer col : columns.keySet()){
					for(java.lang.reflect.Field f : output.get(row).getClass().getDeclaredFields()){
						annotation = f.getAnnotation(com.kaos.reportwriter.WriteField.class);
						f.setAccessible(true);
						if(annotation!=null && ((com.kaos.reportwriter.WriteField) annotation).intoColumn()==col){
							k=String.valueOf(f.get(output.get(row)));
							if(f.get(output.get(row))==null || k.length()==0){
								line=line+""+separator;
								lineCount++;
							}
							else{
								line=line+k+separator;
								lineCount++;
							}
						}
					}
				}
				line=line.substring(0, line.length()-separator.length());
				printWriter.print(line);
				printWriter.print(recordSeparator);
				printWriter.flush();
			}
		}
		else{
			boolean roll=true;
			int count=0;
			String fileName=file.getAbsolutePath();
			java.io.File tempFile=new java.io.File(tempFileLocation);
			java.io.PrintWriter tempWriter=new java.io.PrintWriter(tempFile);
			line="blank";
			for(Integer row : output.keySet()){
				if(roll){
					count++;
					if(!line.equals("blank")){
						tempWriter.close();
						printWriter.close();
					}
					java.io.File appendFile=new java.io.File(fileName.substring(0, fileName.lastIndexOf("."))+nameAppender+format(count)+fileName.substring(fileName.lastIndexOf(".")));
					if(writeMode==com.kaos.reportwriter.WriteMode.PROTECTIVE && appendFile.exists()){
						for(String s : filePath){
							new java.io.File(s).delete();
						}
						throw new RuntimeException("File already present. Delete the file and rerun the utility : "+appendFile.getAbsolutePath());
					}
					printWriter=new java.io.PrintWriter(new java.io.BufferedWriter(new java.io.FileWriter(appendFile, (writeMode==com.kaos.reportwriter.WriteMode.APPEND))));
					filePath.add(appendFile.getAbsolutePath());
					if(tempFile.exists()){
						tempFile.delete();
					}
					tempFile.createNewFile();
					tempWriter=new java.io.PrintWriter(tempFile);
					if(printColumns) {
						if(!(writeMode==com.kaos.reportwriter.WriteMode.APPEND && file.length()>=0) || forcePrintColumns){
							tempWriter.print(columnLine);
							tempWriter.print(recordSeparator);
							printWriter.print(columnLine);
							printWriter.print(recordSeparator);
							lineCount++;
						}
					}
					if(!line.equals("blank")){
						tempWriter.print(line);
						tempWriter.print(recordSeparator);
						tempWriter.flush();
						printWriter.print(line);
						printWriter.print(recordSeparator);
						printWriter.flush();
						lineCount++;
						line="";
					}
					roll=false;
				}

				line="";
				String k="";
				for(Integer col : columns.keySet()){
					for(java.lang.reflect.Field f : output.get(row).getClass().getDeclaredFields()){
						annotation = f.getAnnotation(com.kaos.reportwriter.WriteField.class);
						f.setAccessible(true);
						if(annotation!=null && ((com.kaos.reportwriter.WriteField) annotation).intoColumn()==col){
							k=String.valueOf(f.get(output.get(row)));
							if(f.get(output.get(row))==null || k.length()==0){
								line=line+""+separator;
								lineCount++;
							}
							else{
								line=line+k+separator;
								lineCount++;
							}
						}
					}
				}
				line=line.substring(0, line.length()-separator.length());
				tempWriter.print(line);
				tempWriter.print(recordSeparator);
				tempWriter.flush();
				if(tempFile.length()<=sizeInBytes){
					lineCount++;
					printWriter.print(line);
					printWriter.print(recordSeparator);
					printWriter.flush();
				}
				else{
					roll=true;
				}
			}
			if(roll){
				count++;
				java.io.File appendFile=new java.io.File(fileName.substring(0, fileName.lastIndexOf("."))+nameAppender+format(count)+fileName.substring(fileName.lastIndexOf(".")));
				printWriter=new java.io.PrintWriter(new java.io.BufferedWriter(new java.io.FileWriter(appendFile, (writeMode==com.kaos.reportwriter.WriteMode.APPEND))));
				filePath.add(appendFile.getAbsolutePath());
				if(printColumns) {
					if(!(writeMode==com.kaos.reportwriter.WriteMode.APPEND && file.length()>=0) || forcePrintColumns){
						printWriter.print(columnLine);
						printWriter.print(recordSeparator);
					}
				}
				printWriter.print(line);
				printWriter.print(recordSeparator);
				printWriter.flush();
				roll=false;
			}
			if(tempFile.exists()){
				tempFile.delete();
				tempWriter.close();
			}
		}
		printWriter.close();
		wrote=true;
	}
	protected void setSizeLimit(long sizeInBytes, String nameAppender, int appenderLength, String tempFileLocation){
		this.sizeLimit=true;
		this.sizeInBytes=sizeInBytes;
		this.nameAppender=nameAppender;
		this.appenderLength=appenderLength;
		this.tempFileLocation=tempFileLocation;
	}
	private String format(int count){
		String k=Integer.toString(count);
		for(int i=k.length();i<appenderLength;i++){
			k="0"+k;
		}
		return k;
	}
	protected void writeEmpty(boolean writeIfEmpty){
		this.writeIfEmpty=writeIfEmpty;
	}
	@Override
	protected void finalize() throws Throwable {
		new java.io.File(tempFileLocation).delete();
		super.finalize();
	}
	protected void alwaysPrintColumns(boolean forcePrintColumns) {
		this.forcePrintColumns=forcePrintColumns;
	}
}
