package com.hit.todo.Model;

public class Task {

    private int taskID;
    private int listID;
    private String description;
    private boolean status;

    public Task() {
    }

    public Task(int taskID, int listID, String description, boolean status) {
        this.taskID = taskID;
        this.listID = listID;
        this.description = description;
        this.status = status;
    }

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
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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

}
