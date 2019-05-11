package com.hit.todo.controller;

import com.hit.todo.model.ToDoListException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IController {

    public void addItem(HttpServletRequest request, HttpServletResponse response) throws ToDoListException;
    public void deleteItem(HttpServletRequest request, HttpServletResponse response) throws ToDoListException ;
    public void updateItem (HttpServletRequest request, HttpServletResponse response)throws ToDoListException;
    public void getList(HttpServletRequest request, HttpServletResponse response) throws ToDoListException;
}
