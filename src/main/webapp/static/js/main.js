$(document).ready(function()
{
	// Declaring global variables for caching the data later.
	let firstNameInput;
	let lastNameInput;
	let genderInput;
	let birthdayInput;
	let abilityInput;
	let villageInput;
	
	const BASE_URL = "https://ninja-management.herokuapp.com/restapi/ninjas/";
	
	// GET request to retrieve all the Ninja objects from the REST API.
	$.ajax({
        url: BASE_URL,
        type: 'GET',
        dataType: "json"
    }).then(function(data) {
    	displayNinjas(data);
    }).fail(function(xhr, status, error){
    	let responseBodyStr = xhr.responseText;
    	let exceptionObject;
    	
    	// Need to check if it is a custom exception message from the back-end (json) or a typical HTTP error.
    	if (IsJsonString(responseBodyStr))
    	{
    		exceptionObject = jQuery.parseJSON(responseBodyStr);
	    	alert('GET request failed: ' + xhr.status + ' --- ' + exceptionObject.message + ' --- ' + exceptionObject.documentation);
    	}
    	else
    		alert('GET request failed: ' + xhr.status + ' --- ' + xhr.statusText);
    });
	
	// Event handler for the "Edit" button.
	$('#ninjas-table').on('click', '.edit-button', function(){
		let currentRow = $(this).closest('tr');
		currentRow.find('.edit').show();
		currentRow.find('.no-edit').hide();
		
		populateEditForm(currentRow);
		
		// Caching (quering) the data before hand.
		firstNameInput = currentRow.find('.input-fname');
		lastNameInput = currentRow.find('.input-lname');
		genderInput = currentRow.find('.gender');
		birthdayInput = currentRow.find('.input-birthday');
		abilityInput = currentRow.find('.dropdown-ability');
		villageInput = currentRow.find('.dropdown-village');
	});
	
	// Event handler for the "Cancel" button, which cancels the "Edit" procedure.
	$('#ninjas-table').on('click', '.cancel-button', function(){
		let currentRow = $(this).closest('tr');
		closeEditForm(currentRow);
	});
	
	// Event handler for the "Delete" button, which directly sends a DELETE request.
	$('#ninjas-table').on('click', '.delete-button', function(){
		let currentRow = $(this).closest('tr');
		let ninjaId = currentRow.attr('data-ninja-id');
		
		$.ajax({
	        url: BASE_URL + "ninja/" + ninjaId,
	        type: 'DELETE',
	    }).then(function(data) {
	    	currentRow.fadeOut('slow', function(){
				this.remove();
			});
	    }).fail(function(xhr, status, error){
	    	let responseBodyStr = xhr.responseText;
	    	let exceptionObject;
	    	
	    	// Need to check if it is a custom exception message from the back-end (json) or a typical HTTP error.
	    	if (IsJsonString(responseBodyStr))
	    	{
	    		exceptionObject = jQuery.parseJSON(responseBodyStr);
		    	alert('DELETE request failed: ' + xhr.status + ' --- ' + exceptionObject.message + ' --- ' + exceptionObject.documentation);
	    	}
	    	else
	    		alert('DELETE request failed: ' + xhr.status + ' --- ' + xhr.statusText);
	    });
	});
	
	// Event handler for the "ADD NINJA" button
	$('#add-button').on('click', function(){
		let self = $(this);
		
		let addFormTable = $('#add-form-table-wrapper');
		addFormTable.slideToggle('slow', function(){
			if (addFormTable.is(':visible'))
			{
				self.text('Cancel');
				
				// Caching (quering) the data before hand.
				firstNameInput = addFormTable.find('#input-fname');
				lastNameInput = addFormTable.find('#input-lname');
				genderInput = addFormTable.find('#input-gender');
				birthdayInput = addFormTable.find('#input-birthday');
				abilityInput = addFormTable.find('#input-dropdown-ability');
				villageInput = addFormTable.find('#input-dropdown-village');
			}
			else
			{
				self.text('ADD NINJA');
				firstNameInput.val('');
				lastNameInput.val('');
				genderInput.find('input[name=gender]').prop('checked', false);
				birthdayInput.val('');
			}
		});
	});
	
	// Event handler for the "Submit" button, which is in the  "Add Ninja" form and sends a POST request to create a Ninja.
	$('#add-submit-button').on('click', function(event){
		let addForm = $('#add-form')[0];
		let isValid = addForm.checkValidity();
		
		if (isValid)
		{
			let ninja = {
				firstName: firstNameInput.val(),
				lastName: lastNameInput.val(),
				gender: genderInput.find('input[name=gender]:checked').val(),
				birthday: birthdayInput.val(),
				ability: abilityInput.val(),
				village: villageInput.val(),
			};
			
			// Stopping from default behavior of the form's submit, since custom behavior is written. 
			event.preventDefault();
			
			$.ajax({
		        url: BASE_URL,
		        type: 'POST',
		        dataType: "json",
		        contentType: 'application/json',
		        data: JSON.stringify(ninja)
		    }).then(function(data) {
		    	firstNameInput.val('');
				lastNameInput.val('');
				genderInput.find('input[name=gender]').prop('checked', false);
				birthdayInput.val('');
				
				populateTemplate(data);
		    }).fail(function(xhr, status, error){
		    	let responseBodyStr = xhr.responseText;
		    	let exceptionObject;
		    	
		    	// Need to check if it is a custom exception message from the back-end (json) or a typical HTTP error.
		    	if (IsJsonString(responseBodyStr))
		    	{
		    		exceptionObject = jQuery.parseJSON(responseBodyStr);
			    	alert('POST request failed: ' + xhr.status + ' --- ' + exceptionObject.message + ' --- ' + exceptionObject.documentation);
		    	}
		    	else
		    		alert('POST request failed: ' + xhr.status + ' --- ' + xhr.statusText);
		    });
		}
		else
			alert('Invalid input when adding a ninja!');
	});
	
	// Event handler for the "Submit" button, which is in the  "Edit Ninja" form and sends a PUT request to update a Ninja.
	$('#ninjas-table').on('click', '.edit-submit-button', function(){
		let currentRow = $(this).closest('tr');
		let ninjaId = currentRow.attr('data-ninja-id');
		
		let ninja = {
			id: ninjaId,
			firstName: firstNameInput.val(),
			lastName: lastNameInput.val(),
			gender: genderInput.find('input[name=gender]:checked').val(),
			birthday: birthdayInput.val(),
			ability: abilityInput.val(),
			village: villageInput.val(),
		};
		
		if (checkIfNinjaModified(currentRow,ninja))
		{
			$.ajax({
		        url: BASE_URL + "ninja",
		        type: 'PUT',
		        dataType: "json",
		        contentType: 'application/json',
		        data: JSON.stringify(ninja)
		    }).then(function(data) {
				closeEditForm(currentRow);
				updateNinjaRow(currentRow,data);
		    }).fail(function(xhr, status, error){
		    	let responseBodyStr = xhr.responseText;
		    	let exceptionObject;
		    	
		    	// Need to check if it is a custom exception message from the back-end (json) or a typical HTTP error.
		    	if (IsJsonString(responseBodyStr))
		    	{
		    		exceptionObject = jQuery.parseJSON(responseBodyStr);
			    	alert('PUT request failed: ' + xhr.status + ' --- ' + exceptionObject.message + ' --- ' + exceptionObject.documentation);
		    	}
		    	else
		    		alert('PUT request failed: ' + xhr.status + ' --- ' + xhr.statusText);
		    });
		}
		else
			alert('The data in the fields are not modified or the same data are submitted again.');
	});
	
	// Utility methods
	
	// This method populates the edit form with the already existing data of the particular Ninja object.
	function populateEditForm(aRow)
	{
		let firstName = aRow.find('.display-fname').text();
		aRow.find('.input-fname').val(firstName);
		
		let lastName = aRow.find('.display-lname').text();
		aRow.find('.input-lname').val(lastName);
		
		let gender = aRow.find('.display-gender').text();
		aRow.find('input[value=' + gender + ']').prop('checked', true);
		
		let birthday = aRow.find('.display-birthday').text();
		aRow.find('.input-birthday').val(birthday);
		
		let ability = aRow.find('.display-ability').text();
		aRow.find('.dropdown-ability option:contains(' + ability + ')').prop('selected',true);
		
		let village = aRow.find('.display-village').text();
		aRow.find('.dropdown-village option:contains(' + village + ')').prop('selected',true);
	}
	
	// This method updates the ninja row, which is displayed to the user, with the new updated data from the server.
	function updateNinjaRow(aRow, updatedNinja)
	{
		aRow.find('.display-fname').text(updatedNinja.firstName);
		
		aRow.find('.display-lname').text(updatedNinja.lastName);
		
		aRow.find('.display-gender').text(updatedNinja.gender);
		
		aRow.find('.display-birthday').text(updatedNinja.birthday);
		
		aRow.find('.display-ability').text(updatedNinja.ability);
		
		aRow.find('.display-village').text(updatedNinja.village);
	}
	
	// This method outputs all the Ninja objects to the HTML page.
	function displayNinjas(ninjaObjects)
	{
		$.each(ninjaObjects, function(index, value){
	    	   populateTemplate(value);
	    });
	}
	
	// This method populates with data the template which displays a Ninja object.
	function populateTemplate(ninja)
	{
		let table = $('#ninjas-table');
		let template = $('#template').html();
		
		// This line removes the "Z" character from the date format
		ninja.birthday = ninja.birthday.slice(0,-1);
		
		table.append(Mustache.render(template, ninja));
	}
	
	function closeEditForm(aRow)
	{
		aRow.find('.edit').hide();
		aRow.find('.no-edit').show();
	}
	
	// Checks if a string has JSON structure
	function IsJsonString(string) {
		var my_JSON_object = !(/[^,:{}\[\]0-9.\-+Eaeflnr-u \n\r\t]/.test(string.replace(/"(\\.|[^"\\])*"/g, ''))) &&
		     eval('(' + string + ')');
		
		return my_JSON_object;
	}
	
	function checkIfNinjaModified(aRow,submitedNinja)
	{
		if (aRow.find('.display-fname').text() === submitedNinja.firstName &&
				aRow.find('.display-lname').text() === submitedNinja.lastName &&
				aRow.find('.display-gender').text() === submitedNinja.gender &&
				aRow.find('.display-birthday').text() === submitedNinja.birthday &&
				aRow.find('.display-ability').text() === submitedNinja.ability &&
				aRow.find('.display-village').text() === submitedNinja.village)
			return false;
		else
			return true;
	}
});