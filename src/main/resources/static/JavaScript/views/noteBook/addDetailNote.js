document.addEventListener('DOMContentLoaded', function(){
    const schoolValue = document.getElementById('note_school_selected').value;
    const classValue = document.getElementById('note_class_selected').value;
    const teacherSelect = document.getElementById('note_detail_teacher_select');
    const subjectSelect = document.getElementById('note_detail_subject_select');

    function getSubjectByClass(classId){
        fetch(`/event/getListSubjectByGrade/${classId}`)
        .then(response => response.json())
        .then(subjectSelects => {
            // clear previous subject
            subjectSelect.innerHTML = '<option value="0">--Select Subject--</option>';

            // populate new subject
            subjectSelects.forEach(subject => {
                const option = document.createElement('option');
                option.value = subject.id;
                option.textContent = subject.nameSubject;
                subjectSelect.appendChild(option);
            });
        })
        .catch(error => console.error('Error fetching subject:', error));
    }

    function getTeacherBySchoolAndClassAndSubject(schoolId, classId, subjectId){
        fetch(`/event/getTeacherBySchoolAndClassAndSubject/${schoolId}/${classId}/${subjectId}`)
        .then(response => response.json())
        .then(teacherSelects =>{
            // clear previous teacher
            teacherSelect.innerHTML = '<option value="0">--Select Teacher--</option>';

            // populate new subject
            teacherSelects.forEach(teacher => {
                const option = document.createElement('option');
                option.value = teacher.id;
                option.textContent = `${teacher.firstName} ${teacher.lastName}`;
                teacherSelect.appendChild(option);
            });
        })
        .catch(error => console.error('Error fetching Teacher and Class:', error));
    }

    subjectSelect.addEventListener('change', function(){
        const subjectId = subjectSelect.value;
        if(subjectId !== "0"){
            getTeacherBySchoolAndClassAndSubject(schoolValue, classValue, subjectId);
        }
    });

    if(classValue && classValue !== "0"){
        getSubjectByClass(classValue);
    } else {
        subjectSelect.innerHTML = '<option value="0">--Select Subject--</option>';
    }

    // if subject selected
    const subjectId = subjectSelect.value;
    if(subjectId && subjectId !== "0"){
        getTeacherBySchoolAndClassAndSubject(schoolValue, classValue, subjectId);
    } else {
        teacherSelect.innerHTML = '<option value="0">--Select Teacher--</option>';
    }
});