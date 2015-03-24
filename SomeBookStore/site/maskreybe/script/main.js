/**
 * main.js
 * Provides functionality that should be shared cross-website on SomeBookStore.net.
 * 
 * Author:  Bridger Maskrey
 * Version: 1.1.0
 * Date:	2014-09-03
 */

/**
 * Resizes the body of the website to the size of the screen displayed.
 * This sets minheight, so overflow simply resizes the body.
 */
function sizeBodyOnLoad()
{
	var windowHeight = $(window).height();
	setTimeout(function() {
		document.body.style.minHeight = windowHeight + "px";
	}, 50);
  
   matchMinHeight(document.getElementById('main'), document.body, 100, 256);
}

/**
 * Matches the minimum height of two elements with a specified offset.
 * 
 * Parameter: {Element} element0 The element to resize.
 * Parameter: {Element} element1 The element from which to set the new height.
 * Parameter: {Integer} timeout  The timeout to wait for before resizing.
 * Parameter: {Integer} offset	 The offset height to set.
 */
function matchMinHeight(element0, element1, timeout, offset)
{
	setTimeout(function() {
		var minHeight = element1.style.minHeight;
		minHeight = minHeight.slice(0, -2);
		element0.style.minHeight = (parseInt(minHeight) - offset) + "px";
	}, timeout);
}

/**
 * Enables specified placeholder text to be shown on an event.
 *
 * Parameter: {Element} element0    The element to which the placeholder should be applied.
 * Parameter: {String}  placeholder The placeholder text to apply to the element.
 */
function showPlaceholder(element0, placeholder)
{
	element0.placeholder = placeholder;
}

/**
 * Sets the current element placeholder to nothing.
 *
 * Parameter: {Element} element0 The element from which the placeholder should be removed.
 */
function hidePlaceholder(element0)
{
	element0.placeholder = "";
}

function submitRegistration()
{
	if(validateRegInput())
	{
		document.forms["registrationForm"].submit();
	}
}

function submitUpdate()
{
	if(validateUpdateInput())
	{
		document.forms["updateInformationForm"].submit();
	}
}

function generateForm(method, action)
{
	var form = document.createElement("form");
	form.setAttribute("method", method);
	form.setAttribute("action", action);
	
	return form;
}

function updatePrice(isbn)
{
	var singlePrice = document.getElementById("singleBookVal_" + isbn).value;
	var calcPrice = document.getElementById("price_" + isbn);
	var quantity = document.getElementById("quantity_" + isbn).value;
	
	calcPrice.innerHTML = "$" + (singlePrice * quantity).toFixed(2);
}

function updateCardNumber()
{
	var select = document.getElementById("cardNumberSelect").value;
	var cardNum = document.getElementById("card_" + select + "_num").value;
	var csvNum = document.getElementById("card_" + select + "_csv").value;
	
	document.getElementById("cardNumber").value = cardNum;
	document.getElementById("cardCSV").value = csvNum;
}

function updateAddressVisual()
{
	var select = document.getElementById("addressNumberSelect").value;
	
	var line1 = document.getElementById("address_" + select + "_street_ln1").value;
	var line2 = document.getElementById("address_" + select + "_street_ln2").value;
	var city = document.getElementById("address_" + select + "_city").value;
	var state = document.getElementById("address_" + select + "_state").value;
	var zip = document.getElementById("address_" + select + "_zip").value;
	
	document.getElementById("addressLine1").value = line1;
	document.getElementById("addressLine2").value = line2;
	document.getElementById("city").value = city;
	document.getElementById("state").value = state;
	document.getElementById("zip").value = zip;
}

function removeCard()
{
	var form = generateForm("post", "/maskreybe/creditCardRemove.do");
	
	var cardNum = document.createElement("input");
	cardNum.setAttribute("type", "hidden");
	cardNum.setAttribute("name", "cardNumber");
	cardNum.setAttribute("value", document.getElementById("cardNumber").value);
	
	form.appendChild(cardNum);
	
	var cardCSV = document.createElement("input");
	cardCSV.setAttribute("type", "hidden");
	cardCSV.setAttribute("name", "cardCSV");
	cardCSV.setAttribute("value", document.getElementById("cardCSV").value);
	
	form.appendChild(cardCSV);
	
	document.body.appendChild(form);
	
	form.submit();
}

