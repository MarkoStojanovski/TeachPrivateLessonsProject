package spring.learnteachsubject.service;

import spring.learnteachsubject.model.StudentSubject;

import java.util.List;

public interface StudentSubjectService {

    List<StudentSubject> findAll();

    StudentSubject findById(Long id);
    StudentSubject createStudentSubject(StudentSubject studentSubject);

    StudentSubject updateStudentSubject(Long id, StudentSubject studentSubject);

    void deleteById(Long id);

}
