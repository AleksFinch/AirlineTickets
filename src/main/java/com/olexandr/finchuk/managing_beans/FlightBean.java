package com.olexandr.finchuk.managing_beans;

import com.olexandr.finchuk.entities.*;
import com.olexandr.finchuk.jpa_dao.JPADAOFlight;
import com.olexandr.finchuk.jpa_dao.JPADAORoute;
import com.olexandr.finchuk.jpa_dao.JPADAOTicket;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Olexandr Finchuk on 19.11.2016.
 */
@ManagedBean
@SessionScoped
public class FlightBean {
    Collection<Flight> flights = new ArrayList<>();

    Timestamp departureTime;
    Route route;
    double priceForFirstClass;
    double priceForSecondClass;
    double priceForSecondClassNearWindow;

    @EJB
    JPADAOFlight jpadaoFlight;
    @EJB
    JPADAOTicket jpadaoTicket;


    public Collection<Flight> getFlights() {
        return flights;
    }

    @PostConstruct
    public void updatecurrFlights() {


        flights = jpadaoFlight.getAllObjects();

    }

    public void addNewFlight() {
        Flight flight = new Flight();
        flight.setDepartureTime(departureTime);
        flight.setRoute(route);
        ArrayList<Ticket> tickets= new ArrayList<>();

        flight.setTickets(tickets);

        jpadaoFlight.addObject(flight);

        for (int i = 1; i <= 4; i++) {
            Ticket t = new Ticket();
            t.setFlight(flight);
            t.setPlaceNumber(i);
            t.setPrice(priceForFirstClass);

            jpadaoTicket.addObject(t);
        }
        for (int i = 5; i <= 12; i++) {
            Ticket t = new Ticket();
            t.setFlight(flight);
            t.setPlaceNumber(i);
            t.setPrice(priceForSecondClassNearWindow);
            flight.getTickets().add(t);

            jpadaoTicket.addObject(t);
        }
        for (int i = 13; i <=20 ; i++) {
            Ticket t = new Ticket();
            t.setFlight(flight);
            t.setPlaceNumber(i);
            t.setPrice(priceForSecondClass);

            jpadaoTicket.addObject(t);
        }
        for (int i = 21; i <= 28; i++) {
            Ticket t = new Ticket();
            t.setFlight(flight);
            t.setPlaceNumber(i);
            t.setPrice(priceForSecondClassNearWindow);

            jpadaoTicket.addObject(t);
        }
        updatecurrFlights();
        departureTime = null;
        route = null;
        double priceForFirstClass = 0;
        double priceForSecondClass = 0;
        double priceForSecondClassNearWindow = 0;

    }

    public Timestamp getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Timestamp departureTime) {
        this.departureTime = departureTime;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public double getPriceForFirstClass() {
        return priceForFirstClass;
    }

    public void setPriceForFirstClass(double priceForFirstClass) {
        this.priceForFirstClass = priceForFirstClass;
    }

    public double getPriceForSecondClass() {
        return priceForSecondClass;
    }

    public void setPriceForSecondClass(double priceForSecondClass) {
        this.priceForSecondClass = priceForSecondClass;
    }

    public double getPriceForSecondClassNearWindow() {
        return priceForSecondClassNearWindow;
    }

    public void setPriceForSecondClassNearWindow(double priceForSecondClassNearWindow) {
        this.priceForSecondClassNearWindow = priceForSecondClassNearWindow;
    }

    public int getOpenTickets(Flight flight) {
        int openTic = 0;
        for (Ticket ticket :
                flight.getTickets()) {
            if (ticket.getOwner() == null)
                openTic++;

        }
        return openTic;
    }
}
