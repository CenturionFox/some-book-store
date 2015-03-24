package edu.pti.students.bem9.bookstore.ecommerce;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.pti.students.bem9.bookstore.beans.Book;
import edu.pti.students.bem9.bookstore.beans.UserSession;

/**
 * Update an item in the cart.
 * 
 * @author  Bridger Maskrey (bem9@students.pti.edu)
 * @version 1.0.0
 */
public class UpdateCartItem extends HttpServlet
{
	/* (non-Javadoc)
	 * The version ID.
	 */
	private static final long	serialVersionUID	= 2192652763013904650L;

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * Updates the number of books in the cart to the new number supplied by request.
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		try
		{
			try
			{
				Object attrib = request.getSession().getAttribute("USER");
				if(attrib != null && (attrib instanceof UserSession))
				{
					UserSession session = (UserSession)attrib;
					
					String isbn = request.getParameter("bookIsbn");
					int quantity = Integer.parseInt(request.getParameter("quantity"));
					
					for(Iterator<Book> iterator = session.getCart().iterator(); iterator.hasNext();)
					{
						Book book = iterator.next();
						
						if(book.getIsbn().equals(isbn))
						{
							if(quantity <= 0)
							{
								iterator.remove(); 
								continue;
							}
							
							book.setQuantity(quantity);
							
							break;
						}
					}
				}
			}
			catch(NumberFormatException e)
			{
				throw new ServletException(e);
			}
			
			response.sendRedirect(request.getHeader("referer"));
		}
		catch(ServletException exception)
		{
			throw new IOException(exception);
		}
	}
}
