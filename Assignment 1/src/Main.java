import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String args[]) throws IOException {
		//Instantiates each data structure
		Map<Integer, Map<String, Object>> txt = readTxt("http://mas.lvc.edu/cds280/Student.txt");
		Map<Integer, Map<String, Object>> csv = readCsv("Student.csv");

		//gives the amount of rows (a.k.a. the size) of each data structure
		System.out.println("Student.txt size: " + txt.size());
		System.out.println("Student.csv size: " + csv.size());
		
		//sums the GPA column entries
		System.out.println("Student.txt sum of the GPA column: " + sum(txt, "GPA"));
		System.out.println("Student.csv sum of the GPA column: " + sum(csv, "GPA"));
		
		//sums the length of the Address.Street columns
		System.out.println("Student.txt sum of the length of Address.Street column: " + columnLengthSum("Address.Street", txt));
		System.out.println("Student.csv sum of the length of Address.Street column: " + columnLengthSum("Address.Street", csv));
	}

	/** method used to sum the column length of the data structure Map<Integer, Map<String, Object>>
	 * pre: col exists as a column in the specified dict data structure, and dict.size() > 0
	 * post: return a sum of the lengths of the specified column
	 */
	static int columnLengthSum(String col, Map<Integer, Map<String, Object>> dict){
		int sum = 0;
		
		for(Integer key : dict.keySet()){
			sum += ((String) dict.get(key).get(col)).length();
		}
		
		return sum;
	}
	
	/** creates a sum of of all the values in a column
	 * pre: dict.size() > 0, and col is a numeric column that exists in dict
	 * post: returns the sum of all numeric values in a column
	 */
	static double sum(Map<Integer, Map<String, Object>> dict, String col){
		double sum = 0;
		
		for(Integer key : dict.keySet()){
			sum += (double) dict.get(key).get(col);
		}
		
		return sum;
	}
	
	/** checks to see if some string can be written as a double
	 * post: returns whether a string can be parsed to a double or not
	 */
	static boolean isDouble(String str) {
	
		try {
			double d = Double.parseDouble(str);
		} 
		catch (NumberFormatException e) {
			return false;
		}

		return true;
	}
	
	/** reads a tab delimited text file and puts it into a data structure
	 * pre: s is a string that refers to a URL which holds a text file
	 * post: returns a Map holding Maps that contain each column as a key and each row as an object
	 */
	static Map<Integer, Map<String, Object>> readTxt(String s) throws IOException {
		URL url = new URL(s);
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		// starts the reader at the second line
		in.readLine();

		// reads the key names (column names) and stores them
		BufferedReader colIn = new BufferedReader(new InputStreamReader(url.openStream()));
		String colString = colIn.readLine();
		colIn.close();
		String[] col = colString.split("\t");

		//instantiates the object being returned so we can add in objects
		Map<Integer, Map<String, Object>> dict = new HashMap<Integer, Map<String, Object>>();
		
		//loops while there is still more data to read
		while (in.ready()) {
			
			//pulls the next line and splits it apart by the delimiter
			String valString = in.readLine();
			String[] val = valString.split("\t");
			
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
				
			}	//end for

			//since map.get("StudentID") is a double, we cast it to an int so we can have a Integer Key for the outside map
			int i = ((Double) map.get("StudentID")).intValue();
			
			//puts the map into the outside container
			dict.put(i, map);
			
		}	//end while

		//closes the BufferedReader
		in.close();

		//returns our data structure
		return dict;
	}

	/**reads csv file and puts it into a data structure
	 * pre: s is a string that refers to a local csv file
	 * post: returns a Map holding Maps that contain each column as a key and each row as an object
	 */
	static Map<Integer, Map<String, Object>> readCsv(String s) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(new File(s)));
		// starts the reader at the second line
		in.readLine();

		// reads the key names (column names) and stores them
		BufferedReader colIn = new BufferedReader(new FileReader(new File("Student.csv")));
		String colString = colIn.readLine();
		colIn.close();
		String[] col = colString.split(",");

		//instantiates the object being returned so we can add in objects
		Map<Integer, Map<String, Object>> dict = new HashMap<Integer, Map<String, Object>>();
		
		//loops while there is still more data to read
		while (in.ready()) {
			
			//pulls the next line and splits it apart by the delimiter
			String valString = in.readLine();
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

			//since map.get("StudentID") is a double, we cast it to an int so we can have a Integer Key for the outside map
			int i = ((Double) map.get("StudentID")).intValue();
			
			//puts the map into the list
			dict.put(i, map);
			
		}

		//closes the BufferedReader
		in.close();

		//returns our data structure
		return dict;
	}
}
