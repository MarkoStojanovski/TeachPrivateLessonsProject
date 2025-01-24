package spring.learnteachsubject.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.learnteachsubject.model.Professor;
import spring.learnteachsubject.model.ProfessorSubject;
import spring.learnteachsubject.service.ProfessorSubjectService;

import java.util.List;

@RestController
@RequestMapping("/api/professorSubjects")
public class ProfessorSubjectRestController {

    private final ProfessorSubjectService professorSubjectService;

    public ProfessorSubjectRestController(ProfessorSubjectService professorSubjectService) {
        this.professorSubjectService = professorSubjectService;
    }

    @GetMapping
    public ResponseEntity<List<ProfessorSubject>> getAllProfSubjects() {
        List<ProfessorSubject> professorSubjects = this.professorSubjectService.findAll();
        return new ResponseEntity<>(professorSubjects, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorSubject> getProfessorSubject(@PathVariable Long id) {
        ProfessorSubject professorSubject = this.professorSubjectService.findById(id);
        return ResponseEntity.ok(professorSubject);
    }

    @PostMapping
    public ResponseEntity<ProfessorSubject> createProfSubject(
            @RequestBody ProfessorSubject professorSubject
    ) {
        ProfessorSubject createdProfessorSubject = this.professorSubjectService.createProfessorSubject(professorSubject);
        return new ResponseEntity<>(createdProfessorSubject, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorSubject> updateProfessorSubject(
            @PathVariable Long id,
            @RequestBody ProfessorSubject professorSubject
    ) {
        ProfessorSubject updatedProfessorSubject = this.professorSubjectService.updateProfessorSubject(id, professorSubject);
        return new ResponseEntity<>(updatedProfessorSubject, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ProfessorSubject> deleteProfessorSubject(
            @PathVariable Long id
    ) {
        this.professorSubjectService.deleteById(id);
        return ResponseEntity.ok().build();
    }
//    @ExceptionHandler(ProfessorSubjectNotFoundException.class)
//    public ResponseEntity<String> handleProfessorSubjectNotFound(ProfessorSubjectNotFoundException ex) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ProfessorSubject not found");
//    }
}
