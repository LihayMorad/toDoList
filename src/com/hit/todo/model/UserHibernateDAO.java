package com.hit.todo.model;

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
    protected Query QueryToCheckIfAlreadyExists(String uniqueParameter, Session hibernateSession){
        Query query =hibernateSession.createQuery("FROM User WHERE username=:uniqueParameter");
        query.setParameter("uniqueParameter", uniqueParameter);
        return query;
    }

    @Override
    protected Query  QueryToFetchTheList(int listID,Session hibernateSession){
        return TaskHibernateDAO.getInstance().QueryToFetchTheList(listID,hibernateSession);
    }


    @Override
    protected DBObject RetrieveSingleItem(Serializable uniqueParameter, Session hibernateSession) {
        User lamer = (User) hibernateSession.get(User.class, uniqueParameter);
        return  lamer;
    }
}
