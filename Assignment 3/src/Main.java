import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) throws Exception{
		
		BufferedReader in = new BufferedReader(new FileReader(new File("Student.csv")));
		
		ArrayList<Map<String, Object>> map = readCsv(in);
	}
	
	static ArrayList<Map<String, Object>> readCsv(BufferedReader in) throws IOException {

		// holds column names
		ArrayList<String> colNames = new ArrayList<String>();
		for(String s : in.readLine().split(","))
			colNames.add(s);
		
		ArrayList<Map<String, Object>> dict = new ArrayList<Map<String, Object>>();
		
		while(in.ready()){
			
			//splits the next line by commas and keeps commas within quotes (example: "name, major, "street, city"" is split into "name", "major", "street, city")
			String[] input = in.readLine().split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
			
			//map to be added in dict
			Map<String, Object> map = new HashMap<String, Object>();
			
			Address address = new Address(input[colNames.indexOf("Address.Street")], input[colNames.indexOf("Address.city")], 
					input[colNames.indexOf("Address.State")], Integer.parseInt(input[colNames.indexOf("Address.Zip")]));
			
			 for(int i = 0; i < input.length; i++){
				 //if the column name does not contain an address field, add the item to the map
				 if(!colNames.get(i).contains("Address"))
					 map.
			 }
		}
		
		return dict;
	}
}