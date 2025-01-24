package spring.learnteachsubject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import spring.learnteachsubject.model.Session;
import spring.learnteachsubject.model.enumeration.SessionStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    @Query("SELECT S FROM Session S WHERE S.isDeleted = false " +
            "AND S.subject.isDeleted = false " +
            "AND S.room.isDeleted = false " +
            "AND S.room.institution.isDeleted = false " +
            "AND S.student.isDeleted = false " +
            "ORDER BY S.price ASC")
    Page<Session> findAllSessions(Pageable pageable);

    @Query("SELECT S FROM Session S WHERE S.isDeleted = false " +
            "AND S.subject.isDeleted = false " +
            "AND S.room.isDeleted = false " +
            "AND S.room.institution.isDeleted = false " +
            "AND S.student.isDeleted = false " +
            "ORDER BY S.price ASC")
    List<Session> findAllSessionsList();


    @Query("SELECT S FROM Session AS S " +
            "WHERE S.isDeleted = false " +
            "AND S.room.isDeleted = false " +
            "AND S.room.institution.isDeleted = false " +
            "AND S.subject.isDeleted = false " +
            "AND S.student.isDeleted = false " +
            "AND (:price IS NULL OR S.price > :price)" +
            "AND (:sessionStatus IS NULL OR S.sessionStatus = :sessionStatus)" +
            "AND (:subjectName IS NULL OR S.subject.subjectName ILIKE %:subjectName%) " +
            "AND (:institutionId IS NULL OR S.room.institution.id = :institutionId)" +
            "AND (:professorId IS NULL OR S.professor.id = :professorId) " +
            "AND (:studentTutorId IS NULL OR S.studentAsTutor.id = :studentTutorId) " +
            "AND (:studentId IS NULL OR S.student.id = :studentId)" +
            "ORDER BY S.price ASC ")
    Page<Session> findByCriteria(@Param("price") Double price,
                                 @Param("sessionStatus") SessionStatus sessionStatus,
                                 @Param("subjectName") String subjectName,
                                 @Param("institutionId") Long institutionId,
                                 @Param("professorId") Long professorId,
                                 @Param("studentTutorId") Long studentTutorId,
                                 @Param("studentId") Long studentId,
                                 Pageable pageable);

}
