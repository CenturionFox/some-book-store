<!-- 
/include/cardUpdater.jsp

Contains the markup for an address selection and editing webpage portion.

THESE VALUES MUST BE PREDEFINED:
	List<CreditCard> creditCardList: A list of CreditCard objects to iterate through.
	-->

<form method="post" action="/maskreybe/creditCardInsert.do">
	<%
	for(int cardIndex = 0; cardIndex < creditCardList.size();)
	{
		CreditCard card = creditCardList.get(cardIndex);
		
		String cardNumber = card.getCardNumber();
		String cardCSV = card.getCardCSV();
		%>
		<input type="hidden" name="card_<%= ++cardIndex %>_num" id="card_<%= cardIndex %>_num" value="<%= cardNumber %>">
		<input type="hidden" name="card_<%= cardIndex %>_csv" id="card_<%= cardIndex %>_csv" value="<%= cardCSV %>">
		<%
	}

	%>

	<table>
		<tr>
			<td style="text-align: right;">Card Index:</td>
			<td>
				<select name="cardNumberSelect" id="cardNumberSelect" onClick="updateCardNumber()">

				<%
				for(int cardIndex = 0; cardIndex < creditCardList.size();)
				{
				%>
					<option value="<%= ++cardIndex %>"><%= cardIndex %></option>
				<%
				}

				%>

				</select>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">* Card Number:</td>
			<td><input type="text" id="cardNumber" name="cardNumber" value="<%= creditCardList.size() > 0 ? creditCardList.get(0).getCardNumber() : "" %>"></td>
		</tr>
		<tr>
			<td style="text-align: right;">* Card CSV:</td>
			<td><input type="text" id="cardCSV" name="cardCSV" value="<%= creditCardList.size() > 0 ? creditCardList.get(0).getCardCSV() : "" %>" onKeyPress="return forceNumeric(event)"></td>
		</tr>
		<tr>
			<td colspan="2"><input type="submit" value="Add New Card"><input type="button" value="Remove Card" onClick="removeCard()"></td>
		</tr>
		<tr>
			<td colspan="2"><span id="cardOpsOutput"><%= session.getAttribute("CardOperationError") != null ? session.getAttribute("CardOperationError") : "" %></span></td>
		</tr>
	</table>
</form>