import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import com.opencsv.CSVReader;

public class Main {

	public static void main(String[] args) throws IOException{
		CSVReader in = new CSVReader(new FileReader(new File("Student.csv")), ',' , '"');
		DataOutputStream out = new DataOutputStream(new FileOutputStream("out.txt"));
		Iterator<String[]> it = in.iterator();
		
		
		while(it.hasNext()){
			String[] arr = it.next();
			
			if(arr == null) break;
			for(String s: arr){
				out.writeChars(s);
			}	
		}
	}
	
}
