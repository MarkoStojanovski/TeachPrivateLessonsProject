package spring.learnteachsubject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "subjects")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String subjectName;

    private String subjectLevel;

    private Boolean uniSubject;

    private String description;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    public Subject(String subjectName, String subjectLevel, Boolean uniSubject, String description) {
        this.subjectName = subjectName;
        this.subjectLevel = subjectLevel;
        this.uniSubject = uniSubject;
        this.description = description;
    }
}
