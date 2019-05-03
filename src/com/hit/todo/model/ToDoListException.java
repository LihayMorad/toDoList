package com.hit.todo.model;

public class ToDoListException extends Exception {

    public ToDoListException() {
        super("General ToDoListException");
    }            // Constructor

    public ToDoListException(String msg) {
        super(msg);
    }                          // Constructor

    public ToDoListException(String msg, Throwable cause) {
        super(msg, cause);
    }  // Constructor

}
