package il.ac.hit.todolist.model;

import java.io.Serializable;
import java.util.List;

public interface IToDoListDAO {


    boolean addItem(DBObject item) throws ToDoListException; //Generic method for addition of item
    boolean deleteItem(Serializable itemID) throws ToDoListException; //Generic method for deletion of item
    //Generic method to update an attribute of either subclass of DBObject
    boolean updateColumnValue(String columnName, Serializable newValue, String primaryKey, Serializable keyValue) throws ToDoListException;
    List<DBObject> getList(int listID) throws ToDoListException; // Generic method to get list of DB items

}

