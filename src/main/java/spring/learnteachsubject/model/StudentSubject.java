package spring.learnteachsubject.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "students_subjects")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;
}
