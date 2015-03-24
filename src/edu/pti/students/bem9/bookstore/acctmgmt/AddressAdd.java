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
 * Adds a new address into the database, providing that it does not already exist.
 * This servlet makes use of the SQL query "<code>SELECT * FROM address WHERE {database_field}='{matching_passed_value}'
 * 	AND {database_field_2}='{matching_field_value_2}...;</code>", and the SQL update statement "<code>INSERT INTO address (
 * 	{database_field},...{database_field_n}) VALUES ('{matching_passed_value}',...'{matching_passed_value_n}');</code>".
 * 	It also takes into account the request parameters "addressLine1", "addressLine2", "city", "state", and "zip".
 * The end result of this server's operation is either a failure message returned to the user or a new
 * 	entry in the address table in the database.
 * 
 * @author  Bridger Maskrey (bem9@students.pti.edu)
 * @version 1.0.0
 */
public class AddressAdd extends HttpServlet
{
	/* (non-Javadoc)
	 * The serializable version of this servlet.
	 */
	private static final long	serialVersionUID	= 6599122586683421168L;
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * Adds a new address entry to the address table.
	 * First queries the database to ensure that no address with the passed values exists already, then, if no such address exists,
	 * creates and inserts a new address entry.
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
					String addressLn1 = request.getParameter("addressLine1");
					String addressLn2 = request.getParameter("addressLine2");
					String city 	  = request.getParameter("city");
					String state      = request.getParameter("state");
					String zip        = request.getParameter("zip");
					
					// Used to determine whether or not we should include in the SELECT and INSERT
					//	statements the second address line information.
					boolean includeLine2Information = !addressLn2.isEmpty();
					
					// Begin building the selection check statement.
					statementBuilder.append("SELECT * FROM address WHERE street_ln1 = '").append(addressLn1)
						.append("' AND ");
					
					if(includeLine2Information)
					{
						statementBuilder.append("street_ln2 = '").append(addressLn2).append("' AND ");
					}
					
					statementBuilder.append("city = '").append(city).append("' AND zip_code = '").append(zip)
						.append("' AND state_code = '").append(state).append("' AND email = '")
						.append(user.getUsername()).append("';");
					// End statement building.
					
					// Execute the completed SQL query statement.
					ResultSet results = statement.executeQuery(statementBuilder.toString());
					
					if(!results.next())
					{
						// Clear the statement builder and begin building the update statement.
						statementBuilder.delete(0, statementBuilder.length()).append("INSERT INTO address ( street_ln1, ");
						
						if(includeLine2Information)
						{
							statementBuilder.append("street_ln2, ");
						}
						
						statementBuilder.append("city, zip_code, state_code, email) VALUES ('").append(addressLn1).append("', '");
						
						if(includeLine2Information)
						{
							statementBuilder.append(addressLn2).append("', '");
						}
						
						statementBuilder.append(city).append("', '").append(zip).append("', '").append(state).append("', '")
							.append(user.getUsername()).append("');");
						// End building the update statement.
						
						// Execute the completed update statement.
						statement.executeUpdate(statementBuilder.toString());
						
						request.getSession().setAttribute("AddressOperationError", "New address \"" + addressLn1 + "\" added successfully.");
					}
					// The address already exists in the database and registered to this user, so no need to add it again.
					else
					{
						throw new ServletException("The address is already present in the database.");
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
		// Failed to add address.
		catch(ServletException exception)
		{
			// Set the address op error attribute to alert the user of the failure.
			request.getSession().setAttribute("AddressOperationError", "Unable to add new address: " + exception.getMessage());
		}
		
		// Redirect to the previous page.
		response.sendRedirect(request.getHeader("referer"));
	}
}