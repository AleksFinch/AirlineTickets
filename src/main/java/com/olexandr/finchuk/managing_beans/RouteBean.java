package com.olexandr.finchuk.managing_beans;

import com.olexandr.finchuk.entities.Address;
import com.olexandr.finchuk.entities.AirlineCompany;
import com.olexandr.finchuk.entities.Route;
import com.olexandr.finchuk.jpa_dao.JPADAORoute;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Olexandr Finchuk on 19.11.2016.
 */
@ManagedBean
@SessionScoped
public class RouteBean {
    Collection<Route> routes = new ArrayList<Route>();
    Address townFrom;
    Address townTo;
    Time flightTime;
    AirlineCompany company;

    @EJB
    JPADAORoute jpadaoRoute;

    public Collection<Route> getRoutes() {
        return routes;
    }
    @PostConstruct
    public void updatecurrRoutes() {


        routes = jpadaoRoute.getAllObjects();

    }

    public void addNewRoute(){
        Route route = new Route();
        route.setAddressFrom(townFrom);
        route.setAddressTo(townTo);
        route.setCompany(company);
        route.setFlightTime(flightTime);
        jpadaoRoute.addObject(route);
        updatecurrRoutes();
        townFrom=null;
        townTo = null;
        company = null;
    }

    public Address getTownFrom() {
        return townFrom;
    }

    public void setTownFrom(Address townFrom) {
        this.townFrom = townFrom;
    }

    public Address getTownTo() {
        return townTo;
    }

    public void setTownTo(Address townTo) {
        this.townTo = townTo;
    }

    public Time getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(Time flightTime) {
        this.flightTime = flightTime;
    }

    public AirlineCompany getCompany() {
        return company;
    }

    public void setCompany(AirlineCompany company) {
        this.company = company;
    }
}
