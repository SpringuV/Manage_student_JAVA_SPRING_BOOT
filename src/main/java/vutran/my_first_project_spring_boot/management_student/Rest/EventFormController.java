package vutran.my_first_project_spring_boot.management_student.Rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import vutran.my_first_project_spring_boot.management_student.Dao.AuthorityRepository;
import vutran.my_first_project_spring_boot.management_student.Entity.*;
import vutran.my_first_project_spring_boot.management_student.Service.*;

import java.util.*;

@Controller
public class EventFormController {
    private UserService userService;
    private AuthorityRepository authorityRepository;
    private SchoolService schoolService;
    private ClassService classService;
    private SubjectService subjectService;
    private StudentService studentService;
    private TeacherService teacherService;
    private TranscriptService transcriptService;
    private ScoreCardService scoreCardService;

    @Autowired
    public EventFormController(ScoreCardService scoreCardService, TranscriptService transcriptService, UserService userService, TeacherService teacherService, StudentService studentService, AuthorityRepository authorityRepository,SubjectService subjectService, SchoolService schoolService, ClassService classService){
        this.userService = userService;
        this.subjectService = subjectService;
        this.authorityRepository = authorityRepository;
        this.schoolService = schoolService;
        this.classService = classService;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.transcriptService = transcriptService;
        this.scoreCardService = scoreCardService;
    }

    @GetMapping("/event/getTeacherBySchoolAndClass/{schoolId}/{classId}")
    @ResponseBody
    public List<Teacher> returnListTeacher(@PathVariable int schoolId, @PathVariable int classId){
        return teacherService.getListTeacherBySchoolIdAndClassID(schoolId, classId);
    }

    @GetMapping("/event/getStudentByClassAndSchool/{schoolId}/{classId}")
    @ResponseBody
    public List<Student> getListStudent(@PathVariable("schoolId") int school_id, @PathVariable("classId") int class_id){
        return  studentService.getListStudentByClassAndSchool(class_id, school_id);
    }


    @GetMapping("/event/getSubjectBySchool/{schoolId}")
    @ResponseBody
    public List<Subject> returnListSubject(@PathVariable int schoolId){
        return subjectService.getListSubjectOfSchoolId(schoolId);
    }

    @GetMapping("/event/getSchoolById/{schoolId}")
    @ResponseBody
    public School returnSchool(@PathVariable("schoolId") int id_school){
        return  schoolService.getSchoolById(id_school);
    }

    @GetMapping("/event/getClassBySchoolId/{schoolId}")
    @ResponseBody
    public List<Classes> returnListClass(@PathVariable("schoolId") int schoolId){
        return classService.getListClassByIdSchool(schoolId);
    }

    @GetMapping("/event/getListStudentByClass/{classId}")
    @ResponseBody
    public List<Student> returnListStudent(@PathVariable("classId") int class_id){
        return studentService.getListByClassId(class_id);
    }

    @GetMapping("/event/getListStudentByClassForDetailTranscript/{classId}/{semester}")
    @ResponseBody
    public ResponseEntity<?> returnListStudentAndScore(@PathVariable("classId") int class_id, @PathVariable("semester") int semester){
        List<Student> studentList = studentService.getListByClassId(class_id);
        List<Map<String, Object>> result = new ArrayList<>();

        for(Student student : studentList){
            Map<String, Object> studentData = new HashMap<>();
            studentData.put("id", student.getId());
            studentData.put("firstName", student.getFirstName());
            studentData.put("lastName", student.getLastName());

            // for each student, get their scores
            List<ScoreCard> scoreCardList = scoreCardService.getScorecardByStudentAndClassAndSemester(student.getId(), class_id, semester);
            Map<Integer, Double> subjectScores = new HashMap<>();
            for(ScoreCard scoreCard : scoreCardList){
                subjectScores.put(scoreCard.getSubject().getId(), scoreCard.getScore());
            }
            studentData.put("scores", subjectScores);
            result.add(studentData);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/event/getTranscriptBySchool/{schoolId}")
    @ResponseBody
    public List<Transcript> returnListTranscript(@PathVariable("schoolId") int id_school){
        return transcriptService.getTranscriptBySchool(id_school);
    }

    @GetMapping("/event/getListSubjectByGrade/{classId}")
    @ResponseBody
    public List<Subject> returnListSubjectByGrade(@PathVariable("classId") int id_class){
        String gradeClass = classService.getGrade(id_class);
        System.out.println("grade: "+ gradeClass);
        ArrayList<Subject> subjectListFromData = (ArrayList<Subject>) subjectService.getSubjectByClassGrade(gradeClass);
        subjectListFromData.sort(new Comparator<Subject>() {
            @Override
            public int compare(Subject o1, Subject o2) {
                if(o1.getNameSubject().compareTo(o2.getNameSubject()) < 0){
                    return -1;
                } else if(o1.getNameSubject().compareTo(o2.getNameSubject()) > 0){
                    return 1;
                } else return 0;
            }
        });
        return subjectListFromData;
    }

    // after login success return to home
    @GetMapping()
    public String showHomePage(Model model){
        return "home";
    }

    //homepage
    @GetMapping("/backHomepage")
    public String returnHomePage(){
        return "home";
    }

    // login
    @GetMapping("/showLoginPage")
    public String showLoginPage(@RequestParam(value = "expired", required = false) String expired, Model model){
        // Có thể kiểm tra xem tham số expired có tồn tại hay không
        if (expired != null) {
            // Xử lý logic nếu cần khi tham số expired tồn tại
            model.addAttribute("error", "Your session has expired. Please log in again.");
        }
        return "login";
    }

    //403
    @GetMapping("/showPage403")
    public String showPage403(){
        return "Error/403";
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
}
