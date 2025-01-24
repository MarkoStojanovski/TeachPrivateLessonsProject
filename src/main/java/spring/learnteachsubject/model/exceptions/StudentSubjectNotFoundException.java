package spring.learnteachsubject.model.exceptions;

public class StudentSubjectNotFoundException extends RuntimeException{
    public StudentSubjectNotFoundException() {
        super("Student Subject Not Found.");
    }
}
