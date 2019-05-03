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

    public boolean addTask(Task task) throws ToDoListException {
        boolean success = false;
        Session hibernateSession = null;
        try {
            hibernateSession = this.factory.openSession();
            hibernateSession.beginTransaction();
            hibernateSession.save(task);
            hibernateSession.getTransaction().commit();
            success = true;
        } catch (HibernateException e) {
            if (hibernateSession.getTransaction() != null) {
                rollback(hibernateSession.getTransaction());
            }
            throw new ToDoListException(e.getMessage(), e.getCause());
        } finally {
            try {
                if (hibernateSession != null) hibernateSession.close();
            } catch (HibernateException e) {
                throw new ToDoListException(e.getMessage(), e.getCause());
            }
            return success;
        }
    }

    public boolean deleteTask(int taskID) throws ToDoListException {
        boolean success = false;
        Session hibernateSession = null;
        try {
            hibernateSession = this.factory.openSession();
            hibernateSession.beginTransaction();
            Task task = (Task) hibernateSession.get(Task.class, taskID);
            if (task != null) { // delete only if the task exists in database
                hibernateSession.delete(task);
                hibernateSession.getTransaction().commit();
                success = true;
            }
        } catch (HibernateException e) {
            if (hibernateSession.getTransaction() != null)
                rollback(hibernateSession.getTransaction());
            throw new ToDoListException(e.getMessage(), e.getCause());
        } finally {
            try {
                if (hibernateSession != null) hibernateSession.close();
            } catch (HibernateException e) {
                throw new ToDoListException(e.getMessage(), e.getCause());
            }
            return success;
        }
    }

    public boolean updateTaskStatus(int taskID, boolean newStatus) throws ToDoListException {
        boolean success = false;
        Session hibernateSession = null;
        try {
            hibernateSession = this.factory.openSession();
            hibernateSession.beginTransaction();
            Query query = hibernateSession.createQuery("UPDATE Task SET status=:newStatus WHERE taskID=:taskID");
            query.setParameter("newStatus", newStatus);
            query.setParameter("taskID", taskID);
            int updatedCount = query.executeUpdate();
            hibernateSession.getTransaction().commit();
            success = updatedCount > 0;
        } catch (HibernateException e) {
            if (hibernateSession.getTransaction() != null)
                rollback(hibernateSession.getTransaction());
            throw new ToDoListException(e.getMessage(), e.getCause());
        } finally {
            try {
                if (hibernateSession != null) hibernateSession.close();
                return success;
            } catch (HibernateException e) {
                throw new ToDoListException(e.getMessage(), e.getCause());
            }
        }
    }

    public List<Task> getList(int listID) throws ToDoListException {
        Session hibernateSession = null;
        try {
            hibernateSession = this.factory.openSession();
            hibernateSession.beginTransaction();
            Query query = hibernateSession.createQuery("FROM Task WHERE listID=:listID");
            query.setParameter("listID", listID);
            List<Task> list = query.list();
            hibernateSession.getTransaction().commit();
            return list;
        } catch (HibernateException e) {
            if (hibernateSession.getTransaction() != null)
                rollback(hibernateSession.getTransaction());
            throw new ToDoListException(e.getMessage(), e.getCause());
        } finally {
            try {
                if (hibernateSession != null) hibernateSession.close();
            } catch (HibernateException e) {
                throw new ToDoListException(e.getMessage(), e.getCause());
            }
        }
    }

    private void rollback(Transaction transaction) throws ToDoListException {
        try {
            transaction.rollback();
        } catch (HibernateException e) {
            throw new ToDoListException(e.getMessage(), e.getCause());
        }
    }

}
