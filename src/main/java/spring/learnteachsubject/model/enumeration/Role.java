package spring.learnteachsubject.model.enumeration;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    PROFESSOR, STUDENT_TUTOR, STUDENT, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
