package spring.learnteachsubject.model.exceptions;

public class StudentNotFoundException extends RuntimeException{

    public StudentNotFoundException() {
        super("Student not found.");
    }
}
