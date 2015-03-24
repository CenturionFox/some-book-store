<!DOCTYPE html>
<!-- cart.jsp -->
<!--
	Project: E-Commerce Website
			 PTI > Computer Programming > Database (SSD750)
	
	Catalog Items Page
	
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

		<jsp:useBean class="edu.pti.students.bem9.bookstore.beans.Catalog" id="catalog" scope="page"/>
	</head>
	
	<body onLoad="sizeBodyOnLoad()" onResize="sizeBodyOnLoad()">
		<%@include file="../include/header.jsp"%>
				
		<div class="main" id="main">
		
			<hr>
			<div class="centered">
				<h2>Cart</h2>
			</div>
			<hr>
		
			<%
			catalog.loadList();
			Object user = session.getAttribute("USER");
			if(user == null || !(user instanceof UserSession) || ((UserSession)user).getUsername().isEmpty())
			{
				%>
				
				<div class="section">
					<div><h2>You are not logged in.</h2></div>
					<hr>
					<div><p>Please log in to access your cart.</p></div>
				</div>
				
				<%
			}
			else
			{			
				List<Book> cart = ((UserSession)user).getCart();
				
				int bookCounter = 0;
				
				if(cart.size() > 0)
				{
					
					for(Iterator<Book> iterator = cart.iterator(); iterator.hasNext();)
					{
						bookCounter++;
						Book book = iterator.next();
						String isbn = book.getIsbn();
						int numInCart = book.getQuantity();
						
						Book catalogIndex = null;
						for(Book book1 : catalog.getBookList())
						{
							if(book1.equals(book))
							{
								catalogIndex = book1;
								break;
							}
						}
						
						if(numInCart <= 0)
						{
							iterator.remove();
							continue;
						}
						%>
					
						<div class="section" id="booksection_<%= isbn %>">
							<form method="post" action="/maskreybe/updateCartItem.do" id="form_<%= isbn %>">
								<input type="hidden" name="bookIsbn" value="<%= isbn %>">
								<div><h2 style="display:inline-table">
										<%= bookCounter %>. <%= book.getTitle() %>
									</h2>
									<table>
									<tr>
										<td>ISBN: </td>
										<td><%= isbn %> </td>
									</tr>
									<tr>
										<td> Author: </td>
										<td><%= book.getAuthor() %></td>
									</tr>
									</table>
								</div>
								<hr>
								<div>
									<table>
									  <tr>
										<td rowspan="3"><img width="100" height="125" alt="<%= isbn %>" src="../images/book/<%= isbn %>.png"></td>
										<td><select name="quantity" id="quantity_<%= isbn %>" onClick="updatePrice('<%= isbn %>')">
													<%											
													for(int i = 0; i <= catalogIndex.getQuantity();) {%>
														<option <%= i == 1 ? "selected" : "" %> value="<%= i %>"><%= i++ %></option><%
													} %>
												</select>
												<input type="submit" value="Set New Quantity">
												<span id="price_<%= isbn %>">$<%= String.format("%,.2f", book.getPrice() * numInCart) %></span></td>
									  </tr>
									  <tr>
										<td><%= book.getDescription() %></td>
									  </tr>
									  <tr>
										<td>Total Quantity: <%= numInCart %></td>
									  </tr>
									</table>
								</div>
							</form>
						</div>
					
						<%
					} %>
				<hr>
				<div class="centered">
				<form method="post" action="/maskreybe/checkout.do">
					<input type="submit" value="Complete Purchase">
				</form>
				</div>
				<hr>
				<%
				}
				else
				{
				%>
					<div class="section">
					<div><h2>Looks like your cart is empty!</h2></div>
					<hr>
					<div><p>How about you <a href="/maskreybe/jsp/catalog.jsp">browse</a> a bit and see if there's something you like.</p></div>
				</div>
				<%
				}
			} %>
		</div>
		
		<%@include file="../include/footer.html"%>
	</body>
</html>