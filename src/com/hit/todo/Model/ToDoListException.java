package com.hit.todo.Model;

public class ToDoListException extends Exception {

    public ToDoListException() {
        super("General ToDoListException");
    }

    public ToDoListException(String msg) {
        super(msg);
    }

    public ToDoListException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
