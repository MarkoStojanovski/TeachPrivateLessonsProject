package spring.learnteachsubject.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.learnteachsubject.model.*;
import spring.learnteachsubject.model.enumeration.Role;
import spring.learnteachsubject.model.exceptions.*;
import spring.learnteachsubject.repository.InstitutionRepository;
import spring.learnteachsubject.repository.ProfessorRepository;
import spring.learnteachsubject.repository.SubjectRepository;
import spring.learnteachsubject.repository.UserRepository;
import spring.learnteachsubject.service.ProfessorService;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfessorServiceImpl implements ProfessorService {

    private final ProfessorRepository professorRepository;
    private final UserRepository userRepository;
    private final InstitutionRepository institutionRepository;

    private final SubjectRepository subjectRepository;

    public ProfessorServiceImpl(ProfessorRepository professorRepository, UserRepository userRepository, InstitutionRepository institutionRepository, SubjectRepository subjectRepository) {
        this.professorRepository = professorRepository;
        this.userRepository = userRepository;
        this.institutionRepository = institutionRepository;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public Page<Professor> findAll(Pageable pageable) {
        return this.professorRepository.findAllProfessors(pageable);
    }

    @Override
    public Optional<Professor> findProfessorById(Long id) {
        return Optional.of(this.professorRepository.findById(id).orElseThrow(ProfessorNotFoundException::new));
    }

    @Override
    public List<Professor> findAllProfessors() {
        return this.professorRepository.findAllProfessorsNoPag();
    }

    @Override
    public void deleteProfessorById(Long id) {
        Professor professor = this.professorRepository.findById(id)
                .orElseThrow(ProfessorNotFoundException::new);
        professor.setDeleted(true);
        this.professorRepository.save(professor);
    }

    @Override
    @Transactional
    public Professor createProfessor(Professor professor) {
        User user = this.userRepository.findByEmail(professor.getUser().getEmail());
        if (user == null) {
            throw new UserNotFoundException();
        }
        Institution institution = this.institutionRepository.findById(professor.getInstitution().getId())
                .orElseThrow(InstitutionNotFoundException::new);

        Professor createdProfessor = new Professor();

        createdProfessor.setName(professor.getName());
        createdProfessor.setSurname(professor.getSurname());
        createdProfessor.setYears(professor.getYears());
        createdProfessor.setDescription(professor.getDescription());
        createdProfessor.setPhoneNumber(professor.getPhoneNumber());
        createdProfessor.setUser(user);
        createdProfessor.setInstitution(institution);



        return this.professorRepository.save(createdProfessor);
    }

    @Override
    @Transactional
    public Professor updateProfessor(Long id, Professor professor) {
        User user = this.userRepository.findByEmail(professor.getUser().getEmail());
        if (user == null || !user.getRole().equals(Role.PROFESSOR)) {
            throw new ProfessorNotSaveException("User not found or not a professor");
        }
        Institution institution = this.institutionRepository.findById(professor.getInstitution().getId())
                .orElseThrow(InstitutionNotFoundException::new);

        Professor updatedProfessor = this.professorRepository.findById(id)
                .orElseThrow(ProfessorNotFoundException::new);

        updatedProfessor.setName(professor.getName());
        updatedProfessor.setSurname(professor.getSurname());
        updatedProfessor.setYears(professor.getYears());
        updatedProfessor.setDescription(professor.getDescription());
        updatedProfessor.setPhoneNumber(professor.getPhoneNumber());
        updatedProfessor.setUser(user);
        updatedProfessor.setInstitution(institution);


        return this.professorRepository.save(updatedProfessor);
    }

    @Override
    public Page<Professor> findByCriteria(Long institutionId, String name, String phoneNumber, Integer years, String email, Pageable pageable) {
        if (name == null && institutionId == null && phoneNumber == null && years == null && email == null) {
        return this.professorRepository.findAllProfessors(pageable);
        }
        if (name != null && institutionId != null && phoneNumber == null && years == null && email == null) {
            return this.professorRepository.findByNameAndInstitution(name, institutionId, pageable);
        }
        if (email != null && years == null && institutionId == null && phoneNumber == null && name == null) {
            return this.professorRepository.findByProfEmail(email, pageable);
        }
        if (phoneNumber != null && email == null && years == null && institutionId == null && name == null) {
            return this.professorRepository.findByPhoneNumber(phoneNumber, pageable);
        }
        if (years != null && email == null && institutionId == null && name == null && phoneNumber == null) {
            return this.professorRepository.findByYearsGreaterThan(years, pageable);
        }
        if (name != null && years == null && email == null && institutionId == null && phoneNumber == null) {
            return this.professorRepository.findByName(name, pageable);
        }
        if( institutionId != null && name == null && years == null && email == null && phoneNumber == null) {
            return this.professorRepository.findByInstitution(institutionId, pageable);
        }
        return Page.empty();
    }

    @Override
    public void writeProfessorsToCSV(PrintWriter writer) {
        writer.println("ID, Name, Surname, Years, Phone Number, Email, Institution");

        List<Professor> professors = this.professorRepository.findAllProfessorsNoPag();

        for (Professor professor : professors) {
            writer.printf("%s,%s,%s,%s,%s,%s,%s%n",
                    professor.getId() != null ? professor.getId() : "",
                    professor.getName() != null ? professor.getName() : "",
                    professor.getSurname() != null ? professor.getSurname() : "",
                    professor.getYears(),
                    professor.getPhoneNumber() != null ? professor.getPhoneNumber() : "",
                    professor.getUser().getEmail() != null ? professor.getUser().getEmail() : "",
                    professor.getInstitution() != null ? professor.getInstitution().getName() + ' ' + professor.getInstitution().getLocation() : ""
                    );
        }
    }
}
