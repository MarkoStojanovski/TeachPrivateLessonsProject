package spring.learnteachsubject.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.learnteachsubject.model.*;
import spring.learnteachsubject.model.enumeration.SessionStatus;
import spring.learnteachsubject.model.exceptions.*;
import spring.learnteachsubject.repository.*;
import spring.learnteachsubject.service.SessionService;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final ProfessorRepository professorRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final RoomRepository roomRepository;


    public SessionServiceImpl(SessionRepository sessionRepository, ProfessorRepository professorRepository, StudentRepository studentRepository, SubjectRepository subjectRepository, RoomRepository roomRepository) {
        this.sessionRepository = sessionRepository;
        this.professorRepository = professorRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public List<Session> findAll() {
        return this.sessionRepository.findAll();
    }

    @Override
    public Page<Session> findAllSessions(Pageable pageable) {
        return this.sessionRepository.findAllSessions(pageable);
    }

    @Override
    public Optional<Session> findSessionById(Long id) {
        return Optional.of(this.sessionRepository.findById(id).orElseThrow(SessionNotFoundException::new));
    }

    @Override
    @Transactional
    public void deleteSessionById(Long id) {
        Session session = this.sessionRepository.findById(id).orElseThrow(SessionNotFoundException::new);
        session.setDeleted(true);
        this.sessionRepository.save(session);
    }

    @Override
    @Transactional
    public Session createSession(Session session) {
        Subject subject = this.subjectRepository.findById(session.getSubject().getId())
                .orElseThrow(SubjectNotFoundException::new);

        Room room = this.roomRepository.findById(session.getRoom().getId())
                .orElseThrow(RoomNotFoundException::new);

        Professor professor = (session.getProfessor() != null && session.getProfessor().getId() != null) ? this.professorRepository.findById(session.getProfessor().getId())
                .orElseThrow(ProfessorNotFoundException::new) : null;

        Student studentAsTutor = (session.getStudentAsTutor() != null && session.getStudentAsTutor().getId() != null)
                ? this.studentRepository.findById(session.getStudentAsTutor().getId()).
                orElseThrow(StudentNotFoundException::new) : null;

        Student student = session.getStudent();

        if (session.getSessionTime().isAfter(session.getSessionEndTime())) {
            throw new InvalidSessionException("Session start time must be before the end time.");
        }

        if (professor == null && studentAsTutor == null) {
            throw new InvalidSessionException("A session must have either a professor or a student acting as a tutor.");
        }

        Session createdSession = new Session();

        createdSession.setSessionStatus(SessionStatus.PENDING);
        createdSession.setSessionTime(session.getSessionTime());
        createdSession.setSessionEndTime(session.getSessionEndTime());
        createdSession.setPrice(session.getPrice());
        createdSession.setSubject(subject);
        createdSession.setProfessor(professor);
        createdSession.setStudentAsTutor(studentAsTutor);
        createdSession.setStudent(student);
        createdSession.setRoom(room);

        return this.sessionRepository.save(createdSession);
    }

    @Override
    @Transactional
    public Session updateSession(Long id, Session session) {
        Subject subject = this.subjectRepository.findById(session.getSubject().getId())
                .orElseThrow(SubjectNotFoundException::new);

        Room room = this.roomRepository.findById(session.getRoom().getId())
                .orElseThrow(RoomNotFoundException::new);

        Professor professor = session.getProfessor().getId() != null ? this.professorRepository.findById(session.getProfessor().getId())
                .orElseThrow(ProfessorNotFoundException::new) : null;

        Student studentAsTutor = session.getStudentAsTutor().getId() != null ? this.studentRepository.findById(session.getStudentAsTutor().getId())
                .orElseThrow(StudentNotFoundException::new) : null;

        Student student = this.studentRepository.findById(session.getStudent().getId())
                .orElseThrow(StudentNotFoundException::new);

        Session updatedSession = this.sessionRepository.findById(id)
                .orElseThrow(SessionNotFoundException::new);

        if (session.getSessionTime().isAfter(session.getSessionEndTime())) {
            throw new InvalidSessionException("Session start time must be before the end time.");
        }

        if (professor == null && studentAsTutor == null) {
            throw new InvalidSessionException("A session must have either a professor or a student acting as a tutor.");
        }

        updatedSession.setSessionStatus(session.getSessionStatus());
        updatedSession.setSessionTime(session.getSessionTime());
        updatedSession.setSessionEndTime(session.getSessionEndTime());
        updatedSession.setPrice(session.getPrice());
        updatedSession.setSubject(subject);
        updatedSession.setProfessor(professor);
        updatedSession.setStudentAsTutor(studentAsTutor);
        updatedSession.setStudent(student);
        updatedSession.setRoom(room);

        return this.sessionRepository.save(updatedSession);
    }

    @Override
    public Page<Session> findByCriteria(Double price, SessionStatus sessionStatus,
                                        String subjectName, Long institutionId,
                                        Long professorId, Long studentTutorId, Long studentId,
                                        Pageable pageable) {
        if (price != null || sessionStatus != null || subjectName != null || institutionId != null || professorId != null || studentTutorId != null || studentId != null) {
            return this.sessionRepository.findByCriteria(price, sessionStatus, subjectName, institutionId, professorId, studentTutorId, studentId, pageable);
        }
        return this.sessionRepository.findAllSessions(pageable);
    }

    @Override
    public void writeSessionsToCSV(PrintWriter writer) {
        writer.println("ID, Session Status, Session Time, Session End Time, Price, Subject, Professor, Student Tutor, Student, Room, Institution");

        List<Session> sessions = this.sessionRepository.findAllSessionsList();

        for (Session session : sessions) {
            writer.printf("%s,%s,%s,%s,%.2f,%s,%s,%s,%s,%s,%s%n",
                    session.getId() != null ? session.getId() : "",
                    session.getSessionStatus() != null ? session.getSessionStatus().name() : "",
                    session.getSessionTime() !=null ? session.getSessionTime().toString() : "",
                    session.getSessionEndTime() !=null ? session.getSessionEndTime().toString() : "",
                    session.getPrice() != null ? session.getPrice() : 0.0,
                    session.getSubject() != null ? session.getSubject().getSubjectName() : "",
                    session.getProfessor() != null ? session.getProfessor().getName() + ' ' + session.getProfessor().getSurname() : "",
                    session.getStudentAsTutor() != null ? session.getStudentAsTutor().getName() + ' ' + session.getStudentAsTutor().getSurname() : "",
                    session.getStudent() != null ? session.getStudent().getName() + ' ' + session.getStudent().getSurname() : "",
                    session.getRoom() != null ? session.getRoom().getRoomNumber() : "",
                    session.getRoom() != null ? session.getRoom().getInstitution().getName() + ' ' + session.getRoom().getInstitution().getLocation() : ""
                    );
        }

    }

}
