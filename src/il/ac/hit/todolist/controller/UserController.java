package il.ac.hit.todolist.controller;

import il.ac.hit.todolist.model.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class, responsible for user registration and login.
 */
public class UserController extends Controller {

    /**
     * this parameter is used for generating ID for each new list
     */
    private static AtomicInteger listIDGenerator = new AtomicInteger();

    public UserController() { // default constructor
    }

    // Constructor
    public UserController(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    /**
     * @return  an integer, id for a new list
     */
    public static int getNewListID() {
        return listIDGenerator.getAndIncrement();
    }

    /**
     * check whether user owns the list or not
     * @param	loggingIn
     * @param	providedPassword
     * @return  true if the credentials are wrong, else return false
     */
    private boolean credentialsAreWrong(User loggingIn, String providedPassword) throws ToDoListException {
        return loggingIn == null || !UserHibernateDAO.getInstance().validateUser(loggingIn, providedPassword);
    }

    /**
     * forward user to tasks list page after a successful login
     * @param	loggedInUser
     */
    private void successfulLogin(User loggedInUser) throws ToDoListException {
        getRequest().getSession().setAttribute("loggedInUser", loggedInUser);
        forwardToTasksList();
    }

    /**
     * This method is invoked while  request for login is received
     */
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

    /**
     * this function is used by "login" function
     * initiate login
     */
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

    /**
     * This method is invoked while  request for registration is received
     */
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

    /**
     * this function is used by "register" function
     * initiate register
     */
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
