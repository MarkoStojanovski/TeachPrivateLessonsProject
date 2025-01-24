package spring.learnteachsubject.model.DTO;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import spring.learnteachsubject.model.enumeration.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {

    @Email
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String repeatPassword;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

}
