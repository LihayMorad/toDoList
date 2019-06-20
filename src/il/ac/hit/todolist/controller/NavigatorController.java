package il.ac.hit.todolist.controller;

import il.ac.hit.todolist.model.ToDoListException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//This class provides switch between different screens ( in other words redirection from one jsp file to another)
public class NavigatorController extends Controller {

    public NavigatorController() { // constructor
    }

    public NavigatorController(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    // router/navigator/location?goto=value
    public void location() throws ToDoListException {
        try {
            String goTo = request.getParameter("goto");
            String[] queryArray = request.getQueryString().split("&");
            StringBuilder redirectTo = new StringBuilder(request.getContextPath()).append("/").append(goTo).append(".jsp");
            if (goTo != null) {
                if (queryArray.length > 1) { // supporting requests that contains query parameters other than 'goTo'
                    int i;
                    StringBuilder queryParametersString = new StringBuilder("?");
                    for (i = 1; i < queryArray.length - 1; i++) {
                        queryParametersString.append(queryArray[i]).append("&");
                    }
                    queryParametersString.append(queryArray[i]); // last parameter doesn't end with '&'
                    redirectTo.append(queryParametersString);
                }
                response.sendRedirect(redirectTo.toString());
            } else {
                request.setAttribute("error", "Location not found");
                request.getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } catch (ServletException | IOException e) {
            //e.printStackTrace();
            throw new ToDoListException("Something went wrong");
        }
    }
}
