package il.ac.hit.todolist.controller;

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

    private void forwardToTasksList(User user) throws ToDoListException {
        try {
            request.getRequestDispatcher("/router/task/getTasksList").forward(request, response);
        } catch (ServletException | IOException e) {
            throw new ToDoListException(e.getMessage(), e);
        }
    }

    private void successfulLogin(User loggedInUser) throws ToDoListException {
        getRequest().getSession().setAttribute("loggedInUser", loggedInUser);
        forwardToTasksList(loggedInUser);
    }

    public void login() throws ToDoListException {

        try {
            User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
            if (userIsLoggedIn(loggedInUser)) { // user is already logged in !!
                forwardToTasksList(loggedInUser);
            } else { // user is not logged in
                initiateLogin();
                //// @@@@@@@@@@@@ maybe we should do the check in sql query (for security reasons)
                //// @@@@@@@@@@@@ maybe we should do the check in sql query (for security reasons)
                //// @@@@@@@@@@@@ maybe we should do the check in sql query (for security reasons)
                //passwordsMatch(data.get("password").getAsString(),data.get("retype").getAsString());
            }
        } catch (ToDoListException e) {
            //System.out.println(e.getMessage());
            //e.printStackTrace();
            request.setAttribute("error", "Unknown error while login.");
        } finally {
            redirectToErrorPage();
        }
    }

    private void initiateLogin() throws ToDoListException {
        String usernameProvided = request.getParameter("username");
        String passwordProvided = request.getParameter("password");
        if (usernameProvided != null && passwordProvided != null) {
            User loggingIn = (User) UserHibernateDAO.getInstance().requestForSingleItem(usernameProvided);
            if (credentialsAreWrong(loggingIn, passwordProvided)) {
                request.setAttribute("error", "Username and/or password are incorrect.");
            } else {
                successfulLogin(loggingIn);
            }
        } else { // [ERROR]
            request.setAttribute("error", "Username or password not provided.");
        }
    }

    private boolean credentialsAreWrong(User loggingIn, String providedPassword) throws ToDoListException {
        return loggingIn == null || !UserHibernateDAO.getInstance().validateUser(loggingIn, providedPassword);
    }

    private void initiateSignUp() throws ToDoListException {
        int generatedListID = getNewListID();

        //User potentialUser=new User(request.getAttribute("username"),request.getAttribute("password"),generatedListID);

        User potentialUser = new User(
                getRequestBody().get("username").getAsString(),
                getRequestBody().get("password").getAsString(),
                generatedListID);
        if (UserHibernateDAO.getInstance().addItem(potentialUser)) {// user was added successfully
            successfulLogin(potentialUser);
        } else { // [ERROR]
//            setErrorReport("Username already exists", 409);
            request.setAttribute("error", "Username already exists");
        }
    }

    public void register() throws ToDoListException {

        try {
            //passwordsMatch(data.get("password").getAsString(),data.get("retype").getAsString());

            User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
            if (userIsLoggedIn(loggedInUser)) { // user is already logged in !!
                successfulLogin(loggedInUser);
            } else { // user is not logged in
                initiateSignUp();
            }
        } catch (ToDoListException e) {
            //System.out.println(e.getMessage());
            //e.printStackTrace();
//            setErrorReport("Unknown error while signing up", 503);
            request.setAttribute("error", "Unknown error while signing up.");//Better to send the message of exception
        } finally {
            redirectToErrorPage();
        }
    }

    private void passwordsMatch(String password, String retype) {
        if (!password.equals(retype))
            throw new IllegalArgumentException("Passwords mismatch");
    }

}
