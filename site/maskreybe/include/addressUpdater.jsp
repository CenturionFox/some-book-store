<!-- 
/include/addressUpdater.jsp

Contains the markup for an address selection and editing webpage portion.

THESE VALUES MUST BE PREDEFINED:
	List<Address> addressList: A list of address objects to iterate through.
-->

<form method="post" action="/maskreybe/addressInsert.do">	
	<!-- Scriptlet to generate the hidden address information fields by the address's position in the
		predefined addressList variable. -->
	<%

	for(int addressIndex = 0; addressIndex < addressList.size();)
	{
		Address address = addressList.get(addressIndex);
		
		String addressLine1 = address.getAddressLine1();
		String addressLine2 = address.getAddressLine2();
		String city = address.getCity();
		String state = address.getState();
		String zip = address.getZipCode();
		int addressId = address.getAddressId();
		%>
		<input type="hidden" name="address_<%= ++addressIndex %>_street_ln1" id="address_<%= addressIndex %>_street_ln1" value="<%= addressLine1 %>">
		<input type="hidden" name="address_<%= addressIndex %>_street_ln2" id="address_<%= addressIndex %>_street_ln2" value="<%= addressLine2 %>">
		<input type="hidden" name="address_<%= addressIndex %>_city" id="address_<%= addressIndex %>_city" value="<%= city %>">
		<input type="hidden" name="address_<%= addressIndex %>_state" id="address_<%= addressIndex %>_state" value="<%= state %>">
		<input type="hidden" name="address_<%= addressIndex %>_zip" id="address_<%= addressIndex %>_zip" value="<%= zip %>">
		<input type="hidden" name="address_<%= addressIndex %>_id" id="address_<%= addressIndex %>_id" value="<%= addressId %>">
		<%
	}

	%>

	<table>
		<tr>
			<td style="text-align: right;">
				Address Selection:
			</td>
			<td>
				<select name="addressNumberSelect" id="addressNumberSelect" onClick="updateAddressVisual()">

				<!-- Scriptlet generated select object.  Remember to define addressList. -->
				<%
				for(int addressIndex = 0; addressIndex < addressList.size();)
				{
				%>
				<option value="<%= ++addressIndex %>"><%= addressIndex %></option>
				<%
				}

				%>

				</select>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">* Address Line 1:</td>
			<td><input type="text" name="addressLine1" id="addressLine1" value="<%= addressList.size() > 0 ? addressList.get(0).getAddressLine1() : "" %>"></td>
		</tr>
		<tr>
			<td style="text-align: right;">Address Line 2:</td>
			<td><input type="text" name="addressLine2" id="addressLine2" value="<%= addressList.size() > 0 ? addressList.get(0).getAddressLine2() : "" %>"></td>
		</tr>
		<tr>
			<td style="text-align: right;">* City:</td>
			<td><input type="text" name="city" id="city" value="<%= addressList.size() > 0 ? addressList.get(0).getCity() : "" %>"></td>
		</tr>
		<tr>
			<td style="text-align: right;">* State:</td>
			<% String stateCode = (addressList.size() > 0 ? addressList.get(0).getState() : "");%>
			<td>
				<select id="state" name="state">
					<option value="">Choose a State...</option>
					<option value="AL" <%= stateCode.equals("AL") ? "selected" : "" %>>Alabama</option>
					<option value="AK" <%= stateCode.equals("AK") ? "selected" : "" %>>Alaska</option>
					<option value="AZ" <%= stateCode.equals("AZ") ? "selected" : "" %>>Arizona</option>
					<option value="AR" <%= stateCode.equals("AR") ? "selected" : "" %>>Arkansas</option>
					<option value="CA" <%= stateCode.equals("CA") ? "selected" : "" %>>California</option>
					<option value="CO" <%= stateCode.equals("CO") ? "selected" : "" %>>Colorado</option>
					<option value="CT" <%= stateCode.equals("CT") ? "selected" : "" %>>Connecticut</option>
					<option value="DE" <%= stateCode.equals("DE") ? "selected" : "" %>>Delaware</option>
					<option value="DC" <%= stateCode.equals("DC") ? "selected" : "" %>>District of Columbia</option>
					<option value="FL" <%= stateCode.equals("FL") ? "selected" : "" %>>Florida</option>
					<option value="GA" <%= stateCode.equals("GA") ? "selected" : "" %>>Georgia</option>
					<option value="HI" <%= stateCode.equals("HI") ? "selected" : "" %>>Hawaii</option>
					<option value="ID" <%= stateCode.equals("ID") ? "selected" : "" %>>Idaho</option>
					<option value="IL" <%= stateCode.equals("IL") ? "selected" : "" %>>Illinois</option>
					<option value="IN" <%= stateCode.equals("IN") ? "selected" : "" %>>Indiana</option>
					<option value="IA" <%= stateCode.equals("IA") ? "selected" : "" %>>Iowa</option>
					<option value="KS" <%= stateCode.equals("KS") ? "selected" : "" %>>Kansas</option>
					<option value="KY" <%= stateCode.equals("KY") ? "selected" : "" %>>Kentucky</option>
					<option value="LA" <%= stateCode.equals("LA") ? "selected" : "" %>>Louisiana</option>
					<option value="ME" <%= stateCode.equals("ME") ? "selected" : "" %>>Maine</option>
					<option value="MD" <%= stateCode.equals("MD") ? "selected" : "" %>>Maryland</option>
					<option value="MA" <%= stateCode.equals("MA") ? "selected" : "" %>>Massachusetts</option>
					<option value="MI" <%= stateCode.equals("MI") ? "selected" : "" %>>Michigan</option>
					<option value="MN" <%= stateCode.equals("MN") ? "selected" : "" %>>Minnesota</option>
					<option value="MS" <%= stateCode.equals("MS") ? "selected" : "" %>>Mississippi</option>
					<option value="MO" <%= stateCode.equals("MO") ? "selected" : "" %>>Missouri</option>
					<option value="MT" <%= stateCode.equals("MT") ? "selected" : "" %>>Montana</option>
					<option value="NE" <%= stateCode.equals("NE") ? "selected" : "" %>>Nebraska</option>
					<option value="NV" <%= stateCode.equals("NV") ? "selected" : "" %>>Nevada</option>
					<option value="NH" <%= stateCode.equals("NH") ? "selected" : "" %>>New Hampshire</option>
					<option value="NJ" <%= stateCode.equals("NJ") ? "selected" : "" %>>New Jersey</option>
					<option value="NM" <%= stateCode.equals("NM") ? "selected" : "" %>>New Mexico</option>
					<option value="NY" <%= stateCode.equals("NY") ? "selected" : "" %>>New York</option>
					<option value="NC" <%= stateCode.equals("NC") ? "selected" : "" %>>North Carolina</option>
					<option value="ND" <%= stateCode.equals("ND") ? "selected" : "" %>>North Dakota</option>
					<option value="OH" <%= stateCode.equals("OH") ? "selected" : "" %>>Ohio</option>
					<option value="OK" <%= stateCode.equals("OK") ? "selected" : "" %>>Oklahoma</option>
					<option value="OR" <%= stateCode.equals("OR") ? "selected" : "" %>>Oregon</option>
					<option value="PA" <%= stateCode.equals("PA") ? "selected" : "" %>>Pennsylvania</option>
					<option value="PR" <%= stateCode.equals("PR") ? "selected" : "" %>>Puerto Rico</option>
					<option value="RI" <%= stateCode.equals("RI") ? "selected" : "" %>>Rhode Island</option>
					<option value="SC" <%= stateCode.equals("SC") ? "selected" : "" %>>South Carolina</option>
					<option value="SD" <%= stateCode.equals("SD") ? "selected" : "" %>>South Dakota</option>
					<option value="TN" <%= stateCode.equals("TN") ? "selected" : "" %>>Tennessee</option>
					<option value="TX" <%= stateCode.equals("TX") ? "selected" : "" %>>Texas</option>
					<option value="UT" <%= stateCode.equals("UT") ? "selected" : "" %>>Utah</option>
					<option value="VT" <%= stateCode.equals("VT") ? "selected" : "" %>>Vermont</option>
					<option value="VI" <%= stateCode.equals("VI") ? "selected" : "" %>>Virgin Islands</option>
					<option value="VA" <%= stateCode.equals("VA") ? "selected" : "" %>>Virginia</option>
					<option value="WA" <%= stateCode.equals("WA") ? "selected" : "" %>>Washington</option>
					<option value="WV" <%= stateCode.equals("WV") ? "selected" : "" %>>West Virginia</option>
					<option value="WI" <%= stateCode.equals("WI") ? "selected" : "" %>>Wisconsin</option>
					<option value="WY" <%= stateCode.equals("WY") ? "selected" : "" %>>Wyoming</option>
				</select>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">* Zip Code:</td>
			<td><input type="text" name="zip" id="zip" value="<%= addressList.size() > 0 ? addressList.get(0).getZipCode() : "" %>" onKeyPress="return forceNumeric(event)"></td>
		</tr>
		<tr>
			<td colspan="2"><input type="submit" value="Add New Address"><input type="button" value="Update Address" onClick="updateAddressInDatabase()"><input type="button" value="Remove Address" onClick="removeAddress()"></td>
		</tr>
		<tr>
			<td colspan="2"><span id="addressOpsOutput"><%= session.getAttribute("AddressOperationError") != null ? session.getAttribute("AddressOperationError") : "" %></span></td>
		</tr>
	</table>
</form>