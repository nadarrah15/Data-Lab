import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.mariadb.jdbc.MariaDbDataSource;


public class Main {

	public static void main(String[] args) throws SQLException, IOException{
		
		//connect to the data base
		MariaDbDataSource source = new MariaDbDataSource("jdbc:mariadb://mas.lvc.edu:3306/cds280");
		source.setUser("cds280");
		source.setPassword("082sdc");
		Connection connection = source.getConnection();
		
		//write a MySQL statement
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery("SELECT * FROM Student");
		
		//get data such as the columns and the column types from the meta information
		ResultSetMetaData meta = result.getMetaData();
		String[] col = new String[meta.getColumnCount()];
		String[] colType = new String[meta.getColumnCount()];
		for(int i = 1; i <= meta.getColumnCount(); i++){
			col[i - 1] = meta.getColumnName(i);			//MySQL column indices start at 1 while java indices start at 0
			colType[i - 1] = meta.getColumnTypeName(i);	//MySQL column indices start at 1 while java indices start at 0
		}
		
		//instantiate a record map, dictionary of dictionaries
		Map<Integer, Map<String, Object>> record = new HashMap<Integer, Map<String, Object>>();
		
		//go through the table and put the data into the record
		while(result.next()){
			//instantiate a map for each row
			Map<String, Object> row = new HashMap<String, Object>();
			
			//we go through all the columns of the table
			for(int i = 0; i < meta.getColumnCount(); i++){
				//Complicated if statement to see what the value's type is and insert each object as the correct type instead of just
				// inserting them all as objects. If none of the available types, we default to Object type
				if(colType[i] == "INTEGER"){
					row.put(col[i], result.getInt(i + 1));	//MySQL column indices start at 1 while java indices start at 0
				}
				else if(colType[i] == "VARCHAR" || colType[i] == "CHAR"){
					row.put(col[i], result.getString(i + 1));
				}
				else if(colType[i] == "DECIMAL"){
					row.put(col[i], result.getDouble(i + 1));
				}
				else
					row.put(col[i], result.getObject(i));
				
				//insert the row in the record
				record.put((Integer) row.get("StudentID"), row);
			}
		}
		
		//output for spot checking
		for(Integer i: record.keySet()){
			System.out.print(i + " = ");
			for(String s: record.get(i).keySet()){
				System.out.print(s + ": " + record.get(i).get(s) + ", ");
			}
			System.out.println();
		}
	}
}
