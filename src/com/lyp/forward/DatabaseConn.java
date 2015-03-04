package com.lyp.forward;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConn {
	public final static Connection GetConnection() throws SQLException{
		String firstPath = System.getProperty("user.dir");
		String url = "jdbc:odbc:Driver={MicroSoft Access Driver (*.mdb, *.accdb)};"
				+ "DBQ="+firstPath+"\\bin\\com\\lyp\\forward\\english.mdb";
		
		Connection conn ;
		try{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			
		}catch (ClassNotFoundException e){
			e.printStackTrace();
		}
		conn = DriverManager.getConnection(url, "", "");
		if(conn!=null){
			return conn;
		}else {
		    return null;
		}
	}
}
