package spring.learnteachsubject.model.exceptions;

public class StudentNotSaveException extends RuntimeException{
    public StudentNotSaveException(String message) {
        super(message);
    }
}
