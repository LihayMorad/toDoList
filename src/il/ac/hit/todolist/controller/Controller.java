package il.ac.hit.todolist.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import il.ac.hit.todolist.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class Controller {

    protected JsonObject requestBody;
    protected Map<String, String> responseBody;
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    protected Controller() {
    }

    protected Controller(HttpServletRequest request, HttpServletResponse response) {
        setRequest(request);
        setResponse(response);
        setRequestBody();
        setResponseBody();
        setContentTypeAndEncoding();
    }

    public JsonObject getRequestBody() {
        return requestBody;
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

    private void setRequestBody() {
//        try {
//            this.requestBody = new Gson().fromJson(this.request.getReader(), JsonObject.class);
//        } catch (IOException error) {
//            request.setAttribute("message", error.getMessage());
//        }
    }

    protected boolean userIsLoggedIn(User loggedInUser) {
        return loggedInUser != null;
    }

    private void setResponseBody() {
        responseBody = new HashMap<>();
    }

    private void setContentTypeAndEncoding() {
        response.setContentType("application/json"); //Must be changed since we don't use Json Object ("application/html" ?)
        response.setCharacterEncoding("UTF-8");
    }

    protected void setErrorReport(String errorMessage, int errorCode) {
        responseBody.put("error", errorMessage);
        response.setStatus(errorCode);
    }

    protected  void redirectToErrorPage(){
        try {
            if (request.getAttribute("error") != null)
                request.getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
        } catch (IOException | ServletException ex) {}
    }
}
