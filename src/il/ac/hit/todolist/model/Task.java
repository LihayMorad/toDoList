package il.ac.hit.todolist.model;

import java.io.Serializable;
import il.ac.hit.todolist.validation.*;
public class Task extends DBObject {

    //Missing deadline parameter

    // Class members
    private int taskID; // auto generated by hibernate
    private int listID;
    private String description;
    private boolean status;
    private String deadline;

    // Class constructors
    public Task() {
    } // default Constructor

    public Task(int taskID, int listID, String description, boolean status, String deadline) { // constructor
        this.setTaskID(taskID);
        this.setListID(listID);
        this.setDescription(description);
        this.setStatus(status);
        this.setDeadline(deadline);
    }

    // Class methods
    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public int getListID() {
        return listID;
    }

    public void setListID(int listID) {
        this.listID = listID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
//        UtilityFunctions.OnlyLettersNumbersAndSpaces(description);
        this.description = description;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
        //        this.status=UtilityFunctions.BooleanValidator(status);
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        //UtilityFunctions.DateValidation(deadline);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskID=" + taskID +
                ", listID=" + listID +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", deadline=" + deadline +
                '}';
    }

    @Override
    public Serializable getUniqueParameter() {
        return getTaskID();
    }

}
