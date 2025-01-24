package spring.learnteachsubject.model.exceptions;

public class InstitutionNotSaveException extends RuntimeException{
    public InstitutionNotSaveException() {
        super("Institution is not saving, no valid arguments.");
    }
}
