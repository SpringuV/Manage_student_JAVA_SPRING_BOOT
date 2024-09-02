document.addEventListener('DOMContentLoaded', function () {
// START DISPLAY POSITION
        const positionSelect = document.getElementById('position_select');
        function hideAllFields() {
            // Hide all additional fields
            document.getElementById('parent-fields').style.display = "none";
            document.getElementById('teacher-fields').style.display = "none";
            document.getElementById('student-fields').style.display = "none";

//            // Clear all dropdowns
//                document.querySelectorAll('.class_select').forEach(select => select.innerHTML = '<option value="0">--Select Class--</option>');
//                document.querySelectorAll('.student_select').forEach(select => select.innerHTML = '<option value="0">--Select Student--</option>');
//                document.querySelectorAll('.teacher_select').forEach(select => select.innerHTML = '<option value="0">--Select Teacher--</option>');
//                document.querySelectorAll('.subject_select').forEach(select => select.innerHTML = '<option value="0">--Select Subject--</option>');
        }

        // Hide all fields initially
        hideAllFields();

        // Display the corresponding fields based on the position
        if(positionSelect){
            positionSelect.addEventListener('change', function () {
                hideAllFields();
                const position = positionSelect.value;
                if (position === "Parent") {
                    document.getElementById('parent-fields').style.display = "block";
                }
                if (position === "Teacher") {
                    document.getElementById('teacher-fields').style.display = "block";
                }
                if (position === "Student") {
                    document.getElementById('student-fields').style.display = "block";
                }
                if(position === "User"){
                    hideAllFields();
                }
            });
        }
    // END DISPLAY POSITION
});