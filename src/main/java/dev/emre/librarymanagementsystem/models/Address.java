package dev.emre.librarymanagementsystem.models;

import java.util.Objects;

public class Address {
    private final String city;
    private final String street;
    private final String zipCode;
    public Address(String city,
                   String street,
                   String zipCode
    ) {
        if(city == null || city.isBlank()){
            throw new IllegalArgumentException("City cannot be null or empty");
        }
        if(street == null || street.isBlank()){
            throw new IllegalArgumentException("Street cannot be null or empty");
        }
        if(zipCode == null || zipCode.isBlank()){
            throw new IllegalArgumentException("Zip code cannot be null or empty");
        }
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }
    public String getStreet() {
        return street;
    }
    public String getZipCode() {
        return zipCode;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", city, street, zipCode);
    }
    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if( obj == null || getClass() != obj.getClass() ) return false;
        Address address = (Address) obj;
        return Objects.equals(city, address.city)
                && Objects.equals(street, address.street)
                && Objects.equals(zipCode, address.zipCode);
    }
    @Override
    public int hashCode() {
        return Objects.hash(city, street, zipCode);
    }


}
