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
		MariaDbDataSource source = new MariaDbDataSource("jdbc:mariadb://mas.lvc.edu:3306/cds280");
		source.setUser("cds280");
		source.setPassword("082sdc");
		Connection connection = source.getConnection();
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery("SELECT * FROM Student");
		
		ResultSetMetaData meta = result.getMetaData();
		String[] col = new String[meta.getColumnCount()];
		String[] colType = new String[meta.getColumnCount()];
		for(int i = 1; i <= meta.getColumnCount(); i++){
			col[i - 1] = meta.getColumnName(i);
			colType[i - 1] = meta.getColumnTypeName(i);
		}
		
		Map<Integer, Map<String, Object>> record = new HashMap<Integer, Map<String, Object>>();
		
		while(result.next()){
			Map<String, Object> row = new HashMap<String, Object>();
			for(int i = 0; i < col.length; i++){
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
				
				record.put((Integer) row.get("StudentID"), row);
			}
		}
		
		
		for(Integer i: record.keySet()){
			System.out.print(i + " = ");
			for(String s: record.get(i).keySet()){
				System.out.print(s + ": " + record.get(i).get(s) + ", ");
			}
			System.out.println();
		}
	}
}
