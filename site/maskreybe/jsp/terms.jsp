<!DOCTYPE html>
<!-- terms.jsp -->
<!--
	Project: E-Commerce Website
			 PTI > Computer Programming > Database (SSD750)
	
	Terms and Stuff Page
	
	Author:  Bridger Maskrey
	Version: 1.0.0
	Date:	 2014-09-30
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
			
			<hr>
			<div class="centered">
				<h1>Terms and Privacy</h1>
			</div>
			<hr>
		
			<div class="section">
				<div>
					<h2>Under Construction!</h2>
				</div>
				<hr>
				<div>
					<p>Huh. Looks like there's nothing here yet.</p>
				</div>
			</div>
			
			<div class="section">
				<div>
					<a href="#cookies"></a>
				</div>
			</div>
		</div>
		
		<%@include file="../include/footer.html"%>
	</body>
</html>