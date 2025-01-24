package spring.learnteachsubject.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.learnteachsubject.model.enumeration.SessionStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SessionStatus sessionStatus;

    @Future(message = "Session time must be in the future.")
    private LocalDateTime sessionTime;



    @Future(message = "Session time must be in the future.")
    private LocalDateTime sessionEndTime;

    @Min(0)
    private Double price;

    @ManyToOne()
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne()
    @JoinColumn(name = "professor_id", nullable = true)
    private Professor professor;

    @ManyToOne()
    @JoinColumn(name = "student_teacher_id", nullable = true)
    private Student studentAsTutor;

    @ManyToOne()
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne()
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    public Session(SessionStatus sessionStatus, LocalDateTime sessionTime, LocalDateTime sessionEndTime, Double price, Subject subject, Professor professor, Student studentAsTutor, Student student, Room room) {
        this.sessionStatus = sessionStatus;
        this.sessionTime = sessionTime;
        this.sessionEndTime = sessionEndTime;
        this.price = price;
        this.subject = subject;
        this.professor = professor;
        this.studentAsTutor = studentAsTutor;
        this.student = student;
        this.room = room;
    }
}
