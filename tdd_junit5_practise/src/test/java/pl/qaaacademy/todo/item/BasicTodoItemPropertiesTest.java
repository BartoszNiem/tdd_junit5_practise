package pl.qaaacademy.todo.item;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import pl.qaaacademy.todo.exceptions.IllegalStatusChangeException;
import pl.qaaacademy.todo.exceptions.TodoItemValidationException;

import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static pl.qaaacademy.todo.matchers.ValidTodoItemMatcher.isValidTodoItemWith;

@Tag("item")
public class BasicTodoItemPropertiesTest extends BaseTest {


    @Tag("happy")
    @Test
    public void shouldCreateTodoItemWithTitleAndDescription() {
        assertThat(item, isValidTodoItemWith(title, description));
    }

    @Tag("exception")
    @Test
    public void shouldThrowAnExceptionWhileCreatingItemWithEmptyTitle() {
        String invalidTitle = "";
        String description = "Some weird description for non-existing todo item";
        assertThrows(TodoItemValidationException.class,
                () -> TodoItem.of(invalidTitle, description));
    }

    @Test
    public void shouldThrowAnExceptionWileSettingAnEmptyTitle() {
        String invalidTitle = "";
        assertThrows(TodoItemValidationException.class, () -> item.setTitle(invalidTitle));
    }

    @Test
    public void shouldToggleStatusFromPendingToInProgress() {
        item.toggleStatus();
        assertEquals(item.getStatus(), ItemStatus.IN_PROGRESS);
    }

    @Test
    public void shouldToggleStatusFromInProgressToPending() {
        TodoItem item = TodoItem.of(title, description);
        item.toggleStatus();
        item.toggleStatus();
        assertEquals(item.getStatus(), ItemStatus.PENDING);
    }

    @Test
    public void shouldCompleteATaskRepresented() {
        item.toggleStatus();
        item.complete();
        assertEquals(item.getStatus(), ItemStatus.COMPLETED);
    }

    @Tag("exception")
    @Test
    public void shouldThrowExceptionWhenTryingToCompleteAnItemThatIsPending() {
        assertThrows(IllegalStatusChangeException.class, () -> item.complete());
    }

    @Tag("exception")
    @Test
    public void shouldThrowExceptionWhenTryingToCompleteAnItemThatIsCompleted() {
        item.toggleStatus();
        item.complete();
        assertThrows(IllegalStatusChangeException.class, () -> item.complete());
    }

    @Test
    public void shouldNotToggleStatusFromCompletedTOInProgress() {
        item.toggleStatus();
        item.complete();
        assertThrows(IllegalStatusChangeException.class, () -> item.toggleStatus());
    }

    @Test
    public void shouldNotCreateATodoItemWithDescriptionLongerThan250Characters() {
        String title = "Complete Java Udemy Course";
        String description = "Ivan said to do it quickly, Ivan said to do it quickly, Ivan said to do it quickly" +
                "Ivan said to do it quickly, Ivan said to do it quickly, Ivan said to do it quickly, Ivan said to do it quickly" +
                "Ivan said to do it quickly, Ivan said to do it quickly, Ivan said to do it quickly, Ivan said to do it quickly";
        //System.out.println(description.length());
        assertThrows(TodoItemValidationException.class, () -> TodoItem.of(title, description));

    }

    @Test
    public void shouldNotCreateATodoItemWithNullDescription() {
        String title = "Complete Java Udemy Course";
        String description = null;
        assertThrows(TodoItemValidationException.class, () -> TodoItem.of(title, description));
    }

    @Test
    public void shouldNotSetADescriptionLongerThan250Characters() {
        String title = "Complete Java Udemy Course";
        String correctDescription = "Description shorter than 250 characters.";
        TodoItem item = TodoItem.of(title, correctDescription);

        String incorrectDescription = "Ivan said to do it quickly, Ivan said to do it quickly, Ivan said to do it quickly" +
                "Ivan said to do it quickly, Ivan said to do it quickly, Ivan said to do it quickly, Ivan said to do it quickly" +
                "Ivan said to do it quickly, Ivan said to do it quickly, Ivan said to do it quickly, Ivan said to do it quickly";

        assertThrows(TodoItemValidationException.class, () -> item.setDescription(incorrectDescription));

    }

    @Test
    public void shouldNotSetANullNewDescription() {
        String title = "Complete Java Udemy Course";
        String correctDescription = "Description shorter than 250 characters.";
        TodoItem item = TodoItem.of(title, correctDescription);

        String incorrectDescription = null;

        assertThrows(TodoItemValidationException.class, () -> item.setDescription(incorrectDescription));

    }

    @Tag("exception")
    @Test
    public void checkingIfExceptionIsThrownWhileSettingTitleThatDoesNotMeetSpecifiedCriteria() {
        Predicate<String> criterion1 = (title) -> !title.contains("@");
        Predicate<String> criterion2 = (title) -> !title.contains("&");
        Predicate<String> criterion3 = (title) -> !title.contains("#");
        Predicate<String> criterion4 = (title) -> !title.contains("$");
        List<Predicate<String>> listOfCriteria = List.of(criterion1, criterion2, criterion3, criterion4);
        String titleThatDoesNotMeetCriteria = "This #title is incorrect";
        assertThrows(TodoItemValidationException.class, () -> item.setTitle(titleThatDoesNotMeetCriteria, listOfCriteria));
    }

    @ParameterizedTest
    @CsvFileSource(files = {"src/test/resources/todos.csv"}, numLinesToSkip = 1)
    public void shouldCreateValidTodoItemsCsvFileSource(String title, String description) {
        TodoItem newTodo = TodoItem.of(title, description);
        assertThat(newTodo, isValidTodoItemWith(title, description));
    }


    @ParameterizedTest
    @ArgumentsSource(TodoItemArgumentsProvider.class)
    public void shouldCreateValidTodoItemsWithArgumentsSource(String title, String description){
        TodoItem newTodo = TodoItem.of(title, description);
        assertThat(newTodo, isValidTodoItemWith(title, description));
    }


}






