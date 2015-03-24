/**
 * Header.js
 * Provides all functionality for the included header on each page
 * 	of SomeBookStore.net.
 * 
 * Author:  Bridger Maskrey
 * Version: 1.2.0
 * Date:    2014-09-03
 */

function buildForm()
{
	console.log("Showing login form...");
	
	document.getElementById("loginForm").innerHTML = 
	"<div style='background-color: #464B4B; z-index: 3'>" + 
	"<table style='background-color: #464B4B; right: 0px; bottom: 0px; position: absolute'>" + 
		"<tr>" +
			"<td>&nbsp;</td>" +
			"<td>&nbsp;</td>" +
		"</tr>" + 
		"<tr>" + 
			"<td>Username:</td>" +
			"<td><input type='text' name='username' id='userName' onMouseOver='showPlaceholder(document.getElementById(\"userName\"), \"your@email.here\")' " +	
				"onMouseOut='hidePlaceholder(this)'></td>" +
		"</tr>" +
		"<tr>" +
			"<td>Password:</td>" +
			"<td><input type='password' name='password' id='password' onMouseOver='showPlaceholder(document.getElementById(\"password\"), \"Password\")' " +
				"onMouseOut='hidePlaceholder(this)'></td>" +
		"</tr>" +
			"<td><span id='javascriptOutput'>" +
			(document.getElementById("logInErrVal").value != "null" ? 
				document.getElementById("logInErrVal").value : "") +
			"</span></td>" +
			"<td><input type='button' id='cancel' value='Cancel' onClick='closeForm()'> " +
				"<input type='button' id='submission' value='Log In' onClick='logIn()'></td>" +
	"</table>" +
	"</div>";
	document.getElementById("headerItems").style.transitionDelay = "0s";
	document.getElementById("stickyHeader").style.height = "145px";
	document.getElementById("headerItems").style.marginTop = (40 - ($("headerItems").height() / 2)) + "px";
}

function closeForm()
{
	console.log("Hiding login form...");
	
	document.getElementById("loginForm").innerHTML = 
		"<input type='button' name='showForm' value='Log In...' onClick='buildForm()'> " + 
		"<input type='button' name='Register' value='Register' onClick='registerHit()'>";
	document.getElementById("stickyHeader").style.height = "36px";
	
	document.getElementById("headerItems").style.transitionDelay = "0.2s";
	document.getElementById("headerItems").style.marginTop = "0px";
}

function registerHit()
{
	window.location = "/maskreybe/jsp/register.jsp";
}

function listResized()
{
	list = document.getElementById("headerItems");
	
	var newListItemWidth = list.width() * 0.75 / list.length;
	
	console.log(newListItemWidth);
}

function logIn()
{
	if(validateInput())
	{
		document.forms["loginForm"].submit();
	}
}

function logOut()
{
	var form = generateForm("post", "/maskreybe/logout.do");
	document.body.appendChild(form);
	form.submit();
}

$(window).scroll(function() {

	if($(this).scrollTop() > 100)
	{
		document.getElementById("stickyHeader").style.position = "fixed";
	}
	else
	{
		document.getElementById("stickyHeader").style.position = "relative";
	}
});