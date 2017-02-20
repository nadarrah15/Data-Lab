/**
 * 	Nathan Darrah, Lebanon Valley College class of '19
 * 	Project for CDS-280 Introductory Data Analysis Lab
 * 	February, 2017
 */

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import com.opencsv.CSVReader;

public class PartA {

	public static void main(String[] args) throws Exception{
		
		BufferedReader in = new BufferedReader(new FileReader(new File("Student.csv")));
		DataOutputStream out = new DataOutputStream(new FileOutputStream("out.bin"));
		
		ArrayList<Map<String, Object>> map = readCsv(in);
		writeFile(map, out);
		
		DataInputStream stream = new DataInputStream(new FileInputStream(new File("out.bin")));
		
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
	
	static void readFile(DataInputStream stream) throws IOException {

		int rows = stream.readInt();
		System.out.println(rows);
		int cols = stream.readInt();
		int[] colType = new int[cols];
		String[] col = new String[cols];
		
		//read in the column types
		for(int i = 0; i < cols; i++)
			colType[i] = stream.readInt();
		
		//read in the column names
		for(int i = 0; i < cols; i++)
			col[i] = stream.readUTF();
		
		//new container
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		for(int i = 0; i < rows - 1; i++){
			Map<String, Object> map = new HashMap<String, Object>();
			for(int j = 0; j < cols; j++){
				if(colType[j] == 0)
					map.put(col[j], stream.readUTF());
				else if(colType[j] == 1)
					map.put(col[j], stream.readDouble());
			}
			list.add(map);
		}
		
		for(int i = 0; i < list.size(); i++){
			for(Map.Entry<String, Object> map: list.get(i).entrySet()){
				System.out.print(map.getValue());
			}
			System.out.println();
		}
	}

	static void writeFile(ArrayList<Map<String, Object>> arr, DataOutputStream out) throws Exception{

		long length = 0;
		
		//the column names are in the first line
		int count = 0;
		String[] col = new String[arr.get(0).keySet().size()];
		for(String s: arr.get(0).keySet()){
			col[count] = s;
			count++;
		}
		
		//holding column type, 0 is string, 1 is number
		int[] colType = new int[col.length];
		Arrays.fill(colType, 0);
		
		Map<String, Object> tempMap = arr.get(0);
		count = 0;
		for(Map.Entry<String, Object> entry: tempMap.entrySet()){
			if(isDouble("" + entry.getValue()))
				colType[count] = 1;
			else colType[count] = 0;
			
			count++;
		}
		
		//place the amount of rows and columns
		out.writeInt(arr.size());		//rows
		length++;
		out.writeInt(col.length);		//columns
		length++;
		
		//writes in the column types
		for(int i: colType){
			out.writeInt(i);
			length++;
		}
		
		//writes in the column names
		for(String s: col){
			out.writeUTF(s);
			length++;
		}
		
		//write in the rest of the values
		for(int i = 0; i < arr.size(); i++){
			Map<String, Object> map = arr.get(i);
			for(Map.Entry<String, Object> entry: map.entrySet()){
				
			}
		}
		
		out.writeLong(length);
		
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