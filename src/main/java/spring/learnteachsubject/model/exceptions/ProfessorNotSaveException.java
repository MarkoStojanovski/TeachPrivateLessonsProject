package spring.learnteachsubject.model.exceptions;

public class ProfessorNotSaveException extends RuntimeException{
    public ProfessorNotSaveException(String message) {
        super(message);
    }
}
