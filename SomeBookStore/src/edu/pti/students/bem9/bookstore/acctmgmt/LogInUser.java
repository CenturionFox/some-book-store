package edu.pti.students.bem9.bookstore.acctmgmt;

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

import edu.pti.students.bem9.bookstore.beans.UserSession;

/**
 * Logs a user in if they supply the correct username / password value.
 * 
 * @author  Bridger Maskrey (bem9@students.pti.edu)
 * @version 1.0.0
 */
public class LogInUser extends HttpServlet
{
	/* (non-Javadoc)
	 * The serial UID of the log in servlet.
	 */
	private static final long	serialVersionUID	= 98608654059178853L;

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * Checks to see whether or not the username / password combination supplied by the user is correct and
	 * 	logs the user in if necessary.
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		// Clear the add to cart error value.
		request.getSession().setAttribute("AddtoCartError", "");
		// Clear the log-in error value.
		request.getSession().setAttribute("LogInError", "");
		
		try
		{
			try
			{
				// Load the postgresql JDBC driver.
				Class.forName("org.postgresql.Driver");

				// Create the connecton, statement, and SQL builder.
				Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres", "postgres", "");
				Statement statement = connection.createStatement();
				StringBuilder statementBuilder = new StringBuilder();
				
				// Gather request attributes.
				String userName = request.getParameter("username");
				String password = request.getParameter("password");
				
				// Check that the username is neither null nor empty.
				if(userName == null || userName.isEmpty())
				{
					throw new ServletException("Invalid username.");
				}
				
				// Check that the password is neither null nor empty.
				if(password == null || password.isEmpty())
				{
					throw new ServletException("Invalid password.");
				}
				
				// Build the sql statement.
				statementBuilder.append("SELECT * FROM member WHERE member.email = '")
					.append(userName).append("' AND member.password = '").append(password).append("';");
				
				// Execute the statement.
				ResultSet result = statement.executeQuery(statementBuilder.toString());
				
				// Set values if necessary, or alert the user of an incorrect password.
				if(result.next())
				{
					UserSession session = new UserSession();
					session.setUsername(userName);
					
					request.getSession().setAttribute("USER", session);
					request.getSession().setAttribute("LogInError", "null");
				}
				else throw new ServletException("Incorrect password.");
			}
			// SQL error.
			catch (SQLException e)
			{
				throw new ServletException("ERROR: Database error.");
			}
			// Driver load error.
			catch (ClassNotFoundException e)
			{
				throw new ServletException("ERROR: No driver loaded.");
			}
		}
		// Log-in failure.
		catch (ServletException exception)
		{
			request.getSession().setAttribute("LogInError", exception.getMessage());
		}
		 
		// Redirect to the referring page.
		response.sendRedirect(request.getHeader("referer"));
	}
}
