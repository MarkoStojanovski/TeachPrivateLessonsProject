package spring.learnteachsubject.model.exceptions;

public class InvalidSessionException extends RuntimeException{
    public InvalidSessionException(String message) {
        super(message);
    }
}
