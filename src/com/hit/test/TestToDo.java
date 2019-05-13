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

//            User u1 = new User("alex", "1111", 2);
//            System.out.println("Add user \"alex\": " + hibernateToDoListDAO.addItem(u1));

            System.out.println("Deleted: " + hibernateToDoListDAO.deleteItem(2));
            System.out.println("Updated: " + hibernateToDoListDAO.updateStatus(5, true));
            printToDoList(2);

        } catch (ToDoListException ex) {
            System.out.println("Exception message: " + ex.getMessage());
            System.out.println("Exception cause cause message: " + ex.getCause().getCause().getMessage());
//            ex.printStackTrace();
        }
    }

    public static void printToDoList(int listID) {
        try {

            List<Task> list = HibernateToDoListDAO.getInstance().getList(listID);
            System.out.println("There are " + list.size() + " item(s) in the list");
            Iterator iii = list.iterator();
            while (iii.hasNext()) System.out.println(iii.next());
        } catch (ToDoListException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
