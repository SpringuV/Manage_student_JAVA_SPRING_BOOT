document.addEventListener('DOMContentLoaded', function () {
    const schoolLevelSelect = document.getElementById('schoolLevelSelect');
    const subjectSelectName = document.getElementById('subjectSelectName');
    function getValueSchoolLevelSelected(selectValue) {
        let option = [];
        if (selectValue === "Primary_School") {
            option = ['Literature 1', 'Literature 2', 'Literature 3', 'Literature 4', 'Literature 5', 'Math 1',
                'Math 2',
                'Math 3',
                'Math 4',
                'Math 5',
                'Music 1',
                'Music 2',
                'Music 3',
                'Music 4',
                'Music 5',
                'History 1',
                'History 2',
                'History 3',
                'History 4',
                'History 5',
                'Geography 1',
                'Geography 2',
                'Geography 3',
                'Geography 4',
                'Geography 5',
                'Civic Education 1',
                'Civic Education 2',
                'Civic Education 3',
                'Civic Education 4',
                'Civic Education 5',
                'English Language 1',
                'English Language 2',
                'English Language 3',
                'English Language 4',
                'English Language 5',
            ];
        } else if (selectValue === "Junior_High_School") {
            option = [
                'Literature 6',
                'Literature 7',
                'Literature 8',
                'Literature 9',
                'Math 6',
                'Math 7',
                'Math 8',
                'Math 9',
                'History 6',
                'History 7',
                'History 8',
                'History 9',
                'Music 6',
                'Music 7',
                'Music 8',
                'Music 9',
                'Geography 6',
                'Geography 7',
                'Geography 8',
                'Geography 9',
                'Biology 6',
                'Biology 7',
                'Biology 8',
                'Biology 9',
                'Civic Education 6',
                'Civic Education 7',
                'Civic Education 8',
                'Civic Education 9',
                'Physical Education 6',
                'Physical Education 7',
                'Physical Education 8',
                'Physical Education 9',
                'English Language 6',
                'English Language 7',
                'English Language 8',
                'English Language 9',
                'Information Technology 6',
                'Information Technology 7',
                'Information Technology 8',
                'Information Technology 9',
                'Chemistry 8',
                'Chemistry 9',
            ];
        } else {
            option = [
                'Literature 10',
                'Literature 11',
                'Literature 12',
                'Math 10',
                'Math 11',
                'Math 12',
                'History 10',
                'History 11',
                'History 12',
                'Geography 10',
                'Geography 11',
                'Geography 12',
                'Biology 10',
                'Biology 11',
                'Biology 12',
                'Music 10',
                'Music 11',
                'Music 12',
                'Civic Education 10',
                'Civic Education 11',
                'Civic Education 12',
                'Physical Education 10',
                'Physical Education 11',
                'Physical Education 12',
                'English Language 10',
                'English Language 11',
                'English Language 12',
                'Chemistry 10',
                'Chemistry 11',
                'Chemistry 12',
                'Information Technology 10',
                'Information Technology 11',
                'Information Technology 12',
            ];
        }
        // Xóa tất cả các option hiện tại trừ option đầu tiên (None)
        subjectSelectName.innerHTML = 1;

        // add new options into subject select
        option.forEach(function (otp) {
            const optionElement = document.createElement('option');
            optionElement.value = otp.replace(/\s+/g, '_');
            // \s+: This regular expression pattern matches one or more whitespace characters (e.g., spaces, tabs).
            // g: The global flag, which means that the replacement should be done for all matches in the string, not just the first one.
            // '_': The replacement string, which will replace each sequence of whitespace characters.
            optionElement.textContent = otp;
            subjectSelectName.appendChild(optionElement);
        });
    }

    schoolLevelSelect.addEventListener('change', function () {
        // show subject
        const selectLevelValue = schoolLevelSelect.value;

        getValueSchoolLevelSelected(selectLevelValue);

        // Tùy chọn "Select Subject"
        const defaultOption = document.createElement('option');
        defaultOption.value = "None";
        defaultOption.textContent = "--Select Subject Name--";
        defaultOption.selected = true;
        subjectSelectName.prepend(defaultOption);
    });

    // if schoolLevelSelected
});