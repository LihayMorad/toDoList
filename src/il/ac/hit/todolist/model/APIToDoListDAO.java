package il.ac.hit.todolist.model;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import java.io.Serializable;
import java.util.List;


//Abstract class,including generic implementation of addition- ,deletion- and retrieve list methods
public abstract class APIToDoListDAO implements IToDoListDAO{

    private SessionFactory factory = null;


    protected APIToDoListDAO() { // Constructor
        this.factory = new AnnotationConfiguration().configure().buildSessionFactory();
    }

   @Override
    public final boolean addItem(DBObject item) throws ToDoListException {
        boolean success = false;
        Session hibernateSession = null;
        try {
            hibernateSession = this.factory.openSession();

            if (!ifItemIsInDB(item.getUniqueParameter(), hibernateSession)) { // there isn't a task with the same description OR DBObject is an instance of User
                hibernateSession.beginTransaction();
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
    @Override
    public final boolean deleteItem(Serializable uniqueParameter) throws ToDoListException{
        boolean success = false;
        Session hibernateSession = null;
        try {
            hibernateSession = this.factory.openSession();
            hibernateSession.beginTransaction();
            //DBObject item = (DBObject)hibernateSession.get(DBObject.class, uniqueParameter);
            //No need to check if item exists, an exception will be thrown in case of deletion of one that doesn't
            //Then worth to think if this method might be void ( alternatively ,message can be sent from jsp file in case of success
            hibernateSession.delete(retrieveSingleItem(uniqueParameter,hibernateSession));
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

    @Override
    public final List<DBObject> getList(int listID) throws ToDoListException{
        Session hibernateSession = null;
        try {
            hibernateSession = this.factory.openSession();
            hibernateSession.beginTransaction();
            List<DBObject> tasks = queryToFetchTheList(listID,hibernateSession).list();
            hibernateSession.getTransaction().commit();
            return tasks;
        } catch (HibernateException e) {
            if (hibernateSession.getTransaction() != null)
                try {
                    hibernateSession.getTransaction().rollback();
                } catch (HibernateException ex) { throw new ToDoListException(ex.getMessage(), ex); }
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
        boolean exists = false;
        hibernateSession.beginTransaction();
        List<DBObject> items = queryToCheckIfAlreadyExists(uniqueParameter,hibernateSession).list();
            hibernateSession.getTransaction().commit();
            if (items.size() > 0) { // There is already a task with that description
                exists = true;
            }

        return exists;
    }

    abstract protected Query queryToCheckIfAlreadyExists(String uniqueParameter, Session hibernateSession);
    abstract  protected Query queryToFetchTheList(int listID, Session hibernateSession);
    abstract  protected  DBObject retrieveSingleItem(Serializable uniqueParameter, Session hibernateSession);


}