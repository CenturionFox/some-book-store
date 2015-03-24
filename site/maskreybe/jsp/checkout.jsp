<!DOCTYPE html>
<!-- checkout.jsp -->
<!--
	Project: E-Commerce Website
			 PTI > Computer Programming > Database (SSD750)
	
	Checkout page stuff
	
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
			
			<% 
			UserSession checkoutUser = null;
			if(session != null && session.getAttribute("USER") != null &&
					session.getAttribute("USER") instanceof UserSession && !
					((UserSession)session.getAttribute("USER")).getUsername().isEmpty())
			{
				checkoutUser = (UserSession)session.getAttribute("USER");
			}%>
			
			<hr>
			<div class="centered">
				<h1>Checkout</h1>
			</div>
			<hr>
			
			<div class="section">
				<div><h2>Cart</h2></div>
				<hr>
				
				<%
				if(checkoutUser != null)
				{ %>
					<div>
					<table style="width: 100%;">
					<%
					List<Book> cart = checkoutUser.getCart();
					
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
						} %>
						
						</table>
						</div>
						<hr>
						<div style="text-align: right;"><h2>Total: $<%= String.format("%,.2f", totalPrice) %> </h2></div>
						<br><br>
						<%
						List<CreditCard> creditCardList = (List<CreditCard>)session.getAttribute("CARDS");
						List<Address> addressList = (List<Address>)session.getAttribute("ADDRESSES");
						%>
						<table style="width: 100%;">
							<tr>
								<td style="width: 50%; vertical-align: top; text-align: center;">
									<h2>Credit Card Information</h2>
									<hr>
									<center>
									<%@include file="../include/cardUpdater.jsp"%>
									</center>
								</td>
								<td style="width: 50%; vertical-align: top; text-align: center">
									<div><h2>Address Information</hr></div>
									<hr>
									<center>
									<%@include file="../include/addressUpdater.jsp"%>
									</center>
								</td>
							</tr>
						</table>
						<hr>
						<div style="text-align: center;">
							<input type="button" value="Complete Checkout" onClick="submitCheckoutForm()">
						</div>
						<%
					}
					else
					{
						%>
						
						<div><h2>Looks like your cart is empty!</h2></div>
						<hr>
						<div><p>How about you <a href="/maskreybe/jsp/catalog.jsp">browse</a> a bit and see if there's something you like.</p></div>
						
						<%
					}
				}
				else
				{
					%>
					
					<div><h2>You are not logged in.</h2></div>
					<hr>
					<div><p>Please log in to access your cart.</p></div>
					
					<%
				}
				%>
			</div>
		
		</div>
		
		<%@include file="../include/footer.html"%>
	</body>
</html>