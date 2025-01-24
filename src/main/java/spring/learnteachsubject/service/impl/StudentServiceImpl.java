package spring.learnteachsubject.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.learnteachsubject.model.Institution;
import spring.learnteachsubject.model.Student;
import spring.learnteachsubject.model.User;
import spring.learnteachsubject.model.enumeration.Role;
import spring.learnteachsubject.model.exceptions.InstitutionNotFoundException;
import spring.learnteachsubject.model.exceptions.StudentNotFoundException;
import spring.learnteachsubject.model.exceptions.StudentNotSaveException;
import spring.learnteachsubject.model.exceptions.UserNotFoundException;
import spring.learnteachsubject.repository.InstitutionRepository;
import spring.learnteachsubject.repository.StudentRepository;
import spring.learnteachsubject.repository.UserRepository;
import spring.learnteachsubject.service.StudentService;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final InstitutionRepository institutionRepository;
    private final UserRepository userRepository;


    public StudentServiceImpl(StudentRepository studentRepository, InstitutionRepository institutionRepository, UserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.institutionRepository = institutionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Page<Student> findAll(Pageable pageable) {
        return this.studentRepository.findAllStudents(pageable);
    }

    @Override
    public List<Student> findStudentTutors() {
        return this.studentRepository.findAllStudentTutors();
    }


    @Override
    public Optional<Student> findStudentById(Long id) {
        return Optional.of(this.studentRepository.findById(id).orElseThrow(StudentNotFoundException::new));
    }

    @Override
    public List<Student> findAllStudents() {
        return this.studentRepository.findStudents();
    }

    @Override
    public void deleteStudentById(Long id) {
        Student student = this.studentRepository.findById(id)
                .orElseThrow(StudentNotFoundException::new);
        student.setDeleted(true);
        this.studentRepository.save(student);
    }


    @Override
    @Transactional
    public Student createStudent(Student student) {
        User user = this.userRepository.findByEmail(student.getUser().getEmail());

        if (user == null) {
            throw new UserNotFoundException();
        }

        Institution institution = this.institutionRepository.findById(student.getInstitution().getId()).orElseThrow(InstitutionNotFoundException::new);

        Student createdStudent = new Student();

        createdStudent.setName(student.getName());
        createdStudent.setSurname(student.getSurname());
        createdStudent.setBirthDate(student.getBirthDate());
        createdStudent.setPhoneNumber(student.getPhoneNumber());
        createdStudent.setUser(user);
        createdStudent.setInstitution(institution);


        return this.studentRepository.save(createdStudent);
    }

    @Override
    @Transactional
    public Student updateStudent(Long id, Student student) {
        User user = this.userRepository.findByEmail(student.getUser().getEmail());
        Institution institution = this.institutionRepository.findById(student.getInstitution().getId()).orElseThrow(InstitutionNotFoundException::new);

        if (user == null || (!user.getRole().equals(Role.STUDENT) && !user.getRole().equals(Role.STUDENT_TUTOR))) {
            throw new StudentNotSaveException("User not found or not a student/nor a student tutor");
        }
        Student updatedStudent = this.studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);

        updatedStudent.setName(student.getName());
        updatedStudent.setSurname(student.getSurname());
        updatedStudent.setBirthDate(student.getBirthDate());
        updatedStudent.setPhoneNumber(student.getPhoneNumber());
        updatedStudent.setUser(user);
        updatedStudent.setInstitution(institution);

        return this.studentRepository.save(updatedStudent);
    }

    @Override
    public Page<Student> findByCriteria(Long institutionId, String name, String phoneNumber, LocalDate birthDate, String email, Pageable pageable) {
        if (name == null && institutionId == null && phoneNumber == null && birthDate == null && email == null) {
            return studentRepository.findByDeletedIsFalse(pageable);
        }

        if (name != null && birthDate != null && email == null && phoneNumber == null && institutionId == null) {
            return this.studentRepository.findByNameAndBirth(name, birthDate, pageable);
        }

        if (name != null && institutionId != null && birthDate == null) {
            return this.studentRepository.findByNameAndInstitution(name, institutionId, pageable);
        }

        if (name != null && phoneNumber == null && email == null && birthDate == null) {
            return this.studentRepository.findByName(name, pageable);
        }

        if (institutionId != null && birthDate == null) {
            return studentRepository.findByInstitutionId(institutionId, pageable);
        }

        if (phoneNumber != null && birthDate == null) {
            return studentRepository.findByPhone(phoneNumber, pageable);
        }

        if (email != null && birthDate == null) {
            return studentRepository.findByEmail(email, pageable);
        }
        if (birthDate != null) {
            return this.studentRepository.findByBirthDateAfter(birthDate, pageable);
        }
        return Page.empty();
    }

    @Override
    public void writeStudentsToCSV(PrintWriter writer) {
        writer.println("ID, Name, Surname, Birth Date, Phone Number, Email, Institution");

        List<Student> students = this.studentRepository.findStudents();

        for (Student student : students) {
            writer.printf("%s,%s,%s,%s,%s,%s,%s%n",
                    student.getId() != null ? student.getId() : "",
                    student.getName() != null ? student.getName() : "",
                    student.getSurname() != null ? student.getSurname() : "",
                    student.getBirthDate() != null ? student.getBirthDate().toString() : "",
                    student.getPhoneNumber() != null ? student.getPhoneNumber() : "",
                    student.getUser().getEmail() != null ? student.getUser().getEmail() : "",
                    student.getInstitution() != null ? student.getInstitution().getName() + ' ' + student.getInstitution().getLocation() : ""
            );
        }

    }

}
