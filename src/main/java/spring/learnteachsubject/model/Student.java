package spring.learnteachsubject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "students")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String surname;

    private LocalDate birthDate;

    @Pattern(regexp = "^[+]?\\d{9}([-\\s]?\\d{1,3})*$", message = "Invalid phone number format")
    private String phoneNumber;

    @OneToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email", nullable = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "institution_id")
    private Institution institution;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    public Student(String name, String surname, LocalDate birthDate, String phoneNumber, User user, Institution institution) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.user = user;
        this.institution = institution;
    }
}
