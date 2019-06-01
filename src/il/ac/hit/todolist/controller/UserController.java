package il.ac.hit.todolist.controller;

import com.google.gson.Gson;
import il.ac.hit.todolist.model.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class UserController extends Controller {
//No need in request and response as method parameters , since they are class members

    private static AtomicInteger listIDGenerator = new AtomicInteger();

    public UserController() { // default constructor
    }

    public UserController(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    public static int getNewListID() {
        return listIDGenerator.getAndIncrement();
    }

    public void login() throws ToDoListException {

//        $.ajax({
//                type: "POST",
//                url: "http://localhost:8080/toDoList_war_exploded/router/user/login",
//                data: JSON.stringify({ username: "lihay2", password: "pass1" }),
//        success: (response) => {
//            console.log("response: ", response);
//        },
//        error: (error) => {
//            console.warn("error: ", error);
//            console.warn("error.responseJSON: ", error.responseJSON);
//        }
//        })

        HttpServletResponse response = this.getResponse();
        HttpServletRequest request = this.getRequest();
        Map<String, String> responseBody = new HashMap<>();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
            if (loggedInUser != null) { // user is already logged in !!
                responseBody.put("message", "You are already logged in");
                responseBody.put("userListID", String.valueOf(loggedInUser.getListID()));
                responseBody.put("redirectTo", request.getContextPath() + "/index.jsp"); // SHOULD BE 'TO DO LIST' PAGE
                response.setStatus(200);

            } else { // user is not logged in

                //// @@@@@@@@@@@@ maybe we should do the check in sql query (for security reasons)
                //// @@@@@@@@@@@@ maybe we should do the check in sql query (for security reasons)
                //// @@@@@@@@@@@@ maybe we should do the check in sql query (for security reasons)

                //passwordsMatch(data.get("password").getAsString(),data.get("retype").getAsString());

                String usernameProvided = getRequestBody().get("username").getAsString();
                String passwordProvided = getRequestBody().get("password").getAsString();
                User user = (User) UserHibernateDAO.getInstance().requestForSingleItem(usernameProvided);

                if (user != null) { // user with the provided 'username' exists
                    String username = user.getUsername();
                    String password = user.getPassword();

                    if (username.equals(usernameProvided) && password.equals(passwordProvided)) { // provided login details are correct
                        getRequest().getSession().setAttribute("loggedInUser", user); // to indicate that user is logged in

                        responseBody.put("message", "You are now logged in.");
                        responseBody.put("userListID", String.valueOf(user.getListID()));
                        responseBody.put("redirectTo", request.getContextPath() + "/index.jsp"); // SHOULD BE 'TO DO LIST' PAGE
                        response.setStatus(200);
                    } else { // [ERROR] provided password is incorrect
                        responseBody.put("error", "Wrong password provided.");
                        response.setStatus(406);
                    }
                } else { // [ERROR] user with the provided username doesn't exists
                    responseBody.put("error", "Wrong username provided.");
                    response.setStatus(406);
                }
            }
        } catch (ToDoListException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            responseBody.put("error", "Login error");
            response.setStatus(503);
        } finally {
            try {
                String responseBodyJSON = new Gson().toJson(responseBody);
                response.getWriter().write(responseBodyJSON);
                response.getWriter().flush();
                response.getWriter().close();
            } catch (IOException ex) {
                throw new ToDoListException(ex.getMessage(), ex);
            }
        }
    }

    public void register() throws ToDoListException {

//        $.ajax({
//                type: "POST",
//                url: "http://localhost:8080/toDoList_war_exploded/router/user/register",
//                data: JSON.stringify({ username: "lihay2", password: "pass1" }),
//        success: (response) => {
//            console.log("response: ", response);
//        },
//        error: (error) => {
//            console.warn("error: ", error);
//            console.warn("error.responseJSON: ", error.responseJSON);
//        }
//        })

        HttpServletResponse response = this.getResponse();
        HttpServletRequest request = this.getRequest();
        Map<String, String> body = new HashMap<>();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            //passwordsMatch(data.get("password").getAsString(),data.get("retype").getAsString());

            User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
            if (loggedInUser != null) { // user is already logged in !!
                body.put("message", "You are already logged in");
                body.put("userListID", String.valueOf(loggedInUser.getListID()));
                body.put("redirectTo", request.getContextPath() + "/index.jsp"); // SHOULD BE 'TO DO LIST' PAGE
                response.setStatus(200);

            } else { // user is not logged in
                int generatedListID = getNewListID();

                User potentialUser = new User(
                        getRequestBody().get("username").getAsString(),
                        getRequestBody().get("password").getAsString(),
                        generatedListID);
                if (UserHibernateDAO.getInstance().addItem(potentialUser)) { // user was added successfully
                    User userToRegister = (User) UserHibernateDAO.getInstance().requestForSingleItem(potentialUser.getUniqueParameter());
                    getRequest().getSession().setAttribute("loggedInUser", userToRegister); // to indicate that user is logged in

                    body.put("message", "Registration completed successfully and you are now logged in.");
                    body.put("userListID", String.valueOf(generatedListID));
                    body.put("redirectTo", request.getContextPath() + "/index.jsp"); // SHOULD BE 'TO DO LIST' PAGE
                    response.setStatus(200);
                } else { // [ERROR] there is already a username with the same with
                    body.put("error", "Username already exists");
                    response.setStatus(409);
                }
            }

        } catch (ToDoListException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            body.put("error", "Registration error");
            response.setStatus(503);
        } finally {
            try {
                String responseBody = new Gson().toJson(body);
                response.getWriter().write(responseBody);
                response.getWriter().flush();
                response.getWriter().close();
            } catch (IOException ex) {
                throw new ToDoListException(ex.getMessage(), ex);
            }
        }
    }

    private void passwordsMatch(String password, String retype) {
        if (!password.equals(retype))
            throw new IllegalArgumentException("Passwords mismatch");
    }

}
