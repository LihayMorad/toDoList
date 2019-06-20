package il.ac.hit.todolist.controller;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import il.ac.hit.todolist.model.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
//Unit ,responsible for user registration and login.
public class UserController extends Controller {

    private static AtomicInteger listIDGenerator = new AtomicInteger();

    public UserController() { // default constructor
    }

    public UserController(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    public static int getNewListID() {
        return listIDGenerator.getAndIncrement();
    }

    private boolean credentialsAreWrong(User loggingIn, String providedPassword) throws ToDoListException {
        return loggingIn == null || !UserHibernateDAO.getInstance().validateUser(loggingIn, providedPassword);
    }

    private void successfulLogin(User loggedInUser) throws ToDoListException {
        getRequest().getSession().setAttribute("loggedInUser", loggedInUser);
        forwardToTasksList();
    }

    //This method is invoked while  request for login is received
    public void login() throws ToDoListException {
        try {
            User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
            if (userIsLoggedIn(loggedInUser)) { // user is already logged in !!
                forwardToTasksList();
            } else { // user is not logged in
                initiateLogin();
            }
        } catch (ToDoListException | ServletException | IOException e) {
            request.setAttribute("error", "Unknown error while login.");
        } finally {
            redirectToErrorPageIfNecessary();
        }
    }

    private void initiateLogin() throws ToDoListException, ServletException, IOException {
        String expectedParameters[] = {"username", "password"};
        if (requiredParametersProvided(expectedParameters)) {
            String usernameProvided = request.getParameter("username");
            String passwordProvided = request.getParameter("password");
            User loggingIn = (User) UserHibernateDAO.getInstance().requestForSingleItem(usernameProvided);
            if (credentialsAreWrong(loggingIn, passwordProvided)) {
                request.setAttribute("errorMessage", "Username and/or password are incorrect.");
                request.getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
            } else {
                successfulLogin(loggingIn);
            }
        }
    }
    //This method is invoked while  request for registration is received
    public void register() throws ToDoListException {
        try {
            User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
            if (userIsLoggedIn(loggedInUser)) { // user is already logged in !!
                successfulLogin(loggedInUser);
            } else { // user is not logged in
                initiateSignUp();
            }
        } catch (ToDoListException | ServletException | IOException e) {
            request.setAttribute("error", "Unknown error while signing up.");
        } finally {
            redirectToErrorPageIfNecessary();
        }
    }

    private void initiateSignUp() throws ToDoListException, ServletException, IOException {
        int generatedListID = getNewListID();
        String expectedParameters[] = {"username", "password"};
        if (requiredParametersProvided(expectedParameters)) {
            String usernameProvided = request.getParameter("username");
            String passwordProvided = request.getParameter("password");
            User potentialUser = new User(usernameProvided, passwordProvided, generatedListID);
            if (UserHibernateDAO.getInstance().addItem(potentialUser)) {// user was added successfully
                successfulLogin(potentialUser);
            } else { // [ERROR]
                request.setAttribute("errorMessage", "Username already exists");
                request.getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
            }
        }
    }

}
