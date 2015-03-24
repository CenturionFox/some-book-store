<div class="header_main">
	<div class="header_content">
		<h1>Some Book Store</h1>
	</div>
	<div class="header_contact">
		<table>
		<tr>
			<td>Phone:</td>
			<td>(412) 867-9300</td>
		</tr>
		<tr>
			<td>Email:</td>
			<td>contact@somebookstore.net</td>
		</tr>
		</table>
	</div>
</div>

<input type="hidden" id="logInErrVal" value="<%= session.getAttribute("LogInError") %>">

<div class="header_sticky" id="stickyHeader">
	<ul class="header_sticky_list" id="headerItems">
		<li><a href="/maskreybe/index.jsp"><div>Home</div></a></li>
		<li><a href="/maskreybe/jsp/catalog.jsp"><div>Browse the Catalog</div></a></li>
		<li><a href="/maskreybe/jsp/cart.jsp"><div>Cart
		<% int counter = 0;
		double price = 0;
		if(session != null && (session.getAttribute("USER") instanceof UserSession))
		{
			for(Book book : ((UserSession)session.getAttribute("USER")).getCart())
			{
				counter += book.getQuantity();
				price += (book.getPrice() * book.getQuantity());
			}
		}%>
		(<%= counter %>, $<%= String.format("%,.2f", price)%>)</div></a></li>
		<li><a href="/maskreybe/jsp/terms.jsp"><div>Contact / Terms</div></a></li>
	</ul>
	<div class="header_content" id="loginFormContainer">
	<form method="post" action="/maskreybe/login.do" id="loginForm">
		<%= session != null &&
			session.getAttribute("USER") != null &&
			(session.getAttribute("USER") instanceof UserSession) &&
			!((UserSession)session.getAttribute("USER")).getUsername().isEmpty() ? 
			"Logged In as <a href='/maskreybe/jsp/account.jsp'>" + session.getAttribute("USER") + "</a>&nbsp;&nbsp; <input type='button' name='showForm' value='Log Out' onClick=logOut()>" 
			: "<input type='button' name='showForm' value='Log In...' onClick='buildForm()'>\n<input type='button' name='Register' value='Register' onClick='registerHit()'>" %>
	</form>
	</div>
</div>