package com.hit.todo.model;

import com.hit.todo.controller.HasPrimaryKey;
import org.hibernate.mapping.Collection;

import java.util.List;
import java.util.Set;

public interface IToDoListDAO {

    boolean addItem(Object item) throws ToDoListException;

     boolean deleteItem(int itemID) throws ToDoListException;

     boolean updateStatus(int itemID, boolean newStatus) throws ToDoListException;

     List<HasPrimaryKey> getList(int listID) throws ToDoListException;

}

