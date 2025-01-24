package spring.learnteachsubject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.learnteachsubject.model.Subject;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    @Query("SELECT s from Subject s WHERE s.isDeleted = false ")
    Page<Subject> findAllSubjects(Pageable pageable);

    @Query("SELECT s from Subject s WHERE s.isDeleted = false ")
    List<Subject> findNonDeletedSubjects();

    @Query("SELECT S FROM Subject AS S WHERE S.isDeleted = false AND " +
            "(:subjectName IS NULL OR S.subjectName ILIKE %:subjectName% ) AND " +
            "(:subjectLevel IS NULL  OR S.subjectLevel = :subjectLevel)")
    Page<Subject> findByCriteria(@Param("subjectName") String subjectName,
                                 @Param("subjectLevel") String subjectLevel,
                                 Pageable pageable);

}
