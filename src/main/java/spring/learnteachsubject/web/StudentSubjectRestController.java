package spring.learnteachsubject.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.learnteachsubject.model.ProfessorSubject;
import spring.learnteachsubject.model.StudentSubject;
import spring.learnteachsubject.service.ProfessorSubjectService;
import spring.learnteachsubject.service.StudentSubjectService;

import java.util.List;

@RestController
@RequestMapping("/api/studentSubjects")
public class StudentSubjectRestController {
    private final StudentSubjectService studentSubjectService;

    public StudentSubjectRestController(StudentSubjectService studentSubjectService) {
        this.studentSubjectService = studentSubjectService;
    }

    @GetMapping
    public ResponseEntity<List<StudentSubject>> getAllStudentSubjects() {
        List<StudentSubject> studentSubjects = this.studentSubjectService.findAll();
        return new ResponseEntity<>(studentSubjects, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentSubject> getStudentSubject(@PathVariable Long id) {
        StudentSubject studentSubject = this.studentSubjectService.findById(id);
        return ResponseEntity.ok(studentSubject);
    }

    @PostMapping
    public ResponseEntity<StudentSubject> createStudentSubject(
            @RequestBody StudentSubject studentSubject
    ) {
        StudentSubject createdStudentSubject = this.studentSubjectService.createStudentSubject(studentSubject);
        return new ResponseEntity<>(createdStudentSubject, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentSubject> updateStudentSubject(
            @PathVariable Long id,
            @RequestBody StudentSubject studentSubject
    ) {
        StudentSubject updatedStudentSubject = this.studentSubjectService.updateStudentSubject(id, studentSubject);
        return new ResponseEntity<>(updatedStudentSubject, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<StudentSubject> deleteStudentSubject(
            @PathVariable Long id
    ) {
        this.studentSubjectService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
