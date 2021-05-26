package entities;

import java.util.Objects;

public class Venue {
    private int id;
    private static int nrVenues;
    private static int max = 1;
    protected String name;
    protected String description;
    private int capacity;
    protected  String address;
    protected String region;
    protected String ZIP;
    protected String country;
    protected double parkingFee;

    public Venue(Integer id, String name, String description, int capacity, String address, String region, String ZIP, String country, double parkingFee)
    {
        this(name,description,capacity,address,region,ZIP,country,parkingFee);
        this.id = id;
        max = Math.max(id,max - 1) + 1;

    }
    public Venue(String name, String description, int capacity, String address, String region, String ZIP, String country, double parkingFee)
    {
        nrVenues ++;
        this.id = max ++;
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
        return capacity == venue.capacity && Double.compare(venue.parkingFee, parkingFee) == 0 &&
                Objects.equals(name, venue.name) && Objects.equals(description, venue.description)
                && Objects.equals(address, venue.address) && Objects.equals(region, venue.region)
                && Objects.equals(ZIP, venue.ZIP) && Objects.equals(country, venue.country);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + capacity;
        result = 31 * result + address.hashCode();
        result = 31 * result + ZIP.hashCode();
        result = 31 * result + region.hashCode();
        result = 31 * result + country.hashCode();
        result = 31 * result + Double.valueOf(parkingFee).hashCode();
        return result;
    }
    public String show()
    {
        return name;
    }
}
