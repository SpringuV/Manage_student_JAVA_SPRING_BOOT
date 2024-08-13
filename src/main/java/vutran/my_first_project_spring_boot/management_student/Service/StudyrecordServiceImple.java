package vutran.my_first_project_spring_boot.management_student.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vutran.my_first_project_spring_boot.management_student.Dao.StudyrecordRepository;
import vutran.my_first_project_spring_boot.management_student.Entity.StudyRecord;

import java.util.List;

@Service
public class StudyrecordServiceImple implements StudyrecordService {

    private StudyrecordRepository studyrecordRepository;

    @Autowired
    public StudyrecordServiceImple(StudyrecordRepository studyrecordRepository) {
        this.studyrecordRepository = studyrecordRepository;
    }

    @Override
    public List<StudyRecord> getAllStudyRecords() {
        return this.studyrecordRepository.findAll();
    }

    @Override
    public StudyRecord getStudyRecordById(int id) {
        return this.studyrecordRepository.findById(id).get();
    }

    @Override
    public StudyRecord addStudyRecord(StudyRecord studyRecord) {
        return this.studyrecordRepository.save(studyRecord);
    }

    @Override
    public void deleteStudyRecordById(int id) {
        this.studyrecordRepository.deleteById(id);
    }

    @Override
    public StudyRecord updateStudyRecord(StudyRecord studyRecord) {
        return this.studyrecordRepository.saveAndFlush(studyRecord);
    }

    @Override
    public StudyRecord getStudyRecordByStudentAndSchoolAndSchoolYear(int student_id, String schoolYear) {
        return this.studyrecordRepository.getStudyRecordByStudentAndSchoolAndSchoolYear(student_id, schoolYear);
    }
}
