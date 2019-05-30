package il.ac.hit.todolist.controller;


import il.ac.hit.todolist.model.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TaskController extends Controller {

//IMPORTANT !!!! All the methods must be WITHOUT parameters, since request and response are class members

    public void addtask(HttpServletRequest request, HttpServletResponse response) throws ToDoListException {
        int irrelevant = 0000;

        String description = getRequestBody().get("description").getAsString();
        boolean status = getRequestBody().get("status").getAsBoolean();
        int listID = getRequestBody().get("listID").getAsInt();//ARGUABLE!!!Do we need to get it as String ?

//            try {
//                 TaskHibernateDAO.getInstance().addItem(new Task(irrelevant,listID,description, status)); //How do we get listID ?
//            }catch (IllegalArgumentException | ToDoListException error){ // Add ToDoListException to the catch clause
//                throw new ToDoListException(error.getMessage(),error);
//            }
    }

    public void deletetask(HttpServletRequest request, HttpServletResponse response) throws ToDoListException {
        String descriptionInput = getRequestBody().get("description").getAsString();

        try {
            UtilityFunctions.OnlyLettersNumbersAndSpaces(descriptionInput);
            TaskHibernateDAO.getInstance().deleteItem(descriptionInput);
            //response.setStatus(200);
        } catch (IllegalArgumentException | ToDoListException error) {
            //throw new ToDoListException(error.getMessage(),)
        }

    }

    public void getTasksList(HttpServletRequest request, HttpServletResponse response) throws ToDoListException {
        try {
            String listIDInput = request.getParameter("listID");
            TaskHibernateDAO.getInstance().getList(UtilityFunctions.integerParser(listIDInput));
            response.setStatus(200);
        } catch (ToDoListException error) {
            throw new ToDoListException(error.getMessage(), error);
        }
    }

    public void updatetaskstatus(HttpServletRequest request, HttpServletResponse response) throws ToDoListException {

    }
}