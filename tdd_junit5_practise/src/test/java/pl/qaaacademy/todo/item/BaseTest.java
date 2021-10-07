package pl.qaaacademy.todo.item;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseTest {

   protected Logger logger2 = LoggerFactory.getLogger(BasicTodoItemPropertiesTest.class);
  protected  String title;
  protected  String description;
   protected TodoItem item;


    @BeforeEach
    public void setupTest(){
        title = "Complete Java Udemy Course";
        description = "Ivan said to do it quickly";
        item = TodoItem.of(title, description);
    }

    @AfterEach
    public void cleanUpAfterTest(){
        item = null;
    }


}
