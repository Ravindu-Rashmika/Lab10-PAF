<%@page import="com.Order"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Order Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery.min.js"></script>
<script src="Components/Order.js"></script>
</head>
<body> 
<div class="container"><div class="row"><div class="col-6"> 
	<h2>Order Management</h2>
	<br>
	
	<form id="formOrder" name="formOrder" method="post" action="Order.jsp">
		 Order Description: 
		 <input id="orderDescription" name="orderDescription" type="text" class="form-control form-control-sm">
		 
		 <br> Order Value: 
		 <input id="orderValue" name="orderValue" type="text" class="form-control form-control-sm">
		 
		 <br> Order Quantity: 
		 <input id="orderQuantity" name="orderQuantity" type="text" class="form-control form-control-sm">
		 <br>
		 
		 <input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
		 <input type="hidden" id="hidOrderIDSave" name="hidOrderIDSave" value="">
		 
	</form>
	
	<div id="alertSuccess" class="alert alert-success"></div>
	<div id="alertError" class="alert alert-danger"></div>

	<br>
	<div id="divOrdersGrid">
		<%
 			Order orderObj = new Order();
			out.print(orderObj.readOrder());
 		%>
	</div>
	</div> </div> 
</div> 
</body>
</html>