function removeAddress()
{
	var form = generateForm("post", "/maskreybe/addressRemove.do");

	var addressIndex = document.getElementById("addressNumberSelect").value;
	
	var addr_id = document.createElement("input");
	addr_id.setAttribute("type", "hidden");
	addr_id.setAttribute("name", "id");
	addr_id.setAttribute("value", document.getElementById("address_" + addressIndex + "_id").value);
	
	form.appendChild(addr_id);
	
	document.body.appendChild(form);
	
	form.submit();
}

function updateAddressInDatabase()
{
	var form = generateForm("post", "/maskreybe/addressUpdate.do");
	
	var line1 = document.createElement("input");
	line1.setAttribute("type", "hidden");
	line1.setAttribute("name", "addressLine1");
	line1.setAttribute("value", document.getElementById("addressLine1").value);
	
	form.appendChild(line1);
	
	var line2 = document.createElement("input");
	line2.setAttribute("type", "hidden");
	line2.setAttribute("name", "addressLine2");
	line2.setAttribute("value", document.getElementById("addressLine2").value);
	
	form.appendChild(line2);
	
	var city = document.createElement("input");
	city.setAttribute("type", "hidden");
	city.setAttribute("name", "city");
	city.setAttribute("value", document.getElementById("city").value);
	
	form.appendChild(city);	
	
	var state = document.createElement("input");
	state.setAttribute("type", "hidden");
	state.setAttribute("name", "state");
	state.setAttribute("value", document.getElementById("state").value);
	
	form.appendChild(state);
	
	var zip = document.createElement("input");
	zip.setAttribute("type", "hidden");
	zip.setAttribute("name", "zip");
	zip.setAttribute("value", document.getElementById("zip").value);
	
	form.appendChild(zip);
	
	var addressIndex = document.getElementById("addressNumberSelect").value;
	
	var addr_id = document.createElement("input");
	addr_id.setAttribute("type", "hidden");
	addr_id.setAttribute("name", "id");
	addr_id.setAttribute("value", document.getElementById("address_" + addressIndex + "_id").value);
	
	form.appendChild(addr_id);
	
	document.body.appendChild(form);
	
	form.submit();
}

function submitCheckoutForm()
{
	if(validateCheckoutInfo())
	{
		var form = generateForm("post", "/maskreybe/submitOrder.do");
		
		var cardNum = document.createElement("input");
		cardNum.setAttribute("type", "hidden");
		cardNum.setAttribute("name", "cardNumber");
		cardNum.setAttribute("value", document.getElementById("cardNumber").value);
		
		form.appendChild(cardNum);
		
		var line1 = document.createElement("input");
		line1.setAttribute("type", "hidden");
		line1.setAttribute("name", "addressLine1");
		line1.setAttribute("value", document.getElementById("addressLine1").value);
		
		form.appendChild(line1);
		
		var line2 = document.createElement("input");
		line2.setAttribute("type", "hidden");
		line2.setAttribute("name", "addressLine2");
		line2.setAttribute("value", document.getElementById("addressLine2").value);
		
		form.appendChild(line2);
		
		var city = document.createElement("input");
		city.setAttribute("type", "hidden");
		city.setAttribute("name", "city");
		city.setAttribute("value", document.getElementById("city").value);
		
		form.appendChild(city);	
		
		var state = document.createElement("input");
		state.setAttribute("type", "hidden");
		state.setAttribute("name", "state");
		state.setAttribute("value", document.getElementById("state").value);
		
		form.appendChild(state);
		
		var zip = document.createElement("input");
		zip.setAttribute("type", "hidden");
		zip.setAttribute("name", "zip");
		zip.setAttribute("value", document.getElementById("zip").value);
		
		form.appendChild(zip);
		
		document.body.appendChild(form);

		form.submit();
	}
}