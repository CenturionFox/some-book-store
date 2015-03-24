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
 * Updates a specific address in the database.  Uses a variable amount of 
 * 	"UPDATE {table} SET {field} = '{value}' WHERE {field1} = {idValue}"
 * 	statements strung into a single execution.
 * 
 * @author 	Bridger Maskrey (bem9@students.pti.edu)
 * @version 1.0.0
 */
public class AddressUpdate extends HttpServlet
{
	/* (non-Javadoc)
	 * The serial version id of address update.
	 */
	private static final long	serialVersionUID	= 6599122586683421168L;
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * Executes a multi-part update statement that updates all changed values in 
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
				// Load the postgresql JDBC driver.
				Class.forName("org.postgresql.Driver");
				
				// Create a connection, statement, and query builder.
				Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres", "postgres", "");
				Statement statement = connection.createStatement();
				StringBuilder statementBuilder = new StringBuilder();
				
				// Gather values from the request object.
				String addressLn1 = request.getParameter("addressLine1");
				String addressLn2 = request.getParameter("addressLine2");
				String city 	  = request.getParameter("city");
				String state      = request.getParameter("state");
				String zip        = request.getParameter("zip");
				int id			  = Integer.parseInt(request.getParameter("id"));
				
				// Used to determine whether we should update or clear the line 2 address value.
				boolean includeLine2Information = !addressLn2.isEmpty();
				
				// Start building the statement.
				statementBuilder.append("UPDATE address SET street_ln1 = '").append(addressLn1).append("' WHERE id = ").append(id).append("; ");
				
				if(includeLine2Information)
				{
					statementBuilder.append("UPDATE address SET street_ln2 = '").append(addressLn2).append("' WHERE id = ").append(id).append("; ");
				}
				else
				{
					statementBuilder.append("UPDATE address SET street_ln2 = NULL WHERE id = ").append(id).append("; ");
				}
				
				statementBuilder.append("UPDATE address SET city = '").append(city).append("' WHERE id = ").append(id).append("; ");
				
				statementBuilder.append("UPDATE address SET state_code = '").append(state).append("' WHERE id = ").append(id).append("; ");
				
				statementBuilder.append("UPDATE address SET zip_code = '").append(zip).append("' WHERE id = ").append(id).append("; ");
				// Finish building the statement.
				
				// Execute the statement.
				statement.execute(statementBuilder.toString());
				
				// Alert user of success.
				request.getSession().setAttribute("AddressOperationError", "Address update succeeded!");				
			}
			// Address ID misformat.
			catch (NumberFormatException nfe)
			{
				throw new ServletException("The address ID was incorrectly formatted.  Unfortuately, the address cannot be updated.");
			}
			// "USER" attribute not a UserSession
			catch (ClassCastException exc)
			{
				throw new ServletException("User was invalid!");
			}
			// SQL error.
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
		// Adress update failure.
		catch(ServletException exception)
		{
			// Alert user of the failure.
			request.getSession().setAttribute("AddressOperationError", "Unable to update address: " + exception.getMessage());
		}
		
		response.sendRedirect(request.getHeader("referer"));
	}
}