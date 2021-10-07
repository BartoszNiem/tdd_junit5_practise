package pl.qaaacademy.todo.exceptions;

public class TodoItemValidationException extends RuntimeException {
    public TodoItemValidationException(String s) {
        super(s);
    }
}
