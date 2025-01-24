package spring.learnteachsubject.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.learnteachsubject.model.Institution;
import spring.learnteachsubject.model.Professor;
import spring.learnteachsubject.model.Student;
import spring.learnteachsubject.model.User;
import spring.learnteachsubject.service.ProfessorService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/professors")
public class ProfessorRestController {

    private final ProfessorService professorService;

    public ProfessorRestController(ProfessorService professorService) {
        this.professorService = professorService;
    }


    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllProfessors(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "institutionId", required = false) Long institutionId,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
            @RequestParam(value = "years", required = false) Integer years,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        Pageable pageable = PageRequest.of(page, 6);
        Page<Professor> professorsPage = this.professorService.findByCriteria(institutionId
                , name, phoneNumber, years, email, pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("professors", professorsPage.getContent());
        response.put("totalPages", professorsPage.getTotalPages());
        response.put("currentPage", professorsPage.getNumber());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Professor>> getProfessors() {
        List<Professor> professors = this.professorService.findAllProfessors();
        return new ResponseEntity<>(professors, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Professor> getProfessor(
            @PathVariable Long id
    ) {

        Optional<Professor> professor = this.professorService.findProfessorById(id);
        return professor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Professor> createProfessor(
            @RequestBody Professor professor) {

        Professor createdProfessor = this.professorService.createProfessor(professor);
        return new ResponseEntity<>(createdProfessor, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Professor> updateProfessor(
            @PathVariable Long id,
            @RequestBody Professor professor) {

        Professor updatingProfessor = this.professorService.updateProfessor(id, professor);
        return new ResponseEntity<>(updatingProfessor, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Professor> deleteProfessor(
            @PathVariable Long id
    ) {
        this.professorService.deleteProfessorById(id);
        return ResponseEntity.ok().build();
    }

}
