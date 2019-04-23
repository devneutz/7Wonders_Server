package ch.fhnw.sevenwonders.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

// Source: https://stackoverflow.com/questions/39067373/jdbc-and-multithreading
public class DbConnectionPool {
	 private String connString = "jdbc:mysql://127.0.0.1:3306/five4sevenwonders?connectTimeout=5000&serverTimezone=UTC";
	 private static DbConnectionPool _instance;
	 
	 private static final Logger logger = Logger.getLogger("");
		
     static final int INITIAL_CAPACITY = 20;
     LinkedList<Connection> pool = new LinkedList<Connection>();
     public String getConnString() {
         return connString;
     }

     private DbConnectionPool() throws SQLException {
         for (int i = 0; i < INITIAL_CAPACITY; i++) {
              pool.add(DriverManager.getConnection(connString, "five4seven", "five4sevenwonders"));
         }
     }

     // May not be thread-safe! Since it's only a read-operation, we neglect this here.
     public static DbConnectionPool getInstance() {
    	 if(_instance == null) {
    		 try {
    			 _instance = new DbConnectionPool();
    		 }
    		 catch(SQLException inEx) {
    			 logger.log(Level.SEVERE, "Error instantiating DbPool!", inEx);
    		 }
    	 }
    	 return _instance;
     }
     
     public synchronized Connection getConnection() throws SQLException {
         if (pool.isEmpty()) {
             pool.add(DriverManager.getConnection(connString, "five4seven", "five4sevenwonders"));
         }
         return pool.pop();
     }

     public synchronized void returnConnection(Connection connection) {
         pool.push(connection);
     }  
}
