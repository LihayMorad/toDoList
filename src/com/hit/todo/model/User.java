package com.hit.todo.model;

public class User extends DBObject {

    private String username;
    private String password;
    private int listID;

//    private Map<String, Iaccessible> taskLog;

    public User() {
    }

    public User(String username, String password, int listID) {
        this.setUsername(username);
        this.setPassword(password);
        this.setListID(listID);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getListID() {
        return listID;
    }

    public void setListID(int listID) {
        this.listID = listID;
    }

//    @Override
//    public void map() throws ToDoListException {
//
//           /*HibernateToDoListDAO dbinteract = HibernateToDoListDAO.getInstance();
//           Task a = (Task)dbinteract.getList(listID).get(1);
//
//           taskLog = dbinteract.getList(listID).stream().collect(Collectors.toMap(Task:: getDescription, Function.identity()));*/
//
//    }
}
