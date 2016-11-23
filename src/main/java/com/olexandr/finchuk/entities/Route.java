package com.olexandr.finchuk.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;

/**
 * Created by Olexandr Finchuk on 18.11.2016.
 */
@Entity
public class Route implements Serializable {
    private int routeId;
    private Time flightTime;
    private Address addressFrom;
    private Address addressTo;
    private AirlineCompany company;

    @Id
    @Column(name = "route_id")
    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    @Basic
    @Column(name = "flight_time")
    public Time getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(Time flightTime) {
        this.flightTime = flightTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Route route = (Route) o;

        if (routeId != route.routeId) return false;
        if (addressFrom != null ? !addressFrom.equals(route.addressFrom) : route.addressFrom != null) return false;

        if (addressTo != null ? !addressTo.equals(route.addressTo) : route.addressTo != null) return false;

        if (flightTime != null ? !flightTime.equals(route.flightTime) : route.flightTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = routeId;
        result = 31 * result + (flightTime != null ? flightTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return addressFrom +", "+ addressTo + " company: " + company.getCompanyName();
    }

    @ManyToOne
    @JoinColumn(name = "address_from", referencedColumnName = "address_id", nullable = false)
    public Address getAddressFrom() {
        return addressFrom;
    }

    public void setAddressFrom(Address addressFrom) {
        this.addressFrom = addressFrom;
    }

    @ManyToOne
    @JoinColumn(name = "address_to", referencedColumnName = "address_id", nullable = false)
    public Address getAddressTo() {
        return addressTo;
    }

    public void setAddressTo(Address addressTo) {
        this.addressTo = addressTo;
    }

    @ManyToOne
    @JoinColumn(name = "company", referencedColumnName = "company_id")
    public AirlineCompany getCompany() {
        return company;
    }

    public void setCompany(AirlineCompany company) {
        this.company = company;
    }


}
