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
		
		<%
		Order order = (Order)session.getAttribute("ORDER");
		%>
		
		<div class="main" id="main">
			
			<hr>
			<div class="centered">
				<h1>Order Successful!</h1>
			</div>
			<hr>
		
			<div class="section">
				<div>
					<h2>Your order has passed through.</h2>
				</div>
				<hr>
				<div>
					<p>Order ID: <%= order.getOrderId() %></p>
				</div>
				<hr>
				<div>
				<table style="width: 100%;">
				<%
				List<Book> cart = order.getItems();
				
				int bookCounter = 0;
				double totalPrice = 0;
				
				if(cart.size() > 0)
				{
					for(Iterator<Book> iterator = cart.iterator(); iterator.hasNext();)
					{
						bookCounter++;
						Book book = iterator.next();
						String isbn = book.getIsbn();
						int numInCart = book.getQuantity();
						
						totalPrice += book.getPrice() * numInCart;
						
						if(numInCart <= 0)
						{
							iterator.remove();
							continue;
						}
						%>
						

						  <tr>
							<td>
								<h2 style="display:inline-table;"><%= bookCounter %>.</h2>
							</td>
							<td>
								<h2 style="display:inline;"><%= book.getTitle() %></h2> - <%= book.getAuthor() %>
							</td>
							<td>
								Price: $<%= String.format("%,.2f", book.getPrice() * numInCart) %>
							</td>
							<td>
								Quantity: <%= numInCart %>
							</td>
						  </tr>
						
						<%
					} 
				} %>
				</table>
				</div>
				<hr>
				<div style="text-align: right;"><h2>Total: $<%= String.format("%,.2f", totalPrice) %> </h2></div>
			</div>
		</div>
		
		<%@include file="../include/footer.html"%>
	</body>
</html>