package il.ac.hit.todolist.model;

import java.io.Serializable;
import java.util.List;

public interface IToDoListDAO {

    boolean addItem(DBObject item) throws ToDoListException;

    boolean deleteItem(Serializable itemID) throws ToDoListException;

    //boolean updateStatus(int itemID, boolean newStatus) throws ToDoListException;

    List<DBObject> getList(int listID) throws ToDoListException;

}

