package il.ac.hit.todolist.controller;

import il.ac.hit.todolist.model.ToDoListException;
import il.ac.hit.todolist.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * This class defines common actions and members for all the controllers
 */
public abstract class Controller {

    protected HttpServletRequest request;
    protected HttpServletResponse response;

    protected Controller() { // default constructor
    }

    /**
     * Controller constructor
     * @param	request	  current request
     * @param	response  current response
     */
    protected Controller(HttpServletRequest request, HttpServletResponse response) {
        setRequest(request);
        setResponse(response);
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    private void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    private void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    /**
     * checks whether user is logged in or not
     * @param	loggedInUser
     * @return  true if user is logged in, else false
     */
    protected boolean userIsLoggedIn(User loggedInUser) {
        return loggedInUser != null;
    }

    /**
     * checks whether required parameters provided or not
     * @param	expectedParameters
     * @return  true if required parameters provided, else false
     */
    protected boolean requiredParametersProvided(String[] expectedParameters) {
        boolean provided = true;
        Map<String, String[]> parametersMap = request.getParameterMap();
        StringBuilder missingParameters = new StringBuilder();
        for (String expectedParameter : expectedParameters) {
            if (!parametersMap.containsKey(expectedParameter)) {
                provided = false;
                missingParameters.append(expectedParameter).append(", ");
            }
        }
        if (!provided) {
            request.setAttribute("error", missingParameters + "not provided.");
        }
        return provided;
    }

    /**
     * forward user to tasks list page
     */
    protected void forwardToTasksList() throws ToDoListException {
        try {
            request.getRequestDispatcher("/router/task/getTasksList").forward(request, response);
        } catch (ServletException | IOException e) {
            throw new ToDoListException(e.getMessage(), e);
        }
    }

    /**
     * forward user to error page if necessary
     */
    protected void redirectToErrorPageIfNecessary() {
        try {
            if (request.getAttribute("error") != null)
                request.getRequestDispatcher("/error.jsp").forward(request, response);
        } catch (IOException | ServletException ex) {
            ex.printStackTrace();
        }
    }
}
