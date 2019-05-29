package il.ac.hit.todolist.controller;

import il.ac.hit.todolist.model.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class UserController extends Controller {
//No need in request and response as method parameters , since they are class members

    public UserController() {
    }

    public UserController(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }


    public void register(HttpServletRequest request, HttpServletResponse response) {

        try {

            //passwordsMatch(data.get("password").getAsString(),data.get("retype").getAsString());
            //Check if user exists
            UserHibernateDAO.getInstance().addItem(new User(getRequestBody().get("username").getAsString(),
                    getRequestBody().get("password").getAsString(),
                    getRequestBody().get("listID").getAsString()));
        } catch (ToDoListException | IllegalArgumentException error) {
            request.setAttribute("message", error.getMessage());
            request.getRequestDispatcher("/toDoList_war_exploded/register.jsp");
        }

    }

    private void passwordsMatch(String password, String retype) {
        if (!password.equals(retype))
            throw new IllegalArgumentException("Passwords mismatch");

    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws ToDoListException {


        HttpSession session = request.getSession(false);
        String password = getRequestBody().get("password").getAsString();

        User loggedIn = (User) UserHibernateDAO.getInstance().requestForSingleItem(request.getParameter("username"));
        if (loggedIn == null || !password.equals(loggedIn.getPassword()))
            throw new IllegalArgumentException("Username or password are incorrect");

        session.setAttribute("loggedIn", loggedIn);
        //Redirect to an appropriate page
    }

}
