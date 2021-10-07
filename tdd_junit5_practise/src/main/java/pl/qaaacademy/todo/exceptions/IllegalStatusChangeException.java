package pl.qaaacademy.todo.exceptions;

public class IllegalStatusChangeException extends RuntimeException {
    public IllegalStatusChangeException(String s) {
        super(s);
    }
}
