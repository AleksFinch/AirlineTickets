package com.olexandr.finchuk.jpa_dao;

import com.olexandr.finchuk.entities.Flight;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by Olexandr Finchuk on 19.10.2016.
 */
@Stateless
public class JPADAOFlight extends JPADataAccessObject<Flight> {

    public JPADAOFlight() {

    }
    @Override
    public ArrayList<Flight> getObjectsByCondition(String condition) {

        Query query = manager.createQuery(condition);

        return (ArrayList<Flight>) query.getResultList();
    }


    @Override
    public Flight getObjectById(int id) {

        return manager.find(Flight.class, id);
    }


    @Override
    public void deleteObjectById(int id) {

        Query q = manager.createQuery("DELETE FROM Flight f WHERE f.flightId= :id");
        q.setParameter("id", id);
        q.executeUpdate();

    }

    @Override
    public void deleteAll() {

        manager.createQuery("DELETE FROM Flight f").executeUpdate();

    }

    @Override
    public ArrayList<Flight> getAllObjects() {
        return (ArrayList<Flight>) manager.createQuery("SELECT f from Flight f").getResultList();
    }

    public ArrayList<Flight> getObjectsByParams(String townFrom, String townTo, Date date) {

        Timestamp timestampBefore = new Timestamp(date.getTime());
        Timestamp timeStampAfter = new Timestamp(timestampBefore.getTime() + 24L * 3600L * 1000L);

        Query query = manager.createQuery("SELECT DISTINCT f from Flight f " +
                "JOIN f.route r" +
                "  JOIN r.addressFrom a1" +
                "  JOIN r.addressTo a2" +
                "  JOIN f.tickets ts" +
                " WHERE (:timeBefore<f.departureTime)AND" +
                " (f.departureTime<:timeAfter) AND" +
                "(a1.town = :townFrom) AND " +
                "(a2.town = :townTo ) AND " +
                "ts.owner = null");

        query.setParameter("timeBefore",timestampBefore);
        query.setParameter("timeAfter", timeStampAfter);
        query.setParameter("townFrom",townFrom);
        query.setParameter("townTo", townTo);
        return (ArrayList<Flight>) query.getResultList();
    }
}
