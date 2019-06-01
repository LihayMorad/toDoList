package il.ac.hit.todolist.model;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import java.io.Serializable;
import java.util.List;

//Abstract class,including generic implementation of addition- ,deletion- and retrieve list methods
public abstract class APIToDoListDAO implements IToDoListDAO {

    private static SessionFactory factory = new AnnotationConfiguration().configure().buildSessionFactory();

    protected APIToDoListDAO() { // Constructor
    }

    public SessionFactory getFactory() {
        return factory;
    }

    @Override
    public final boolean addItem(DBObject item) throws ToDoListException {
        boolean success = false;
        Session hibernateSession = null;
        try {
            hibernateSession = this.factory.openSession();
            if (!isItemAlreadyExists(item.getUniqueParameter(), hibernateSession)) {
                hibernateSession.beginTransaction();
                hibernateSession.save(item);
                hibernateSession.getTransaction().commit();
                success = true;
            } // else there is already an item we the same unique parameter
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
    public final boolean deleteItem(Serializable uniqueParameter) throws ToDoListException {
        boolean success = false;
        Session hibernateSession = null;
        try {
            hibernateSession = this.factory.openSession();
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
                throw new ToDoListException(e.getMessage(), e);
            }
        }
    }

    @Override
    public final List<DBObject> getList(int listID) throws ToDoListException {
        Session hibernateSession = null;
        try {
            hibernateSession = this.factory.openSession();
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
                throw new ToDoListException(e.getMessage(), e);
            }
        }
    }

    public boolean isItemAlreadyExists(Serializable uniqueParameter, Session hibernateSession) {
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

    public final DBObject requestForSingleItem(Serializable uniqueParameter) throws ToDoListException {

        Session hibernateSession = null;
        try {
            hibernateSession = this.factory.openSession();
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


}
