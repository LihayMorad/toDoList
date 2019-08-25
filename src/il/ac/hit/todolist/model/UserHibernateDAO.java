package il.ac.hit.todolist.model;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;

public class UserHibernateDAO extends APIToDoListDAO {

    private static volatile UserHibernateDAO uniqueInstance;
    private static final Object lock = new Object();

    private UserHibernateDAO() {
        super();
    }

    //Static method to get an instance of singleton
    public static UserHibernateDAO getInstance() {
        if (uniqueInstance == null) {
            synchronized (lock) {
                if (uniqueInstance == null)
                    uniqueInstance = new UserHibernateDAO();
            }
        }
        return uniqueInstance;
    }

    @Override
    protected String getTableName(){
        return "User";
    }


    // Method for user authentication
    public boolean validateUser(User loggingIn, String providedPassword) throws ToDoListException {
        boolean isValid = false;
        Session hibernateSession = null;
        try {
            hibernateSession = getSessionManager().getFactory().openSession();
            hibernateSession.beginTransaction();
            String username = loggingIn.getUsername();
            Query query = hibernateSession.createQuery("select username from User WHERE username=:username AND password=:providedPassword");
            query.setParameter("username", username);
            query.setParameter("providedPassword", providedPassword);
            List<User> list = query.list();
            hibernateSession.getTransaction().commit();
            isValid = list.size() > 0;
            return isValid;
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

    @Override   //Get User instance
    public DBObject retrieveSingleItem(Serializable uniqueParameter, Session hibernateSession) throws HibernateException {
        User user = (User) hibernateSession.get(User.class, uniqueParameter);
        return user;
    }
}
