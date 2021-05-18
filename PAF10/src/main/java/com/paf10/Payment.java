package com.paf10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Payment {

	
	//Connection
		public Connection connect()
		{
			Connection con = null;

			try
			{
				Class.forName("com.mysql.cj.jdbc.Driver");
				con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test1",	"root", "1234");
				//For testing
				System.out.print("Successfully connected");
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

			return con;
		}

		//Insert
		public String insertPayment(String productID, String name, String address,String phoneNO)
		{
			String output = "";
			try
			{
				Connection con = connect();
				if (con == null)
				{
					return "Error while connecting to the database";
				}

				// create a prepared statement
				String query = " insert into payments(`orderNo`, `productID`,`name`,`address`, `phoneNo`)"+" values (?, ?, ?, ?, ?)";
				PreparedStatement preparedStmt = con.prepareStatement(query);

				// binding values
				preparedStmt.setInt(1, 0);
				preparedStmt.setString(2, productID);
				preparedStmt.setString(3, name);
				preparedStmt.setString(4, address);
				preparedStmt.setString(5, phoneNO); 
				//execute the statement
				preparedStmt.execute();
				con.close();

				String newPayments = readPayments();
				output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}";
				
			}
			catch (Exception e)
			{
				output = "{\"status\":\"error\", \"data\":\"Error while inserting the payment.\"}";
				System.err.println(e.getMessage());
			}
			return output;
		}

		//Read
		public String readPayments()
		{
			String output = "";
			try {
				Connection con = connect();
				if (con == null) {
					return "Error while connecting to the database for reading.";
				}
				// Prepare the html table to be displayed
				output = "<table border='1'><tr> <th> Product ID </th><th> Name </th>" + "<th> Address </th>"
						+ "<th> Phone Number </th>"
						+ "<th> Update </th><th> Remove </th></tr>";

				String query = "select * from payments";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				// iterate through the rows in the result set
				while (rs.next()) {
					String orderNo= Integer.toString(rs.getInt("orderNo"));
					String productID = rs.getString("productID");
					String name = rs.getString("name");
					String address= rs.getString("address");
					String phoneNo = rs.getString("phoneNo");
					// Add into the html table
					output += "<tr><td><input id='hidorderNoUpdate'name='hidorderNoUpdate'type='hidden' value='" + orderNo
							+ "'>" + productID + "</td>";
					output += "<td>" + name + "</td>";
					output += "<td>" + address + "</td>";
					output += "<td>" + phoneNo + "</td>";
					// buttons
					output += "<td><input name='btnUpdate'type='button' value='Update'class='btnUpdate btn btn-secondary'></td>"
							+ "<td><input name='btnRemove'type='button' value='Remove'class='btnRemove btn btn-danger'data-orderno='"
							+ orderNo + "'>" + "</td></tr>";
				}
				con.close();
				// Complete the html table
				output += "</table>";
			} catch (Exception e) {
				output = "Error while reading payments.";
				System.err.println(e.getMessage());
			}
			return output;
		}

		//Update
		public String updatePayment(String orderNo, String productID, String name, String address, String phoneNO)
		{
			String output = "";
			try
			{
				Connection con = connect();
				if (con == null)
				{
					return "Error while connecting to the database";
				}

				// create a prepared statement
				String query = "update payments set productID=?, name =?, address =?, phoneNo =? where orderNo =?";
				PreparedStatement preparedStmt = con.prepareStatement(query);

				// binding values
				preparedStmt.setString(1, productID);
				preparedStmt.setString(2, name);
				preparedStmt.setString(3, address);
				preparedStmt.setString(4, phoneNO);
				preparedStmt.setInt(5, Integer.parseInt(orderNo));

				//execute the statement
				preparedStmt.executeUpdate();
				con.close();

				String newPayments = readPayments();
				output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}";
				
				
			}
			catch (Exception e)
			{
				output = "{\"status\":\"error\", \"data\":\"Error while updating the payment.\"}";
				System.err.println(e.getMessage());
			}
			return output;
		}

		//Delete
		public String removePayment(String orderNo)
		{
			String output = "";
			try
			{
				Connection con = connect();
				if (con == null)
				{
					return "Error while connecting to the database";
				}

				// create a prepared statement
				String query = "delete from payments where `orderNo`=?";
				PreparedStatement preparedStmt = con.prepareStatement(query);

				// binding values
				preparedStmt.setInt(1, Integer.parseInt(orderNo));

				//execute the statement
				preparedStmt.executeUpdate();
				con.close();

				String newPayments = readPayments();
				output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}";
			}
			catch (Exception e)
			{
				output = "{\"status\":\"error\", \"data\":\"Error while deleting the payment.\"}";
				System.err.println(e.getMessage());
			}
			return output;
		}
}
