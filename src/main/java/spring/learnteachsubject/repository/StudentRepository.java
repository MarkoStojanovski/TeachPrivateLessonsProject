package spring.learnteachsubject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.learnteachsubject.model.Student;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {


    @Query("SELECT S FROM Student AS S WHERE S.user IS NOT NULL AND S.isDeleted = false AND S.institution.isDeleted = false ")
    Page<Student> findAllStudents(Pageable pageable);


    @Query("SELECT S FROM Student AS S WHERE S.user IS NOT NULL AND S.user.role = 'STUDENT_TUTOR' AND S.isDeleted = false AND S.institution.isDeleted = false ")
    List<Student> findAllStudentTutors();


    @Query("SELECT S FROM Student AS S WHERE S.user IS NOT NULL AND S.isDeleted = false AND S.institution.isDeleted = false ")
    List<Student> findStudents();

    @Modifying
    @Transactional
    @Query("UPDATE Student S SET S.user = NULL WHERE S.user.email = :email")
    void updateUserToNullByEmail(@Param("email") String email);

    @Query(" SELECT S FROM Student AS S WHERE " +
            "S. user IS NOT NULL  AND (LOWER(S.name) LIKE LOWER(CONCAT('%',:name,'%')) OR :name IS NULL) AND " +
            "(S.institution.id = :institutionId OR :institutionId IS NULL) AND " +
            "S.isDeleted = false")
    Page<Student> findByNameAndInstitution(@Param("name") String name,
                                           @Param("institutionId") Long institutionId,
                                           Pageable pageable);

    @Query("SELECT S FROM Student AS S WHERE S.user IS NOT NULL AND S.isDeleted = false AND S.institution.isDeleted = false AND S.name ILIKE %:name%")
    Page<Student> findByName(@Param("name") String name, Pageable pageable);

    @Query("SELECT S FROM Student AS S WHERE S.user IS NOT NULL  AND S.isDeleted = false AND (:institutionId IS NULL OR S.institution.id = :institutionId)")
    Page<Student> findByInstitutionId(@Param("institutionId") Long institutionId, Pageable pageable);

    @Query("SELECT S FROM Student AS S WHERE S.user IS NOT NULL AND S.institution.isDeleted = false  AND S.isDeleted = false ")
    Page<Student> findByDeletedIsFalse(Pageable pageable);

    @Query("SELECT S FROM Student AS S WHERE S.user IS NOT NULL AND S.isDeleted = false AND S.phoneNumber LIKE %:phoneNumber%")
    Page<Student> findByPhone(@Param("phoneNumber") String phoneNumber, Pageable pageable);

    @Query("SELECT S FROM Student AS S WHERE S.user IS NOT NULL AND S.isDeleted = false AND S.institution.isDeleted = false AND S.user.email ILIKE %:email%")
    Page<Student> findByEmail(@Param("email") String email, Pageable pageable);

    @Query("SELECT S FROM Student AS S WHERE S.user IS NOT NULL AND S.isDeleted = false AND S.institution.isDeleted = false AND S.name ILIKE %:name% AND S.birthDate =:birthDate")
    Page<Student> findByNameAndBirth(@Param("name") String name, @Param("birthDate")LocalDate birthDate, Pageable pageable);

    @Query("SELECT S FROM Student AS S WHERE S.user IS NOT NULL AND S.isDeleted = false AND S.institution.isDeleted = false AND S.birthDate > :birthDate")
    Page<Student> findByBirthDateAfter(@Param("birthDate")  LocalDate birthDate, Pageable pageable);
}
