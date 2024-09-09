document.addEventListener('DOMContentLoaded', function () {
    // START SELECT
        const schoolSelects = document.querySelectorAll('.school_select');
        const classSelects = document.querySelectorAll('.class_select');
        const teacherSelects = document.querySelectorAll('.teacher_select');
        const subjectSelects = document.querySelectorAll('.subject_select');
        const studentSelects = document.querySelectorAll('.student_select');
        const transcriptSelects = document.querySelectorAll('.transcript_select');

        const selectedSubjectId = '${scoreCard.subject.id}'; // Subject đã chọn trong cơ sở dữ liệu

        function getClassBySchoolId(schoolId, classSelect) {
            if (schoolId && schoolId !== "0") {
                // fetch class
                fetch(`/event/getClassBySchoolId/${schoolId}`)
                    .then(response => response.json())
                    .then(classes => {
                        // clear previous class
                        classSelect.innerHTML = '<option value="0">--Select Class--</option>';
                        classes.forEach(classItem => {
                            const option = document.createElement('option');
                            option.value = classItem.id;
                            option.textContent = classItem.name;
                            classSelect.appendChild(option);
                        });
                    })
                    .catch(error => console.error('Error fetching Class:', error));
            } else {
                // if no school selected
                classSelect.innerHTML = '<option value="0">--Select Class--</option>';
            }
        }

        function getStudentByClassAndSchool(schoolId, classId, studentSelect) {
            if (schoolId && classId && schoolId !== "0" && classId !== "0") {
                // fetch student
                fetch(`/event/getStudentByClassAndSchool/${schoolId}/${classId}`)
                    .then(response => response.json())
                    .then(students => {
                        // clear student previous
                        studentSelect.innerHTML = '<option>--Select Student--</option>';

                        // populate new teacher
                        students.forEach(studentItem => {
                            const option = document.createElement('option');
                            option.value = studentItem.id;
                            option.textContent = `${studentItem.lastName} ${studentItem.firstName}`;
                            studentSelect.appendChild(option);
                        });
                    })
                    .catch(error => console.error('Error fetching Student:', error));
            } else {
                // if no class or school selected
                studentSelect.innerHTML = '<option>--Select Student--</option>';
            }
        }

        function getSubjectBySchool(schoolId, subjectSelect, subjectSelectedId) {
            if (schoolId && schoolId !== "0") {
                fetch(`/event/getSubjectBySchool/${schoolId}`)
                    .then(response => response.json())
                    .then(subjects => {
                        // clear previous subject
                        subjectSelect.innerHTML = '<option value="0">--Select Subject--</option>';

                        // populate new subject
                        subjects.forEach(subjectItem => {
                            const option = document.createElement('option');
                            option.value = subjectItem.id;
                            option.textContent = subjectItem.nameSubject;

                            // If subject matches the one in the database, mark it as selected
                            if (subjectItem.id === selectedSubjectId) {
                                option.selected = true;
                            }

                            subjectSelect.appendChild(option);
                        });
                    })
                    .catch(error => console.error('Error fetching Subject:', error));
            } else {
                // if no school selected
                subjectSelect.innerHTML = '<option value="0">--Select Subject--</option>';
            }
        }

        function getTeacherBySchoolIdAndClassId(schoolId, classId, teacherSelect) {
            if (schoolId && classId && schoolId !== "0" && classId !== "0") {
                // fetch teacher
                fetch(`/event/getTeacherBySchoolAndClass/${schoolId}/${classId}`)
                    .then(response => response.json())
                    .then(teachers => {
                        // clear teacher previous
                        teacherSelect.innerHTML = '<option>--Select Teacher--</option>';

                        // populate new teacher
                        teachers.forEach(teacherItem => {
                            const option = document.createElement('option');
                            option.value = teacherItem.id;
                            option.textContent = `${teacherItem.lastName} ${teacherItem.firstName}`;
                            teacherSelect.appendChild(option);
                        });
                    })
                    .catch(error => console.error('Error fetching Teacher:', error));
            } else {
                // if no class or school selected
                teacherSelect.innerHTML = '<option>--Select Teacher--</option>';
            }
        }

        function getTranscriptBySchool(schoolId, transcriptSelect){
            if(schoolId !== "0"){
                // fetch transcript
                fetch(`/event/getTranscriptBySchool/${schoolId}`)
                .then(response => response.json())
                .then(transcripts => {
                    // clear transcript previous
                    transcriptSelect.innerHTML = '<option>--Select Transcript--</option>';
                    // populate new transcript
                    transcripts.forEach(transcriptItem => {
                        const option = document.createElement('option');
                        option.value = transcriptItem.id;
                        option.textContent = `${transcriptItem.nameTranscript} ${transcriptItem.semester} (${transcriptItem.schoolYear})`;
                        transcriptSelect.appendChild(option);
                    });
                })
                .catch(error => console.error('Error fetching Transcript:', error));
            } else {
                // if no class or school selected
                transcriptSelect.innerHTML = '<option>--Select Transcript--</option>';
            }
        }

        // START EVENT LISTENER
            // event listener for school selection change
            schoolSelects.forEach(schoolSelect => {
                schoolSelect.addEventListener('change', function () {
                    const schoolId = schoolSelect.value;
                    const type = schoolSelect.dataset.type;

                    console.log('schoolId: ', schoolId);
                    console.log('Type: ', type);
                    // logic dependence type
                    if (type === "parent" || type === "student") {
                        classSelects.forEach(classSelect => {
                            if (classSelect) {
                                getClassBySchoolId(schoolId, classSelect);
                            }
                        });
                    } else if (type === "teacher") {
                        classSelects.forEach(classSelect => {
                            if (classSelect) {
                                getClassBySchoolId(schoolId, classSelect);
                            }
                        });
                        subjectSelects.forEach(subjectSelect => {
                            if (subjectSelect) {
                                getSubjectBySchool(schoolId, subjectSelect);
                            }
                        });
                    } else if (type === "score-card") {
                        classSelects.forEach(classSelect => {
                            if (classSelect) {
                                getClassBySchoolId(schoolId, classSelect);
                            }
                        });
                        transcriptSelects.forEach(transcriptSelect => {
                            if (transcriptSelect) {
                                getTranscriptBySchool(schoolId, transcriptSelect);
                            }
                        });
                        subjectSelects.forEach(subjectSelect => {
                            if (subjectSelect) {
                                getSubjectBySchool(schoolId, subjectSelect);
                            }
                        });
                    }
                });
            });

            // event listener for the school and the class selection change
            classSelects.forEach(classSelect => {
                classSelect.addEventListener('change', function () {
                    const schoolSelect = classSelect.closest('.form-group').querySelector('.school_select');
                    const schoolId = schoolSelect ? schoolSelect.value : null;
                    const classId = classSelect.value;
                    const type = classSelect.dataset.type;
                    console.log('classId: ', classId);
                    console.log('Type: ', type);

                    if (schoolId && classId && schoolId !== "0" && classId !== "0") {
                        if (type === "student") {
                            // Assume teacherSelects is a collection of all teacher select elements
                            teacherSelects.forEach(teacherSelect =>{
                                getTeacherBySchoolIdAndClassId(schoolId, classId, teacherSelect);
                            });

                        } else if (type === "parent") {
                            // Assume studentSelects is a collection of all student select elements
                            studentSelects.forEach(studentSelect =>{
                                getStudentByClassAndSchool(schoolId, classId, studentSelect);
                            });
                        } else if (type === "score-card") {
                            // Assume studentSelects is a collection of all student select elements
                            studentSelects.forEach(studentSelect =>{
                                getStudentByClassAndSchool(schoolId, classId, studentSelect);
                            });
                        }
                    } else {
                        console.warn('Either schoolId or classId is invalid.');
                    }
                });
            });
            // automatically fetch classes if school selected
            schoolSelects.forEach(schoolSelect => {
                const schoolId = schoolSelect.value;
                const type = schoolSelect.dataset.type;

                if (schoolId && schoolId !== "0") {
                    // logic dependence type
                    if (type === "parent" || type === "student") {
                        classSelects.forEach(classSelect => {
                            if (classSelect) {
                                getClassBySchoolId(schoolId, classSelect);
                            }
                        });
                    } else if (type === "teacher") {
                        subjectSelects.forEach(subjectSelect => {
                            if (subjectSelect) {
                                getSubjectBySchool(schoolId, subjectSelect);
                            }
                        });
                        classSelects.forEach(classSelect => {
                            if (classSelect) {
                                getClassBySchoolId(schoolId, classSelect);
                            }
                        });
                    } else if (type === "score-card") {
                        classSelects.forEach(classSelect => {
                            if (classSelect) {
                                getClassBySchoolId(schoolId, classSelect);
                            }
                        });
                        transcriptSelects.forEach(transcriptSelect => {
                            if (transcriptSelect) {
                                getTranscriptBySchool(schoolId, transcriptSelect);
                            }
                        });
                        subjectSelects.forEach(subjectSelect => {
                            if (subjectSelect) {
                                getSubjectBySchool(schoolId, subjectSelect, selectedSubjectId);
                            }
                        });
                    }
                }
            });
        // END EVENT LISTENER
    // END SELECT
});




