// JavaScript file for the login JSP
/**
 * To be called onFormSubmit, it checks that the password #pass1 and repeat #pass2 are identic
 * @returns {Boolean} the comparision of #pass1 and #pass2
 */
function validatePassword() {
	var result = $("#pass1").val() == $("#pass2").val();
	if (!result) {
		alert("Passwords do not match");
	}
	return result;
}