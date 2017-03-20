
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.mariadb.jdbc.MariaDbDataSource;

public class Test {

	public static void main(String[] args) throws SQLException {
		MariaDbDataSource dataSource = new MariaDbDataSource("jdbc:mariadb://mas.lvc.edu:3306/cds280");
		dataSource.setUser("cds280");
		dataSource.setPassword("082sdc");
		Connection connection = dataSource.getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM MyTable");
		while (resultSet.next()) {
			Double rate = resultSet.getDouble("Rate");
			System.out.println("" + rate);
		}
		ResultSetMetaData meta = resultSet.getMetaData();
		for (int i = 1; i <= meta.getColumnCount(); ++i) {
			System.out.println("" + meta.getColumnName(i));
		}
		resultSet.close();
		connection.close();
	}

}
