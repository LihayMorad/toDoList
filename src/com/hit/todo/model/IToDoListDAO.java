package com.hit.todo.model;

import org.hibernate.Session;

import java.util.List;

public interface IToDoListDAO {

    boolean addItem(DBObject item) throws ToDoListException;

    boolean deleteItem(int itemID) throws ToDoListException;

    boolean updateStatus(int itemID, boolean newStatus) throws ToDoListException;

    List<Task> getList(int listID) throws ToDoListException;

}

