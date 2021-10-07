# tdd_junit5_practise
TDD and Junit5 practise implementation of TodoItem and List containing these TodoItems.
The implementation was made according to specified below requirements:

Requirements:
Todo item:
 - Item have title, description, status (optional: creation date in dd-mm-YYYY hh:mm)
 - Item cannot be created with an empty title or set to an empty string
 - Item statuses: COMPLETED, IN_PROGRESS, PENDING
 - Item status after creation - PENDING
 - Status flow: PENDING <-> IN_PROGRESS ->(<-> optional) COMPLETED (-> - one directional, <-> - bi-directional); 
 - The description should have maximum 250 characters in length (homework)
 
Todo list (my proposal for underlying data structure - List or Queue/Deque ):
 - The list should be empty after creation
 - The list will have a title and size
 - List size is 0 after creation and increased by the number of items added
 - Several lists can be combined to one with a new title (1st list title will be assigned by default)
 - The list can not be created with an empty title
 - Items can be added to the list
 - Items can be deleted from the list by their title
 - Items can be filtered by status
 - Items can be sorted by title
 - Items should not have duplicate titles
 - Several items can be toggled or completed (optional)

