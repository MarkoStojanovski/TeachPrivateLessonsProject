package spring.learnteachsubject.model.exceptions;


public class StudentSubjectNotSaveException extends RuntimeException{
    public StudentSubjectNotSaveException() {
        super("Something is not valid.");
    }
}
