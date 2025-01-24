package spring.learnteachsubject.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.learnteachsubject.model.*;
import spring.learnteachsubject.model.exceptions.InstitutionNotFoundException;
import spring.learnteachsubject.repository.InstitutionRepository;
import spring.learnteachsubject.service.InstitutionService;

import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@Service
public class InstitutionServiceImpl implements InstitutionService {

    private final InstitutionRepository institutionRepository;

    public InstitutionServiceImpl(InstitutionRepository institutionRepository) {
        this.institutionRepository = institutionRepository;
    }


    @Override
    public List<Institution> findAll() {
        return this.institutionRepository.findNonDeletedInstitutions();
    }

    @Override
    public Page<Institution> findAllInstitutions(Pageable pageable) {
        return this.institutionRepository.findAllInstitutions(pageable);
    }


    @Override
    public Optional<Institution> findInstitutionById(Long id) {
        return Optional.of(this.institutionRepository.findById(id).orElseThrow(InstitutionNotFoundException::new));
    }

    @Override
    public void deleteInstitutionById(Long id) {
        Institution institution = this.institutionRepository.findById(id)
                .orElseThrow(InstitutionNotFoundException::new);
        institution.setDeleted(true);
        this.institutionRepository.save(institution);
    }

    @Override
    @Transactional
    public Institution createInstitution(Institution institution) {

        Institution createdInstitution = new Institution();

        createdInstitution.setName(institution.getName());
        createdInstitution.setLocation(institution.getLocation());
        createdInstitution.setPhoneNumber(institution.getPhoneNumber());
        createdInstitution.setEmail(institution.getEmail());
        createdInstitution.setWebsite(institution.getWebsite());
        createdInstitution.setIsActive(institution.getIsActive());
        createdInstitution.setWorkingHours(institution.getWorkingHours());

        return this.institutionRepository.save(createdInstitution);
    }

    @Override
    @Transactional
    public Institution updateInstitution(Long id, Institution institution) {
        Institution updatedInstitution = this.institutionRepository.findById(id).orElseThrow(InstitutionNotFoundException::new);

        updatedInstitution.setName(institution.getName());
        updatedInstitution.setLocation(institution.getLocation());

        updatedInstitution.setPhoneNumber(institution.getPhoneNumber());
        updatedInstitution.setEmail(institution.getEmail());

        updatedInstitution.setWebsite(institution.getWebsite());
        updatedInstitution.setIsActive(institution.getIsActive());
        updatedInstitution.setWorkingHours(institution.getWorkingHours());

        return this.institutionRepository.save(updatedInstitution);
    }

    @Override
    public Page<Institution> findByCriteria(String email, String phoneNumber, String locationCity, String name, Pageable pageable) {
        if (name != null || email != null || phoneNumber != null || locationCity != null) {
            return this.institutionRepository.findByCriteria(name, email, phoneNumber, locationCity, pageable);
        }
        return this.institutionRepository.findAllInstitutions(pageable);
    }

    @Override
    public void writeInstitutionsToCSV(PrintWriter writer) {
        writer.println("ID, Name, Location, Phone Number, Email, Website, Active, Working Hours");

        List<Institution> institutions = this.institutionRepository.findNonDeletedInstitutions();

        for (Institution institution : institutions) {
            writer.printf("%s,%s,%s,%s,%s,%s,%s,%s%n",
                    institution.getId() != null ? institution.getId() : "",
                    institution.getName() != null ? institution.getName() : "",
                    institution.getLocation() != null ? institution.getLocation() : "",
                    institution.getPhoneNumber() != null ? institution.getPhoneNumber() : "",
                    institution.getEmail() != null ? institution.getEmail() : "",
                    institution.getWebsite() != null ? institution.getWebsite() : "",
                    institution.getIsActive() != null ? institution.getIsActive().toString() : "",
                    institution.getWorkingHours() != null ? institution.getWorkingHours() : ""
            );
        }
    }
}
