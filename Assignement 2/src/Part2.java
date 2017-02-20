/**
 * 	Nathan Darrah, Lebanon Valley College class of '19
 * 	Project for CDS-280 Introductory Data Analysis Lab
 * 	February, 2017
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Part2 {

	public static void main(String[] args) throws Exception{
		
		BufferedReader in = new BufferedReader(new FileReader(new File("Student.csv")));
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("out.bin"));
		
		ArrayList<Map<String, Object>> map = readCsv(in);
		writeFile(map, out);
		
		ObjectInputStream stream = new ObjectInputStream(new FileInputStream(new File("out.bin")));
		
		readFile(stream);
	}
	
	static ArrayList<Map<String, Object>> readCsv(BufferedReader in) throws IOException {

		// reads the key names (column names) and stores them
		String colString = in.readLine();
		String[] col = colString.split(",");

		//instantiates the object being returned so we can add in objects
		ArrayList<Map<String, Object>> dict = new ArrayList<Map<String, Object>>();
		
		//loops while there is still more data to read
		while (in.ready()) {
			
			//pulls the next line and splits it apart by the delimiter
			String valString = in.readLine();
			
			/*code found for the regex expression found at 
			* http://stackoverflow.com/questions/1757065/java-splitting-a-comma-separated-string-but-ignoring-commas-in-quotes
			* by user Bart Kiers
			*/
			String[] val = valString.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

			//instantiates the object to be put in the list
			Map<String, Object> map = new HashMap<String, Object>();

			//loops per amount of columns
			for (int i = 0; i < col.length; i++) {
				
				//instantiates to be put into the map and checks if it is a numeric data type
				Object value = val[i];
				if (isDouble((String) value)) {
					value = Double.parseDouble((String) value);
				}

				//puts the object into the map
				map.put(col[i], value);
			}

			//puts the map into the list
			dict.add(map);
			
		}

		//closes the BufferedReader
		in.close();

		//returns our data structure
		return dict;
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
}

