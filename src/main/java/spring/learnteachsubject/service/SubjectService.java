package spring.learnteachsubject.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spring.learnteachsubject.model.Subject;

import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

public interface SubjectService {

    List<Subject> findAll();

    Page<Subject> findAllSubjects(Pageable pageable);

    Optional<Subject> findSubjectById(Long id);

    void deleteSubjectById(Long id);

    Subject createSubject(Subject subject);

    Subject updateSubject(Long id, Subject subject);

    Page<Subject> findByCriteria(String subjectName, String subjectLevel, Pageable pageable);

    void writeSubjectsToCSV(PrintWriter writer);

}
