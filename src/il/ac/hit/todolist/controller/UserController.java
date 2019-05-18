package il.ac.hit.todolist.controller;

import il.ac.hit.todolist.model.ToDoListException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserController implements  IController{

    @Override
    public void addItem(HttpServletRequest request, HttpServletResponse response) throws ToDoListException {

      /*try {


          //UserHibernateDAO.getInstance().addItem();

      }catch (ToDoListException error){

      }*/

    }

    @Override
    public void deleteItem(HttpServletRequest request, HttpServletResponse response) throws ToDoListException{

    }

    @Override
    public void updateItem(HttpServletRequest request, HttpServletResponse response) throws ToDoListException{

    }

    @Override
    public void getList(HttpServletRequest request, HttpServletResponse response)throws ToDoListException {

    }
}
