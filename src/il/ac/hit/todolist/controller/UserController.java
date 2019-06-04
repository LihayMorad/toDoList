package il.ac.hit.todolist.controller;

import com.google.gson.Gson;
import il.ac.hit.todolist.model.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class UserController extends Controller {
//No need in request and response as method parameters , since they are class members

    private static AtomicInteger listIDGenerator = new AtomicInteger();
    //private User loggedInUser;

    public UserController() { // default constructor
    }

    public UserController(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    public static int getNewListID() {
        return listIDGenerator.getAndIncrement();
    }

    private void forwardToHomePage(User user) throws ToDoListException {
        try {
            request.getRequestDispatcher("/router/task/getTasksList").forward(request, response);
        } catch (ServletException | IOException e) {
            throw new ToDoListException(e.getMessage(), e);
        }
//        request.setAttribute(("userListID", String.valueOf(user.getListID()));
//        response.setStatus(200);
    }

    private void setAlreadyLoggedInReport(User loggedInUser) throws ToDoListException {
//        responseBody.put("message", "You are already logged in");
        forwardToHomePage(loggedInUser);
    }

    private void setLoginSuccessReport(User signingIn) throws ToDoListException {
        getRequest().getSession().setAttribute("loggedInUser", signingIn);
//        responseBody.put("message", "You are now logged in.");
        forwardToHomePage(signingIn);
    }

    private void setSignUpSuccessReport(User userToRegister) throws ToDoListException {
        getRequest().getSession().setAttribute("loggedInUser", userToRegister); // to indicate that user is logged in
//        responseBody.put("message", "Registration completed successfully and you are now logged in.");
        forwardToHomePage(userToRegister);
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

        try {
            User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
            if (userIsAlreadyLoggedIn(loggedInUser)) { // user is already logged in !!
                setAlreadyLoggedInReport(loggedInUser);
            } else { // user is not logged in
                startLogin();
                //// @@@@@@@@@@@@ maybe we should do the check in sql query (for security reasons)
                //// @@@@@@@@@@@@ maybe we should do the check in sql query (for security reasons)
                //// @@@@@@@@@@@@ maybe we should do the check in sql query (for security reasons)
                //passwordsMatch(data.get("password").getAsString(),data.get("retype").getAsString());
            }
        } catch (ToDoListException e) {
            //System.out.println(e.getMessage());
            //e.printStackTrace();
//            setErrorReport("Unknown error while login", 503);
            request.setAttribute("error", "Unknown error while login.");
        } finally {
            try {
                if (request.getAttribute("error") != null)
                    request.getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
            } catch (IOException | ServletException ex) {
                throw new ToDoListException(ex.getMessage(), ex);//send to error page or make error report
            }
        }
    }

    private void startLogin() throws ToDoListException {
        String usernameProvided = requestBody.get("username").getAsString();
        String passwordProvided = requestBody.get("password").getAsString();
        User loggingIn = (User) UserHibernateDAO.getInstance().requestForSingleItem(usernameProvided);
        if (credentialsAreWrong(loggingIn, passwordProvided)) {
//            setErrorReport("Username and/or password are incorrect.", 406);
            request.setAttribute("error", "Username and/or password are incorrect.");
        } else {
            setLoginSuccessReport(loggingIn);
        }
    }

    private boolean credentialsAreWrong(User loggingIn, String providedPassword) {
        return loggingIn == null || !loggingIn.getPassword().equals(providedPassword);
    }

    private void startSigningUp() throws ToDoListException {
        int generatedListID = getNewListID();

        //User potentialUser=new User(request.getAttribute("username"),request.getAttribute("password"),generatedListID);

        User potentialUser = new User(
                getRequestBody().get("username").getAsString(),
                getRequestBody().get("password").getAsString(),
                generatedListID);
        if (UserHibernateDAO.getInstance().addItem(potentialUser)) {// user was added successfully
            setSignUpSuccessReport(potentialUser);
        } else { // [ERROR]
//            setErrorReport("Username already exists", 409);
            request.setAttribute("error", "Username already exists");
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

        try {
            //passwordsMatch(data.get("password").getAsString(),data.get("retype").getAsString());

            User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
            if (userIsAlreadyLoggedIn(loggedInUser)) { // user is already logged in !!
                setAlreadyLoggedInReport(loggedInUser);
            } else { // user is not logged in
                startSigningUp();
            }
        } catch (ToDoListException e) {
            //System.out.println(e.getMessage());
            //e.printStackTrace();
//            setErrorReport("Unknown error while signing up", 503);
            request.setAttribute("error", "Unknown error while signing up.");//Better to send the message of exception
        } finally {
            try {
                if (request.getAttribute("error") != null)
                    request.getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
            } catch (IOException | ServletException ex) {
                throw new ToDoListException(ex.getMessage(), ex); //send to error page or make error report
                //DON'T throw exception in finally!!!!!!
            }
        }
    }

    private void passwordsMatch(String password, String retype) {
        if (!password.equals(retype))
            throw new IllegalArgumentException("Passwords mismatch");
    }

}
