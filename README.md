# reportwriter
A rapid CSV generator based on annotation processing

A bean class defined like so :

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
	}

Can be converted into a CSV easily :

	public class Main {
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
			ReportWriter writer=new ReportWriter(Bean.class, list, ReportWriterConstants.APPEND, filePath, ",", true);
			try{
				writer.generateReport();
				System.out.println("Sample report generated at : "+filePath);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}

Result :

	First Name,Last Name,Age,Marks
	Max,Payne,25,34.9
	Frank,Roosevelt,21,74.9
	Theodore,Heski,55,84.9
	Wayne,Rooney,49,94.9
