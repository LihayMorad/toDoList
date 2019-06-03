package il.ac.hit.todolist.model;

import org.hibernate.*;

import java.io.Serializable;

public class TaskHibernateDAO extends APIToDoListDAO { // Singleton

    private static volatile TaskHibernateDAO uniqueInstance = null;
    private static final Object lock= new Object ();
    private TaskHibernateDAO() { // Constructor
        super();
    }

    public static TaskHibernateDAO getInstance() {
        if(uniqueInstance ==null) {
            synchronized (lock) {
                if (uniqueInstance == null)
                    uniqueInstance = new TaskHibernateDAO();
            }
        }
        return uniqueInstance;
    }

    public boolean updateStatus(int itemID, int listID, boolean newStatus) throws ToDoListException {
        boolean success = false;
        Session hibernateSession = null;
        try {
            hibernateSession = getSessionManager().getFactory().openSession();
            hibernateSession.beginTransaction();
            Query query = hibernateSession.createQuery("UPDATE Task SET status=:newStatus WHERE taskID=:taskID AND listID=:listID");
            query.setParameter("newStatus", newStatus);
            query.setParameter("taskID", itemID);
            query.setParameter("listID", listID);
            int updatedCount = query.executeUpdate();
            hibernateSession.getTransaction().commit();
            success = updatedCount > 0;
            return success;
        } catch (HibernateException e) {
            if (hibernateSession.getTransaction() != null)
                try {
                    hibernateSession.getTransaction().rollback();
                } catch (HibernateException ex) {
                    throw new ToDoListException(ex.getMessage(), ex);
                }
            throw new ToDoListException(e.getMessage(), e);
        } finally {
            try {
                if (hibernateSession != null) {
                    hibernateSession.close();
                }
            } catch (HibernateException e) {
                throw new ToDoListException(e.getMessage(), e);
            }
        }
    }

    @Override
    protected Query queryToFetchTheList(int listID, Session hibernateSession) {
        Query query = hibernateSession.createQuery("FROM Task WHERE listID=:listID");
        query.setParameter("listID", listID);
        return query;
    }

    @Override
    public DBObject retrieveSingleItem(Serializable uniqueParameter, Session hibernateSession) {
        Task task = (Task) hibernateSession.get(Task.class, uniqueParameter);
        return task;
    }

}
