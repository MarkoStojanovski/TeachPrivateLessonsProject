package spring.learnteachsubject.model.exceptions;

public class RoomNotFoundException extends RuntimeException{
    public RoomNotFoundException() {
        super("Room not found.");
    }
}
