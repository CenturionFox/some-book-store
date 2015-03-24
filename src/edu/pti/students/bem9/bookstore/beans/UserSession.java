package edu.pti.students.bem9.bookstore.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class UserSession implements Serializable
{
	private static final long	serialVersionUID	= -231406472560481789L;
	private List<Book>			cart;
	private String				username;

	/**
	 * Create a new user session with no set variables.
	 */
	public UserSession()
	{
		this.username = "";
		this.cart = new ArrayList<Book>();
	}

	/**
	 * Set the user's username to the specified string.
	 * 
	 * @param name
	 *            The name to set.
	 */
	public void setUsername(String name)
	{
		this.username = name;
	}

	/**
	 * Add a new item to the cart.
	 * 
	 * @param book
	 *            The item to add.
	 */
	public void addCartItem(Book book)
	{
		if (this.cart.contains(book))
		{
			for (Book cartedBook : this.cart)
			{
				if (cartedBook.equals(book))
				{
					cartedBook.setQuantity(cartedBook.getQuantity() + book.getQuantity());
				}
			}
		}
		else this.cart.add(book);
	}

	public void setCart(List<Book> list)
	{
		this.cart = list;
	}

	public String getUsername()
	{
		return this.username;
	}

	public Book getItem(int index)
	{
		return this.cart.get(index);
	}

	public List<Book> getCart()
	{
		return this.cart;
	}

	public String getCartList()
	{
		StringBuilder cartList = new StringBuilder();

		for (Book book : this.cart)
		{
			cartList.append(book);
		}

		return cartList.toString();
	}

	@Override
	public String toString()
	{
		return this.username;
	}

	public void clearCart()
	{
		this.cart.clear();
	}
}
