package il.ac.hit.todolist.model;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.io.Serializable;

public class UserHibernateDAO extends APIToDoListDAO {

    private static volatile UserHibernateDAO uniqueInstance;
    private static final Object lock= new Object ();


    private UserHibernateDAO() {
        super();
    }

    public static UserHibernateDAO getInstance() {
        if(uniqueInstance ==null) {
            synchronized (lock) {
                if (uniqueInstance == null)
                    uniqueInstance = new UserHibernateDAO();
            }
        }
        return uniqueInstance;
    }

    @Override
    public DBObject retrieveSingleItem(Serializable uniqueParameter, Session hibernateSession) throws HibernateException {
        User user = (User) hibernateSession.get(User.class, uniqueParameter);
        return user;
    }
}
