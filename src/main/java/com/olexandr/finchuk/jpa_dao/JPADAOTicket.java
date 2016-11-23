package com.olexandr.finchuk.jpa_dao;

import com.olexandr.finchuk.entities.Ticket;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.ArrayList;

/**
 * Created by Olexandr Finchuk on 19.10.2016.
 */
@Stateless
public class JPADAOTicket extends JPADataAccessObject<Ticket> {

    public JPADAOTicket() {

    }
    @Override
    public ArrayList<Ticket> getObjectsByCondition(String condition){

        Query query =manager.createQuery("SELECT t from Ticket t WHERE "+ condition);
        return (ArrayList<Ticket>) query.getResultList();
    }


    @Override
    public Ticket getObjectById(int id){

        return manager.find(Ticket.class,id);
    }



    @Override
    public void deleteObjectById(int id){

        Query q = manager.createQuery("DELETE FROM Ticket t WHERE t.ticketId= :id");
        q.setParameter("id",id);
        q.executeUpdate();

    }

    @Override
    public void deleteAll(){

        manager.createQuery("DELETE FROM Ticket t").executeUpdate();

    }
    @Override
    public ArrayList<Ticket> getAllObjects() {
        return (ArrayList<Ticket>) manager.createQuery("SELECT t from Ticket t").getResultList();
    }
}
