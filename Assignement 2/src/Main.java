import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;

import com.opencsv.CSVReader;

public class Main {

	public static void main(String[] args) throws FileNotFoundException{
		CSVReader in = new CSVReader(new FileReader(new File("Student.csv")), ',' , '"');
		DataOutputStream out = new DataOutputStream(new FileOutputStream("out.txt"));
		
		while(true){
			try{
				String[] arr = in.readNext();
				for(String s: arr){
					out.writeUTF(s);
				}
			} catch(Exception e){
				break;
			}
		}
	}
	
}
