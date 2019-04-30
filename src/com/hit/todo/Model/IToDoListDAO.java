package com.hit.todo.Model;

import org.hibernate.Session;

import java.util.List;

public interface IToDoListDAO {

    public boolean addTask(Session hibernateSession, Task task) throws ToDoListException;

    public boolean deleteTask(Session hibernateSession, int taskID) throws ToDoListException;

    public boolean updateTaskStatus(Session hibernateSession, int taskID, boolean newStatus) throws ToDoListException;

    public List<Task> getList(Session hibernateSession, int listID) throws ToDoListException;

}
