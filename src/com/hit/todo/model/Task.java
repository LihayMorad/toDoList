package com.hit.todo.model;

public class Task extends DBObject {

    //Missing deadline parameter

    // Class members
    private int taskID; // auto generated by hibernate
    private int listID;
    private String description;
    private boolean status;

    // Class constructors
    public Task() {
    } // default Constructor

    public Task(int taskID, int listID, String description, boolean status) { // constructor
        this.setTaskID(taskID);
        this.setListID(listID);
        this.setDescription(description);
        this.setStatus(status);
    }

//    public Task(int taskID, int listID, String description, String status) {
//        setTaskID(taskID);
//        setListID(listID);
//        setDescription(description);
//        setDescription(status);
//    }

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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
//        this.status=UtilityFunctions.BooleanValidator(status);
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskID=" + taskID +
                ", listID=" + listID +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public String getUniqueParameter(){
        return  getDescription();
    }

}
