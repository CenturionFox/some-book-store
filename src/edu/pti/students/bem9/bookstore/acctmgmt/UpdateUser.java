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
 * Updates user information, namely their name and password, if the user supplied the
 * 	correct previous password.
 * 
 * @author Bridger Masrkey
 * @version 1.0.0.
 */
public class UpdateUser extends HttpServlet
{
	/* (non-Javadoc)
	 * The version ID for this servlet.
	 */
	private static final long	serialVersionUID	= 7612036020456085649L;

	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * Checks the input password, then updates necessary fields.
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		// Clear update error.
		request.getSession().setAttribute("UpdateError", "");
		
		try
		{
			try
			{
				// Joad postgresql jdbc drivers.
				Class.forName("org.postgresql.Driver");

				// Create a new connection, statement, and string builder.
				Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres", "postgres", "");
				Statement statement = connection.createStatement();
				StringBuilder statementBuilder = new StringBuilder();

				// Gather information from request.
				String currentPass = request.getParameter("currentpass");
				
				String password = request.getParameter("pass");

				String firstName = request.getParameter("fname");
				String lastName = request.getParameter("lname");

				String initials = request.getParameter("minits");
				String title = request.getParameter("title");

				UserSession user = ((UserSession) request.getSession().getAttribute("USER"));

				// If the user was successfully obtained
				if (user != null)
				{
					// Execute a statement to determine if te user entered the correct current password.
					ResultSet results = statement.executeQuery("SELECT * FROM member WHERE email = '" + user.getUsername() + "' AND password = '" + currentPass + "';");
					
					if(results.next())
					{
						// Build statement.
						if (!firstName.isEmpty())
						{
							statementBuilder.append("UPDATE member SET fname = '").append(firstName).append("' WHERE email = '")
								.append(user.getUsername()).append("';\n");
						}
	
						if (!lastName.isEmpty())
						{
							statementBuilder.append("UPDATE member SET lname = '").append(lastName).append("' WHERE email = '")
								.append(user.getUsername()).append("';\n");
						}
	
						if (!initials.isEmpty())
						{
							statementBuilder.append("UPDATE member SET minits = '").append(initials).append("' WHERE email = '")
								.append(user.getUsername()).append("';\n");
						}
	
						if (!title.isEmpty())
						{
							statementBuilder.append("UPDATE member SET title = '").append(title).append("' WHERE email = '")
								.append(user.getUsername()).append("';\n");
						}
	
						if (!password.isEmpty())
						{
							statementBuilder.append("UPDATE member SET password = '").append(password).append("' WHERE email = '")
								.append(user.getUsername()).append("';\n");
						}
						// Statement build end.
						
						statement.execute(statementBuilder.toString());
						
						// Alert user of successful update.
						request.getSession().setAttribute("UpdateError", "Information updated successfully!");
					}
					// Password incorrect.
					else
					{
						throw new ServletException("Current password was incorrect!");
					}
				}
				// User was null.
				else
				{
					throw new ServletException("No user is logged in to update!");
				}
			}
			// User object not valid.
			catch (ClassCastException exc)
			{
				throw new ServletException("User was invalid!");
			}
			// Database exception.
			catch (SQLException sql)
			{
				throw new ServletException(sql.getMessage());
			}
			// Driver load error.
			catch (ClassNotFoundException classNotFound)
			{
				throw new ServletException("Could not load required database drivers!");
			}
		}
		// Update failed.
		catch (ServletException servlet)
		{
			request.getSession().setAttribute("UpdateError", "Unable to update information: " + servlet.getMessage());
		}

		// Redirect to the referer page.
		response.sendRedirect(request.getHeader("referer"));
	}
}
