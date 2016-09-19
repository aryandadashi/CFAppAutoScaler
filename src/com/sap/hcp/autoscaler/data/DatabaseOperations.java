package com.sap.hcp.autoscaler.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.sap.hcp.autoscaler.MonitoringService;

public class DatabaseOperations {

	 // JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost/masterthesis";

	   //  Database credentials
	   static final String USER = "root";
	   static final String PASS = "root";
	   
	   static Connection conn = null;
	   static Statement stmt = null;
	   
	   public void insertIntoModelTable(){
		   	if(  MonitoringService.modelDataObject.getAvgResponseTime() == 0.0 || MonitoringService.modelDataObject.getRequestsPerSecond() == 0.0 || 
		   			( MonitoringService.modelDataObject.getNoInstances() == 0 && MonitoringService.modelDataObject.getMemory_percent() == 0.0 && 
		   			MonitoringService.modelDataObject.getDisk_percent() == 0.0 && MonitoringService.modelDataObject.getCpu_percent() == 0.0)){
		   		return;
		   	}
			try   
			{
				initializeDB();				
			    String sql;
			    
			    sql = "INSERT INTO modeldata VALUES (" + MonitoringService.modelDataObject.getTimeStamp() + "," + 
			    		 MonitoringService.modelDataObject.getAvgResponseTime() + "," + 
			    		 MonitoringService.modelDataObject.getRequestsPerSecond() + "," + 
			    		 MonitoringService.modelDataObject.getNoInstances()+ "," + 
			    		 MonitoringService.modelDataObject.getMemory_percent() + "," + 
			    		 MonitoringService.modelDataObject.getDisk_percent() + "," +
			    		 MonitoringService.modelDataObject.getCpu_percent() + ")";				
				
			    stmt.executeUpdate(sql);
			      System.out.println("Inserted records into the table...");			      
			    }catch(SQLException se){
			        //Handle errors for JDBC
			        se.printStackTrace();
			     }catch(Exception e){
			        //Handle errors for Class.forName
			        e.printStackTrace();
			     }finally{
			        //finally block used to close resources
			    	 closeAllConnections();
			     }//end finally	
		}
	   
	   public void initializeDB(){
		   try {
				Class.forName(JDBC_DRIVER);
				System.out.println("Connecting to database...");
				conn = DriverManager.getConnection(DB_URL,USER,PASS);
				System.out.println("Creating statement...");
				stmt = conn.createStatement();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   }
	
	   public void closeAllConnections(){
	        try{
		           if(stmt!=null)
		              stmt.close();
		        }catch(SQLException se2){
		        }// nothing we can do
		        try{
		           if(conn!=null)
		              conn.close();
		        }catch(SQLException se){
		           se.printStackTrace();
		        }
		}
}
