package com.hit.todo.model;

import java.util.List;

public interface IToDoListDAO {

    public boolean addTask(Task task) throws ToDoListException;

    public boolean deleteTask(int taskID) throws ToDoListException;

    public boolean updateTaskStatus(int taskID, boolean newStatus) throws ToDoListException;

    public List<Task> getList(int listID) throws ToDoListException;

}
