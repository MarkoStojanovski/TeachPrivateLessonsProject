package spring.learnteachsubject.model.exceptions;

public class SubjectNotFoundException extends RuntimeException{
    public SubjectNotFoundException() {
        super("Subject Not Found.");
    }
}
