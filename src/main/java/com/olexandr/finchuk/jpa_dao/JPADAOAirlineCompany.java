package com.olexandr.finchuk.jpa_dao;

import com.olexandr.finchuk.entities.AirlineCompany;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.ArrayList;

/**
 * Created by Olexandr Finchuk on 19.10.2016.
 */
@Stateless
public class JPADAOAirlineCompany extends JPADataAccessObject<AirlineCompany> {

    public JPADAOAirlineCompany() {

    }
    @Override
    public ArrayList<AirlineCompany> getObjectsByCondition(String condition){

        Query query =manager.createQuery("SELECT a from AirlineCompany a WHERE "+ condition);
        return (ArrayList<AirlineCompany>) query.getResultList();
    }


    @Override
    public AirlineCompany getObjectById(int id){

        return manager.find(AirlineCompany.class,id);
    }



    @Override
    public void deleteObjectById(int id){

        Query q = manager.createQuery("DELETE FROM AirlineCompany a WHERE a.companyId= :id");
        q.setParameter("id",id);
        q.executeUpdate();

    }

    @Override
    public void deleteAll(){

        manager.createQuery("DELETE FROM AirlineCompany a").executeUpdate();

    }
    @Override
    public ArrayList<AirlineCompany> getAllObjects() {
        return (ArrayList<AirlineCompany>) manager.createQuery("SELECT a from AirlineCompany a").getResultList();
    }
}
