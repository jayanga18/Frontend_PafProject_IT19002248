/**
 * 
 */
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
var status = validatePaymentForm();
if (status != true)
 {
 $("#alertError").text(status);
 $("#alertError").show();
 return;
 }
// If valid------------------------
var type = ($("#hidorderNoSave").val() == "") ? "POST" : "PUT";
 $.ajax(
 {
 url : "PaymentsAPI",
 type : type,
 data : $("#formPayment").serialize(),
 dataType : "text",
 complete : function(response, status)
 {
 onPaymentSaveComplete(response.responseText, status);
 }
 });
});


function onPaymentSaveComplete(response, status)
{
if (status == "success")
 {
 var resultSet = JSON.parse(response);
 if (resultSet.status.trim() == "success")
 {
 $("#alertSuccess").text("Successfully saved.");
 $("#alertSuccess").show();
 $("#divPaymentsGrid").html(resultSet.data);
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
$("#hidorderNoSave").val("");
 $("#formPayment")[0].reset();
}



// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
{
 $("#hidorderNoSave").val($(this).closest("tr").find('#hidorderNoUpdate').val());
 $("#productID").val($(this).closest("tr").find('td:eq(0)').text());
 $("#name").val($(this).closest("tr").find('td:eq(1)').text());
 $("#address").val($(this).closest("tr").find('td:eq(2)').text());
 $("#phoneNO").val($(this).closest("tr").find('td:eq(3)').text());
});


// REMOVE==========================================
$(document).on("click", ".btnRemove", function(event)
{
 $.ajax(
 {
 url : "PaymentsAPI",
 type : "DELETE",
 data : "orderNo=" + $(this).data("orderno"),
 dataType : "text",
 complete : function(response, status)
 {
 onPaymentDeleteComplete(response.responseText, status);
 }
 });
});

function onPaymentDeleteComplete(response, status)
{
if (status == "success")
 {
 var resultSet = JSON.parse(response);
 if (resultSet.status.trim() == "success")
 {
 $("#alertSuccess").text("Successfully deleted.");
 $("#alertSuccess").show();
 $("#divPaymentsGrid").html(resultSet.data);
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
function validatePaymentForm()
{
// PRODUCTID
if ($("#productID").val().trim() == "")
 {
 return "Insert Product ID.";
 }
// NAME
if ($("#name").val().trim() == "")
 {
 return "Insert Name.";
 } 
// ADDRESS
if ($("#address").val().trim() == "")
 {
 return "Insert Address.";
 } 
// PHONENO------------------------
if ($("#phoneNo").val().trim() == "")
 {
 return "Insert Phone No.";
 }
return true;
}