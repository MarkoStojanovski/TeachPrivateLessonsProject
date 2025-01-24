package spring.learnteachsubject.model.exceptions;

public class InvalidUserCredentialsException extends RuntimeException{

    public InvalidUserCredentialsException() {
        super("Invalid User Credentials.");
    }
}
