package il.ac.hit.test;
import il.ac.hit.todolist.model.*;

import java.util.Iterator;
import java.util.List;


public class TestToDo {

    public static  void main (String[] args){

        TaskHibernateDAO hibernateToDoListDAO = TaskHibernateDAO.getInstance();
        UserHibernateDAO dao=UserHibernateDAO.getInstance();
        try {
//            printToDoList(2);

            int irrelevant = 0; // id is irrelevant because of auto increment ID
            //Router router=new Router();





            Task t1 = new Task(irrelevant, 1, "text1", false);
            Task t2= new Task(irrelevant,1,"text22",false);
            User u1= new User ("lihay","33333",3);
            // hibernateToDoListDAO.deleteItem(3);
            //dao.addItem(u1);
            //hibernateToDoListDAO.addItem(t1);
            //hibernateToDoListDAO.addItem(t2);
            List<DBObject> list = TaskHibernateDAO.getInstance().getList(1);
            System.out.println(list.size());

//            System.out.println("Add user \"alex\": " + hibernateToDoListDAO.addItem(u1));

//            System.out.println("Deleted: " + hibernateToDoListDAO.deleteItem("vasya"));
//            System.out.println("Updated: " + hibernateToDoListDAO.updateStatus(5, true));
//            printToDoList(2);

        } catch (ToDoListException ex) {
            System.out.println("Exception message: " + ex.getMessage());
//            System.out.println("Exception cause cause message: " + ex.getCause().getCause().getMessage());
            ex.printStackTrace();
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
