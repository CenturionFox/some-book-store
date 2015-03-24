package edu.pti.students.bem9.bookstore.ecommerce;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.pti.students.bem9.bookstore.beans.Book;

public class Search extends HttpServlet
{
	private static final long	serialVersionUID	= 2161999953094767556L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		try
		{
			try
			{
				request.getSession().setAttribute("SearchResults", null);
				
				Class.forName("org.postgresql.Driver");
				
				Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres", "postgres", "");
				Statement statement = connection.createStatement();
				StringBuilder statementBuilder = new StringBuilder().append("SELECT book.*, author.*, title.title AS book_title FROM book INNER JOIN ")
					.append("title ON title.isbn = book.isbn ");
				
				int searchType = Integer.parseInt(request.getParameter("searchType"));
				String keyword = request.getParameter("searchValue");
				
				List<Book> searchResults = new ArrayList<Book>();
				
				switch(searchType)
				{
				case 0:
					
					keyword = "%" + keyword.replaceAll(" ", "%") + "%";
					
					statementBuilder.append("INNER JOIN author ON author.id = title.author_id AND ")
						.append("UPPER((CASE WHEN author.title IS NULL THEN ' ' ELSE author.title END || ' ' || ")
						.append("author.fname || ' ' || CASE WHEN author.minits IS NULL THEN ' ' ELSE author.minits END || ")
						.append("' ' || author.lname)) LIKE UPPER('").append(keyword).append("') ");
					
					break;
					
				case 1:
					
					keyword = "%" + keyword.replaceAll(" ", "%") + "%";
					
					statementBuilder.append("AND UPPER(title.title) LIKE UPPER('").append(keyword).append("') INNER JOIN author ON title.author_id = author.id ");
					
					break;
					
				case 2:
					
					statementBuilder.append("AND book.isbn = '").append(keyword).append("' INNER JOIN author ON title.author_id = author.id ");
					
					break;
				}
				
				statementBuilder.append("ORDER BY author.lname;");
				
				System.out.println("EXECUTE QUERY " + statementBuilder.toString());
				
				ResultSet results = statement.executeQuery(statementBuilder.toString());
				
				while(results.next())
				{
					Book book = Book.readFromResult(results);
					
					searchResults.add(book);
				}
				
				request.getSession().setAttribute("SearchResults", searchResults);
				
				response.sendRedirect("/maskreybe/jsp/search.jsp");
			}
			catch(ClassNotFoundException exception)
			{
				throw new ServletException("Unable to load driver.");
			}
			catch (SQLException | NumberFormatException e)
			{
				throw new ServletException("Error: " + e.getMessage());
			}
		}
		catch(ServletException exception)
		{
			throw new IOException(exception);
		}
	}
}
