package edu.pti.students.bem9.bookstore.ecommerce;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.pti.students.bem9.bookstore.beans.Book;
import edu.pti.students.bem9.bookstore.beans.UserSession;

public class AddBookToCart extends HttpServlet
{
	private static final long	serialVersionUID	= -700947035115751116L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		request.getSession().setAttribute("AddtoCartError", "");
		
		try
		{
			Object object = request.getSession().getAttribute("USER");
			
			if(object == null || !(object instanceof UserSession) || ((UserSession)object).getUsername().isEmpty())
			{
				throw new ServletException("Please log in to add items to your cart.");
			}
			
			UserSession session = (UserSession) object;
			
			int quantity = 0;
			
			try
			{
				quantity = Integer.parseInt(request.getParameter("quantity"));
			}
			catch(NumberFormatException nfe)
			{
				throw new ServletException("Unable to parse number: " + nfe.getMessage());
			}
			
			String isbn = request.getParameter("bookIsbn");
			
			Book book = null;
			
			try
			{
				Class.forName("org.postgresql.Driver");

				Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres", "postgres", "");
				Statement statement = connection.createStatement();
				StringBuilder statementBuilder = new StringBuilder();
				
				statementBuilder.append("SELECT book.*, author.*, title.title AS book_title ")
					.append("FROM book INNER JOIN title ON title.isbn = book.isbn ")
					.append("AND book.isbn = '").append(isbn).append("' INNER JOIN author ")
					.append("ON author.id = title.author_id;");
				
				ResultSet results = statement.executeQuery(statementBuilder.toString());
				
				if(results.next())
				{
					book = Book.readFromResult(results);
					
					book.setQuantity(quantity);
				}
				else
				{
					throw new ServletException("Somehow, the requested book was nonexistant. Sorry fo any inconvenience!");
				}
			}
			catch(ClassNotFoundException exception)
			{
				throw new ServletException("Unable to load database drivers.");
			}
			catch (SQLException e)
			{
				throw new ServletException("Unable to add to cart: " + e.getMessage());
			}
			
			session.addCartItem(book);	
			request.getSession().setAttribute("USER", session);
		} 
		catch (ServletException exception)
		{
			request.getSession().setAttribute("AddtoCartError", exception.getMessage());
		}
		
		response.sendRedirect(request.getHeader("referer"));
	}
}
