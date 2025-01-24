package spring.learnteachsubject.model.exceptions;

public class SubjectNotSaveException extends RuntimeException{

    public SubjectNotSaveException() {
        super("Subject is not saving, no valid arguments.");
    }
}
