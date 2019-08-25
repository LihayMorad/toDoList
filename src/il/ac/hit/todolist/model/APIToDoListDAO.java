package il.ac.hit.todolist.model;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;

/**
 * Abstract class, defining polymorphic family of singletons
 * where each one in turn contains a singleton member ( an instance of ConnectionPool)
 * Includes implementation of addition- ,deletion-, update field- and retrieve list methods.
 */
public abstract class APIToDoListDAO implements IToDoListDAO {

    private ConnectionPool sessionManager;

    protected APIToDoListDAO() { // Constructor
        sessionManager = ConnectionPool.getInstance();
    }

    public ConnectionPool getSessionManager() {
        return sessionManager;
    }

    /**
     * adds an item to the database
     * @param	item  Task or User object
     * @return  true if the item addition was successful, else return false
     */
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

    /**
     * deletes an item from the database
     * @param	uniqueParameter  Task or User object
     * @return  true if the item deletion was successful, else return false
     */
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

    /**
     * gets a list by provided ID from the database
     * list type depends on the class which called this function
     * @param	listID  list's ID
     * @return  returns list of items
     */
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


    /**
     * checks whether requested item exists in Database by calling "retrieveSingleItem" method.
     * Created in order to improve code readability.
     * @param	uniqueParameter   primary key to check if exists
     * @param   hibernateSession
     * @return  returns true when there is already an item with provided key, else returns false
     */
    public boolean itemAlreadyExists(Serializable uniqueParameter, Session hibernateSession) {
        boolean exists = false;
        try {
            exists = retrieveSingleItem(uniqueParameter, hibernateSession) != null;
        } catch (HibernateException error) {
            exists = false;
        }
        return exists;
    }

    protected Query queryToFetchTheList(int listID, Session hibernateSession) { // can be overridden in subclasses if necessary
        throw new IllegalArgumentException("Get list is not supported!");
    }

    /**
     * Generic method for getting a single object from DB.
     * Abstract declaration enables to postpone its implementation
     * and thus not to change the code of the methods where it's used.
     * @return  an item with the provided primary key
     */
    public abstract DBObject retrieveSingleItem(Serializable uniqueParameter, Session hibernateSession);

    protected abstract String getTableName();

    /**
     * Wrapper for more convenient way to call "retrieveSingleItem" from the outside
     * (no need to open the session in client code).
     * and thus not to change the code of the methods where it's used.
     * @param	uniqueParameter   primary key of an item
     * @return  an item with the provided primary key
     */
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

    /**
     * Unlike the rest of IToDoListDAO methods, this method CAN be overridden in subclasses
     * ( for instance all the attributes of Task can be updated
      * while for password in User it's better to write separate method resetPassword
     * @param	columnName
     * @param	newValue
     * @param	primaryKey
     * @param	keyValue
     * @return  true if update was successful
     */
    @Override
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
