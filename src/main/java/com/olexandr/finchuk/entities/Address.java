package com.olexandr.finchuk.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Olexandr Finchuk on 18.11.2016.
 */
@Entity
public class Address implements Serializable {
    private int addressId;
    private String country;
    private String town;

    @Id
    @Column(name = "address_id")
    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    @Basic
    @Column(name = "country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Basic
    @Column(name = "town")
    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (addressId != address.addressId) return false;
        if (country != null ? !country.equals(address.country) : address.country != null) return false;
        if (town != null ? !town.equals(address.town) : address.town != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = addressId;
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (town != null ? town.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return country +", "+ town ;
    }
}
