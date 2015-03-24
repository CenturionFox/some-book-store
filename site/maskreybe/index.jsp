<!DOCTYPE html>
<!-- index.jsp -->
<!--
	Project: E-Commerce Website
			 PTI > Computer Programming > Database (SSD750)
	
	Main Page
	
	Author:  Bridger Maskrey
	Version: 1.0.0
	Date:	 2014-09-03
-->
<%@ page import="java.util.*" %>
<%@ page import="edu.pti.students.bem9.bookstore.beans.*" %>
<html>
	
	<head>
		<title>SomeBookStore.net | Your Premier Source for Books Online!</title>

		<%@include file="include/meta.html"%>

	</head>
	
	<body onLoad="sizeBodyOnLoad()" onResize="sizeBodyOnLoad()">
		<%@include file="include/header.jsp"%>
				
		<div class="main" id="main">
			<hr>
			<div class="centered"><h1>Welcome to SomeBookStore.net</h1></div>
			<hr>
			<div class="section" id="welcomeSection">
				<div><h2>The Premier Stop for Online Book Shopping</h2></div>
				<hr>
				<div><p>Some Book Store is your first and foremost spot for hard-copy books online.  We offer a large selection of
					hardcover and paperback books, all shipped directly to you absolutely free*. With our wide selection of genres from action to romance
					and everything in between, we assure you that you will be able to find something for yourself.</p>
					<p>Check out our catalog by clicking the "Browse the Catalog" button in the header bar.  Just remember that you
					will need an account to complete your order or save your cart for later! Until such a time, feel free to just browse freely.</p></div>
				<div>
				<span style="font-size: 0.6em; font-style: oblique; margin-top: 10px;">*Shipping times may vary.  See cart for details.</span></div>
			</div>
			<br>
			<hr>
			<div class="centered"><h2 style="padding-left: 20px;">Site Updates / News</h2></div>
			<hr>
			<br>
			<%@include file="include/news.html"%>
			
		</div>
		
		<%@include file="include/footer.html"%>
	</body>
	
</html>