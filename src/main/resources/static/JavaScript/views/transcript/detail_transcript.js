// statistical
let excellent = document.getElementById('excellent');
let very_good = document.getElementById('very_good');
let fairly = document.getElementById('fairly');
let average = document.getElementById('average');
let below_average = document.getElementById('below_average');
let weak = document.getElementById('weak');
let poor = document.getElementById('poor');

const classSelect = document.getElementById('transcript_class_select');
let subject_columnspan = document.getElementById('subject_columnspan');
classSelect.addEventListener('change', function () {
    const classId = classSelect.value;
    const semester = document.getElementById('semester').textContent;

    // count or reset counter before starting a new calculation
    let excellent_total = 0;
    let fairly_total = 0;
    let very_good_total = 0;
    let average_total = 0;
    let below_average_total = 0;
    let weak_total = 0;
    let poor_total = 0;

    if (classId !== '0') {
        // fetch student
        fetch(`/event/getListStudentByClassForDetailTranscript/${classId}/${semester}`)
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
                        let row = `<td style="width: 100px;">${subject.nameSubject.replace(/_/g, ' ')}</td>`;
                        subjectHeaders.insertAdjacentHTML('beforeend', row);
                    });

                    // calculator gpa
                    let average_score = 0;
                    let total_score = 0;

                    students.forEach(student => {
                        total_score = 0; // reset total score for each student

                        // Build the row for each student
                        let row = `<tr>
                                    <td>${student.id}</td>
                                    <td>${student.firstName} ${student.lastName}</td>`;
                            // Add input cells for each subject
                            subjects.forEach(subject => {
                                // Check if student already has a score for this subject
                                let score = parseFloat(student.scores[subject.id]) || ''; // get score if exists or empty string and parse score to float or default to 0
                                if(score !== ''){
                                    row += `<td>
                                                <label class="form-label">${score}</label>
                                            </td>`;
                                    total_score += score;
                                }
                                if(score === ''){
                                    row += `<td>
                                                <input type="number" class="form-control text-center"
                                                 min="0" max="10" name="studentScores[${student.id}][${subject.id}]"
                                                 step="0.1" placeholder="Input score" value="${score}">
                                            </td>`;
                                }
                            });
                        row += `</tr>`;
                        // Add the complete row to the table body
                        tableBody.insertAdjacentHTML('beforeend', row);

                        // classification logic
                        console.log('total_score: ', total_score);
                        average_score = (total_score / subjects.length).toFixed(2); // calculate average score, tofixed: round up points
                        console.log('average_score: ', average_score);
                        if(average_score >= 9.0 && average_score <= 10.0){
                            excellent_total += 1;
                        } else if(average_score >= 8.0 && average_score < 9.0){
                            very_good_total += 1;
                        } else if(average_score >= 6.5 && average_score < 8.0){
                            fairly_total += 1;
                        } else if(average_score >= 5.0 && average_score < 6.5){
                            average_total += 1;
                        } else if(average_score >= 4.0 && average_score < 5.0){
                            below_average_total += 1;
                        } else if(average_score >= 2.5 && average_score < 4.0){
                            weak_total += 1;
                        } else if(average_score >= 0.1 && average_score < 2.5){
                            poor_total += 1;
                        }
                    });
                    excellent.textContent = excellent_total;
                    very_good.textContent = very_good_total;
                    fairly.textContent = fairly_total;
                    average.textContent = average_total;
                    below_average.textContent = below_average_total;
                    weak.textContent = weak_total;
                    poor.textContent = poor_total;
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