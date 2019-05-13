package com.hit.todo.model;

import org.hibernate.*;
import org.hibernate.cfg.AnnotationConfiguration;

import java.util.List;

public class HibernateToDoListDAO implements IToDoListDAO { // Singleton

    private static HibernateToDoListDAO hibernateToDoListDAO = null;
    private SessionFactory factory = null;

    private HibernateToDoListDAO() { // Constructor
        this.factory = new AnnotationConfiguration().configure().buildSessionFactory();
    }

    public static HibernateToDoListDAO getInstance() {
        if (hibernateToDoListDAO == null)
            hibernateToDoListDAO = new HibernateToDoListDAO();
        return hibernateToDoListDAO;
    }

    public boolean addItem(DBObject item) throws ToDoListException { // add Task or User
        boolean success = false;
        Session hibernateSession = null;
        try {
            hibernateSession = this.factory.openSession();
            hibernateSession.beginTransaction();
            if (shouldInsertItem(item, hibernateSession)) { // there isn't a task with the same description OR DBObject is an instance of User
                hibernateSession.save(item);
                hibernateSession.getTransaction().commit();
                success = true;
            } // else there is already a task with the same description
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

    public boolean deleteItem(int itemID) throws ToDoListException {
        boolean success = false;
        Session hibernateSession = null;
        try {
            hibernateSession = this.factory.openSession();
            hibernateSession.beginTransaction();
            Task task = (Task) hibernateSession.get(Task.class, itemID);
            if (task != null) { // delete only if the task exists in database
                hibernateSession.delete(task);
                hibernateSession.getTransaction().commit();
                success = true;
            }
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

    public boolean updateStatus(int itemID, boolean newStatus) throws ToDoListException {
        boolean success = false;
        Session hibernateSession = null;
        try {
            hibernateSession = this.factory.openSession();
            hibernateSession.beginTransaction();
            Query query = hibernateSession.createQuery("UPDATE Task SET status=:newStatus WHERE taskID=:taskID");
            query.setParameter("newStatus", newStatus);
            query.setParameter("taskID", itemID);
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
    public List<Task> getList(int listID) throws ToDoListException {
        Session hibernateSession = null;
        try {
            hibernateSession = this.factory.openSession();
            hibernateSession.beginTransaction();
            Query query = hibernateSession.createQuery("FROM Task WHERE listID=:listID");
            query.setParameter("listID", listID);
            List<Task> tasks = query.list();
            hibernateSession.getTransaction().commit();
            return tasks;
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
                if (hibernateSession != null) hibernateSession.close();
            } catch (HibernateException e) {
                throw new ToDoListException(e.getMessage(), e);
            }
        }
    }

    boolean shouldInsertItem(DBObject item, Session hibernateSession) {
        boolean insert = true;
        if (item instanceof Task) {
            String desc = ((Task) item).getDescription();
            Query query = hibernateSession.createQuery("FROM Task WHERE description=:desc");
            query.setParameter("desc", desc);
            List<Task> tasks = query.list();
            hibernateSession.getTransaction().commit();
            if (tasks.size() > 0) { // There is already a task with that description
                insert = false;
            }
        }
        return insert;
    }
}
