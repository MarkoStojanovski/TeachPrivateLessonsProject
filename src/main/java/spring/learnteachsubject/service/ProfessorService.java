package spring.learnteachsubject.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spring.learnteachsubject.model.Professor;
import spring.learnteachsubject.model.Student;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProfessorService {

    Page<Professor> findAll(Pageable pageable);
    Optional<Professor> findProfessorById(Long id);

    List<Professor> findAllProfessors();

    void deleteProfessorById(Long id);

    Professor createProfessor(Professor professor);

    Professor updateProfessor(Long id, Professor professor);

    Page<Professor> findByCriteria(Long institutionId, String name, String phoneNumber, Integer years, String email, Pageable pageable);

    void writeProfessorsToCSV(PrintWriter writer);
}
