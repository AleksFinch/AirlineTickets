package com.olexandr.finchuk.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by Olexandr Finchuk on 18.11.2016.
 */
@Entity
public class Flight implements Serializable {
    private int flightId;
    private Timestamp departureTime;
    private Route route;
    private Collection<Ticket> tickets;

    @Id
    @Column(name = "flight_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    @Basic
    @Column(name = "departure_time")
    public Timestamp getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Timestamp departureTime) {
        this.departureTime = departureTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Flight flight = (Flight) o;

        if (flightId != flight.flightId) return false;
        if (route != null ? !route.equals(flight.route) : flight.route != null)
            return false;
        if (departureTime != null ? !departureTime.equals(flight.departureTime) : flight.departureTime != null)
            return false;


        return true;
    }

    @Override
    public int hashCode() {
        int result = flightId;
        result = 31 * result + (departureTime != null ? departureTime.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "route", referencedColumnName = "route_id", nullable = false)
    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    @OneToMany(mappedBy = "flight")
    public Collection<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Collection<Ticket> tickets) {
        this.tickets = tickets;
    }
}
