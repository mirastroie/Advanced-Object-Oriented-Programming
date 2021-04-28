package services.IO;
import entities.Venue;
import java.util.List;

public class VenuesIOService extends AbstractIOService<Venue> {
    private static VenuesIOService service;

    public static VenuesIOService getVenuesIOService() {
        if (service == null) {
            service = new VenuesIOService();
        }
        return service;
    }
    public List<Venue> load()
    {
        String FILE_NAME = "data/venues.csv";
        return super.load(FILE_NAME);
    }
    public void save(List<Venue> s)
    {
        String FILE_NAME = "data/venues.csv";
        String HEADER_FILE = "Id, Name, Description, Capacity, Address, Region, ZIP, Country, Parking Fee";
        super.save(s,FILE_NAME, HEADER_FILE);
    }
    public Venue parse(List<String> value) {
        return new Venue(Integer.parseInt(value.get(0)), value.get(1),value.get(2), Integer.parseInt(value.get(3)),
                value.get(4), value.get(5), value.get(6), value.get(7),
                Double.parseDouble(value.get(8))
        );
    }

    public String unparse(Venue venue) {
        return venue.getId() + ", " + venue.getName() + ", " + "\"" + venue.getDescription() + "\", " +
                venue.getCapacity() + ", \"" + venue.getAddress() + "\", " + venue.getRegion() + ", " + venue.getZIP() +
                ", " + venue.getCountry() + ", " + venue.getParkingFee();
    }
}