package il.ac.hit.test;

import il.ac.hit.todolist.model.*;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class TestToDo {

    public static void main(String[] args) {

        System.out.println(new Date().getTime());

    }

    public static void printToDoList(int listID) {
        try {
            List<DBObject> list = TaskHibernateDAO.getInstance().getList(listID);
            System.out.println("There are " + list.size() + " item(s) in the list");
            Iterator iii = list.iterator();
            while (iii.hasNext()) System.out.println(iii.next());
        } catch (ToDoListException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
