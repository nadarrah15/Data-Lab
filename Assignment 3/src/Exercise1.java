import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Exercise1 {

	public static void main(String[] args) throws Exception{
		
		BufferedReader in = new BufferedReader(new FileReader(new File("Student.csv")));
		
		ArrayList<Record> map = readCsv(in);
	}
	
	static ArrayList<Record> readCsv(BufferedReader in) throws IOException {

		// holds column names
		ArrayList<String> colNames = new ArrayList<String>();
		for(String s : in.readLine().split(","))
			colNames.add(s);
		
		while(in.ready()){
			
			String[] input = in.readLine().split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
			
			Address address = new Address(input[colNames.indexOf("Address.Street")], input[colNames.indexOf("Address.City")], 
					input[colNames.indexOf("Address.State")], Integer.parseInt(input[colNames.indexOf("Address.Zip")]));
			
			Record record = new Record(Long.parseLong(input[colNames.indexOf("StudentID")]), Integer.parseInt(input[colNames.indexOf("Credits")]), 
					Double.parseDouble(input[colNames.indexOf("GPA")]), input[colNames.indexOf("Major")], address);
			
			
		}
		
	}
	
	static void readFile(ObjectInputStream stream) throws ClassNotFoundException, IOException {
		
		ArrayList<Map<String, Object>> arr = (ArrayList<Map<String, Object>>) stream.readObject();
		
		//print out the columns
		for(Map.Entry<String, Object> entry: arr.get(0).entrySet())
			System.out.print(entry.getKey() + " ");
		
		System.out.println();
		
		//print out the values
		for(int i = 0; i < arr.size(); i++){
			for(Map.Entry<String, Object> entry: arr.get(i).entrySet())
				System.out.print(entry.getValue() + " ");
			
			System.out.println();
		}
	}

	static void writeFile(ArrayList<Map<String, Object>> arr, ObjectOutputStream out) throws IOException{
		out.writeObject(arr);
		out.close();
	}
	
	/** method used to sum the column length of the data structure Map<Number, Map<String, Object>>
	 * pre: col exists as a column in the specified dict data structure, and dict.size() > 0
	 * post: return a sum of the lengths of the specified column
	 */
	static int columnLengthSum(String col, Map<Number, Map<String, Object>> dict){
		int sum = 0;
		
		for(Number key : dict.keySet()){
			sum += ((String) dict.get(key).get(col)).length();
		}
		
		return sum;
	}
	
	/** creates a sum of of all the values in a column
	 * pre: dict.size() > 0, and col is a numeric column that exists in dict
	 * post: returns the sum of all numeric values in a column
	 */
	static Number sum(Map<Number, Map<String, Object>> dict, String col){
		double sum = 0;
		
		for(Number key : dict.keySet()){
			sum += (Double) dict.get(key).get(col);
		}
		
		return sum;
	}
	
	static boolean isDouble(String str) {
		
		try{
			Double.parseDouble(str);
		} 
		catch (NumberFormatException e) {
			return false;
		}

		return true;
	}
	
	private class Record implements Serializable{
		
		private long StudentID;
		private int credits;
		private double GPA;
		private String major;
		private Address address;
		
		public Record(long StudentID, int credits, double GPA, String major, Address address){
			this.StudentID = StudentID;
			this.credits = credits;
			this.GPA = GPA;
			this.major = major;
			this.address = address;
		}
		
		public long getID(){
			return StudentID;
		}
		
		public int getCredits(){
			return credits;
		}
		
		public double getGPA(){
			return GPA;
		}
		
		public String getMajor(){
			return major;
		}
		
		public Address getAddress(){
			return address;
		}
	}
	
	private class Address{
		
		private String street, city, state;
		private int zip;
		
		public Address(String street, String city, String state, int zip){
			this.street = street;
			this.city = city;
			this.state = state;
			this.zip = zip;
		}
		
		public String toString(){
			return street + ", " + city + ", " + state + " " + zip;
		}
	}
}