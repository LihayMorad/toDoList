package il.ac.hit.todolist.model;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;

//Abstract class,including generic implementation of addition- ,deletion-, update field- and retrieve list methods
public abstract class APIToDoListDAO implements IToDoListDAO {

    //private static SessionFactory factory = new AnnotationConfiguration().configure().buildSessionFactory();
    private ConnectionPool sessionManager;
    //instead of factory

    protected APIToDoListDAO() { // Constructor
        sessionManager = ConnectionPool.getInstance();
    }


    public ConnectionPool getSessionManager() {
        return sessionManager;
    }

    @Override
    public final boolean addItem(DBObject item) throws ToDoListException {
        boolean success = false;
        Session hibernateSession = null;
        try {
            hibernateSession = sessionManager.getFactory().openSession();
            if (!itemAlreadyExists(item.getUniqueParameter(), hibernateSession)) {
                hibernateSession.beginTransaction();
                hibernateSession.save(item);
                hibernateSession.getTransaction().commit();
                success = true;
            } // else there is already an item we the same unique parameter
            return success;
        } catch (HibernateException error) {
            if (hibernateSession.getTransaction() != null)
                try {
                    hibernateSession.getTransaction().rollback();
                } catch (HibernateException exception) {
                    throw new ToDoListException(exception.getMessage(), exception);
                }
            throw new ToDoListException(error.getMessage(), error);
        } finally {
            try {
                if (hibernateSession != null) {
                    hibernateSession.close();
                }
            } catch (HibernateException e) {
            }
        }
    }

    @Override
    public final boolean deleteItem(Serializable uniqueParameter) throws ToDoListException {
        boolean success = false;
        Session hibernateSession = null;
        try {
            hibernateSession = sessionManager.getFactory().openSession();
            hibernateSession.beginTransaction();
            DBObject itemToDelete = retrieveSingleItem(uniqueParameter, hibernateSession);
            if (itemToDelete != null) {
                hibernateSession.delete(itemToDelete);
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
            }
        }
    }

    @Override
    public final List<DBObject> getList(int listID) throws ToDoListException {
        Session hibernateSession = null;
        try {
            hibernateSession = sessionManager.getFactory().openSession();
            hibernateSession.beginTransaction();
            List<DBObject> tasks = queryToFetchTheList(listID, hibernateSession).list();
            hibernateSession.getTransaction().commit();
            return tasks;
        } catch (HibernateException e) {
            if (hibernateSession.getTransaction() != null)
                try {
                    hibernateSession.getTransaction().rollback();
                } catch (HibernateException ex) {
                    throw new ToDoListException(e.getMessage() + ", " + ex.getMessage(), ex);
                }
            throw new ToDoListException(e.getMessage(), e);
        } finally {
            try {
                if (hibernateSession != null) hibernateSession.close();
            } catch (HibernateException e) {
            }
        }
    }

    public boolean itemAlreadyExists(Serializable uniqueParameter, Session hibernateSession) {
        boolean exists = false;
        try {
            exists = retrieveSingleItem(uniqueParameter, hibernateSession) != null;
        } catch (HibernateException error) {
            exists = false;
        }
        return exists;
    }

    protected Query queryToFetchTheList(int listID, Session hibernateSession) { // overrided in TaskHibernateDAO
        throw new IllegalArgumentException("Get list is not supported!");
    }

    public abstract DBObject retrieveSingleItem(Serializable uniqueParameter, Session hibernateSession);

    protected abstract String getTableName();

    public final DBObject requestForSingleItem(Serializable uniqueParameter) throws ToDoListException {

        Session hibernateSession = null;
        try {
            hibernateSession = sessionManager.getFactory().openSession();
            return retrieveSingleItem(uniqueParameter, hibernateSession);
        } catch (HibernateException error) {
            throw new ToDoListException(error.getMessage(), error);
        } finally {
            try {
                if (hibernateSession != null) hibernateSession.close();
            } catch (HibernateException error) {
                throw new ToDoListException(error.getMessage(), error);
            }
        }
    }

    //Generic method that enables to update either object attribute you want. Obviously it might be overridden in subclasses, if necessary
    public boolean updateColumnValue(String columnName, Serializable newValue, String primaryKey, Serializable keyValue) throws ToDoListException {

        boolean success = false;
        Session hibernateSession = null;
        try {
            hibernateSession = getSessionManager().getFactory().openSession();
            if (!columnName.equals(primaryKey) || !itemAlreadyExists(newValue, hibernateSession)) {
                hibernateSession.beginTransaction();
                success = updateQuery(columnName, newValue, primaryKey, keyValue, hibernateSession).executeUpdate() > 0;
                hibernateSession.getTransaction().commit();
            }
            return success;
        } catch (HibernateException error) {
            if (hibernateSession.getTransaction() != null)
                try {
                    hibernateSession.getTransaction().rollback();
                } catch (HibernateException ex) {
                    throw new ToDoListException(ex.getMessage(), ex);
                }
            throw new ToDoListException(error.getMessage(), error);
        } finally {
            try {
                if (hibernateSession != null) {
                    hibernateSession.close();
                }
            } catch (HibernateException e) {
            }
        }
    }

    protected Query updateQuery(String columnName, Serializable newValue, String primaryKey, Serializable keyValue, Session hibernateSession) {

        Query query = hibernateSession.createQuery(sqlUpdateRequest(columnName, primaryKey));
        query.setParameter("newValue", newValue);
        query.setParameter("keyValue", keyValue);
        return query;
    }

    protected String sqlUpdateRequest(String columnName, String primaryKey) {

        StringBuilder queryString = new StringBuilder(100);
        queryString.append("UPDATE ").append(getTableName()).append(" SET ").append(columnName)
                .append("=:newValue").append(" WHERE ").append(primaryKey).append("=:keyValue");

        return queryString.toString();
    }

}
