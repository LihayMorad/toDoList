package com.hit.todo.model;

import org.hibernate.*;


import java.util.List;

public class HibernateToDoListDAO extends APIToDoListDAO { // Singleton

    private static HibernateToDoListDAO hibernateToDoListDAO = null;


    private HibernateToDoListDAO() { // Constructor
        super();
    }


    public static HibernateToDoListDAO getInstance() {
        if (hibernateToDoListDAO == null)
            hibernateToDoListDAO = new HibernateToDoListDAO();
        return hibernateToDoListDAO;
    }


    public boolean updateStatus(int itemID, boolean newStatus) throws ToDoListException {
        boolean success = false;
        Session hibernateSession = null;
        try {
            hibernateSession = this.getFactory().openSession();
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
    protected Query QueryToCheckIfAlreadyExists(String uniqueParameter,Session hibernateSession){
        Query query =hibernateSession.createQuery("FROM Task WHERE description=:uniqueParameter");
        query.setParameter("uniqueParameter", uniqueParameter);
        return query;
    }


    @Override
    protected Query  QueryToFetchTheList(int listID,Session hibernateSession){
         Query query=hibernateSession.createQuery("FROM Task WHERE listID=:listID");
         query.setParameter("listID", listID);
        return query;
    }

}
