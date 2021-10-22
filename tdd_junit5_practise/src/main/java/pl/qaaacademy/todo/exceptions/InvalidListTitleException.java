package pl.qaaacademy.todo.exceptions;

public class InvalidListTitleException extends RuntimeException {
    public InvalidListTitleException(String s){
        super(s);
    }
}
