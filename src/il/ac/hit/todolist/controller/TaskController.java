package il.ac.hit.todolist.controller;

import il.ac.hit.todolist.model.*;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TaskController extends Controller {

    public TaskController() { // default constructor
    }

    public TaskController(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    public void addtask() throws ToDoListException {

//        $.ajax({
//                type: "POST",
//                url: "http://localhost:8080/toDoList_war_exploded/router/task/addtask",
//                data: JSON.stringify({
//                description: "my first task",
//                listID: 1,
//                status: false,
//                deadline: "1.7.19"
//            }),
//        success: (response) => {
//            console.log("response: ", response);
//        },
//        error: (error) => {
//            console.warn("error: ", error);
//            console.warn("error.responseJSON: ", error.responseJSON);
//        }
//            })

        HttpServletResponse response = this.getResponse();
        HttpServletRequest request = this.getRequest();
        Map<String, String> responseBody = new HashMap<>();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
            if (loggedInUser != null) { // the user is logged in
                int irrelevantTaskID = 00; // because it's auto generated hibernate
                int listID = getRequestBody().get("listID").getAsInt(); //ARGUABLE!!!Do we need to get it as String ?
                String description = getRequestBody().get("description").getAsString();
                boolean status = getRequestBody().get("status").getAsBoolean();
                String deadline = getRequestBody().get("deadline").getAsString();

                if (loggedInUser.getListID() == listID) { // the user own the list
                    Task task = new Task(irrelevantTaskID, listID, description, status, deadline);
                    if (TaskHibernateDAO.getInstance().addItem(task)) {
                        responseBody.put("message", "Task was added successfully");
                        responseBody.put("taskID", String.valueOf(task.getTaskID()));
                        responseBody.put("listID", String.valueOf(listID));
                        response.setStatus(200);
                    } else { // [ERROR]
                        responseBody.put("error", "There was a problem adding the task.");
                        response.setStatus(503);
                    }
                } else { // [ERROR]
                    responseBody.put("error", "You can't add a task to a list you don't own.");
                    response.setStatus(401);
                }
            } else { // [ERROR] unauthorized!
                responseBody.put("error", "Please login to edit your to do list.");
                response.setStatus(401);
            }
        } catch (ToDoListException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            responseBody.put("error", "Add task error");
            response.setStatus(503);
        } finally {
            try {
                String responseBodyJSON = new Gson().toJson(responseBody);
                response.getWriter().write(responseBodyJSON);
                response.getWriter().flush();
                response.getWriter().close();
            } catch (IOException ex) {
                throw new ToDoListException(ex.getMessage(), ex);
            }
        }
    }

    public void deletetask() throws ToDoListException {
        // @@@@@@@@@@@@@@@@@@@@ there is a problem when using 'DELETE' method from another origin.
        // @@@@@@@@@@@@@@@@@@@@ there is a problem when using 'DELETE' method from another origin.
        // @@@@@@@@@@@@@@@@@@@@ there is a problem when using 'DELETE' method from another origin.

//        $.ajax({
//                type: "DELETE",
//                url: "http://localhost:8080/toDoList_war_exploded/router/task/deletetask",
//                data: JSON.stringify({ taskID: 9 }),
//        success: (response) => {
//            console.log("response: ", response);
//        },
//        error: (error) => {
//            console.warn("error: ", error);
//            console.warn("error.responseJSON: ", error.responseJSON);
//        }
//            })

        HttpServletResponse response = this.getResponse();
        HttpServletRequest request = this.getRequest();
        Map<String, String> responseBody = new HashMap<>();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
            if (loggedInUser != null) { // the user is logged in

                int taskID = getRequestBody().get("taskID").getAsInt();
                int listID = loggedInUser.getListID();
                if (loggedInUser.getListID() == listID) { // the user own the list
                    if (TaskHibernateDAO.getInstance().deleteItem(taskID)) {
                        responseBody.put("message", "Task was deleted successfully");
                        responseBody.put("listID", String.valueOf(listID));
                        response.setStatus(200);
                    } else { // [ERROR]
                        responseBody.put("error", "There was a problem deleting the task.");
                        response.setStatus(503);
                    }
                } else { // [ERROR]
                    responseBody.put("error", "You can't delete a task from a list you don't own.");
                    response.setStatus(401);
                }
            } else { // [ERROR] unauthorized!
                responseBody.put("error", "Please login to edit your to do list.");
                response.setStatus(401);
            }
        } catch (ToDoListException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            responseBody.put("error", "Delete task error");
            response.setStatus(503);
        } finally {
            try {
                String responseBodyJSON = new Gson().toJson(responseBody);
                response.getWriter().write(responseBodyJSON);
                response.getWriter().flush();
                response.getWriter().close();
            } catch (IOException ex) {
                throw new ToDoListException(ex.getMessage(), ex);
            }
        }
    }

    public void getTasksList() throws ToDoListException {
        try {
            String listIDInput = getRequest().getParameter("listID");
            TaskHibernateDAO.getInstance().getList(UtilityFunctions.integerParser(listIDInput));
            getResponse().setStatus(200);
        } catch (ToDoListException error) {
            throw new ToDoListException(error.getMessage(), error);
        }
    }

    public void updatetaskstatus() throws ToDoListException {

    }


}