//        function fetchOptions(url, selectElement, optionPlaceholder, createOption) {
//            fetch(url)
//                .then(response => response.json())
//                .then(data => {
//                    selectElement.innerHTML = optionPlaceholder;
//                    data.forEach(createOption);
//                })
//                .catch(error => console.error('Error fetching data:', error));
//        }
        // function getTeacherBySchoolIdAndClassId(schoolId, classId, teacherSelects) {
        //     if (schoolId && classId && schoolId !== "0" && classId !== "0") {
        //         fetchOptions(`/m-teacher/getTeacherBySchoolAndClass/${schoolId}/${classId}`, teacherSelects,
        //             '<option value="0">--Select Teacher--</option>',
        //             teacherItem => {
        //                 const option = document.createElement('option');
        //                 option.value = teacherItem.id;
        //                 option.textContent = `${teacherItem.lastName} ${teacherItem.firstName}`;
        //                 teacherSelects.forEach(teacherSelect => teacherSelect.appendChild(option.cloneNode(true)));
        //             });
        //     } else {
        //         teacherSelects.forEach(teacherSelect => teacherSelect.innerHTML = '<option value="0">--Select Teacher--</option>');
        //     }
        // }

        // function getStudentByClassAndSchool(schoolId, classId, studentSelects) {
        //     if (schoolId && classId && schoolId !== "0" && classId !== "0") {
        //         fetchOptions(`/m-student/getStudentByClassAndSchool/${schoolId}/${classId}`, studentSelects, '<option value="0">--Select Student--</option>', studentItem =>{
        //             const option = document.createElement('option');
        //             option.value = studentItem.id;
        //             option.textContent = `${studentItem.lastName} ${studentItem.firstName}`;
        //             studentSelect.appendChild(option);
        //         });
        //     } else {
        //         // if no class or school selected
        //         studentSelects.forEach(studentSelect => studentSelect.innerHTML = '<option value="0">--Select Student--</option>');
        //     }
        // }
