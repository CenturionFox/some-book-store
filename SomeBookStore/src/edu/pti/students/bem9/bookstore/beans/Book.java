package edu.pti.students.bem9.bookstore.beans;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Book implements Serializable
{
	private static final long	serialVersionUID	= -4975503446936400570L;
	private String isbn;
	private int quantity;
	
	private String title;
	private String author;
	private String description;
	
	private double price;
	
	public Book()
	{
		this.isbn = "000-0000000000";
		this.quantity = 0;
		this.title = "";
		this.author = "";
		this.description = "";
		this.price = 0D;
	}

	public String getIsbn()
	{
		return this.isbn;
	}

	public void setIsbn(String isbn)
	{
		this.isbn = isbn;
	}
	
	public int getQuantity()
	{
		return this.quantity;
	}

	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}

	public String getTitle()
	{
		return this.title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getAuthor()
	{
		return this.author;
	}

	public void setAuthor(String author)
	{
		this.author = author;
	}

	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public double getPrice()
	{
		return this.price;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}
	
	@Override
	public boolean equals(Object b)
	{
		if(b instanceof Book)
		{
			return ((Book)b).getIsbn().equals(this.isbn);
		}
		
		return false;
	}

	public static Book readFromResult(ResultSet result) throws SQLException
	{
		Book book = new Book();
		
		book.setIsbn(result.getString("isbn"));
		book.setQuantity(result.getInt("stock"));
		book.setTitle(result.getString("book_title"));
		
		book.setDescription(result.getString("description"));
		book.setPrice(result.getDouble("price"));
		
		StringBuilder authName = new StringBuilder();
		
		String authtitle = result.getString("title");
		
		if(authtitle != null && !authtitle.isEmpty())
		{
			authName.append(authtitle).append(" ");
		}
		
		authName.append(result.getString("fname")).append(" ");
		
		String authMinits = result.getString("minits");
		
		if(authMinits != null && !authMinits.isEmpty())
		{
			authName.append(authMinits).append(" ");
		}
		
		authName.append(result.getString("lname"));
		
		book.setAuthor(authName.toString());
		
		return book;
	}
}
