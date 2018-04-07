package com.kaos.reportwriter.example;

import java.util.ArrayList;

import com.kaos.reportwriter.ReportWriter;
import com.kaos.reportwriter.WriteMode;
import com.kaos.reportwriter.WriteField;

public class Example {

	public static void main(String[] args) {
		String filePath="C:\\report.csv";
		if(args.length>0){
			filePath=args[0];
		}
		ArrayList<Bean> list=new ArrayList<Bean>();
		list.add(new Bean("Max", "Payne", 25, 34.9));
		list.add(new Bean("Frank", "Roosevelt", 21, 74.9));
		list.add(new Bean("Theodore", "Heski", 55, 84.9));
		list.add(new Bean("Wayne", "Rooney", 49, 94.9));
		list.add(new Bean("Will", "Smith", 55, 84.9));
		ReportWriter writer=new ReportWriter(Bean.class, list, WriteMode.APPEND, filePath, ",", true);
		try{
			writer.generateReport();
			System.out.println("Sample report generated at : "+filePath);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}

class Bean{
	@WriteField(columnName="First Name", intoColumn=1)
	private String firstName;
	@WriteField(columnName="Last Name", intoColumn=2)
	private String lastName;
	@WriteField(columnName="Age", intoColumn=3)
	private int age;
	@WriteField(columnName="Marks", intoColumn=4)
	private double marks;
	public Bean(String firstName, String lastName, int age, double marks) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.marks = marks;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public void setMarks(double marks) {
		this.marks = marks;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public int getAge() {
		return age;
	}
	public double getMarks() {
		return marks;
	}
}