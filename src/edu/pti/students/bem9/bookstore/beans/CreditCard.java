package edu.pti.students.bem9.bookstore.beans;

import java.io.Serializable;

public class CreditCard implements Serializable
{
	private static final long	serialVersionUID	= 4015165460709864052L;
	private String cardNumber;
	private String cardCSV;
	
	public CreditCard()
	{
		this.cardNumber = "";
		this.cardCSV = "";
	}

	public String getCardNumber()
	{
		return this.cardNumber;
	}

	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	public String getCardCSV()
	{
		return this.cardCSV;
	}

	public void setCardCSV(String cardCSV)
	{
		this.cardCSV = cardCSV;
	}
}
