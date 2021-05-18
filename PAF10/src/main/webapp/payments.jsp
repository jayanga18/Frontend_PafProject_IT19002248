
<%@ page import="com.paf10.Payment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title> Payment Management</title>
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/payments.js"></script>
<link rel="stylesheet" href="views/bootstrap.min.css">
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col">


				<h1> Payment Management</h1>
				<form method='post' action='payments.jsp' id='formPayment' name='formPayment'>
					productID: <input id='productID' name='productID' type='text' class='form-control col-md-3'><br> 
					name: <input id='name' name='name' type='text' class='form-control col-md-3'><br> 
					address: <input id='address' name='address' type='text' class='form-control col-md-3'><br> 
					phoneNO: <input id='phoneNO' name='phoneNO' type='text' class='form-control col-md-3'><br> 
					<input id='btnSave' name='btnSave' type='submit' value='Save' class='btn btn-primary'
					onClick="<%
							Payment payment = new Payment();
							payment.insertPayment(request.getParameter("productID"), request.getParameter("name"), request.getParameter("address"), request.getParameter("phoneNO"));
						%>"
					> 
					<input type='hidden' id='hidorderNoSave' name='hidorderNoSave' >
				</form>
				
				<div id="alertSuccess" class="alert alert-success">
					<%
						out.print(session.getAttribute("statusMsg"));
					%>
				</div>
				<div id="alertError" class="alert alert-danger"></div>
				<br>

				<%
				Payment paymentObj = new Payment();
				    out.print(paymentObj.readPayments());
				%>
			</div>
		</div>
	</div>
</body>
</html>