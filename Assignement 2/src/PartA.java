import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.opencsv.CSVParser;
import com.opencsv.CSVReader;

public class PartA {

	public static void main(String[] args) throws IOException{
		File file = new File("Student.csv");
		CSVReader in = new CSVReader(new FileReader(file));
		DataOutputStream out = new DataOutputStream(new FileOutputStream("out.bin"));
		Iterator<String[]> it = in.iterator();
		
		while(it.hasNext()){
			String[] arr = it.next();
			
			if(arr == null) break;
			for(String s: arr){
				if(isDouble(s))
					out.writeDouble(Double.parseDouble(s));
				else
					out.writeChars(s);
				
			}	
		}
		
		/*DataInputStream data = new DataInputStream(new FileInputStream(file));
		String s = data.readLine();
		CSVParser parser = new CSVParser();
		
		String[] col = parser.parseLine(s);
		Map<Number, Map<String, Object>> dict = new HashMap<Number, Map<String, Object>>();
		
		//start the first line
		s = data.readLine();
		while(s != null){
			Map<String, Object> map = new HashMap<String, Object>();
			String[] arr = parser.parseLine(s);
			for(int i = 0; i < col.length; i++){
				Object o;
				if(isDouble(arr[i])){
					o = Double.parseDouble(arr[i]);
				}
				else{
					o = arr[i];
				}
				
				map.put(col[i], o);
			}
			
			dict.put(((Number) map.get("StudentID")), map);
			
			s = data.readLine();
		}
		
		
		System.out.println(dict.size());
		System.out.println(sum(dict, "GPA"));
		System.out.println(columnLengthSum("Address.Street", dict));*/
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
		
		try {
			Double.parseDouble(str);
		} 
		catch (NumberFormatException e) {
			return false;
		}

		return true;
	}
}