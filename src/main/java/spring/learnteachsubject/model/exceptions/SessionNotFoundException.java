package spring.learnteachsubject.model.exceptions;

public class SessionNotFoundException extends RuntimeException{
    public SessionNotFoundException() {
        super("Session not found.");
    }
}
