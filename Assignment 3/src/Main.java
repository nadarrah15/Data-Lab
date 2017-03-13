import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Main {

	public static void main(String[] args) throws Exception{
		
		BufferedReader in = new BufferedReader(new FileReader(new File("Student.csv")));
		
		Map<Integer, Map<String, Object>> dict = readCsv(in);
		
		Address address = (Address) dict.get(dict.keySet().iterator().next()).get("Address");
		JSONObject jsonAddress = addressToJSONObject(address);
		
		FileWriter out = new FileWriter(new File("student_json.json"));
		JSONObject jsonDict = dictToJSONObject(dict);
		out.write(jsonDict.toJSONString());
		out.close();
		
		in = new BufferedReader(new FileReader(new File("student_json.json")));
		JSONParser parser = new JSONParser();
		
		Map<Integer, Map<String, Object>> dictIn = readDict((JSONObject) parser.parse(in.readLine()));

		Iterator<Integer> it = dict.keySet().iterator();
		JSONObject jsonRecord1 = recordToJSONObject(dict.get(it.next()));
		out = new FileWriter(new File("record1_json.json"));
		out.write(jsonRecord1.toJSONString());
		out.close();
		
		in = new BufferedReader(new FileReader(new File("record1_json.json")));

		Map<String, Object> record = readRecord((JSONObject) parser.parse(in));
	}
	
	static Address readAddress(JSONObject json){
		return new Address((String) json.get("Street"), (String) json.get("City"), (String) json.get("State"), (int) json.get("Zip"));
	}
	
	static Map<String, Object> readRecord(JSONObject json){
		return (Map<String, Object>) json;	
	}
	
	static Map<Integer, Map<String , Object>> readDict(JSONObject json){
		return (Map<Integer, Map<String, Object>>) json;
	}
	
	static Map<Integer, Map<String, Object>> readCsv(BufferedReader in) throws IOException {

		// holds column names
		ArrayList<String> colNames = new ArrayList<String>();
		for(String s : in.readLine().split(","))
			colNames.add(s);
		
		Map<Integer, Map<String, Object>> dict = new HashMap<Integer, Map<String, Object>>();
		
		while(in.ready()){
			
			//splits the next line by commas and keeps commas within quotes (example: "name, major, "street, city"" is split into "name", "major", "street, city")
			String[] input = in.readLine().split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
			
			//map to be added in dict
			Map<String, Object> record = new HashMap<String, Object>();
			
			Address address = new Address(input[colNames.indexOf("Address.Street")], input[colNames.indexOf("Address.City")], 
					input[colNames.indexOf("Address.State")], Integer.parseInt(input[colNames.indexOf("Address.Zip")]));
			
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
			 
			 int index = colNames.indexOf("StudentID");
			 
			 dict.put(Integer.parseInt(input[index]), record);
		}
		
		return dict;
	}
	
	static JSONObject addressToJSONObject(Address address){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Street", address.getStreet());
		map.put("City", address.getCity());
		map.put("State", address.getState());
		map.put("Zip", address.getZip());
		
		return new JSONObject(map);
	}
	
	static JSONObject recordToJSONObject(Map<String, Object> record){
		return new JSONObject(record);
	}
	
	static JSONObject dictToJSONObject(Map<Integer, Map<String, Object>> dict){
		return new JSONObject(dict);
	}
}