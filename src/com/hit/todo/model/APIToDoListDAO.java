package com.hit.todo.model;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import java.util.List;

public abstract class APIToDoListDAO {

    private SessionFactory factory = null;


    protected APIToDoListDAO() { // Constructor
        this.factory = new AnnotationConfiguration().configure().buildSessionFactory();
    }


    public final boolean addItem(DBObject item) throws ToDoListException {
        boolean success = false;
        Session hibernateSession = null;
        try {
            hibernateSession = this.factory.openSession();
            hibernateSession.beginTransaction();
            if (!ifItemIsInDB(item.getUniqueParameter(), hibernateSession)) { // there isn't a task with the same description OR DBObject is an instance of User
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
                if (hibernateSession != null) { hibernateSession.close();}
            } catch (HibernateException e) {
                throw new ToDoListException(e.getMessage(), e);
            }
        }
    }
    public final boolean deleteItem(String uniqueParameter) throws ToDoListException{
        boolean success = false;
        Session hibernateSession = null;
        try {
            hibernateSession = this.factory.openSession();
            hibernateSession.beginTransaction();
            DBObject item = (DBObject)hibernateSession.get(DBObject.class, uniqueParameter);

            hibernateSession.delete(item);
            hibernateSession.getTransaction().commit();
            success = true;

            return success;
        } catch (HibernateException e) {
            if (hibernateSession.getTransaction() != null)
                try {
                    hibernateSession.getTransaction().rollback();
                } catch (HibernateException ex) { throw new ToDoListException(ex.getMessage(), ex); }
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

    public SessionFactory getFactory() {
        return factory;
    }

    public final List<DBObject> getList(int listID) throws ToDoListException{
        Session hibernateSession = null;
        try {
            hibernateSession = this.factory.openSession();
            hibernateSession.beginTransaction();
            List<DBObject> tasks = QueryToFetchTheList(listID,hibernateSession).list();
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


    protected boolean ifItemIsInDB(String uniqueParameter, Session hibernateSession) {
        boolean exists = true;

        List<DBObject> items = QueryToCheckIfAlreadyExists(uniqueParameter,hibernateSession).list();
            hibernateSession.getTransaction().commit();
            if (items.size() > 0) { // There is already a task with that description
                exists = false;
            }

        return exists;
    }

    abstract protected Query QueryToCheckIfAlreadyExists(String uniqueParameter,Session hibernateSession);
    abstract  protected Query QueryToFetchTheList(int listID,Session hibernateSession);//Check if it's the right type of Query



}
