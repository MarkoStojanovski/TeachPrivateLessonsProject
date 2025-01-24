package spring.learnteachsubject.model.exceptions;

public class UsernameOrPasswordEmptyException extends RuntimeException{
    public UsernameOrPasswordEmptyException() {
        super("Username or Password is empty.");
    }
}
