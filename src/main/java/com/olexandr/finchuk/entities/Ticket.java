package com.olexandr.finchuk.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Olexandr Finchuk on 18.11.2016.
 */
@Entity
public class Ticket implements Serializable {
    private int ticketId;
    private int placeNumber;
    private double price;
    private Flight flight;
    private User owner;

    @Id
    @Column(name = "ticket_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }


    @Basic
    @Column(name = "place_number")
    public int getPlaceNumber() {
        return placeNumber;
    }

    public void setPlaceNumber(int placeNumber) {
        this.placeNumber = placeNumber;
    }

    @Basic
    @Column(name = "price")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        if (ticketId != ticket.ticketId) return false;
        if (placeNumber != ticket.placeNumber) return false;
        if (Double.compare(ticket.price, price) != 0) return false;
        if (flight != null ? !flight.equals(ticket.flight) : ticket.owner != null) return false;
        if (owner != null ? !owner.equals(ticket.owner) : ticket.owner != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = ticketId;
        result = 31 * result + placeNumber;
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
       return result;
    }

    @ManyToOne
    @JoinColumn(name = "flight", referencedColumnName = "flight_id", nullable = false)
    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "owner", referencedColumnName = "user_id", nullable = false)
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }


}
