package il.ac.hit.todolist.controller;


import il.ac.hit.todolist.model.TaskHibernateDAO;
import il.ac.hit.todolist.model.ToDoListException;
import il.ac.hit.todolist.model.UtilityFunctions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TaskController  {



        public void addtask(HttpServletRequest request, HttpServletResponse response) throws ToDoListException {
            int irrelevant=0000;

           /* try {
                // TaskHibernateDAO.getInstance().addTask(new Task(irrelevant,listID,request.getParameter("description"),
                //                                            request.getParameter("status"))); How do we get listID ?
                response.setStatus(200);
            }catch (IllegalArgumentException  error){ // Add ToDoListException to the catch clause
                throw new ToDoListException(error.getMessage(),error);
            }*/
        }


        public void deletetask(HttpServletRequest request, HttpServletResponse response) throws ToDoListException  {
            String descriptionInput=request.getParameter("description");

        /*try{
            UtilityFunctions.OnlyLettersNumbersAndSpaces(descriptionInput);
            //TaskHibernateDAO.getInstance().deleteTask(descriptionInput);
            response.setStatus(200);
         }catch(IllegalArgumentException | ToDoListException error){
        }*/
        }


        public void gettasklist(HttpServletRequest request, HttpServletResponse response) throws  ToDoListException{
            try {
                String listIDInput = request.getParameter("listID");
                TaskHibernateDAO.getInstance().getList(UtilityFunctions.IntegerParser(listIDInput));
                response.setStatus(200);
            }catch (ToDoListException error ){
                throw  new ToDoListException(error.getMessage(),error);
            }
        }


        public void updatetaskstatus(HttpServletRequest request, HttpServletResponse response) throws ToDoListException {

        }


}









