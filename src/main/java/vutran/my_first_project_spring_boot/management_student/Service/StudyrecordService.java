package vutran.my_first_project_spring_boot.management_student.Service;

import vutran.my_first_project_spring_boot.management_student.Entity.StudyRecord;

import java.util.List;

public interface StudyrecordService {
    public List<StudyRecord> getAllStudyRecords();
    public StudyRecord getStudyRecordById(int id);
    public StudyRecord addStudyRecord(StudyRecord studyRecord);
    public void deleteStudyRecordById(int id);
    public StudyRecord updateStudyRecord(StudyRecord studyRecord);
    public StudyRecord getStudyRecordByStudentAndSchoolAndSchoolYear(int student_id, String schoolYear);
}
