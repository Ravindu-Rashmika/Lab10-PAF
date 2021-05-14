$(document).ready(function()
{ 
	$("#alertSuccess").hide(); 
 	$("#alertError").hide(); 
}); 

// SAVE ============================================
$(document).on("click", "#btnSave", function(event) 
{ 
	// Clear alerts---------------------
	 $("#alertSuccess").text(""); 
	 $("#alertSuccess").hide(); 
	 $("#alertError").text(""); 
	 $("#alertError").hide(); 

	// Form validation-------------------
	var status = validateOrderForm(); 
	if (status != true) 
 	{ 
 		$("#alertError").text(status); 
 		$("#alertError").show(); 
 		return; 
 	} 

	// If valid------------------------
 	var type = ($("#hidOrderIDSave").val() == "") ? "POST" : "PUT"; 
 	
	$.ajax( 
 	{ 
 		url : "OrderAPI", 
 		type : type, 
 		data : $("#formOrder").serialize(), 
 		dataType : "text", 
 		complete : function(response, status) 
 		{ 
 			onOrderSaveComplete(response.responseText, status); 
 		} 
 	}); 
}); 


function onOrderSaveComplete(response, status)
{ 
	if (status == "success") 
 	{ 
 		var resultSet = JSON.parse(response); 
 
		if (resultSet.status.trim() == "success") 
 		{ 
			 $("#alertSuccess").text("Successfully saved."); 
			 $("#alertSuccess").show(); 
			 $("#divOrdersGrid").html(resultSet.data); 
 		} else if (resultSet.status.trim() == "error") 
 		{ 
 			$("#alertError").text(resultSet.data); 
 			$("#alertError").show(); 
 		} 
 	} else if (status == "error") 
	{ 
 		$("#alertError").text("Error while saving."); 
 		$("#alertError").show(); 
 	} else
 	{ 
 		$("#alertError").text("Unknown error while saving.."); 
		$("#alertError").show(); 
 	} 
	$("#hidOrderIDSave").val(""); 
 	$("#formOrder")[0].reset(); 
}


// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event) 
{ 
	 $("#hidOrderIDSave").val($(this).closest("tr").find('#hidOrderIDUpdate').val()); 
	 $("#orderDescription").val($(this).closest("tr").find('td:eq(0)').text()); 
	 $("#orderValue").val($(this).closest("tr").find('td:eq(1)').text()); 
	 $("#orderQuantity").val($(this).closest("tr").find('td:eq(2)').text()); 
});

// REMOVE =============================================
$(document).on("click", ".btnRemove", function(event) 
{
	$.ajax( 
 	{ 
 		url : "OrderAPI", 
 		type : "DELETE", 
 		data : "orderID=" + $(this).data("orderid"),
 		dataType : "text", 
 		complete : function(response, status) 
 		{ 
 			onOrderDeleteComplete(response.responseText, status); 
 		} 
 	}); 
});


function onOrderDeleteComplete(response, status)
{ 
	if (status == "success") 
 	{ 
 		var resultSet = JSON.parse(response); 
 
		if (resultSet.status.trim() == "success") 
 		{ 
 			$("#alertSuccess").text("Successfully deleted."); 
 			$("#alertSuccess").show(); 
 			
			$("#divOrdersGrid").html(resultSet.data); 
 		} else if (resultSet.status.trim() == "error") 
 		{ 
 			$("#alertError").text(resultSet.data); 
 			$("#alertError").show(); 
 		} 
 	} else if (status == "error") 
 	{ 
 		$("#alertError").text("Error while deleting."); 
 		$("#alertError").show(); 
 	} else
 	{ 
 		$("#alertError").text("Unknown error while deleting.."); 
 		$("#alertError").show(); 
 	} 

}
 
// CLIENT-MODEL================================================================
function validateOrderForm() 
{ 
	// Description
	if ($("#orderDescription").val().trim() == "") 
 	{ 
 		return "Insert Order Description."; 
 	} 

	// Value
	if ($("#orderValue").val().trim() == "") 
 	{ 
 		return "Insert Order Value."; 
 	}

	// Quantity
	if ($("#orderQuantity").val().trim() == "") 
	 { 
		 return "Insert Order Quantity."; 
	 } 
	
	// is numerical value
	var tmpValue = $("#orderValue").val().trim(); 
	if (!$.isNumeric(tmpValue)) 
 	{ 
 	return "Insert a numerical value for Order Value."; 
 	} 
	
	
	// convert to decimal price
 	$("#orderValue").val(parseFloat(tmpValue).toFixed(2)); 



	// DESCRIPTION------------------------
	if ($("#orderDescription").val().trim() == "") 
 	{ 
 		return "Insert Order Description."; 
 	} 
	return true; 
}