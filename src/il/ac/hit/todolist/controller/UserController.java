package il.ac.hit.todolist.controller;

import com.google.gson.Gson;
import il.ac.hit.todolist.model.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserController extends Controller {
//No need in request and response as method parameters , since they are class members

    public UserController() { // default constructor
    }

    public UserController(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    public void register() {

        try {
            //passwordsMatch(data.get("password").getAsString(),data.get("retype").getAsString());
            //Check if user exists
            HttpServletResponse response = this.getResponse();
            Map<String, String> responseBody = new HashMap<>();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            if (UserHibernateDAO.getInstance().addItem(new User(
                    getRequestBody().get("username").getAsString(),
                    getRequestBody().get("password").getAsString(),
                    66))) { // user was added successfully

                responseBody.put("redirect", "true");
                response.setStatus(200);
            } else { // there is already a username with the same with
                responseBody.put("error", "Username already exists");
                response.setStatus(409);
            }
            String body = new Gson().toJson(responseBody);
            response.getWriter().write(body);

        } catch (ToDoListException | IllegalArgumentException | IOException error) {
            this.getRequest().setAttribute("message", error.getMessage());
            this.getRequest().getRequestDispatcher("/toDoList_war_exploded/register.jsp");
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
