package com.hit.todo.Model;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.TransactionException;

import java.util.Iterator;
import java.util.List;

public class HibernateToDoListDAO implements IToDoListDAO { // Singleton

    private static HibernateToDoListDAO hibernateToDoListDAO = null;

    public static HibernateToDoListDAO getInstance() {
        if (hibernateToDoListDAO == null) {
            hibernateToDoListDAO = new HibernateToDoListDAO();
        }
        return hibernateToDoListDAO;
    }

    public boolean addTask(Session hibernateSession, Task task) throws ToDoListException {
        boolean success = false;
        System.out.println(task);

        try {
            hibernateSession.beginTransaction();
            hibernateSession.save(task);
            hibernateSession.getTransaction().commit();
            success = true;
        } catch (HibernateException e) {
            throw new ToDoListException(e.getMessage(), e.getCause());
        } finally {
            try {
                hibernateSession.close();
            } catch (HibernateException e) {
                throw new ToDoListException(e.getMessage(), e.getCause());
            }
            return success;
        }
    }

    public boolean deleteTask(Session hibernateSession, int taskID) throws ToDoListException {
        boolean success = false;
        try {
            hibernateSession.beginTransaction();
            Task task = (Task) hibernateSession.get(Task.class, taskID);
            hibernateSession.delete(task);
            hibernateSession.getTransaction().commit();
            success = true;
        } catch (HibernateException e) {
            throw new ToDoListException(e.getMessage(), e.getCause());
        } finally {
            try {
                hibernateSession.close();
            } catch (HibernateException e) {
                throw new ToDoListException(e.getMessage(), e.getCause());
            }
            return success;
        }
    }

    public boolean updateTaskStatus(Session hibernateSession, int taskID, boolean newStatus) throws ToDoListException {
        boolean success = false;
        try {
            hibernateSession.beginTransaction();
            Query query = hibernateSession.createQuery("update Task set status=:newStatus where taskID=:taskID");
            query.setParameter("newStatus", newStatus);
            query.setParameter("taskID", taskID);
            int updatedCount = query.executeUpdate();
            success = updatedCount > 0;
        } catch (HibernateException e) {
            throw new ToDoListException(e.getMessage(), e.getCause());
        } finally {
            try {
                hibernateSession.close();
                return success;
            } catch (HibernateException e) {
                throw new ToDoListException(e.getMessage(), e.getCause());
            }
        }
    }

    public List<Task> getList(Session hibernateSession, int listID) throws ToDoListException {
        try {
            List<Task> list = hibernateSession.createQuery("from Task ").list();
            System.out.println("There are " + list.size() + " item(s) in the list");
            Iterator i = list.iterator();
            while (i.hasNext()) {
                System.out.println(i.next());
            }
            return list;
        } catch (HibernateException e) {
            throw new ToDoListException(e.getMessage(), e.getCause());
        } finally {
            try {
                hibernateSession.close();
            } catch (HibernateException e) {
                throw new ToDoListException(e.getMessage(), e.getCause());
            }
        }
    }

}
