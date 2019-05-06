package com.hit.todo.controller;

import com.hit.todo.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "/task/*")
public class TaskController extends HttpServlet {

    public void addTask(HttpServletRequest request, HttpServletResponse response) throws  ToDoListException{
        int irrelevant=0000;
        String descriptionInput=request.getParameter("description");
        String statusInput=request.getParameter("status");
        boolean status = Boolean.parseBoolean(statusInput);
      try {
          UtilityFunctions.OnlyLettersNumbersAndSpaces(descriptionInput);
          // HibernateToDoListDAO.getInstance().addTask(new Task(irrelevant,listID,descriptionInput,status)); How do we get listID ?
          response.setStatus(200);
      }catch (IllegalArgumentException  error){ // Add ToDoListException to the catch clause
          throw new ToDoListException(error.getMessage(),error);
      }
    }

    public void deleteTask(HttpServletRequest request, HttpServletResponse response) {
        String descriptionInput=request.getParameter("description");

        /*try{
            UtilityFunctions.OnlyLettersNumbersAndSpaces(descriptionInput);
            //HibernateToDoListDAO.getInstance().deleteTask(descriptionInput);
            response.setStatus(200);
         }catch(IllegalArgumentException | ToDoListException error){
        }*/
    }


    public void getList(HttpServletRequest request, HttpServletResponse response) throws  ToDoListException{
       try {
           String listIDInput = request.getParameter("listID");
           HibernateToDoListDAO.getInstance().getList(UtilityFunctions.IntegerParser(listIDInput));
           response.setStatus(200);
       }catch (NumberFormatException | ToDoListException error ){
           throw  new ToDoListException(error.getMessage(),error);
       }
    }



   /* protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      //Do we need it  even if no http request is sent ?
    }*/

   /* protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Do we need it  even if no http request is sent ?
    }*/
}
