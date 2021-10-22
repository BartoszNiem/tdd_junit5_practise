package pl.qaaacademy.todo.list;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.qaaacademy.todo.exceptions.InvalidListTitleException;
import pl.qaaacademy.todo.item.TodoItem;

import java.util.ArrayList;
import java.util.List;

public class TodoItemList {
    private String title;
    private int size;
    private List<TodoItem> itemList;

    protected static final Logger logger = LoggerFactory.getLogger(TodoItemList.class);


    private TodoItemList(String title) {
        this.title = title;
        this.size = 0;
        this.itemList = new ArrayList<>();
    }
    public static TodoItemList of(String title){
        if(validateTitle(title))
            return new TodoItemList(title);
        else
            throw new InvalidListTitleException("The TodoItem List cannot be created with a blank or null title");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<TodoItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<TodoItem> itemList) {
        this.itemList = itemList;
    }
    public boolean addItem(TodoItem item){
       /* if(doesListContainItem(item)){
            logger.error("There already is a TodoItem with the same title in the list!");
            return false;
        }
        else{ */
            logger.info(String.format("New item with a title: %s has been added to the list",  item.getTitle()));
            itemList.add(item);
            this.size += 1;
            return true;
     //   }
    }
    public boolean addAllItems(List<TodoItem> listOfTodoItems){
        boolean addedAllItems = true;
        for (TodoItem item : listOfTodoItems) {
            if(!doesListContainItem(item)){
                this.itemList.add(item);
                this.size += 1;
            }
            else{
                addedAllItems = false;
            }
        }
        return addedAllItems;
    }
    public static TodoItemList combineTodoItemLists(TodoItemList...lists){
        TodoItemList listToReturn = TodoItemList.of(lists[0].getTitle());
        for (TodoItemList list : lists) {
            listToReturn.addAllItems(list.getItemList());
        }
        return listToReturn;
    }
    public boolean doesListContainItem(TodoItem item){
        if(this.itemList.contains(item)){
            return true;
        }
        else
            return false;
    }
    public static boolean validateTitle(String title){
        return !(title.isBlank() || title == null);
    }

    @Override
    public String toString() {
        return "TodoItemList{" +
                "title='" + title + '\'' +
                ", size=" + size +
                ", itemList=" + itemList.toString() +
                '}';
    }

    public boolean deleteTodoItemByTitle(String title) {
        boolean wasTodoItemDeleted = false;
        TodoItem itemToBeRemoved = null;
        for (TodoItem item:itemList) {
            if(item.getTitle().equals(title))
                itemToBeRemoved = item;
        }
        if( itemToBeRemoved != null ) {
            itemList.remove(itemToBeRemoved);
            this.size -= 1;
            wasTodoItemDeleted = true;
        }
        return wasTodoItemDeleted;
    }

    public boolean toggleAllItemsFromListOfTitle(List<String> titlesOfItemsThatWillBeToggled) {
        boolean haveAllItemsBeenToggled = true;
        for (String title : titlesOfItemsThatWillBeToggled) {
            for (TodoItem item: itemList) {
                if(item.getTitle().equals(title)){
                    try{
                        item.toggleStatus();
                    }
                    catch (RuntimeException e){
                        haveAllItemsBeenToggled = false;
                    }
                }
            }
        }
        return haveAllItemsBeenToggled;
    }
}
