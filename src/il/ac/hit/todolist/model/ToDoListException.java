package il.ac.hit.todolist.model;

//Universal exception , that can be thrown regardless to kind of database , the program works with.
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
