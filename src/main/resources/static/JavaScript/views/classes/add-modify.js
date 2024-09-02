// if school selected, check level school to input class grade
    document.addEventListener('DOMContentLoaded', function() {
        const schoolSelect = document.getElementById('school_select');
        const gradeSelect = document.getElementById('class_grade_select');

        function getSchoolById(schoolId){
            if (schoolId) {
                fetch(`/m-class/getSchoolById/${schoolId}`)
                    .then(response => response.json())
                    .then(school => {
                        const grade = school.level;

                        // Xóa tất cả các option hiện tại trong gradeSelect
                        gradeSelect.innerHTML = '';

                        // Tạo lại các option dựa trên level của school
                        let options = [];
                        if (grade === 'Primary_School') {
                            options = [1, 2, 3, 4, 5];
                        } else if (grade === 'Junior_High_School') {
                            options = [6, 7, 8, 9];
                        } else if (grade === 'High_School'){
                            options = [10, 11, 12];
                        }

                        // Thêm các option mới vào gradeSelect
                        options.forEach(function(opt) {
                            const optionElement = document.createElement('option');
                            optionElement.value = opt;
                            optionElement.textContent = opt;
                            gradeSelect.appendChild(optionElement);
                        });

                        // Tùy chọn "Select Grade"
                        const defaultOption = document.createElement('option');
                        defaultOption.value = "0";
                        defaultOption.textContent = "--Select Grade--";
                        defaultOption.selected = true;
                        gradeSelect.prepend(defaultOption);
                    })
                    .catch(error => {
                        console.error('Error fetching school data:', error);
                    });
            }
        }

        schoolSelect.addEventListener('change', function() {
            const schoolId = schoolSelect.value;
            getSchoolById(schoolId);
        });

        // if school selected
        const schoolId = schoolSelect.value;
        if(schoolId){
            getSchoolById(schoolId);
        }
    });