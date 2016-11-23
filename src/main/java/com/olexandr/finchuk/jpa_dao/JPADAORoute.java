package com.olexandr.finchuk.jpa_dao;

import com.olexandr.finchuk.entities.Route;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.ArrayList;

/**
 * Created by Olexandr Finchuk on 19.10.2016.
 */
@Stateless
public class JPADAORoute extends JPADataAccessObject<Route> {

    public JPADAORoute() {

    }
    @Override
    public ArrayList<Route> getObjectsByCondition(String condition){

        Query query =manager.createQuery("SELECT r from Route r WHERE "+ condition);
        return (ArrayList<Route>) query.getResultList();
    }


    @Override
    public Route getObjectById(int id){

        return manager.find(Route.class,id);
    }



    @Override
    public void deleteObjectById(int id){

        Query q = manager.createQuery("DELETE FROM Route r WHERE r.routeId= :id");
        q.setParameter("id",id);
        q.executeUpdate();

    }

    @Override
    public void deleteAll(){

        manager.createQuery("DELETE FROM Route r").executeUpdate();

    }
    @Override
    public ArrayList<Route> getAllObjects() {
        return (ArrayList<Route>) manager.createQuery("SELECT r from Route r").getResultList();
    }
}
