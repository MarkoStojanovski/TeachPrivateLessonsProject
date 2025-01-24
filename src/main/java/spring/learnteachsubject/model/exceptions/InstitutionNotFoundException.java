package spring.learnteachsubject.model.exceptions;

public class InstitutionNotFoundException extends RuntimeException{
    public InstitutionNotFoundException() {
        super("Institution Not Found");
    }
}
