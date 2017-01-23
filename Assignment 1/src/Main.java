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
		List<Map<String, Object>> txt = readTxt();
		List<Map<String, Object>> csv = readCsv();

		System.out.println("Student.txt size: " + txt.size());
		System.out.println("Student.csv size: " + csv.size());
		
		System.out.println("Student.txt sum of the GPA column: " + sum(txt));
		System.out.println("Student.csv sum of the GPA column: " + sum(csv));
		
		System.out.println("Student.txt sum of the length of Address.Street column: " + columnLengthSum("Address.Street", txt));
		System.out.println("Student.csv sum of the length of Address.Street column: " + columnLengthSum("Address.Street", csv));
	}

	static int columnLengthSum(String col, List<Map<String, Object>> dict){
		int sum = 0;
		for(int i = 0; i < dict.size(); i++){
			sum += ((String) dict.get(i).get(col)).length();
		}
		
		return sum;
	}
	
	static double sum(List<Map<String, Object>> dict){
		double sum = 0;
		for(int i = 0; i < dict.size(); i++){
			sum += (double) dict.get(i).get("GPA");
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
	
	static List<Map<String, Object>> readTxt() throws IOException {
		URL url = new URL("http://mas.lvc.edu/cds280/Student.txt");
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

	static List<Map<String, Object>> readCsv() throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(new File("Student.csv")));
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
			String[] val = valString.split(",", col.length);

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
