package il.ac.hit.todolist.model;

import org.hibernate.*;

import java.io.Serializable;

public class TaskHibernateDAO extends APIToDoListDAO { // Singleton

    private static volatile TaskHibernateDAO uniqueInstance = null;
    private static final Object lock = new Object();

    private TaskHibernateDAO() {
        super();
    } // Constructor

    //Static method to get an instance of singleton
    public static TaskHibernateDAO getInstance() {
        if (uniqueInstance == null) {
            synchronized (lock) {
                if (uniqueInstance == null)
                    uniqueInstance = new TaskHibernateDAO();
            }
        }
        return uniqueInstance;
    }

    @Override
    protected String getTableName() {
        return "Task";
    }

    @Override
    protected Query queryToFetchTheList(int listID, Session hibernateSession) {
        Query query = hibernateSession.createQuery("FROM Task WHERE listID=:listID");
        query.setParameter("listID", listID);
        return query;
    }

    @Override
    public DBObject retrieveSingleItem(Serializable uniqueParameter, Session hibernateSession) { //Get Task instance
        Task task = (Task) hibernateSession.get(Task.class, uniqueParameter);
        return task;
    }

}
