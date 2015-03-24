package edu.pti.students.bem9.bookstore.beans;

import java.io.Serializable;

/**
 * Represents a database address entry.
 * 
 * @author Bridger Maskrey (bem9@students.pti.edu)
 * @version 1.0.0
 */
public class Address implements Serializable
{
	/* (non-Javadoc)
	 * The servlet version ID.
	 */
	private static final long	serialVersionUID	= -1483830562705723578L;
	
	/**
	 * The first address line.
	 */
	public String addressLine1;
	
	/**
	 * The second address line.
	 */
	public String addressLine2;
	
	/**
	 * The city the address is in.
	 */
	private String city;
	
	/**
	 * The state the address is in.
	 */
	private String state;
	
	/**
	 * The zip code of the city.
	 */
	private String zipCode;
	
	/**
	 * The ID of the address.
	 */
	private int addressId;

	/**
	 * Set up default values for the address.
	 */
	public Address()
	{
		this.addressLine1 = "";
		this.addressLine2 = "";
		this.city = "";
		this.state = "";
		this.zipCode = "";
		this.addressId = 0;
	}

	/**
	 * Get the first address line.
	 * @return The first address line.
	 */
	public String getAddressLine1()
	{
		return this.addressLine1;
	}

	/**
	 * Get the second address line.
	 * @return The second address line.
	 */
	public String getAddressLine2()
	{
		return this.addressLine2;
	}

	/**
	 * Get the address city.
	 * @return The address city.
	 */
	public String getCity()
	{
		return this.city;
	}

	/**
	 * Get the state.
	 * @return The address state.
	 */
	public String getState()
	{
		return this.state;
	}

	/**
	 * Get the zip code.
	 * @return The address zip code.
	 */
	public String getZipCode()
	{
		return this.zipCode;
	}
	
	/**
	 * Get the ID.
	 * @return The address ID.
	 */
	public int getAddressId()
	{
		return this.addressId;
	}

	/**
	 * Set the first adress line.
	 * @param addressLine1 The new first address line.
	 */
	public void setAddressLine1(String addressLine1)
	{
		this.addressLine1 = addressLine1;
	}

	/**
	 * Set the second address line.  If null or "null", set to an empty string.
	 * @param addressLine2 The new second address line.
	 */
	public void setAddressLine2(String addressLine2)
	{
		if(addressLine2 == null || addressLine2.equals("null"))
		{
			addressLine2 = "";
		}
		this.addressLine2 = addressLine2;
	}

	/**
	 * Set the city.
	 * @param city The new city.
	 */
	public void setCity(String city)
	{
		this.city = city;
	}

	/**
	 * Set the state.
	 * @param state The new state.
	 */
	public void setState(String state)
	{
		this.state = state;
	}

	/**
	 * Set the zip code.
	 * @param zipCode The new zip code.
	 */
	public void setZipCode(String zipCode)
	{
		this.zipCode = zipCode;
	}

	/**
	 * Set the address ID.
	 * @param addressId The new address ID.
	 */
	public void setAddressId(int addressId)
	{
		this.addressId = addressId;
	}
}
