import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String args[]) throws IOException {
		//Instantiates each data structure
		List<Map<String, Object>> txt = readTxt("http://mas.lvc.edu/cds280/Student.txt");
		List<Map<String, Object>> csv = readCsv("Student.csv");

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

	/** method used to sum the column length of the data structure List<Map<String, Object>>
	 * pre: col exists as a column in the specified dict data structure, and dict.size() > 0
	 * post: return a sum of the lengths of the specified column
	 */
	static int columnLengthSum(String col, List<Map<String, Object>> dict){
		int sum = 0;
		for(int i = 0; i < dict.size(); i++){
			sum += ((String) dict.get(i).get(col)).length();
		}
		
		return sum;
	}
	
	/** creates a sum of of all the values in a column
	 * pre: dict.size() > 0, and col is a numeric column that exists in dict
	 * post: returns the sum of all numeric values in a column
	 */
	static double sum(List<Map<String, Object>> dict, String col){
		double sum = 0;
		for(int i = 0; i < dict.size(); i++){
			sum += (double) dict.get(i).get(col);
		}
		
		return sum;
	}
	
	static boolean isDouble(String str) {

		try {
			double d = Double.parseDouble(str);
		} 
		catch (NumberFormatException e) {
			return false;
		}

		return true;
	}
	
	static List<Map<String, Object>> readTxt(String s) throws IOException {
		URL url = new URL(s);
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		// starts the reader at the second line
		in.readLine();

		// reads the key names and stores them
		BufferedReader colIn = new BufferedReader(new InputStreamReader(url.openStream()));
		String colString = colIn.readLine();
		colIn.close();
		String[] col = colString.split("\t");

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		while (in.ready()) {

			String valString = in.readLine();
			String[] val = valString.split("\t");

			Map<String, Object> map = new HashMap<String, Object>();

			for (int i = 0; i < col.length; i++) {

				Object value = val[i];

				if (isDouble((String) value)) {
					value = Double.parseDouble((String) value);
				}

				map.put(col[i], value);
			}

			list.add(map);
		}

		in.close();

		return list;
	}

	static List<Map<String, Object>> readCsv(String s) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(new File(s)));
		// starts the reader at the second line
		in.readLine();

		// reads the key names and stores them
		BufferedReader colIn = new BufferedReader(new FileReader(new File("Student.csv")));
		String colString = colIn.readLine();
		colIn.close();
		String[] col = colString.split(",");

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		while (in.ready()) {
			String valString = in.readLine();
			String[] val = valString.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

			Map<String, Object> map = new HashMap<String, Object>();

			for (int i = 0; i < col.length; i++) {
				
				Object value = val[i];

				if (isDouble((String) value)) {
					value = Double.parseDouble((String) value);
				}

				map.put(col[i], value);
			}

			list.add(map);
		}

		in.close();

		return list;
	}
}
