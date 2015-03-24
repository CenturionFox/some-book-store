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
 * 
 * @author Bridger Maskrey (bem9@students.pti.edu)
 *
 */
public class CreditCardAdd extends HttpServlet
{
	/* (non-Javadoc)
	 * The serial ID of this servlet.
	 */
	private static final long	serialVersionUID	= 4172876295076356362L;

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * Adds a new credit card to the database using information from the current user session and the values passed
	 * 	from the request object, but only if the credit card does not already exist within the database.
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		request.getSession().setAttribute("CardOperationError", "");
		
		try
		{
			try
			{
				// Load the postgres JDBC Driver.
				Class.forName("org.postgresql.Driver");
				
				// Open a new database connection and create a new statement object.
				Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres", "postgres", "");
				Statement statement = connection.createStatement();
				
				// The StringBuffer used to dynamically create select statements.
				StringBuffer statementBuilder = new StringBuffer();
				
				// Obtain the current user session. The likelihood that the stored object
				//	is an instance of UserSession is extremely high at this point; however,
				//	if casting fails it is handled. See the catch block "User Cast Failed".
				UserSession user = ((UserSession) request.getSession().getAttribute("USER"));

				if (user != null)
				{
					// Gather information from the request object.
					String cardNum = request.getParameter("cardNumber");
					String cardCSV = request.getParameter("cardCSV");
					
					// Begin building the selection check statement.
					//	cardnum is the primary key, so lets check that a card does not exist that shares that key.
					statementBuilder.append("SELECT * FROM credit_card WHERE cardnum = '").append(cardNum).append("';");
					// End statement building.
					
					// Execute the completed SQL query statement.
					ResultSet results = statement.executeQuery(statementBuilder.toString());
					
					if(!results.next())
					{
						// Clear the statement builder and begin building the update statement.
						statementBuilder.delete(0, statementBuilder.length()).append("INSERT INTO credit_card VALUES ('")
							.append(user.getUsername()).append("', '").append(cardNum).append("', '").append(cardCSV).append("');");
						
						// End building the update statement.
						
						// Execute the completed update statement.
						statement.executeUpdate(statementBuilder.toString());
						
						request.getSession().setAttribute("CardOperationError", "New card \"" + cardNum + "\" added successfully.");
					}
					// The credit card already exists in the database, so no need to add it again.
					else
					{
						throw new ServletException("The credit card is already present in the database.");
					}
				}
				// The user object could not be successfully obtained.
				else
				{
					throw new ServletException("No user is logged in to update!");
				}
			}
			// User cast failed
			catch (ClassCastException exc)
			{
				throw new ServletException("User was invalid!");
			}
			// SQL operation fail
			catch (SQLException sql)
			{
				throw new ServletException(sql.getMessage());
			}
			// Database driver load fail
			catch (ClassNotFoundException classNotFound)
			{
				throw new ServletException("Could not load required database drivers!");
			}
		}
		// Failed to add credit card.
		catch(ServletException exception)
		{
			// Set the card op error attribute to alert the user of the failure.
			request.getSession().setAttribute("CardOperationError", "Unable to add new credit card: " + exception.getMessage());
		}
		
		// Redirect to the previous page.
		response.sendRedirect(request.getHeader("referer"));
	}
}
