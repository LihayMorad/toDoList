package il.ac.hit.todolist.model;

import il.ac.hit.todolist.validation.*;

//Hibernate class defining an appropriate table in DB ( in this case users table)
public class User extends DBObject {

    private String username;
    private String password;
    private int listID;

    public User() { // default constructor
    }

    public User(String username, String password, int listID) {
        this.setUsername(username);
        this.setPassword(password);
        this.setListID(listID);
    }

    // getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        //UtilityFunctions.onlyLettersAndNumbers(username);
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        //UtilityFunctions.passwordValidation(password);
        this.password = password;
    }

    public int getListID() {
        return listID;
    }

    public void setListID(int listID) {
        this.listID = listID;
    }

    @Override
    public String getUniqueParameter() {
        return getUsername();
    }

    @Override
    public String toString() { // The returned string is a line from Users table
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", listID=" + listID +
                '}';
    }

}
