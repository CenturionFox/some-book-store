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
 * Removes the address specified by the "id" parameter set in the request object.
 * Note that this servlet uses the "doPost" method.
 * 
 * @author  Bridger Maskrey (bem9@students.pti.edu)
 * @version 1.0.0
 */
public class AddressRemove extends HttpServlet
{
	/* (non-Javadoc)
	 * The serial ID of this servlet.
	 */
	private static final long	serialVersionUID	= -7295333344769128607L;

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * Executes an update statement deleting any address from the database where the address ID is equivalent to the value
	 * 	of the ID field passed as the "id" parameter in the request object.
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		// Clear the address op error session attribute.
		request.getSession().setAttribute("AddressOperationError", "");
		try
		{
			try
			{
				// Load the postgres jdbc drivers.
				Class.forName("org.postgresql.Driver");
				
				// Open a new connection and create the statement object.
				Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres", "postgres", "");
				Statement statement = connection.createStatement();
				
				// Read the "id" parameter from the request object and parse as an integer.
				//	If the integer parse fails the NumberFormatException will be caught
				//	and a new ServletException will be thrown (see "ID Parse Fail" catch block)
				int id = Integer.parseInt(request.getParameter("id"));
				
				// Deletes all addresses from the database where the address ID is equivalent to
				//	the passed ID variable.  Note that there is supposed to be and will likely be
				//	only a single match to this delete statement.  No checking will be done to
				//	ensure this, as the database itself should handle that.
				statement.executeUpdate("DELETE FROM address WHERE id = " + id + ";");
				
				request.getSession().setAttribute("AddressOperationError", "Successfully removed the address.");
			}
			// SQL Operation Fail
			catch(SQLException exception)
			{
				throw new ServletException("Unable to remove address: " + exception.getMessage());
			}
			// Driver Load Fail (Class Not Found)
			catch(ClassNotFoundException exception)
			{
				throw new ServletException("Unable to load database drivers!");
			}
			// ID Parse Fail
			catch(NumberFormatException exception)
			{
				throw new ServletException("Supplied address ID was incorrectly formatted. Unable to remove address.");
			}
		}
		// Failed to delete address
		catch(ServletException exception)
		{
			// Set the address op error attribute in the session so that the appropriate feedback is supplied to the user.
			request.getSession().setAttribute("AddressOperationError", "Unable to remove address: " + exception.getMessage());
		}
		
		// Redirect back to wherever we first executed from.
		response.sendRedirect(request.getHeader("referer"));
	}
}
