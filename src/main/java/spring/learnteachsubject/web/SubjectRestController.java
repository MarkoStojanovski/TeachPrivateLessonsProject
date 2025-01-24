package spring.learnteachsubject.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.learnteachsubject.model.Subject;
import spring.learnteachsubject.service.SubjectService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/subjects")
public class SubjectRestController {

    private final SubjectService subjectService;

    public SubjectRestController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Subject>> getAllSubjects() {
        List<Subject> subjects = this.subjectService.findAll();
        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Map<String,Object>> getSubjects(
            @RequestParam(value = "subjectName", required = false) String subjectName,
            @RequestParam(value = "subjectLevel", required = false) String subjectLevel,
            @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        Pageable pageable = PageRequest.of(page,6);
        Page<Subject> subjectPage = this.subjectService.findByCriteria(subjectName,subjectLevel,pageable);

        Map<String, Object> response = new HashMap<>();

        response.put("subjects",subjectPage.getContent());
        response.put("totalPages",subjectPage.getTotalPages());
        response.put("currentPage",subjectPage.getNumber());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subject> getSubject(@PathVariable Long id) {
        Optional<Subject> subject = this.subjectService.findSubjectById(id);
        return subject.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<Subject> createSubject(
            @RequestBody Subject subject
    ) {
        Subject newSubject = this.subjectService.createSubject(subject);
        return new ResponseEntity<>(newSubject, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subject> updateSubject(
            @PathVariable Long id,
            @RequestBody Subject subject) {
        Subject updatedSubject = this.subjectService.updateSubject(id, subject);
        return new ResponseEntity<>(updatedSubject, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Subject> deleteSubject(@PathVariable Long id) {
        this.subjectService.deleteSubjectById(id);
        return ResponseEntity.ok().build();
    }

}
