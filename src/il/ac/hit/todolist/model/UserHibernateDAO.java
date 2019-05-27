package il.ac.hit.todolist.model;

import org.hibernate.Query;
import org.hibernate.Session;

import java.io.Serializable;

public class UserHibernateDAO extends APIToDoListDAO {

    private static UserHibernateDAO uniqueInstance;

    private UserHibernateDAO (){
        super();
    }

    public static UserHibernateDAO  getInstance(){
        if (uniqueInstance == null)
            uniqueInstance = new UserHibernateDAO();
        return uniqueInstance;
    }



    @Override
    protected Query queryToCheckIfAlreadyExists(String uniqueParameter, Session hibernateSession){
        Query query =hibernateSession.createQuery("FROM User WHERE username=:uniqueParameter");
        query.setParameter("uniqueParameter", uniqueParameter);
        return query;
    }

    @Override
    protected Query queryToFetchTheList(int listID, Session hibernateSession){
        return TaskHibernateDAO.getInstance().queryToFetchTheList(listID,hibernateSession);
    }


    @Override
    protected DBObject retrieveSingleItem(Serializable uniqueParameter, Session hibernateSession) {
        User lamer = (User) hibernateSession.get(User.class, uniqueParameter);
        return  lamer;
    }
}
