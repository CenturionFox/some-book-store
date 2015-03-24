package edu.pti.students.bem9.bookstore.acctmgmt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.pti.students.bem9.bookstore.beans.UserSession;

/**
 * Logs out the current user.
 * 
 * @author Bridger Maskrey (bem9@students.pti.edu)
 * @version 1.0.0
 */
public class LogOutUser extends HttpServlet
{
	/* (non-Javadoc)
	 * The setial ID of the log out servlet.
	 */
	private static final long	serialVersionUID	= 966270765192376685L;

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * Logs a logout message and resets the "USER" variable.
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		Object session = request.getSession().getAttribute("USER");
		
		if(session != null)
		{
			if(session instanceof UserSession)
			{
				System.out.println("Logging out " + session.toString() + "...");
			}
			else
			{
				System.out.println("Removing ghost user!");
			}
		}
		
		// Reset the user session.
		request.getSession().setAttribute("USER", new UserSession());
		
		// Redirect to the homepage.
		response.sendRedirect("/maskreybe/index.jsp");
	}
}
