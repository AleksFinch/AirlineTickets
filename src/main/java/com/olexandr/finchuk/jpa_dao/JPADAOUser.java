package com.olexandr.finchuk.jpa_dao;

import com.olexandr.finchuk.entities.User;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import java.util.ArrayList;

/**
 * Created by Olexandr Finchuk on 19.10.2016.
 */
@Stateless
public class JPADAOUser extends JPADataAccessObject<User> {

    public JPADAOUser() {

    }
    @Override
    public ArrayList<User> getObjectsByCondition(String condition){

        Query query =manager.createQuery("SELECT u from User u WHERE "+ condition);
        return (ArrayList<User>) query.getResultList();
    }


    @Override
    public User getObjectById(int id){

        return manager.find(User.class,id);
    }



    @Override
    public void deleteObjectById(int id){

        Query q = manager.createQuery("DELETE FROM User u WHERE u.userId= :id");
        q.setParameter("id",id);
        q.executeUpdate();

    }

    @Override
    public void deleteAll(){

        manager.createQuery("DELETE FROM User u").executeUpdate();

    }
    @Override
    public ArrayList<User> getAllObjects() {
        return (ArrayList<User>) manager.createQuery("SELECT u from User u").getResultList();
    }
}
