package edu.pti.students.bem9.bookstore.ecommerce;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.pti.students.bem9.bookstore.beans.Book;
import edu.pti.students.bem9.bookstore.beans.Order;
import edu.pti.students.bem9.bookstore.beans.UserSession;

/**
 * Finalizes and submits an order.  This includes incrementing the max value of Order.order_id to prevent
 * 	conflicts, adding a new address if necessary, adding a new credit card if necessary, inserting the
 * 	order data into the database, inserting the order item data for each ordered item into the database,
 *  and lastly resetting the cart. This servlet redirects to the compeltion page, but if an error occurs no
 *  attempt is made to handle it.
 *  
 * @author  Bridger Maskrey (bem9@students.pti.edu)
 * @version 1.0.0
 */
public class SubmitOrder extends HttpServlet
{
	private static final long	serialVersionUID	= 2634616549494826044L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		try
		{
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres", "postgres", "");
			Statement statement = connection.createStatement();
			StringBuilder statementBuilder = new StringBuilder();
			
			String addressLn1 = request.getParameter("addressLine1");
			String addressLn2 = request.getParameter("addressLine2");
			String city = request.getParameter("city");
			String state = request.getParameter("state");
			String zip = request.getParameter("zip");
			
			String cardnum = request.getParameter("cardNumber");
			String csvnum = request.getParameter("cardCSV");
			
			String addressId = null;
			
			Object sessionObject = request.getSession().getAttribute("USER");
			UserSession session = null;
			
			if(sessionObject != null && sessionObject instanceof UserSession)
			{
				session = (UserSession)sessionObject;
			}
			
			if(session == null) throw new ServletException("Unable to get user.");
			
			List<Book> cart = session.getCart();

			// Increment the max order number.
			statementBuilder.append("SELECT MAX(order_no) FROM orders;");
			ResultSet results = statement.executeQuery(statementBuilder.toString());
			
			String maxOrders = "000000000";
			
			if(results.next())
			{
				maxOrders = results.getString("max");
			}
			
			if(maxOrders != null && maxOrders.equals("FFFFFFFF"))
			{
				throw new ServletException("Target number is larger than Long.MAX_VALUE");
			}
			
			long l = 0;
			
			if(maxOrders!= null)
				l = Long.parseLong(maxOrders, 16);
			
			String temp = Long.toHexString(++l);
			
			String orderId = new String();
			for(int i = 0; i < 8 - temp.length(); i++)
			{
				orderId = orderId + "0";
			}
			orderId = orderId + temp;
			
			statementBuilder.delete(0, statementBuilder.length());
			
			// Figure out the address
			statementBuilder.append("SELECT id FROM address WHERE address.street_ln1 = '").append(addressLn1).append("' ");
			
			if(!addressLn2.equals(""))
				statementBuilder.append("AND address.street_ln2 = '").append(addressLn2).append("' ");
			
			statementBuilder.append("AND address.city = '").append(city).append("' AND address.zip_code = '")
				.append(zip).append("' AND address.state_code = '").append(state).append("';");
			
			results = statement.executeQuery(statementBuilder.toString());
			
			statementBuilder.delete(0, statementBuilder.length());
			
			if(results.next())
			{
				addressId = results.getString("id");
			}
			else
			{
				statementBuilder.append("INSERT INTO address (street_ln1, street_ln2, city, zip_code, state_code, email) VALUES ('")
					.append(addressLn1).append("', '").append(addressLn2).append("' , '").append(city).append("', '").append(zip)
					.append("', '").append(state).append("', '").append(session.getUsername()).append("');");
				
				statement.executeUpdate(statementBuilder.toString());
				
				statementBuilder.delete(0, statementBuilder.length());
				
				statementBuilder.append("SELECT id FROM address WHERE address.street_ln1 = '").append(addressLn1).append("' ");
				
				if(!addressLn2.equals(""))
					statementBuilder.append("AND address.street_ln2 = '").append(addressLn2).append("' ");
				
				statementBuilder.append("AND address.city = '").append(city).append("' AND address.zip_code = '")
					.append(zip).append("' AND address.state_code = '").append(state).append("';");
				
				results = statement.executeQuery(statementBuilder.toString());
				
				statementBuilder.delete(0, statementBuilder.length());
				
				if(results.next())
				{
					addressId = results.getString("id");
				}
				else
				{
					throw new ServletException("Something went wrong with addresses.");
				}
			}
			
			// Figure out the credit card
			statementBuilder.append("SELECT * FROM credit_card WHERE cardnum = '").append(cardnum).append("';");
			
			results = statement.executeQuery(statementBuilder.toString());
			
			if(!results.next())
			{
				statementBuilder.delete(0, statementBuilder.length());
				statementBuilder.append("INSERT INTO credit_card VALUES ('").append(session.getUsername())
					.append("', '").append(cardnum).append("', '").append(csvnum).append("');");
				
				statement.executeUpdate(statementBuilder.toString());
			}
			
			statementBuilder.delete(0, statementBuilder.length());
			
			// Create the order and its subitems
			statementBuilder.append("INSERT INTO orders VALUES ('").append(orderId).append("', ").append(addressId).append(", '").append(cardnum).append("');");
			
			statement.executeUpdate(statementBuilder.toString());
			
			ArrayList<Book> orderItems = new ArrayList<Book>();
			
			// Create the order subitems
			for(Book book : cart)
			{	
				statementBuilder.delete(0, statementBuilder.length());
				statementBuilder.append("INSERT INTO order_item VALUES ('").append(book.getIsbn()).append("', '")
					.append(orderId).append("', ").append(book.getQuantity()).append(");");
				
				statement.executeUpdate(statementBuilder.toString());
				
				statementBuilder.delete(0, statementBuilder.length());
				statementBuilder.append("SELECT stock FROM book WHERE book.isbn = '").append(book.getIsbn()).append("';");
				
				results = statement.executeQuery(statementBuilder.toString());
				
				if(results.next())
				{
					int qty = results.getInt("stock");
					
					qty -= book.getQuantity();
					
					if(qty < 0)
					{
						throw new ServletException("Whoops, concurrent access?");
					}
					
					statementBuilder.delete(0, statementBuilder.length());
					statementBuilder.append("UPDATE book SET stock = ").append(qty).append(" WHERE isbn = '").append(book.getIsbn()).append("';");
					
					statement.executeUpdate(statementBuilder.toString());
				}
				
				orderItems.add(book);
			}
			
			// Create a new "Order" object
			Order order = new Order();
			
			order.setItems(orderItems);
			order.setOrderId(orderId);
			
			request.getSession().setAttribute("ORDER", order);
			
			session.clearCart();
			
			response.sendRedirect("/maskreybe/jsp/completion.jsp");
		}
		catch (ClassNotFoundException | SQLException | NumberFormatException e)
		{
			throw new ServletException(e);
		}
		
	}
}
