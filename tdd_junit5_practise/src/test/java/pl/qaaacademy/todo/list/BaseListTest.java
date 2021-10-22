package pl.qaaacademy.todo.list;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.qaaacademy.todo.item.BasicTodoItemPropertiesTest;

public class BaseListTest {
    protected Logger logger = LoggerFactory.getLogger(BasicTodoItemListPropertiesTest.class);
    protected String title;
    protected TodoItemList itemList;

    @BeforeEach
    public void setupTest(){
        this.title = "TodoItem list title";
        this.itemList = TodoItemList.of(title);
    }
    @AfterEach
    public void cleanup(){
        this.itemList = null;
    }
}
