package spring.learnteachsubject.service;

import spring.learnteachsubject.model.ProfessorSubject;

import java.util.List;

public interface ProfessorSubjectService {

    List<ProfessorSubject> findAll();

    ProfessorSubject findById(Long id);

    void deleteById(Long id);

    ProfessorSubject createProfessorSubject(ProfessorSubject professorSubject);

    ProfessorSubject updateProfessorSubject(Long id, ProfessorSubject professorSubject);

}
