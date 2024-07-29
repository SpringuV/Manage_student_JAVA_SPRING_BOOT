// set timeout welcome
document.addEventListener("DOMContentLoaded", function() {
    var element = document.getElementById("autoHide");
        setTimeout(function(){
            element.classList.add("hidden");
        }, 3000); // 3s
    }
);

// add more input in register
 function showRelevantFields() {
        var position = document.getElementById("position").value;
        var userFields = document.getElementById("userFields");
        var parentFields = document.getElementById("parentFields");
        var teacherFields = document.getElementById("teacherFields");

        // Hide all fields initially
        userFields.style.display = 'none';
        parentFields.style.display = 'none';
        teacherFields.style.display = 'none';

        // Show the relevant fields based on the selected position
        if (position === 'User') {
            userFields.style.display = 'block';
        } else if (position === 'Parent') {
            parentFields.style.display = 'block';
        } else if (position === 'Teacher') {
            teacherFields.style.display = 'block';
        }
    }
