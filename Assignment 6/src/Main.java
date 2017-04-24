import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

	public static void main(String[] args)throws Exception{
		BufferedReader in = new BufferedReader(new FileReader(new File("Student.csv")));
		ArrayList<Map<String, Object>> dict = readCsv(in);
		
		dict.stream().forEach((Map<String, Object> map) -> System.out.print(map.get("GPA") + " "));
		
		System.out.println("\n");

		dict.stream().filter((Map<String, Object> map) -> map.get("Major").equals("BIO")).forEach((Map<String, Object> map) -> System.out.print(map.get("GPA") + " "));
		
		System.out.println("\n");
		
		DoubleSummaryStatistics stats = dict.stream().filter((Map<String, Object> map) -> !map.get("Major").equals("MAS")).collect(Collectors.summarizingDouble((Map<String, Object> map) -> (Double) map.get("GPA")));
		
		System.out.printf("%-20s%-20s%-20s%-20s%-20s%n%n", "Count: " + stats.getCount(), "Sum: " + stats.getSum(), "Minimum: " + stats.getMin(), "Average: " + stats.getAverage(), "Maximum: " + stats.getMax());
		
		dict.stream().collect(Collectors.groupingBy((Map<String, Object> map) -> map.get("Major")));
	}
	
	
	
	//from assignment 3 modified a bit
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
			Map<String, Object> record = new HashMap<String, Object>();
			
			Address address = new Address(input[colNames.indexOf("Address.Street")], input[colNames.indexOf("Address.City")], input[colNames.indexOf("Address.State")], Integer.parseInt(input[colNames.indexOf("Address.Zip")]));
			
			 for(int i = 0; i < input.length; i++){
				 //if the column name does not contain an address field, add the item to the map
				 String value = input[i];
				 if(!colNames.get(i).contains("Address")){
					 if(value.matches("(\\d+)([.])(\\d+)"))
						 record.put(colNames.get(i), Double.parseDouble(value));
					 else if(value.matches("\\d+")){
						 record.put(colNames.get(i), Long.parseLong(value));
					 }
					 else
						 record.put(colNames.get(i), input[i]);
				 }
			 }
			 
			 record.put("Address", address);
			 
			 dict.add(record);
		}
		
		return dict;
	}

}
