import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import com.opencsv.CSVReader;

public class PartA {

	public static void main(String[] args) throws IOException{
		File file = new File("Student.csv");
		CSVReader in = new CSVReader(new FileReader(file), ',' , '"');
		DataOutputStream out = new DataOutputStream(new FileOutputStream("out.bin"));
		Iterator<String[]> it = in.iterator();
		
		while(it.hasNext()){
			String[] arr = it.next();
			
			if(arr == null) break;
			for(String s: arr){
				out.writeUTF(s);
			}	
		}
		
		DataInputStream data = new DataInputStream(new FileInputStream(file));
		String s = data.readUTF();
		
		System.out.println(s);
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