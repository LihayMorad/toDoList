package il.ac.hit.todolist.model;

import java.io.Serializable;
import java.util.List;

public interface IToDoListDAO {

    /**
     * Generic method for addition of item
     * @param	item
     * @return  true if item addition was successful
     */
    boolean addItem(DBObject item) throws ToDoListException;

    /**
     * Generic method for deletion of item
     * @param	itemID
     * @return  true if item deletion was successful
     */
    boolean deleteItem(Serializable itemID) throws ToDoListException;

    /**
     * Generic method to update an attribute of either subclass of DBObject
     * @param	columnName
     * @param	newValue
     * @param	primaryKey
     * @param	keyValue
     * @return  true if update was successful
     */
    boolean updateColumnValue(String columnName, Serializable newValue, String primaryKey, Serializable keyValue) throws ToDoListException;

    /**
     * Generic method to get list of DB items
     * @param	listID  list's ID
     * @return  list of items
     */
    List<DBObject> getList(int listID) throws ToDoListException;

}

