package pl.qaaacademy.todo.list;

import org.junit.jupiter.api.Test;
import pl.qaaacademy.todo.exceptions.InvalidListTitleException;
import pl.qaaacademy.todo.item.ItemStatus;
import pl.qaaacademy.todo.item.TodoItem;

import java.util.Comparator;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static pl.qaaacademy.todo.matchers.ValidTodoItemListMatcher.isValidTodoItemListWith;
public class BasicTodoItemListPropertiesTest extends BaseListTest{

    @Test
    public void shouldBeEmptyAfterCreation(){

        assertEquals(this.itemList.getSize(), 0);
        assertTrue(this.itemList.getItemList().isEmpty());

    }
    @Test
    public void shouldHaveValidTitleAndSize(){
        assertThat(itemList, isValidTodoItemListWith(this.title, 0));
    }
    @Test
    public void sizeShouldBeZeroAndIncreaseAfterAddingItems(){
        assertEquals(this.itemList.getSize(), 0);
        TodoItem newTodoItem = TodoItem.of("New TodoItem", "Description for the new todoItem");
        this.itemList.addItem(newTodoItem);
        assertEquals(this.itemList.getSize(), 1);
    }
    @Test
    public void shouldCombineProperlyListsOfTodoItemsIntoOne(){
        TodoItemList list1 = TodoItemList.of("List 1");
        list1.setItemList(List.of( TodoItem.of("Item title 1", "Description of item 1"),
                                    TodoItem.of("Item title 2", "Description of item 2")));

        TodoItemList list2 = TodoItemList.of("List 2");
        list2.setItemList(List.of( TodoItem.of("Item title 3", "Description of item 3"),
                TodoItem.of("Item title 4", "Description of item 4")));

        TodoItemList list3 = TodoItemList.of("List 3");
        list3.setItemList(List.of( TodoItem.of("Item title 5", "Description of item 5"),
                TodoItem.of("Item title 2", "Description of item 2")));

        TodoItemList combinedList = TodoItemList.combineTodoItemLists(list1, list2, list3);
        System.out.println(combinedList.toString());
        assertEquals(combinedList.getTitle(), list1.getTitle());
        assertEquals(combinedList.getSize(), 5);

    }
    @Test
    public void shouldThrowAnExceptionIfGivenAnEmptyTitle(){
        String incorrectTitle = "  ";
        assertThrows(InvalidListTitleException.class,()->TodoItemList.of(incorrectTitle));
    }

    @Test
    public void shouldAddATodoItemCorrectly(){
        TodoItem newTodoItem = TodoItem.of("New title", "New description");
        TodoItem notAddedTodoItem = TodoItem.of("Not added TodoItem title", "New description");
        assertTrue(itemList.addItem(newTodoItem));
        assertTrue(itemList.getItemList().contains(newTodoItem));
    }

    @Test
    public void shouldDeleteATodoItemCorrectly(){
        TodoItem itemToBeDeleted = TodoItem.of("Title of deleted todoItem", "Description of the item");
        itemList.addItem(itemToBeDeleted);
        assertTrue(itemList.deleteTodoItemByTitle("Title of deleted todoItem"));
        assertEquals(itemList.getSize(), 0);
    }
    @Test
    public void itemsShouldBeFilteredByStatus(){
        ItemStatus statusToFilterList = ItemStatus.IN_PROGRESS;
        TodoItem item1 = TodoItem.of("Title 1", "Description 1");
        TodoItem item2 = TodoItem.of("Title 2", "Description 2");
        TodoItem item3 = TodoItem.of("Title 3", "Description 3");
        itemList.addItem(item2);
        itemList.addItem(item3);
        item2.toggleStatus();
        item3.toggleStatus();
        List<TodoItem> filteredList = itemList.getItemList().stream()
                                    .filter(item -> item.getStatus() == statusToFilterList).toList();
        assertEquals(filteredList.size(), 2);
    }
    @Test
    public void listIsSortedByTodoItemTitleCorrectly(){
        TodoItem item1 = TodoItem.of("C", "Description 1");
        TodoItem item2 = TodoItem.of("B", "Description 2");
        TodoItem item3 = TodoItem.of("D", "Description 3");
        TodoItem item4 = TodoItem.of("E", "Description 3");
        TodoItem item5 = TodoItem.of("A", "Description 3");
        itemList.addItem(item1);
        itemList.addItem(item2);
        itemList.addItem(item3);
        itemList.addItem(item4);
        itemList.addItem(item5);
        List<TodoItem> list = itemList.getItemList().stream()
                        .sorted(Comparator.comparing(TodoItem::getTitle)).toList();
        //System.out.println(list.toString());
        itemList.setItemList(list);
        assertEquals(itemList.getItemList().get(0).getTitle(), "A");
    }
    @Test
    public void shouldNotHaveDuplicateTitlesInTheList(){
        TodoItem item1 = TodoItem.of("C", "Description 1");
        TodoItem item2 = TodoItem.of("B", "Description 2");
        TodoItem item3 = TodoItem.of("D", "Description 3");
        TodoItem item4 = TodoItem.of("I", "Description 3");
        TodoItem item5 = TodoItem.of("A", "Description 3");
        itemList.addItem(item1);
        itemList.addItem(item2);
        itemList.addItem(item3);
        itemList.addItem(item4);
        itemList.addItem(item5);

        List<String> listOfDistinctTitles = itemList.getItemList().stream().map(TodoItem::getTitle).distinct().toList();
        assertEquals(itemList.getSize(), listOfDistinctTitles.size()  );
    }
    @Test
    public void shouldToggleStatusFromTodoItemTitleListGiven(){
        TodoItem item1 = TodoItem.of("C", "Description 1");
        TodoItem item2 = TodoItem.of("B", "Description 2");
        TodoItem item3 = TodoItem.of("D", "Description 3");
        itemList.addAllItems(List.of(item1, item2, item3));

        List<String> titlesOfItemsThatWillBeToggled = List.of(item1.getTitle(), item2.getTitle(), item3.getTitle());
        assertTrue(itemList.toggleAllItemsFromListOfTitle(titlesOfItemsThatWillBeToggled));
        assertEquals(itemList.getItemList().get(0).getStatus(), ItemStatus.IN_PROGRESS);
    }
}
