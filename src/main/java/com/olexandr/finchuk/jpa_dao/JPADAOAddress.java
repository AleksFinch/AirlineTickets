package com.olexandr.finchuk.jpa_dao;

import com.olexandr.finchuk.entities.Address;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.ArrayList;

/**
 * Created by Olexandr Finchuk on 19.10.2016.
 */
@Stateless
public class JPADAOAddress extends JPADataAccessObject<Address> {

    public JPADAOAddress() {

    }
    @Override
    public ArrayList<Address> getObjectsByCondition(String condition){

        Query query =manager.createQuery("SELECT a from Address a WHERE "+ condition);
        return (ArrayList<Address>) query.getResultList();
    }


    @Override
    public Address getObjectById(int id){

        return manager.find(Address.class,id);
    }



    @Override
    public void deleteObjectById(int id){

        Query q = manager.createQuery("DELETE FROM Address a WHERE a.addressId= :id");
        q.setParameter("id",id);
        q.executeUpdate();

    }

    @Override
    public void deleteAll(){

        manager.createQuery("DELETE FROM Address a").executeUpdate();

    }
    @Override
    public ArrayList<Address> getAllObjects() {
        return (ArrayList<Address>) manager.createQuery("SELECT a from Address a").getResultList();
    }
}
