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

/* timer in home page
// Set the countdown time (example: 5 minutes)
       var countdownTime = 5 * 60 * 1000; // 5 minutes in milliseconds

       function updateTimer() {
           var now = new Date().getTime();
           var distance = countdownTime - (now % countdownTime); // Subtract the current time from countdown time

           var minutes = Math.floor(distance / (1000 * 60));
           var seconds = Math.floor((distance % (1000 * 60)) / 1000);

           // Format minutes and seconds
           minutes = minutes < 10 ? "0" + minutes : minutes;
           seconds = seconds < 10 ? "0" + seconds : seconds;

           // Display the result in the timer div
           document.getElementById("timer").innerHTML = "Time Left: " + minutes + ":" + seconds;

           // Update the timer every second
           setTimeout(updateTimer, 1000);
       }

       // Call the function to start the timer
       updateTimer();
*/

// time and date home page
function updateTime() {
           const now = new Date();
           const options = { year: 'numeric', month: 'long', day: 'numeric', hour: '2-digit', minute: '2-digit', second: '2-digit', hour12: false };
           const timeString = now.toLocaleDateString('en-US', options);
           document.getElementById('current-time').textContent = timeString;
       }

       setInterval(updateTime, 1000);
       updateTime();


/*
add-contact form description editer

tinymce.init({
		   selector: '#description', // Apply rich editor to the description field
		   menubar: true, // Disable the top menu bar (optional)
		   plugins: 'advlist autolink lists link image charmap print preview anchor',
		   toolbar: 'undo redo | bold italic underline | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image',
		   branding: false
	   });*/