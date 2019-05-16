package com.hit.todo.model;

import org.hibernate.Query;
import org.hibernate.Session;

public class UserDAO extends APIToDoListDAO {

    @Override
    protected Query QueryToCheckIfAlreadyExists(String uniqueParameter, Session hibernateSession){
        Query query =hibernateSession.createQuery("FROM User WHERE username=:uniqueParameter");
        query.setParameter("uniqueParameter", uniqueParameter);
        return query;
    }

    @Override
    protected Query  QueryToFetchTheList(int listID,Session hibernateSession){
        return HibernateToDoListDAO.getInstance().QueryToFetchTheList(listID,hibernateSession);
    }





}
