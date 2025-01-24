package spring.learnteachsubject.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.learnteachsubject.model.Subject;
import spring.learnteachsubject.model.exceptions.SubjectNotFoundException;
import spring.learnteachsubject.repository.SubjectRepository;
import spring.learnteachsubject.service.SubjectService;

import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<Subject> findAll() {
        return this.subjectRepository.findNonDeletedSubjects();
    }

    @Override
    public Page<Subject> findAllSubjects(Pageable pageable) {
        return this.subjectRepository.findAllSubjects(pageable);
    }

    @Override
    public Optional<Subject> findSubjectById(Long id) {
        return Optional.of(this.subjectRepository.findById(id).orElseThrow(SubjectNotFoundException::new));
    }
    @Override
    public void deleteSubjectById(Long id) {
        Subject subject = this.subjectRepository.findById(id)
                .orElseThrow(SubjectNotFoundException::new);
        subject.setDeleted(true);
        this.subjectRepository.save(subject);
    }

    @Override
    @Transactional
    public Subject createSubject(Subject subject) {
        Subject createdSubject = new Subject();

        createdSubject.setDescription(subject.getDescription());
        createdSubject.setSubjectName(subject.getSubjectName());
        createdSubject.setSubjectLevel(subject.getSubjectLevel());
        createdSubject.setUniSubject(subject.getUniSubject());

        return this.subjectRepository.save(createdSubject);
    }

    @Override
    @Transactional
    public Subject updateSubject(Long id, Subject subject) {
        Subject updatedSubject = this.subjectRepository.findById(id).orElseThrow(SubjectNotFoundException::new);

        updatedSubject.setDescription(subject.getDescription());
        updatedSubject.setSubjectName(subject.getSubjectName());
        updatedSubject.setSubjectLevel(subject.getSubjectLevel());
        updatedSubject.setUniSubject(subject.getUniSubject());

        return this.subjectRepository.save(updatedSubject);
    }

    @Override
    public Page<Subject> findByCriteria(String subjectName, String subjectLevel, Pageable pageable) {
        if ( subjectName != null || subjectLevel != null ) {
            return this.subjectRepository.findByCriteria(subjectName, subjectLevel, pageable);
        }
        return this.subjectRepository.findAllSubjects(pageable);
    }

    @Override
    public void writeSubjectsToCSV(PrintWriter writer) {
        writer.println("ID,Subject Name, Subject Level, University Subject");

        List<Subject> subjects = this.subjectRepository.findNonDeletedSubjects();

        for (Subject subject : subjects) {
            writer.printf("%s,%s,%s,%s%n",
                    subject.getId() != null ? subject.getId() : "",
                    subject.getSubjectName() != null ? subject.getSubjectName() : "",
                    subject.getSubjectLevel() != null ? subject.getSubjectLevel() : "",
                    subject.getUniSubject() != null ? subject.getUniSubject().toString() : ""
                    );
        }
    }
}
