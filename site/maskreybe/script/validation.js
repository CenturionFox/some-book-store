function forceNumeric(event)
{	
	return event.keyCode == 8 || (event.keyCode >= 37 && event.keyCode <= 40) || event.keyCode == 46 || (event.charCode >= 48 && event.charCode <= 57);
}

function validateUpdateInput()
{
	document.getElementById("jsUpdateOutput").innerHTML = "";

	var pass = document.getElementById('pass').value;
	var passval = document.getElementById('passval').value;
	
	var passValid = pass == passval;
  var currentPassValid = document.getElementById('currentpass').value != '';
	
	if(!passValid)
	{
		document.getElementById("jsUpdateOutput").innerHTML = "Unable to update: Password Mismatch.";
	}
  
  if(!currentPassValid)
  {
    document.getElementById("jsUpdateOutput").innerHTML = "Please enter your current password.";
  }
	
	return passValid && currentPassValid;
}

function validateRegInput()
{
	var output = document.getElementById('jsRegisterOutput');
	output.innerHTML = '';
	
	var usn = document.getElementById('email').value;
	var pass = document.getElementById('pass').value;
	var passval = document.getElementById('passval').value;
	
	var usnEmpty = usn == '';
	var passEmpty = pass == '';
	var passIsVal = pass == passval;
	
	var fnameEmpty = document.getElementById('fname').value == '';
	var lnameEmpty = document.getElementById('lname').value == '';
	
	var billingEmpty = document.getElementById('billing_street').value == '';
	var cityEmpty = document.getElementById('billing_city').value == '';
	var stateEmpty = document.getElementById('billing_state').value == '';
	var zipEmpty = document.getElementById('billing_zip') == '';
	
	if(usnEmpty || !usn.match(/\S+@\S+\.\S+/))
	{
		output.innerHTML = 'Invalid email entered.';
	}
	else
	{
		if(passEmpty)
		{
			output.innerHTML = 'Please enter a password.';
		}
		else if(!passIsVal)
		{
			output.innerHTML = 'Your passwords do not match.';
		}
		else
		{
			if(fnameEmpty || lnameEmpty)
			{
				output.innerHTML = 'Please enter your first and last name.';
			}
			else if(billingEmpty || cityEmpty || stateEmpty || zipEmpty)
			{
				output.innerHTML = 'Please enter your full billing address (street, city, state, zip).';
			}
			else
			{
				return true;
			}
		}
	}
	
	return false;
}

function validateInput()
{
	document.getElementById('javascriptOutput').innerHTML = '';
	
	var usn = document.getElementById('userName').value;
	var pass = document.getElementById('password').value;
	
	var usnEmpty = usn == '';
	var passEmpty = pass == '';
	
	if(usnEmpty || !usn.match(/\S+@\S+\.\S+/))
	{
		document.getElementById('javascriptOutput').innerHTML = 'Invalid email.';
	}
	else
	{
		if(passEmpty)
		{
			document.getElementById('javascriptOutput').innerHTML = 'Invalid password.';
		}
		else
		{
			return true;
		}
	}
	
	return false;
}

function validateCheckoutInfo()
{
	var success = true;
	
	document.getElementById('cardOpsOutput').innerHTML = "";
	document.getElementById('addressOpsOutput').innerHTML = "";
	
	var cardnum = document.getElementById("cardNumber").value;
	var cardcsv = document.getElementById("cardCSV").value;
	
	var addressLn1 = document.getElementById("addressLine1").value;
	var city = document.getElementById("city").value;
	var state = document.getElementById("state").value;
	var zip = document.getElementById("zip").value;
	
	if(cardnum == '' || !cardnum.match(/\d\d\d\d-\d\d\d\d-\d\d\d\d-\d\d\d\d/))
	{
		document.getElementById('cardOpsOutput').innerHTML = "You must enter a valid credit card number.";
		success = false;
	}
	else if(cardcsv == '' || !cardcsv.match(/\d\d\d/))
	{
		document.getElementById('cardOpsOutput').innerHTML = "You must enter a valid CSV code.";
		success = false;
	}
	
	if(addressLn1 == '' || city == '' || state == '' || zip == '')
	{
		document.getElementById('addressOpsOutput').innerHTML = "Please enter a valid address, consisting of a first address line, city, state code, and zip.";
		success = false;
	}
	
	return success;
}