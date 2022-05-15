

//IT19987408
//R.R.J.K.V.Bandara Aramba

<%@ page import="com.Billing"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>    

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Billing</title>
<link rel="stylesheet" href="Views/bootstrap.min.css"> 
<script src="Components/jquery-3.4.1.min.js"></script> 
<script src="Components/validation.js"></script> 
</head>
<body>
<div class="container"> 
	<div class="row">  
		<div class="col-6"> 
			<h1>Billing System</h1>
				<form id="formBilling" name="formBilling" method="post" action="BillUI.jsp">  
					Account No:  
 	 				<input id="bAcc" name="bAcc" type="text"  class="form-control form-control-sm">
					<br>Customer Name:   
  					<input id="bName" name="bName" type="text" class="form-control form-control-sm">   
  					<br>User NO:   
  					<input id="bUsage" name="bUsage" type="text"  class="form-control form-control-sm">
  					<br>Amount:   
  					<input id="bAmount" name="bAmount" type="text"  class="form-control form-control-sm">
					<br>  
					<input id="btnSave" name="btnSave" type="button" value="SAVE" class="btn btn-primary">  
					<input type="hidden" id="hidBillingIDSave" name="hidBillingIDSave" value=""> 
				</form>
				
				<div id="alertSuccess" class="alert alert-success"> </div>
				
			   <div id="alertError" class="alert alert-danger"></div>
				
			   <br>
				<div id="divBillingGrid">
					<%
					    Billing billingObj = new Billing();
						out.print(billingObj.readBilling());
					%>
				</div>
				
				 
			</div>
		</div>
</div>
 
</body>
</html>