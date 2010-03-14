package ar.com.fdvs.dj.test.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HSQLTest {
	protected static final Log log = LogFactory.getLog(HSQLTest.class);

	public final String TEST_DB_PATH = "target/test-classes/hsql/test_dj_db"; //For read-only purposes

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
	     Class.forName("org.hsqldb.jdbcDriver" );
		 Connection c = DriverManager.getConnection("jdbc:hsqldb:file:target/test-classes/hsql/test_dj_db", "sa", "");

		 PreparedStatement ps = c.prepareStatement("SELECT * FROM Customer");
		 ResultSet rs = ps.executeQuery();
		 while (rs.next()) {
			 log.info(rs.getString("firstname")+ ", " + rs.getString("lastname"));
		}

		rs.close();
		ps.close();
		c.close();

	}

}
