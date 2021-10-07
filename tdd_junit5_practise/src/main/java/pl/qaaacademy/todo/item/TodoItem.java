package pl.qaaacademy.todo.item;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.qaaacademy.todo.exceptions.IllegalStatusChangeException;
import pl.qaaacademy.todo.exceptions.TodoItemValidationException;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class TodoItem  implements StatusChangeable{

    private String title;
    private String description;
    private ItemStatus status;
    protected static final Logger logger = LoggerFactory.getLogger(TodoItem.class);

    private TodoItem(){}

    private TodoItem(String title, String description) {
        this.title = title;
        this.description = description;
        this.status = ItemStatus.PENDING;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        validateTitle(title);
        this.title = title;
    }


    public void setTitle(String title, List<Predicate<String>> criteria) {
        validateTitle(title);
        validateTitle(title, criteria);
        this.title = title;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        validateDescription(description);
        this.description = description;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    public static TodoItem of(String title, String description) {
        validateTitle(title);
        validateDescription(description);
        return new TodoItem(title, description);
    }
    private static void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            logger.error("Title cannot be null or blank");
            throw new TodoItemValidationException("Item title is null or blank!");
        }
    }
    private static void validateDescription(String description) {
        if (description == null || description.length() > 250) {
            logger.error("Description cannot be null or longer than 250 characters");
            throw new TodoItemValidationException("Item description is null or longer than 250 characters!");
        }
    }
    private static void validateTitle(String title, List<Predicate<String>> criteria){
        //stream pipeline for criteria
        //each criteria get a title to perform checks
        //colect to list of boolean
        //filter this list to find falses, collect to list and check if list size is more than 0
        List<Boolean> resultsOfTestingIfTileMeetsCriteria = criteria.stream().map(criterion -> criterion.test(title)).toList();
        if( resultsOfTestingIfTileMeetsCriteria.stream().filter(result -> result == false).count() > 0 ){
            logger.error("Title has to meet all of the given criteria!");
            throw new TodoItemValidationException("This item title does not meet all given criteria");
        }
    }

    @Override
    public void toggleStatus() {
        if(this.status == ItemStatus.PENDING){
            this.status = ItemStatus.IN_PROGRESS;
        }
        else if(this.status == ItemStatus.IN_PROGRESS){
            this.status = ItemStatus.PENDING;
        }
        else{
            logger.error("Status connot be changed for COMPLETED item");
            throw new IllegalStatusChangeException("You cannot change item's status if it is COMPLETED");
        }

    }

    @Override
    public void complete() {
        if(this.status == ItemStatus.IN_PROGRESS)
            this.status = ItemStatus.COMPLETED;
        else if (this.status == ItemStatus.PENDING){
            throw new IllegalStatusChangeException("You cannot complete an item that is not IN_PROGRESS");
        }
        else{
            throw new IllegalStatusChangeException("This item is already COMPLETED");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoItem todoItem = (TodoItem) o;
        return Objects.equals(title, todoItem.title) && Objects.equals(description, todoItem.description) && status == todoItem.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, status);
    }

    @Override
    public String toString() {
        return "TodoItem{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
