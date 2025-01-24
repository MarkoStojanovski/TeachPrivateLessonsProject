package spring.learnteachsubject.model.exceptions;

public class ProfessorNotFoundException extends RuntimeException{
    public ProfessorNotFoundException() {
        super("Professor not found.");
    }
}
