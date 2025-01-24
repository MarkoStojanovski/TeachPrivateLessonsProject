package spring.learnteachsubject.model.exceptions;

public class ProfessorSubjectNotSaveException extends RuntimeException{
    public ProfessorSubjectNotSaveException() {
        super("Something is not valid.");
    }
}
