package pl.qaaacademy.todo.item;

import pl.qaaacademy.todo.exceptions.IllegalStatusChangeException;

public interface StatusChangeable {
    void toggleStatus();
    void complete() ;
}
