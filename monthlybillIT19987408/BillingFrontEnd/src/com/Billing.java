package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;


public class Billing {
	
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/egsystem?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
//insert data
	public String insertBilling(String bAcc, String bName, String bUsage, String bAmount,String bAmount1)  
	{  
		int amount = Integer.parseInt(bAmount);
	   System.out.println(amount);
		String output = ""; 	 
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{return "Error while connecting to the database for inserting."; } 
	 
			// create a prepared statement 
			String query = " insert into monthly_bill(`bID`,`bAcc`,`bName`,`bUsage`,`bAmount`,`FullBill`)" + " values (?, ?, ?, ?, ?, ?)"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			 preparedStmt.setInt(1, 0);
			 preparedStmt.setString(2, bAcc);
			 preparedStmt.setString(3, bName);
			 preparedStmt.setString(4, bUsage);
			 preparedStmt.setString(5, bAmount);
			 preparedStmt.setInt(6, amount);
			
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	   
			String newBilling = readBilling(); 
			output =  "{\"status\":\"success\", \"data\": \"" + newBilling + "\"}";    
		}   
		catch (Exception e)   
		{    
			output =  "{\"status\":\"error\", \"data\": \"Error while inserting the billing.\"}";  
			System.err.println(e.getMessage());   
		} 		
	  return output;  
	} 	
	//read data
	public String readBilling()  
	{   
		String output = ""; 
		try   
		{    
			Connection con = connect(); 
		
			if (con == null)    
			{
				return "Error while connecting to the database for reading."; 
			} 
	 
			// Prepare the html table to be displayed    
			output = "<table border=\'1\'><tr><th>Account No</th><th>Customer Name</th><th>Address</th><th>Unit</th><th>Full Amount</th><th>Update</th><th>Remove</th></tr>";
	 
			String query = "select * from monthly_bill";    
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
	 
			// iterate through the rows in the result set    
			while (rs.next())    
			{     
				 String bID = Integer.toString(rs.getInt("bID"));
				 String bAcc = rs.getString("bAcc");
				 String bName = rs.getString("bName");
				 String bUsage = rs.getString("bUsage");
				 String bAmount = rs.getString("bAmount");
				 String FAmount = rs.getString("bAmount");
				 int amount = Integer.parseInt(FAmount);
				 double Amount;
				 if(amount<=70)
				 {
					 Amount=amount*70;
				 }
				 else {
					 Amount=amount*100;
				 }
				 
				// Add into the html table 
				output += "<tr><td><input id=\'hidBillingIDUpdate\' name=\'hidBillingIDUpdate\' type=\'hidden\' value=\'" + bID + "'>" 
							+ bAcc + "</td>"; 
				output += "<td>" + bName + "</td>";
				output += "<td>" + bUsage + "</td>";
				output += "<td>" + bAmount + "</td>";
				output += "<td>" + Amount + "</td>";
	 
				// buttons     
				output +="<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"       
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-billid='" + bID + "'>" + "</td></tr>"; 
			
			}
			con.close(); 
	   
			output += "</table>";   
		}   
		catch (Exception e)   
		{    
			output = "Error while reading the billing.";    
			System.err.println(e.getMessage());   
		} 	 
		return output;  
	}
	//update data
	public String updateBilling(String bID, String bAcc, String bName, String bUsage, String bAmount)  
	{   
		String output = "";  
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{return "Error while connecting to the database for updating."; } 
	 
			// create a prepared statement    
			String query = "UPDATE monthly_bill SET bAcc=?,bName=?,bUsage=?,bAmount=?"  + "WHERE bID=?";  	 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			preparedStmt.setString(1, bAcc);
			 preparedStmt.setString(2, bName);
			 preparedStmt.setString(3, bUsage);
			 preparedStmt.setString(4, bAmount);
			 preparedStmt.setInt(5, Integer.parseInt(bID)); 
	 
			// execute the statement    
			preparedStmt.execute();    
			con.close();  
			String newBilling = readBilling();    
			output = "{\"status\":\"success\", \"data\": \"" + newBilling + "\"}";    
		}   
		catch (Exception e)   
		{    
			output =  "{\"status\":\"error\", \"data\": \"Error while updating the billing.\"}";   
			System.err.println(e.getMessage());   
		} 	 
	  return output;  
	} 
	
	
	//delete data
	public String deleteBilling(String bID)   
	{   
		String output = ""; 
	 
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{
				return "Error while connecting to the database for deleting."; 			
			} 
	 
			// create a prepared statement    
			String query = "delete from monthly_bill where bID=?"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			preparedStmt.setInt(1, Integer.parseInt(bID)); 
	 
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	 
			String newBilling = readBilling();    
			output = "{\"status\":\"success\", \"data\": \"" +  newBilling + "\"}";    
		}   
		catch (Exception e)   
		{    
			output = "Error while deleting the billing.";    
			System.err.println(e.getMessage());   
		} 
	 
		return output;  
	}
	
}
