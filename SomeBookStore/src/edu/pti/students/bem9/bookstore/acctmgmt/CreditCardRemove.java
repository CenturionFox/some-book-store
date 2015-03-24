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

/**
 * Removes the credit card specified by the "cardNumber" parameter set in the request object.
 * Note that this servlet uses the "doPost" method.
 * 
 * @author  Bridger Maskrey (bem9@students.pti.edu)
 * @version 1.0.0
 */
public class CreditCardRemove extends HttpServlet
{
	/* (non-Javadoc)
	 * The serial ID of this servlet.
	 */
	private static final long	serialVersionUID	= -7295333344769128607L;

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * Executes an update statement deleting any address from the database where the address ID is equivalent to the value
	 * of the ID field passed as the "id" parameter in the request object.
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		// Clear the address op error session attribute.
		request.getSession().setAttribute("CardOperationError", "");
		try
		{
			try
			{
				// Load the postgres jdbc drivers.
				Class.forName("org.postgresql.Driver");
				
				// Open a new connection and create the statement object.
				Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres", "postgres", "");
				Statement statement = connection.createStatement();
				
				// Read the "cardNumber" parameter from the request object.
				String cardNum = request.getParameter("cardNumber");
				
				// Deletes all cards from the database where the card number is equivalent to
				//	the passed cardNumber variable.  Note that there is supposed to be and will likely be
				//	only a single match to this delete statement.  No checking will be done to
				//	ensure this, as the database itself should handle that.
				statement.executeUpdate("DELETE FROM credit_card WHERE cardnum = '" + cardNum + "';");
				
				request.getSession().setAttribute("CardOperationError", "Successfully removed the selected card.");
			}
			// SQL Operation Fail
			catch(SQLException exception)
			{
				throw new ServletException("Unable to remove credit card: " + exception.getMessage());
			}
			// Driver Load Fail (Class Not Found)
			catch(ClassNotFoundException exception)
			{
				throw new ServletException("Unable to load database drivers!");
			}
		}
		// Failed to delete address
		catch(ServletException exception)
		{
			// Set the card op error attribute in the session so that the appropriate feedback is supplied to the user.
			request.getSession().setAttribute("CardOperationError", "Unable to remove card: " + exception.getMessage());
		}
		
		// Redirect back to wherever we first executed from.
		response.sendRedirect(request.getHeader("referer"));
	}
}
