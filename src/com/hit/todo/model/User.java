package com.hit.todo.model;

import com.hit.todo.controller.HasPrimaryKey;
import com.hit.todo.controller.IMappable;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class User implements IMappable{




private String username;
private String password;
private String email;
private  int listID;
private Map<String,HasPrimaryKey> taskLog;

    public User (String username, String password, String phone){
        setPassword(password);
        setEmail(phone);
        setUsername(username);
    }

    public User() {}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        UtilityFunctions.PasswordValidation(password);
        this.password = password;
    }

    @Override
    public void map() throws ToDoListException   {

           /*HibernateToDoListDAO dbinteract = HibernateToDoListDAO.getInstance();
           Task a = (Task)dbinteract.getList(listID).get(1);

           taskLog = dbinteract.getList(listID).stream().collect(Collectors.toMap(Task:: getDescription, Function.identity()));*/

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        UtilityFunctions.EmailValidation(email);
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        UtilityFunctions.OnlyLettersAndNumbers(username);
        this.username = username;
    }
}
