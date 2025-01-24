package spring.learnteachsubject.model.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super("Mail doesn't exist, try again.");
    }
}
