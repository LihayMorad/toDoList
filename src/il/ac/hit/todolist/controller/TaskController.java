package il.ac.hit.todolist.controller;


import il.ac.hit.todolist.model.TaskHibernateDAO;
import il.ac.hit.todolist.model.ToDoListException;
import il.ac.hit.todolist.model.UtilityFunctions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TaskController  implements  IController{


        @Override
        public void addItem(HttpServletRequest request, HttpServletResponse response) throws ToDoListException {
            int irrelevant=0000;

           /* try {
                // TaskHibernateDAO.getInstance().addTask(new Task(irrelevant,listID,request.getParameter("description"),
                //                                            request.getParameter("status"))); How do we get listID ?
                response.setStatus(200);
            }catch (IllegalArgumentException  error){ // Add ToDoListException to the catch clause
                throw new ToDoListException(error.getMessage(),error);
            }*/
        }

        @Override
        public void deleteItem(HttpServletRequest request, HttpServletResponse response) throws ToDoListException  {
            String descriptionInput=request.getParameter("description");

        /*try{
            UtilityFunctions.OnlyLettersNumbersAndSpaces(descriptionInput);
            //TaskHibernateDAO.getInstance().deleteTask(descriptionInput);
            response.setStatus(200);
         }catch(IllegalArgumentException | ToDoListException error){
        }*/
        }

        @Override
        public void getList(HttpServletRequest request, HttpServletResponse response) throws  ToDoListException{
            try {
                String listIDInput = request.getParameter("listID");
                TaskHibernateDAO.getInstance().getList(UtilityFunctions.IntegerParser(listIDInput));
                response.setStatus(200);
            }catch (ToDoListException error ){
                throw  new ToDoListException(error.getMessage(),error);
            }
        }

        @Override
        public void updateItem(HttpServletRequest request, HttpServletResponse response) throws ToDoListException {

        }
}









