package spring.learnteachsubject.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.learnteachsubject.model.Student;
import spring.learnteachsubject.service.StudentService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentRestController {

    private final StudentService studentService;

    public StudentRestController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllStudents(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "institutionId", required = false) Long institutionId,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
            @RequestParam(value = "birthDate", required = false) LocalDate birthDate,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        Pageable pageable = PageRequest.of(page, 6);
        Page<Student> studentsPage = this.studentService.findByCriteria(institutionId, name, phoneNumber, birthDate, email, pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("students", studentsPage.getContent());
        response.put("totalPages", studentsPage.getTotalPages());
        response.put("currentPage", studentsPage.getNumber());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/tutors")
    public ResponseEntity<List<Student>> getAllStudentTutors() {
        List<Student> students = this.studentService.findStudentTutors();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Student>> getStudents() {
        List<Student> students = this.studentService.findAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(
            @PathVariable Long id
    ) {
        Optional<Student> student = this.studentService.findStudentById(id);
        return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(
            @RequestBody Student student
    ) {
        Student createdStudent = this.studentService.createStudent(student);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updatedStudent(
            @PathVariable Long id,
            @RequestBody Student student
    ) {
        Student updatedStudent = this.studentService.updateStudent(id, student);
        return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent(
            @PathVariable Long id
    ) {
        this.studentService.deleteStudentById(id);
        return ResponseEntity.ok().build();
    }

}
