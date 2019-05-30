package il.ac.hit.todolist.model;

import org.hibernate.Query;
import org.hibernate.Session;

import java.io.Serializable;

public class UserHibernateDAO extends APIToDoListDAO {

    private static UserHibernateDAO uniqueInstance;

    private UserHibernateDAO() {
        super();
    }

    public static UserHibernateDAO getInstance() {
        if (uniqueInstance == null)
            uniqueInstance = new UserHibernateDAO();
        return uniqueInstance;
    }

    @Override
    public DBObject retrieveSingleItem(Serializable uniqueParameter, Session hibernateSession) {
        User user = (User) hibernateSession.get(User.class, uniqueParameter);
        return user;
    }
}
