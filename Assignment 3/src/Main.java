import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Main {

	public static void main(String[] args) throws Exception{
		
		BufferedReader in = new BufferedReader(new FileReader(new File("Student.csv")));
		
		ArrayList<Record> map = readCsv(in);
	}
	
	static ArrayList<Record> readCsv(BufferedReader in) throws IOException {

		// holds column names
		ArrayList<String> colNames = new ArrayList<String>();
		for(String s : in.readLine().split(","))
			colNames.add(s);
		
		ArrayList<Record> dict = new ArrayList<Record>();
		
		while(in.ready()){
			
			String[] input = in.readLine().split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
			
			Address address = new Address(input[colNames.indexOf("Address.Street")], input[colNames.indexOf("Address.City")], 
					input[colNames.indexOf("Address.State")], Integer.parseInt(input[colNames.indexOf("Address.Zip")]));
			
			Record record = new Record(Long.parseLong(input[colNames.indexOf("StudentID")]), Integer.parseInt(input[colNames.indexOf("Credits")]), 
					Double.parseDouble(input[colNames.indexOf("GPA")]), input[colNames.indexOf("Major")], address);
			
			dict.add(record);
		}
		
		return dict;
	}
}