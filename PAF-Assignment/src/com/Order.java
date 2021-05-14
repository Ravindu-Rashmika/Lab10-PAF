package com;
import java.sql.*;
public class Order
{ 

	//database connection
	private Connection connect() 
	{ 
		Connection con = null; 
		
		try
		{ 
			Class.forName("com.mysql.jdbc.Driver"); 
 
			//Provide the correct details: DBServer/DBName, username , password 
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/assignment", "root", ""); 
		} 
		catch (Exception e) 
		{e.printStackTrace();} 
		return con; 
	} 
	
	//retrieve order details
	public String readOrder() 
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
			output = "<table border='1'><tr><th>Order Description</th> <th>Order Value</th><th>Order Quantity</th> <th>Update</th><th>Remove</th></tr>"; 
 
			String query = "select * from assignment.order"; 
			Statement stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery(query); 
 
			// iterate through the rows in the result set
			while (rs.next()) 
			{ 
				String orderID = Integer.toString(rs.getInt("orderID")); 
				String orderDescription = rs.getString("orderDescription");
				String orderValue = Double.toString(rs.getDouble("orderValue"));
				String orderQuantity = rs.getString("orderQuantity");
				 
				// Add into the html table
				output += "<tr><td><input id='hidOrderIDUpdate' name='hidOrderIDUpdate' type='hidden' value='" + orderID
				 + "'>" + orderDescription + "</td>"; 
				 output += "<td>" + orderValue + "</td>"; 
				 output += "<td>" + orderQuantity + "</td>"; 
				 
				 // buttons
				 output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary' data-orderid='" + orderID +"'></td> "
				 		+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-orderid='" + orderID + "'></td></tr>";
			} 
			
			con.close(); 
				 
			// Complete the html table
			output += "</table>"; 
				 
		} 
		catch (Exception e) 
		{ 
			output = "Error while reading the orders."; 
			System.err.println(e.getMessage()); 
		} 
		return output; 
	}
	
	//insert order details
	public String insertOrder(String description, String value, String quantity) 
	{ 
			String output = ""; 
			
			try
			{ 
				 Connection con = connect(); 
				 
				 if (con == null) 
				 
				 { 
					 return "Error while connecting to the database for inserting."; 
				 } 
				 
				 // create a prepared statement
				 String query = " insert into assignment.order(orderID, orderDescription, orderValue, orderQuantity) values (?, ?, ?, ?)";
				 
				 PreparedStatement preparedStmt = con.prepareStatement(query); 
				 
				 // binding values
				 preparedStmt.setInt(1, 0); 
				 preparedStmt.setString(2, description); 
				 preparedStmt.setDouble(3, Double.parseDouble(value)); 
				 preparedStmt.setString(4, quantity); 
				 
				 // execute the statement
				 preparedStmt.execute(); 
				 con.close(); 
				 
				 String newOrders = readOrder(); 
				 output = "{\"status\":\"success\", \"data\": \"" + newOrders + "\"}"; 
			} 
			catch (Exception e) 
			{ 
				 output = "{\"status\":\"error\", \"data\":\"Error while inserting the order.\"}"; 
				 System.err.println(e.getMessage()); 
			} 
			
			return output; 
	}
	
	//update Order details
	public String updateOrder(String ID, String description, String value, String quantity) 
	{ 
			String output = ""; 
			try
			{ 
				Connection con = connect(); 
				 
				if (con == null) 
				{ 
					return "Error while connecting to the database for updating."; 
				} 
				 
				// create a prepared statement
				String query = "UPDATE assignment.order SET orderDescription=?, orderValue=?, orderQuantity=? WHERE orderID=?"; 
				 
				PreparedStatement preparedStmt = con.prepareStatement(query); 
					 
				// binding values
				preparedStmt.setString(1, description);  
				preparedStmt.setDouble(2, Double.parseDouble(value)); 
				preparedStmt.setString(3, quantity); 
				preparedStmt.setInt(4, Integer.parseInt(ID)); 
					 
				// execute the statement
				preparedStmt.execute(); 
				con.close(); 
					 
				String newOrders = readOrder(); 
				output = "{\"status\":\"success\", \"data\": \"" + newOrders + "\"}"; 
			} 
			catch (Exception e) 
			{ 
				 output = "{\"status\":\"error\", \"data\": \"Error while updating the order.\"}"; 
				 System.err.println(e.getMessage()); 
			} 
					
			return output; 
		} 
	
	//delete order function
	public String deleteOrder(String orderID) 
	 
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
			String query = "delete from assignment.order where orderID=?"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
					 
			// binding values		 
			preparedStmt.setInt(1, Integer.parseInt(orderID)); 
					 
			// execute the statement
			preparedStmt.execute(); 
			con.close(); 
			
			String newOrders = readOrder(); 
			output = "{\"status\":\"success\", \"data\": \"" + newOrders + "\"}"; 
			
		} 
		catch (Exception e) 
		{ 
			output = "{\"status\":\"error\", \"data\": \"Error while deleting the order.\"}"; 
			System.err.println(e.getMessage()); 
			e.printStackTrace();
		} 
		
		return output; 
	} 
	
}				 
