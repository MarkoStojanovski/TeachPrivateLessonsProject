package spring.learnteachsubject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.learnteachsubject.model.Institution;

import java.util.List;


@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long> {

    @Query("SELECT I FROM Institution AS I WHERE I.isDeleted = false")
    Page<Institution> findAllInstitutions(Pageable pageable);

    @Query("SELECT I FROM Institution AS I WHERE I.isDeleted = false")
    List<Institution> findNonDeletedInstitutions();

    @Query("SELECT I FROM Institution I WHERE " +
            "I.isDeleted = false AND" +
            "(:name IS NULL OR I.name ILIKE %:name%) AND " +
            "(:email IS NULL OR I.email ILIKE %:email%) AND " +
            "(:phoneNumber IS NULL OR I.phoneNumber ILIKE %:phoneNumber%) AND " +
            "(:locationCity IS NULL OR I.location ILIKE %:locationCity%)")
    Page<Institution> findByCriteria(@Param("name") String name,
                                     @Param("email") String email,
                                     @Param("phoneNumber") String phoneNumber,
                                     @Param("locationCity") String locationCity,
                                     Pageable pageable);
}
