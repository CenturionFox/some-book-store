package edu.pti.students.bem9.bookstore.acctmgmt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.pti.students.bem9.bookstore.beans.UserSession;

/**
 * Registers a new user in the database.
 * 
 * @author  Bridger Maskrey (bem9@students.pti.edu)
 * @version 1.0.0
 */
public class RegisterUser extends HttpServlet
{
	/* (non-Javadoc)
	 * The version ID of the registration servlet.
	 */
	private static final long	serialVersionUID	= 7612036020456085649L;

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * Validates and registers a user in the database.
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		// Clear the registration error output.
		request.getSession().setAttribute("RegisterError", "");
		
		try
		{
			try
			{
				// Load the postgresql JDBC driver.
				Class.forName("org.postgresql.Driver");
				
				// Create a new connection and statement object.
				Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres", "postgres", "");
				Statement statement = connection.createStatement();
				
				// Gather values from request.
				String userName = request.getParameter("email");	// NOT NULL
				String password = request.getParameter("pass");		// NOT NULL
				
				String firstName = request.getParameter("fname");	// NOT NULL
				String lastName = request.getParameter("lname");	// NOT NULL
				
				String addressLn1 = request.getParameter("billing_street"); // NOT NULL
				String city = request.getParameter("billing_city");			// NOT NULL
				String state = request.getParameter("billing_state");		// NOT NULL
				String zipCode = request.getParameter("billing_zip");			// NOT NULL
				
				String initials = request.getParameter("minits");
				String title = request.getParameter("title");
				String addressLn2 = request.getParameter("billing_ln_2");
				
				// Check that all required values were given.
				String[] notNullable = new String[]{userName, password, firstName, lastName, addressLn1, city, state, zipCode};
				
				for(String s : notNullable)
				{
					System.out.print(s + ", ");
					
					if(s == null || s.isEmpty())
					{
						throw new ServletException("Required values were not entered.");
					}
				}
				
				// Build insertion for member data.
				StringBuilder statementBuilder = new StringBuilder().append("INSERT INTO member ( email, password, fname, ")
					.append(initials != null && !initials.isEmpty() ? "minits, " : "").append("lname")
					.append(title != null && !title.isEmpty() ? ", title " : " ").append(") ")
					.append("VALUES ( '").append(userName).append("', '").append(password)
					.append("', '").append(firstName).append("', '")
					.append(initials != null && !initials.isEmpty() ? initials + "', '" : "")
					.append(lastName).append("'").append(title != null && !title.isEmpty() ? ", '" + title + "' " : " ")
					.append(");");

				// Update member data.
				statement.executeUpdate(statementBuilder.toString());
				
				// Build insertion for main address.
				statementBuilder.delete(0, statementBuilder.length()).append("INSERT INTO address ( street_ln1, ")
					.append(addressLn2 != null && !addressLn2.isEmpty() ? "street_ln2, " : "")
					.append("city, zip_code, state_code, email ) VALUES ( '")
					.append(addressLn1).append("', ")
					.append(addressLn2 != null && !addressLn2.isEmpty() ? "'" + addressLn2 + "', '" : "'")
					.append(city).append("', '").append(zipCode).append("', '").append(state)
					.append("', '").append(userName).append("' );");
				
				// Update main address.
				statement.executeUpdate(statementBuilder.toString());
				
				// Log the newly registered user in.
				UserSession session = new UserSession();
				session.setUsername(userName);
				
				request.getSession().setAttribute("USER", session);
				request.getSession().setAttribute("RegisterError", "");
				
				// Redirect to the main page.
				response.sendRedirect("/maskreybe/index.jsp");
			}
			// Database error.
			catch (SQLException sql)
			{
				throw new ServletException(sql.getMessage());
			}
			// Driver load error
			catch (ClassNotFoundException classNotFound)
			{
				throw new ServletException("Could not load required database drivers!");
			}
		}
		// Registration failed.
		catch(ServletException servlet)
		{
			request.getSession().setAttribute("RegisterError", "Unable to register: " + servlet.getMessage());
			
			// Redirect to registration page.
			response.sendRedirect("/maskreybe/jsp/register.jsp");
		}
	}
}
