package spring.learnteachsubject.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.learnteachsubject.model.Session;
import spring.learnteachsubject.model.enumeration.SessionStatus;
import spring.learnteachsubject.service.SessionService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/sessions")
public class SessionRestController {

    private final SessionService sessionService;

    public SessionRestController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Session>> getAllSession() {
        List<Session> sessions = this.sessionService.findAll();
        return new ResponseEntity<>(sessions, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getSessions(
            @RequestParam(value = "price", required = false) Double price,
            @RequestParam(value = "sessionStatus", required = false) SessionStatus sessionStatus,
            @RequestParam(value = "professorId", required = false) Long professorId,
            @RequestParam(value = "studentTutorId", required = false) Long studentTutorId,
            @RequestParam(value = "studentId", required = false) Long studentId,
            @RequestParam(value = "subjectName",required = false) String subjectName,
            @RequestParam(value = "institutionId", required = false) Long institutionId,
            @RequestParam(value = "sessionTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime sessionTime,
            @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        Pageable pageable = PageRequest.of(page, 6);
        Page<Session> sessionPage = this.sessionService.findByCriteria(
                price,
                sessionStatus,
                subjectName,
                institutionId,
                professorId,
                studentTutorId,
                studentId,
                pageable
        );
//        Page<Session> sessionPage = this.sessionService.findByCriteria(price, sessionStatus,
//                professorId, studentTutorId,studentId, subjectName, institutionId, sessionTime, pageable);

        Map<String, Object> response = new HashMap<>();

        response.put("sessions", sessionPage.getContent());
        response.put("totalPages", sessionPage.getTotalPages());
        response.put("currentPage", sessionPage.getNumber());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Session> getSessionById(@PathVariable Long id) {
        Optional<Session> session = sessionService.findSessionById(id);
        return session.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Session> createSession(
            @RequestBody Session session
    ) {
        Session newSession = this.sessionService.createSession(session);

        return new ResponseEntity<>(newSession, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Session> updateSession(
            @PathVariable Long id,
            @RequestBody Session session
    ) {
        Session updatedSession = this.sessionService.updateSession(id, session);
        return new ResponseEntity<>(updatedSession, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Session> deleteSession(
            @PathVariable Long id
    ) {
        this.sessionService.deleteSessionById(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/statuses")
    public ResponseEntity<List<SessionStatus>> getAllStatuses() {
        List<SessionStatus> sessionStatuses = Arrays.stream(SessionStatus.values()).toList();
        return new ResponseEntity<>(sessionStatuses, HttpStatus.OK);
    }


}
