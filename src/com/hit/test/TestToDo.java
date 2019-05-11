package com.hit.test;

import com.hit.todo.model.*;


import java.util.Iterator;
import java.util.List;

public class TestToDo {

    public static void main(String[] args) {
        HibernateToDoListDAO hibernateToDoListDAO = HibernateToDoListDAO.getInstance();
        try {
//            printToDoList(2);

            int irrelevant = 0; // id is irrelevant because of auto increment ID
            Task t1 = new Task(irrelevant, 1, "text1", false);
            hibernateToDoListDAO.addItem(t1);
//            System.out.println("Delete success: " + hibernateToDoListDAO.deleteTask(2));

//            System.out.println("Updated: " + hibernateToDoListDAO.updateTaskStatus(5, true));
//            printToDoList(2);

        } catch (ToDoListException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    /*public static void printToDoList(int listID) {
        try {

            List<Task> list = HibernateToDoListDAO.getInstance().getList(listID);
            System.out.println("There are " + list.size() + " item(s) in the list");
            Iterator iii = list.iterator();
            while (iii.hasNext()) System.out.println(iii.next());
        } catch (ToDoListException ex) {
            System.out.println(ex.getMessage());
        }
    }*/
}
