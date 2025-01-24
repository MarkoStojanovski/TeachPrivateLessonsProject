package spring.learnteachsubject.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spring.learnteachsubject.model.Institution;

import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

public interface InstitutionService {

    List<Institution> findAll();

    Page<Institution> findAllInstitutions(Pageable pageable);

    Optional<Institution> findInstitutionById(Long id);

    void deleteInstitutionById(Long id);
    Institution createInstitution(Institution institution);

    Institution updateInstitution(Long id, Institution institution);

    Page<Institution> findByCriteria(String email, String phoneNumber, String location, String name, Pageable pageable);

    void writeInstitutionsToCSV(PrintWriter writer);

}
