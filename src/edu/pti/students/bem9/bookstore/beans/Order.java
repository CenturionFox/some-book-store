package edu.pti.students.bem9.bookstore.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Bridger Maskrey
 *
 */
public class Order implements Serializable
{
	private static final long	serialVersionUID	= -1589431748863385236L;
	private List<Book>	items;
	private String		orderId;

	/**
	 * Creates a new order item 
	 */
	public Order()
	{
		this.items = new ArrayList<Book>();
		this.orderId = "";
	}

	/**
	 * @return
	 */
	public List<Book> getItems()
	{
		return this.items;
	}

	/**
	 * @return
	 */
	public String getOrderId()
	{
		return this.orderId;
	}

	/**
	 * @param items
	 */
	public void setItems(List<Book> items)
	{
		this.items = items;
	}

	/**
	 * @param orderId
	 */
	public void setOrderId(String orderId)
	{
		this.orderId = orderId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	
	public String toString()
	{
		return "Order [items=" + this.items + ", orderId=" + this.orderId + "]";
	}
}
