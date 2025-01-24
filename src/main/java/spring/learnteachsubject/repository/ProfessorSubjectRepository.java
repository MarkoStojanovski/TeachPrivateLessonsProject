package spring.learnteachsubject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.learnteachsubject.model.ProfessorSubject;

@Repository
public interface ProfessorSubjectRepository extends JpaRepository<ProfessorSubject, Long> {
}
