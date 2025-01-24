package spring.learnteachsubject.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.learnteachsubject.model.Professor;
import spring.learnteachsubject.model.ProfessorSubject;
import spring.learnteachsubject.model.Student;
import spring.learnteachsubject.model.Subject;
import spring.learnteachsubject.model.exceptions.*;
import spring.learnteachsubject.repository.ProfessorRepository;
import spring.learnteachsubject.repository.ProfessorSubjectRepository;
import spring.learnteachsubject.repository.SubjectRepository;
import spring.learnteachsubject.service.ProfessorSubjectService;

import java.util.List;

@Service
public class ProfessorSubjectServiceImpl implements ProfessorSubjectService {

    private final ProfessorSubjectRepository professorSubjectRepository;
    private final ProfessorRepository professorRepository;

    private final SubjectRepository subjectRepository;

    public ProfessorSubjectServiceImpl(ProfessorSubjectRepository professorSubjectRepository, ProfessorRepository professorRepository, SubjectRepository subjectRepository) {
        this.professorSubjectRepository = professorSubjectRepository;
        this.professorRepository = professorRepository;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<ProfessorSubject> findAll() {
        return this.professorSubjectRepository.findAll();
    }

    @Override
    public ProfessorSubject findById(Long id) {
        return this.professorSubjectRepository.findById(id).orElseThrow(ProfessorSubjectNotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        if(this.professorSubjectRepository.findById(id).isEmpty())
            throw new ProfessorSubjectNotFoundException();
        this.professorSubjectRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ProfessorSubject createProfessorSubject(ProfessorSubject professorSubject) {
        Professor professor = this.professorRepository.findById(professorSubject.getProfessor().getId()).orElseThrow(ProfessorNotFoundException::new);
        Subject subject = this.subjectRepository.findById(professorSubject.getSubject().getId()).orElseThrow(SubjectNotFoundException::new);
        if (professor== null || subject==null){
            throw new ProfessorSubjectNotSaveException();
        }
        ProfessorSubject newProfSubject = new ProfessorSubject();
        newProfSubject.setProfessor(professor);
        newProfSubject.setSubject(subject);
        return  this.professorSubjectRepository.save(newProfSubject);
    }

    @Override
    @Transactional
    public ProfessorSubject updateProfessorSubject(Long id, ProfessorSubject professorSubject) {
        ProfessorSubject updatedProfSubject = this.professorSubjectRepository.findById(id).orElseThrow(ProfessorSubjectNotFoundException::new);
        Professor professor = this.professorRepository.findById(professorSubject.getProfessor().getId()).orElseThrow(ProfessorNotFoundException::new);
        Subject subject = this.subjectRepository.findById(professorSubject.getSubject().getId()).orElseThrow(SubjectNotFoundException::new);

        updatedProfSubject.setSubject(subject);
        updatedProfSubject.setProfessor(professor);

        return this.professorSubjectRepository.save(updatedProfSubject);
    }
}
