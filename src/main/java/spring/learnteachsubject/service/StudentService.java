package spring.learnteachsubject.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spring.learnteachsubject.model.Student;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudentService {


    Page<Student> findAll(Pageable pageable);

    List<Student> findStudentTutors();

    Optional<Student> findStudentById(Long id);

    List<Student> findAllStudents();

    void deleteStudentById(Long id);

    Student createStudent(Student student);

    Student updateStudent(Long id, Student student);

    Page<Student> findByCriteria(Long institutionId, String name, String phoneNumber, LocalDate birthDate, String email, Pageable pageable);

    void writeStudentsToCSV(PrintWriter writer);


}
