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
				<h2>Search Results</h2>
			</div>
			<hr>
			
			<div class="section">
				<hr>
				<form method="post" action="/maskreybe/search.do">
					<table>
						<tr>
							<td>Search:</td>
							<td><select name="searchType">
									<option value="0">Author</option>
									<option value="1">Volume Title</option>
									<option value="2">ISBN-13</option>
								</select></td>
							<td><input type="text" name="searchValue"></td>
							<td><input type="submit" value="Go"></td>
							<td><span id="searchVerifyOutput"><%= session.getAttribute("SearchError") != null ? session.getAttribute("SearchError") : "" %></span></td>
						</tr>
					</table>
				</form>
				<hr>
				<span id="addtoCartError"><%= session.getAttribute("AddtoCartError") != null ? session.getAttribute("AddtoCartError") : "" %></span>
			</div>
			
			<% List<Book> resultsList = null;
				if(session.getAttribute("SearchResults") != null && 
					(session.getAttribute("SearchResults") instanceof List))
				{
					resultsList = (List<Book>)session.getAttribute("SearchResults");
				}
				
				if(resultsList.size() > 0)
				{
					for(Book book : resultsList)
					{
						String isbn = book.getIsbn(); 
						
						UserSession user = (UserSession)session.getAttribute("USER"); 
						int remainingQuantity = book.getQuantity();
						
						if( user != null ) 
						{
							if(user.getCart().contains(book)) {
								Book cartedBook = null;
								for(Book b : user.getCart())
								{
									if(b.equals(book))
									{
										remainingQuantity -= b.getQuantity();
									}
								}
							}
						}
						
						boolean inStock = remainingQuantity > 0; %>
						<div class="section" id="booksection_<%= isbn %>">
							<form method="post" action="/maskreybe/addToCart.do" id="form_<%= isbn %>">
							<input type="hidden" name="bookIsbn" value="<%= isbn %>">
							<div><h2 style="display:inline-table">
									<%= book.getTitle() %>
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
										<td>
											<select name="quantity" id="quantity_<%= isbn %>" <%= inStock ? "" : "disabled" %> onClick="updatePrice('<%= isbn %>')">
												<% if(inStock) {											
													for(int i = 0; i < remainingQuantity;) {%>
														<option value="<%= ++i %>"><%= i %></option>
												<% 	}
												} %>
											</select>
											<input type="submit" value="Add to Cart" <%= inStock ? "" : "disabled" %>>
											<span id="price_<%= isbn %>">$<%= book.getPrice() %></span>
										</td>
									</tr>
									<tr>
										<td><%= book.getDescription() %></td>
									</tr>
									<tr>
										<td><% if(inStock) { %>
												In Stock! Quantity: <%= remainingQuantity %>
											<% } 
											else 
											{ %>
												Out of Stock.
											<% } %></td>
									</tr>
								</table>
								<input type="hidden" id="singleBookVal_<%= isbn %>" value="<%= book.getPrice() %>">
							</div>
							</form>
						</div>
					<% 	} %>
			
						<div class="section">
							<hr>
							<div><a href="/maskreybe/jsp/catalog.jsp">Return to Catalog</a></div>
							<hr>
						</div>
			
				<% }
				else
				{ %>
					<div class="section">
						<hr>
						<div>No results found!</div>
						<div><a href="/maskreybe/jsp/catalog.jsp">Return to Catalog</a></div>
						<hr>
					</div>
				<% }%>
			</div>
		
		<%@include file="../include/footer.html"%>
	</body>
</html>