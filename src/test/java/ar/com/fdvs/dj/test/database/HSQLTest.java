package ar.com.fdvs.dj.test.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HSQLTest {
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
	     Class.forName("org.hsqldb.jdbcDriver" );
		
		 Connection c = DriverManager.getConnection("jdbc:hsqldb:file:test_dj_db", "sa", "");
		 
	}

}
