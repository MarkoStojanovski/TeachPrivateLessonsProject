package spring.learnteachsubject.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.util.List;

@Entity
@Table(name = "institutions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String location;

    @Pattern(regexp = "^[+]?[0-9\\s-]+$", message = "Invalid phone number format")
    private String phoneNumber;

    @Email
    private String email;

    @URL
    private String website;

    private Boolean isActive;

    private String workingHours;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;


    public Institution(Long id) {
        this.id = id;
    }

    public Institution(String name, String location, String phoneNumber, String email, String website, Boolean isActive, String workingHours) {
        this.name = name;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.website = website;
        this.isActive = isActive;
        this.workingHours = workingHours;
    }
}
