package spring.learnteachsubject.repository;

import jakarta.annotation.security.PermitAll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.learnteachsubject.model.Institution;
import spring.learnteachsubject.model.Professor;

import java.util.List;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {


    @Query("SELECT P FROM Professor AS P WHERE P.user IS NOT NULL AND P.isDeleted = false AND P.institution.isDeleted = false ")
    Page<Professor> findAllProfessors(Pageable pageable);

    @Query("SELECT P FROM Professor AS P WHERE P.user IS NOT NULL AND P.isDeleted = false AND P.institution.isDeleted = false ")
    List<Professor> findAllProfessorsNoPag();

    @Query("SELECT P FROM Professor AS P WHERE P.user IS NOT NULL AND P.isDeleted = false AND P.institution.isDeleted = false AND P.name ILIKE %:name% ")
    Page<Professor> findByName(@Param("name") String name, Pageable pageable);

    @Query("SELECT P FROM Professor AS P WHERE P.user IS NOT NULL AND P.isDeleted = false AND P.institution.isDeleted = false AND P.phoneNumber LIKE %:phoneNumber%")
    Page<Professor> findByPhoneNumber(@Param("phoneNumber") String phoneNumber, Pageable pageable);

    @Query("SELECT P FROM Professor AS P WHERE P.user IS NOT NULL AND P.isDeleted = false AND P.institution.isDeleted = false AND P.years > :years")
    Page<Professor> findByYearsGreaterThan(@Param("years") Integer years, Pageable pageable);

    @Query("SELECT P FROM Professor AS P WHERE P.user IS NOT NULL AND P.isDeleted = false AND P.institution.isDeleted = false AND P.user.email ILIKE %:email%")
    Page<Professor> findByProfEmail(@Param("email") String email, Pageable pageable);

    @Query("SELECT P FROM Professor AS P WHERE P.user IS NOT NULL AND P.isDeleted = false AND (P.institution IS NULL OR P.institution.id = :institutionId ) AND P.name ILIKE %:name%")
    Page<Professor> findByNameAndInstitution(@Param("name") String name, @Param("institutionId") Long institutionId, Pageable pageable);

    @Query("SELECT P FROM Professor AS P WHERE P.user IS NOT NULL AND P.isDeleted = false AND P.institution.id = :institutionId ")
    Page<Professor> findByInstitution(@Param("institutionId") Long institutionId, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Professor P SET P.user = NULL WHERE P.user.email = :email")
    void updateUserToNullByEmail(@Param("email") String email);
}
