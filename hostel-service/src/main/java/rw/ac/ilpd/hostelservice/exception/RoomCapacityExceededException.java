package rw.ac.ilpd.hostelservice.exception;
public class RoomCapacityExceededException extends RuntimeException {
    public RoomCapacityExceededException(String message) {
        super(message);
    }
}
