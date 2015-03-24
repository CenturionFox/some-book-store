<!DOCTYPE html>
<!-- register.jsp -->
<!--
	Project: E-Commerce Website
			 PTI > Computer Programming > Database (SSD750)
	
	Registration Page
	
	Author:  Bridger Maskrey
	Version: 1.0.0
	Date:	 2014-09-22
-->
<%@ page import="java.util.*" %>
<%@ page import="edu.pti.students.bem9.bookstore.beans.*" %>
<html>
	
	<head>
	
		<title>SomeBookStore.net | Your Premier Source for Books Online!</title>
		
		<%@include file="../include/meta.html"%>
		<jsp:useBean class="edu.pti.students.bem9.bookstore.beans.UserInformation" id="user" scope="page"/>
	</head>
	
	<body onLoad="sizeBodyOnLoad()" onResize="sizeBodyOnLoad()">
		<%@include file="../include/header.jsp"%>
		
		<%
		user.loadFromDatabase(((UserSession)session.getAttribute("USER")).getUsername());
		List<CreditCard> creditCardList = user.getCardList();
		List<Address> addressList = user.getAddressList();
		
		session.setAttribute("AddressOperationError", "");
		session.setAttribute("CardOperationError", "");
		%>
		
		<div class="main" id="main">
		
			<hr>
				<div class="centered">
					<h1>Edit User Information</h1>
				</div>
			<hr>
		
			<div class="section">
				<div>
					<p>Edit your name, password, addresses, and credit cards here.</p>
				</div>
				<hr>
				<div>
				<table style="width: 100%;">
				<tr>
					<td style="width: 33.33333333%; text-align: center; vertical-align: top;">
					<h2>Personal Information</h2>
					<hr>
					<center>
					<form id="updateInformationForm" method="post" action="/maskreybe/updateInfo.do">
						<table>
						<tr>
							<td>Email Address:</td>
							<td><span><%= user.getEmail() %></span></td>
						</tr>
						<tr>
							<td>Current Password*:</td>
							<td><input type="password" id="currentpass" name="currentpass"></td>
						</tr>
						<tr>
							<td>New Password:</td>
							<td><input type="password" id="pass" name="pass"></td>
						</tr>
						<tr>
							<td>Repeat Password:</td>
							<td><input type="password" id="passval" name="passval"></td>
						</tr>
						<tr><td>&nbsp;</td></tr>
						<tr>
							<td>First Name:</td>
							<td><input type="text" id="fname" name="fname" value="<%= user.getFname() %>"></td>
						</tr>
						<tr>
							<td>Middle Initial(s):</td>
							<td><input type="text" id="minits" name="minits" value="<%= user.getMinits() %>"></td>
						</tr>
						<tr>
							<td>Last Name:</td>
							<td><input type="text" id="lname" name="lname" value="<%= user.getLname() %>"></td>
						</tr>
						<tr>
							<td>Title:</td>
							<td><input type="text" id="title" name="title" value="<%= user.getTitle() %>"></td>
						</tr>
						<tr><td>&nbsp;</td></tr>
						<tr>
							<td colspan="4"><input type="reset" value="Reset All Fields"><input type="button" value="Update Information" onClick="submitUpdate()"></td>
						</tr>
						<tr>
							<td colspan="4"><span id="jsUpdateOutput"><%= session.getAttribute("UpdateError") != null ? session.getAttribute("UpdateError") : "" %></span></td>
						</tr>
						</table>
					</form>
					</center>
					</td>
					<td style="width: 33.33333333%; text-align: center; vertical-align: top;">
						<h2>Credit Card Information</h2>
						<hr>
						<center>
						<%@include file="../include/cardUpdater.jsp"%>
						</center>
					</td>
					<td style="width: 33.33333333%; text-align: center; vertical-align: top;">
						<h2>Address Information</h2>
						<hr>
						<center>
						<%@include file="../include/addressUpdater.jsp"%>
						</center>
					</td>
				</tr>
				</table>
						
					<span style="font-size: 0.6em; font-style: oblique; margin-top: 10px;">*Required</span>
				</div>
			</div>
		</div>
		
		<%@include file="../include/footer.html"%>
	</body>
</html>