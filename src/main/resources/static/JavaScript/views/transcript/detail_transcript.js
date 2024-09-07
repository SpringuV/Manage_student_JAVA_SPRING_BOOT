const classSelect = document.getElementById('transcript_class_select');
let subject_columnspan = document.getElementById('subject_columnspan');
    classSelect.addEventListener('change', function () {
    const classId = classSelect.value;

    if (classId !== '0') {
        // fetch student
        fetch(`/event/getListStudentByClass/${classId}`)
            .then(response => response.json())
            .then(students => {
                // fetch subject by class
                fetch(`/event/getListSubjectByGrade/${classId}`)
                .then(response => response.json())
                .then(subjects =>{
                    const tableBody = document.querySelector('#studentsTable tbody');
                    const subjectHeaders = document.querySelector('#nameSubject');

                    // clear previous content
                    tableBody.innerHTML = '';
                    subjectHeaders.innerHTML = ''; // reset header
                    // add subject columns to the header
                    columns1 = '<th>Id</th><th>Name</th>'; // add first heading to the student
                    subjectHeaders.insertAdjacentHTML('beforeend', columns1);

                    // set colspan for subject top row
                    const numSubject = subjects.length;
                    subject_columnspan.setAttribute('colspan', numSubject + 2);

                    subjects.forEach(subject => {
                        let row = `<td style="width: 100px;">${subject.nameSubject}</td>`;
                        subjectHeaders.insertAdjacentHTML('beforeend', row.replace(/_/g, ' '));
                    });
                    students.forEach(student => {
                        // Build the row for each student
                        let row = `<tr>
                                    <td>${student.id}</td>
                                    <td>${student.firstName} ${student.lastName}</td>`;
                            // Add input cells for each subject
                            subjects.forEach(subject => {
                                row += `<td>
                                            <input type="number" class="form-control text-center" min="0.0" max="10" name="studentScores[${student.id}][${subject.id}]" step="0.1" placeholder="Input score">
                                        </td>`;
                            });

                        row += `</tr>`;
                        // Add the complete row to the table body
                        tableBody.insertAdjacentHTML('beforeend', row);
                    });
                })
                .catch(error => console.error('Error fetching Subjects:', error));
            })
            .catch(error => console.error('Error fetching students:', error));
    } else {
        const tableBody = document.querySelector('#studentsTable tbody');
        tableBody.innerHTML = ''; // Clear existing rows
        const subjectHeader =  document.querySelector('#nameSubject');
        subjectHeader.innerHTML= '<th>None</th>'
    }
});