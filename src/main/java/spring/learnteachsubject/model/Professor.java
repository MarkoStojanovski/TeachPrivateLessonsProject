package spring.learnteachsubject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "professors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String surname;

    @Min(25)
    @Max(70)
    private int years;

    private String description;

    @Pattern(regexp = "^[+]?\\d{9}([-\\s]?\\d{1,3})*$", message = "Invalid phone number format")
    private String phoneNumber;

    @OneToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email", nullable = true)
    private User user;

    @Transient
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "institution_id")
    private Institution institution;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    public Professor(Long id) {
        this.id = id;
    }

    public Professor(String name, String surname, int years, String description, String phoneNumber, User user, Institution institution) {
        this.name = name;
        this.surname = surname;
        this.years = years;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.user = user;
        this.institution = institution;
    }
}
