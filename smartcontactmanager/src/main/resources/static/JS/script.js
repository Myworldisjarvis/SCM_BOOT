const toggleSidebar = () => {

	if ($(".sidebar").is(":visible")) {
		//band karna hai (true)

		$(".sidebar").css("display", "none");
		$(".content").css("margin-left", "0%");

	} else {
		//show karna hai (flase)
		$(".sidebar").css("display", "block");
		$(".content").css("margin-left", "20%");
	}

};




/*search conatcs using fileds*/
function searchContacts() {
	const input = document.getElementById('searchInput').value.toLowerCase();
	const rows = document.querySelectorAll('.contacts-table tbody tr');

	rows.forEach(row => {
		const name = row.children[1].textContent.toLowerCase();
		const email = row.children[2].textContent.toLowerCase();
		const phone = row.children[3].textContent.toLowerCase();

		if (name.includes(input) || email.includes(input) || phone.includes(input)) {
			row.style.display = '';
		} else {
			row.style.display = 'none';
		}
	});
}



///hide msg after delete contact
window.setTimeout(function() {
       const successAlert = document.getElementById('success-alert');
       const errorAlert = document.getElementById('error-alert');

       if (successAlert) {
           successAlert.classList.remove('show');
       }
       if (errorAlert) {
           errorAlert.classList.remove('show');
       }
   }, 3000); // 3 seconds


/*
add-contact form description editer

tinymce.init({
		   selector: '#description', // Apply rich editor to the description field
		   menubar: true, // Disable the top menu bar (optional)
		   plugins: 'advlist autolink lists link image charmap print preview anchor',
		   toolbar: 'undo redo | bold italic underline | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image',
		   branding: false
	   });*/