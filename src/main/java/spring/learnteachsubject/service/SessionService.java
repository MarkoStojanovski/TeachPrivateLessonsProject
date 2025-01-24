package spring.learnteachsubject.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spring.learnteachsubject.model.*;
import spring.learnteachsubject.model.enumeration.SessionStatus;

import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

public interface SessionService {

    List<Session> findAll();

    Page<Session> findAllSessions(Pageable pageable);

    Optional<Session> findSessionById(Long id);

    void deleteSessionById(Long id);

    Session createSession(Session session);

    Session updateSession(Long id, Session session);

    Page<Session> findByCriteria(Double price,
                                 SessionStatus sessionStatus,
                                 String subjectName,
                                 Long institutionId,
                                 Long professorId,
                                 Long studentTutorId,
                                 Long studentId,
                                 Pageable pageable
    );

    void writeSessionsToCSV(PrintWriter writer);

}
