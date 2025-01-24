package spring.learnteachsubject.model.exceptions;

public class RoomNotSaveException extends RuntimeException{
    public RoomNotSaveException() {
        super("Room not saving, no valid arguments.");
    }
}
