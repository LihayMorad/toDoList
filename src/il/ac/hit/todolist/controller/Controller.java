package il.ac.hit.todolist.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class Controller {

    private JsonObject requestBody;
    private HttpServletRequest request;
    private HttpServletResponse response;

    protected Controller() {
    }

    protected Controller(HttpServletRequest request, HttpServletResponse response) {
        setRequest(request);
        setResponse(response);
        setRequestBody();
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
        try {
            this.requestBody = new Gson().fromJson(this.request.getReader(), JsonObject.class);
        } catch (IOException error) {
            request.setAttribute("message", error.getMessage());
        }
    }

}
