package com.hit.todo.model;

import com.hit.todo.controller.HasPrimaryKey;
import org.hibernate.*;
import org.hibernate.cfg.AnnotationConfiguration;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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

    public boolean addItem(Object item) throws ToDoListException {
        boolean success = false;
        Session hibernateSession = null;
        try {
            hibernateSession = this.factory.openSession();
            hibernateSession.beginTransaction();
            hibernateSession.save(item);
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
    @Override
    public List<HasPrimaryKey> getList(int listID) throws ToDoListException {
        Session hibernateSession = null;
        try {
            hibernateSession = this.factory.openSession();
            hibernateSession.beginTransaction();
            Query query = hibernateSession.createQuery("FROM Task WHERE listID=:listID");
            query.setParameter("listID", listID);
            List<HasPrimaryKey> tasks= query.list();
            hibernateSession.getTransaction().commit();
            return  tasks;
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
