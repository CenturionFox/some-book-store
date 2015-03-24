package edu.pti.students.bem9.bookstore.beans;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 * Contains information on each book present in the database. Can load information
 * 	from the database.
 * 
 * @author  Bridger Maskrey (bem9@students.pti.edu)
 * @version 1.0.0
 */
public class Catalog implements Serializable
{
	/* (non-Javadoc)
	 * The version ID of this servlet.
	 */
	private static final long	serialVersionUID	= 9206088979133302449L;
	
	/**
	 * A list for all book objects.
	 */
	private List<Book> bookList;

	
	/**
	 * Creates a new catalog and initializes the book list.
	 */
	public Catalog()
	{
		this.setBookList(new ArrayList<Book>());
	}

	/**
	 * lads the catalog data from the database.
	 */
	public void loadList()
	{
		try
		{
			Class.forName("org.postgresql.Driver");

			Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres", "postgres", "");
			Statement statement = connection.createStatement();
			StringBuilder statementBuilder = new StringBuilder();

			statementBuilder.append("SELECT book.*, author.*, ")
				.append("title.title AS book_title FROM book INNER JOIN title ON title.isbn")
				.append(" = book.isbn INNER JOIN author ON author.id = title.author_id ORDER BY author.lname;");

			System.out.println("EXECUTE QUERY " + statementBuilder.toString());

			ResultSet result = statement.executeQuery(statementBuilder.toString());

			while (result.next())
			{
				Book book = Book.readFromResult(result);

				this.bookList.add(book);
			}
		}
		catch (ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Get the catalog book list.
	 * @return The catalog book list.
	 */
	public List<Book> getBookList()
	{
		return this.bookList;
	}

	/**
	 * Set the catalog book list tot the specified list.
	 * @param bookList The new catalog book list.
	 */
	public void setBookList(List<Book> bookList)
	{
		this.bookList = bookList;
	}

	@Override
	public String toString()
	{
		StringBuilder bookListOutput = new StringBuilder();

		for (Book book : this.bookList)
		{
			bookListOutput.append(book.toString());
		}

		return bookListOutput.toString();
	}
}
