package spring.learnteachsubject.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.learnteachsubject.model.Student;
import spring.learnteachsubject.model.StudentSubject;
import spring.learnteachsubject.model.Subject;
import spring.learnteachsubject.model.exceptions.ProfessorSubjectNotSaveException;
import spring.learnteachsubject.model.exceptions.StudentNotFoundException;
import spring.learnteachsubject.model.exceptions.StudentSubjectNotFoundException;
import spring.learnteachsubject.model.exceptions.SubjectNotFoundException;
import spring.learnteachsubject.repository.StudentRepository;
import spring.learnteachsubject.repository.StudentSubjectRepository;
import spring.learnteachsubject.repository.SubjectRepository;
import spring.learnteachsubject.service.StudentSubjectService;

import java.util.List;

@Service
public class StudentSubjectServiceImpl implements StudentSubjectService {

    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    private final StudentSubjectRepository studentSubjectRepository;

    public StudentSubjectServiceImpl(StudentRepository studentRepository, SubjectRepository subjectRepository, StudentSubjectRepository studentSubjectRepository) {
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.studentSubjectRepository = studentSubjectRepository;
    }

    @Override
    public List<StudentSubject> findAll() {
        return this.studentSubjectRepository.findAll();
    }

    @Override
    public StudentSubject findById(Long id) {
        return this.studentSubjectRepository.findById(id).orElseThrow(StudentSubjectNotFoundException::new);
    }

    @Override
    @Transactional
    public StudentSubject createStudentSubject(StudentSubject studentSubject) {
        Student student = this.studentRepository.findById(studentSubject.getStudent().getId()).orElseThrow(StudentNotFoundException::new);
        Subject subject = this.subjectRepository.findById(studentSubject.getSubject().getId()).orElseThrow(SubjectNotFoundException::new);

        studentSubject.setSubject(subject);
        studentSubject.setStudent(student);
        return this.studentSubjectRepository.save(studentSubject);
    }

    @Override
    @Transactional
    public StudentSubject updateStudentSubject(Long id, StudentSubject studentSubject) {
        StudentSubject updatedStudentSubject = this.studentSubjectRepository.findById(id).orElseThrow(StudentSubjectNotFoundException::new);

        Student student = this.studentRepository.findById(studentSubject.getStudent().getId()).orElseThrow(StudentNotFoundException::new);
        Subject subject = this.subjectRepository.findById(studentSubject.getSubject().getId()).orElseThrow(SubjectNotFoundException::new);
        updatedStudentSubject.setSubject(subject);
        updatedStudentSubject.setStudent(student);
        return this.studentSubjectRepository.save(updatedStudentSubject);
    }

    @Override
    public void deleteById(Long id) {
        if(this.studentSubjectRepository.findById(id).isEmpty())
            throw new StudentSubjectNotFoundException();
        this.studentSubjectRepository.deleteById(id);
    }
}
