package entities;

import java.util.Objects;

public class Venue {
    private int id;
    private static int nrVenues;
    protected String name;
    protected String description;
    private int capacity;
    protected  String address;
    protected String region;
    protected String ZIP;
    protected String country;
    protected double parkingFee;

    public Venue(String name, String description, int capacity, String address, String region, String ZIP, String country, double parkingFee)
    {
        nrVenues ++;
        this.id = nrVenues;
        this.name = name;
        this.description = description;
        this.capacity = capacity;
        this.address = address;
        this.ZIP = ZIP;
        this.region = region;
        this.country = country;
        this.parkingFee = parkingFee;
    }
    public int getId(){
        return id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getZIP() {
        return ZIP;
    }

    public void setZIP(String ZIP) {
        this.ZIP = ZIP;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getParkingFee() {
        return parkingFee;
    }

    public void setParkingFee(double parkingFee) {
        this.parkingFee = parkingFee;
    }

    @Override
    public String toString() {
        return name + " (Venue) " + " - " + address + " " + region + " " + country + " " + ZIP +
                "\n" + "Description: " + description + "\nCapacity: " + capacity + "  Parking Fee: " + parkingFee + "$";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Venue venue = (Venue) o;
        return id == venue.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    public String show()
    {
        return name;
    }
}
