package com.hit.todo.controller;

import com.hit.todo.model.HibernateToDoListDAO;
import com.hit.todo.model.ToDoListException;
import com.hit.todo.model.UtilityFunctions;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TaskController  implements  IController{


        @Override
        public void addItem(HttpServletRequest request, HttpServletResponse response) throws ToDoListException {
            int irrelevant=0000;

            try {
                // HibernateToDoListDAO.getInstance().addTask(new Task(irrelevant,listID,request.getParameter("description"),
                //                                            request.getParameter("status"))); How do we get listID ?
                response.setStatus(200);
            }catch (IllegalArgumentException  error){ // Add ToDoListException to the catch clause
                throw new ToDoListException(error.getMessage(),error);
            }
        }

        @Override
        public void deleteItem(HttpServletRequest request, HttpServletResponse response) throws ToDoListException  {
            String descriptionInput=request.getParameter("description");

        /*try{
            UtilityFunctions.OnlyLettersNumbersAndSpaces(descriptionInput);
            //HibernateToDoListDAO.getInstance().deleteTask(descriptionInput);
            response.setStatus(200);
         }catch(IllegalArgumentException | ToDoListException error){
        }*/
        }

        @Override
        public void getList(HttpServletRequest request, HttpServletResponse response) throws  ToDoListException{
            try {
                String listIDInput = request.getParameter("listID");
                HibernateToDoListDAO.getInstance().getList(UtilityFunctions.IntegerParser(listIDInput));
                response.setStatus(200);
            }catch (ToDoListException error ){
                throw  new ToDoListException(error.getMessage(),error);
            }
        }

        @Override
        public void updateItem(HttpServletRequest request, HttpServletResponse response) throws ToDoListException {

        }
}









