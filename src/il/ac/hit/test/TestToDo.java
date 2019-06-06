package il.ac.hit.test;

import il.ac.hit.todolist.model.*;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class TestToDo {

    public static void main(String[] args) {

        System.out.println("=== TESTS START === " + new Date().getTime());

        /* Task tests */
//        addTaskTest();
        updateTaskDetailTest();
        updateUserDetailTest();
//        deleteTaskTest();
        //printToDoList(55);

        /* User tests */
//        addUserTest();
//        deleteUserTest();

        System.out.println("=== TESTS DONE === " + new Date().getTime());
    }

    public static void addUserTest() {
        try {
            User u1 = new User("userr1", "mypass1", 1);
            User u2 = new User("userr2", "mypass2", 2);
            User u3 = new User("userr3", "mypass3", 3);
            UserHibernateDAO.getInstance().addItem(u1);
            UserHibernateDAO.getInstance().addItem(u2);
            UserHibernateDAO.getInstance().addItem(u3);
        } catch (ToDoListException e) {
            e.printStackTrace();
        }
    }

    public static void addTaskTest() {
        try {
            int irrelevant = 0;
            Task t1 = new Task(irrelevant, 55, "this is 55", false, "2020");
            Task t2 = new Task(irrelevant, 55, "this is 55", true, "2099");
            Task t3 = new Task(irrelevant, 55, "this is 55", true, "1990");
            TaskHibernateDAO.getInstance().addItem(t1);
            TaskHibernateDAO.getInstance().addItem(t2);
            TaskHibernateDAO.getInstance().addItem(t3);
        } catch (ToDoListException e) {
            e.printStackTrace();
        }
    }

    public static void deleteTaskTest() {
        try {
            int irrelevant = 0;
            Task t1 = new Task(irrelevant, 55, "this is 55", false, "2020");
            TaskHibernateDAO.getInstance().addItem(t1);
            TaskHibernateDAO.getInstance().deleteItem(8977);
        } catch (ToDoListException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public static void deleteUserTest() {
        try {
            User u1 = new User("userr1", "mypass1", 1);
            UserHibernateDAO.getInstance().addItem(u1);
            UserHibernateDAO.getInstance().deleteItem("userr1");
        } catch (ToDoListException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public static void updateTaskDetailTest() {
        try {
            int irrelevant = 0;
            Task t1 = new Task(irrelevant, 55, "this is 55", false, "2020-02-04");
            //TaskHibernateDAO.getInstance().addItem(t1);
            System.out.println("primary key is busy test starts------\nUpdateSucessful:");
            System.out.println(TaskHibernateDAO.getInstance().updateColumnValue("taskID",2,"taskID",4));
            System.out.println("UPDATE TASK STATUS - TEST STARTED--------\nUpdateSucessful:");
            System.out.println(TaskHibernateDAO.getInstance().updateColumnValue("status",true,"taskID",2));
            System.out.println("UPDATE TASK DESCRIPTION - TEST STARTED--------\nUpdateSucessful:");
            System.out.println(TaskHibernateDAO.getInstance().updateColumnValue("description","Buy oranges","taskID",3));
            System.out.println("UPDATE TASK DEADLINE - TEST STARTED--------\nUpdateSucessful:");
            System.out.println(TaskHibernateDAO.getInstance().updateColumnValue("deadline","2014-02-05","taskID",4));
        } catch (ToDoListException e) {
            e.printStackTrace();
        }
    }

    public static void updateUserDetailTest(){
        try {

            System.out.println("Username is busy test starts------\nUpdateSuccessful:");
            System.out.println(UserHibernateDAO.getInstance().updateColumnValue("username","lihay14","username","lihay12"));
            System.out.println("Username is not busy - TEST STARTED--------\nUpdateSuccessful:");
            System.out.println(UserHibernateDAO.getInstance().updateColumnValue("username","vova","username","pavel"));

        } catch (ToDoListException e) {
            e.printStackTrace();
        }

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
