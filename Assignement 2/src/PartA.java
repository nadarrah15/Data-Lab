/**
 * 	Nathan Darrah, Lebanon Valley College class of '19
 * 	Project for CDS-280 Introductory Data Analysis Lab
 * 	February, 2017
 */

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.opencsv.CSVReader;

public class PartA {

	public static void main(String[] args) throws IOException{
		
		CSVReader in = new CSVReader(new FileReader(new File("Student.csv")));
		DataOutputStream out = new DataOutputStream(new FileOutputStream("out.bin"));
		
		Map<Integer, Map<String, Object>> dict = new HashMap<Integer, Map<String, Object>>();
		
		//the column names are in the first line
		String[] col = in.readNext();
		
		//holding column type, 0 is string, 1 is number
		int[] colType = new int[col.length];
		Arrays.fill(colType, 0);
		
		List<String[]> list = in.readAll();
		
		for(int i = 0; i < list.size(); i++){
			int count = 0;
			Map<String, Object> map = new HashMap<String, Object>();
			
			for(String s: list.get(i)){
				if(isDouble(s)){
					double d = Double.parseDouble(s);
					map.put(col[count], d);
					colType[count] = 1;
				}
				else
					map.put(col[count], s);
			
				count++;
			}
			
			dict.put(((Double) map.get("GPA")).intValue(), map);
		}
		
		//place the amount of rows and columns
		out.writeInt(dict.size());	//rows
		out.write(col.length);		//columns
		
		//writes in the column types
		for(int i: colType){
			out.writeInt(i);
		}
		
		//writes in the column names
		for(String s: col)
			out.writeUTF(s);
		
		for(Map.Entry<Integer, Map<String, Object>> map: dict.entrySet()){
			
			
			for(Map.Entry<String, Object> innerMap: dict.get(map.getKey()).entrySet()){
				
			}
		}
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