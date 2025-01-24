package spring.learnteachsubject.model.exceptions;


public class ProfessorSubjectNotFoundException extends RuntimeException{

    public ProfessorSubjectNotFoundException() {
        super("Professor Subject Not Found.");
    }
}
