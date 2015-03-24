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

	</head>
	
	<body onLoad="sizeBodyOnLoad()" onResize="sizeBodyOnLoad()">
		<%@include file="../include/header.jsp"%>
				
		<div class="main" id="main">
			<div class="section">
				<div>
						<h2>Registration</h2>
				</div>
				<hr>
				<div>
					<p>Thanks for choosing to create an account with us!  Before you can begin shopping, we'll need some information from you.
						This information is strictly confidential, and <a href="/maskreybe/jsp/terms.jsp">we will not share it with any third parties</a>.
						</p>
				</div>
				<hr>
				<div>
					<form id="registrationForm" method="post" action="/maskreybe/register.do">
						<table>
						<tr>
							<td>Email Address*:</td>
							<td><input type="text" id="email" name="email" placeholder="your@email.here"></td>
						</tr>
						<tr>
							<td>Password*:</td>
							<td><input type="password" id="pass" name="pass"></td>
						</tr>
						<tr>
							<td>Repeat Password*:</td>
							<td><input type="password" id="passval" name="passval"></td>
						</tr>
						<tr><td>&nbsp;</td></tr>
						<tr>
							<td>First Name*:</td>
							<td><input type="text" id="fname" name="fname" placeholder="John"></td>
						</tr>
						<tr>
							<td>Middle Initial(s):</td>
							<td><input type="text" id="minits" name="minits"></td>
						</tr>
						<tr>
							<td>Last Name*:</td>
							<td><input type="text" id="lname" name="lname" placeholder="Smith"></td>
						</tr>
						<tr>
							<td>Title:</td>
							<td><input type="text" id="title" name="title"></td>
						</tr>
						<tr><td>&nbsp;</td></tr>
						<tr>
							<td>Billing Address (Line 1)*:</td>
							<td><input	type="text"	id="billing_street"	name="billing_street"	placeholder="Street Address"></td>
						</tr>
						<tr>
							<td>Billing Address (Line 2):</td>
							<td colspan="3"><input type="text" id="billing_ln_2" name="billing_ln_2"></td>
						</tr>
						<tr>
							<td>Billing City / State*:</td>
							<td><input	type="text"	id="billing_city"	name="billing_city"		placeholder="City"></td>
							<td><select id="billing_state" name="billing_state">
								<option value="">Choose a State...</option>
								<option value="AL">Alabama</option>
								<option value="AK">Alaska</option>
								<option value="AZ">Arizona</option>
								<option value="AR">Arkansas</option>
								<option value="CA">California</option>
								<option value="CO">Colorado</option>
								<option value="CT">Connecticut</option>
								<option value="DE">Delaware</option>
								<option value="DC">District of Columbia</option>
								<option value="FL">Florida</option>
								<option value="GA">Georgia</option>
								<option value="HI">Hawaii</option>
								<option value="ID">Idaho</option>
								<option value="IL">Illinois</option>
								<option value="IN">Indiana</option>
								<option value="IA">Iowa</option>
								<option value="KS">Kansas</option>
								<option value="KY">Kentucky</option>
								<option value="LA">Louisiana</option>
								<option value="ME">Maine</option>
								<option value="MD">Maryland</option>
								<option value="MA">Massachusetts</option>
								<option value="MI">Michigan</option>
								<option value="MN">Minnesota</option>
								<option value="MS">Mississippi</option>
								<option value="MO">Missouri</option>
								<option value="MT">Montana</option>
								<option value="NE">Nebraska</option>
								<option value="NV">Nevada</option>
								<option value="NH">New Hampshire</option>
								<option value="NJ">New Jersey</option>
								<option value="NM">New Mexico</option>
								<option value="NY">New York</option>
								<option value="NC">North Carolina</option>
								<option value="ND">North Dakota</option>
								<option value="OH">Ohio</option>
								<option value="OK">Oklahoma</option>
								<option value="OR">Oregon</option>
								<option value="PA">Pennsylvania</option>
								<option value="PR">Puerto Rico</option>
								<option value="RI">Rhode Island</option>
								<option value="SC">South Carolina</option>
								<option value="SD">South Dakota</option>
								<option value="TN">Tennessee</option>
								<option value="TX">Texas</option>
								<option value="UT">Utah</option>
								<option value="VT">Vermont</option>
								<option value="VI">Virgin Islands</option>
								<option value="VA">Virginia</option>
								<option value="WA">Washington</option>
								<option value="WV">West Virginia</option>
								<option value="WI">Wisconsin</option>
								<option value="WY">Wyoming</option>
								</select></td>
								<td><input type="text" id="billing_zip" name="billing_zip" placeholder="Zip Code" style="width:5em" onKeyPress="return forceNumeric(event)"></td>
							</tr>
							<tr><td colspan="4">&nbsp;</td></tr>
							<tr>
								<td colspan="4"><input type="reset" value="Reset All Fields"><input type="button" value="Register" onClick="submitRegistration()"></td>
							</tr>
							<tr>
								<td colspan="4"><span id="jsRegisterOutput" style="font-weight:bold;color:red;"><%= session.getAttribute("RegisterError") != null ? session.getAttribute("RegisterError") : "" %></span></td>
							</tr>
						</table>
					</form>
						
					<span style="font-size: 0.6em; font-style: oblique; margin-top: 10px;">*Required</span>
				</div>
			</div>
		</div>
		
		<%@include file="../include/footer.html"%>
	</body>
</html>