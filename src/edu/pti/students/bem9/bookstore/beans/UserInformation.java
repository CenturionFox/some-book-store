package edu.pti.students.bem9.bookstore.beans;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserInformation implements Serializable
{
	private static final long	serialVersionUID	= -8601648466278163534L;
	
	private String email;
	private String title;
	private String fname;
	private String minits;
	private String lname;
	
	private List<CreditCard> cardList;
	private List<Address> addressList;
	
	public UserInformation()
	{
		this.email = "";
		this.title = "";
		this.fname = "";
		this.minits = "";
		this.lname = "";
		
		this.cardList = new ArrayList<CreditCard>();
		this.addressList = new ArrayList<Address>();
	}

	public String getEmail()
	{
		return this.email;
	}

	public String getTitle()
	{
		return this.title;
	}

	public String getFname()
	{
		return this.fname;
	}

	public String getMinits()
	{
		return this.minits;
	}

	public String getLname()
	{
		return this.lname;
	}

	public List<CreditCard> getCardList()
	{
		return this.cardList;
	}

	public List<Address> getAddressList()
	{
		return this.addressList;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public void setTitle(String title)
	{
		if(title == null) title = "";
		
		this.title = title;
	}

	public void setFname(String fname)
	{
		this.fname = fname;
	}

	public void setMinits(String minits)
	{
		if(minits == null) minits = "";
		
		this.minits = minits;
	}

	public void setLname(String lname)
	{
		this.lname = lname;
	}

	public void setCardList(List<CreditCard> cardList)
	{
		this.cardList = cardList;
	}

	public void setAddressList(List<Address> addressList)
	{
		this.addressList = addressList;
	}
	
	public void loadFromDatabase(String email)
	{
		try
		{
			Class.forName("org.postgresql.Driver");
			
			Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres", "postgres", "");
			Statement statement = connection.createStatement();
			StringBuilder statementBuilder = new StringBuilder().append("SELECT * FROM member WHERE member.email = '")
				.append(email).append("';");
			ResultSet results = statement.executeQuery(statementBuilder.toString());
			
			if(results.next())
			{
				this.setFname(results.getString("fname"));
				this.setLname(results.getString("lname"));
				
				this.setEmail(email);
				
				this.setTitle(results.getString("title"));
				this.setMinits(results.getString("minits"));
			}
			
			statementBuilder.delete(0, statementBuilder.length())
				.append("SELECT * FROM credit_card WHERE email = '")
				.append(email).append("';");
			
			results = statement.executeQuery(statementBuilder.toString());
			
			while(results.next())
			{
				CreditCard card = new CreditCard();
				card.setCardNumber(results.getString("cardnum"));
				card.setCardCSV(results.getString("csvnum"));
				
				this.cardList.add(card);
			}
			
			statementBuilder.delete(0, statementBuilder.length())
				.append("SELECT * FROM address WHERE email = '")
				.append(email).append("' ORDER BY id;");
		
			results = statement.executeQuery(statementBuilder.toString());
			
			while(results.next())
			{
				Address address = new Address();
				
				address.setAddressId(results.getInt("id"));
				address.setAddressLine1(results.getString("street_ln1"));
				address.setAddressLine2(results.getString("street_ln2"));
				address.setCity(results.getString("city"));
				address.setZipCode(results.getString("zip_code"));
				address.setState(results.getString("state_code"));
				
				this.addressList.add(address);
			}
		}
		catch (ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}
	}
}
