package services.IO;
import entities.Organizer;
import java.util.List;

public class OrganizerIOService extends AbstractIOService<Organizer>{
    private static OrganizerIOService service;
    public static OrganizerIOService getOrganizerIOService()
    {
        if(service == null) {

            service = new OrganizerIOService();
        }
        return service;
    }
    public List<Organizer> load()
    {
        String FILE_NAME =  "data/organizers.csv";
        return super.load(FILE_NAME);
    }
    public void save(List<Organizer> s)
    {
        String FILE_NAME = "data/organizers.csv";
        String HEADER_FILE = "Id, Description, Full Name, Username, Password, Email";
        super.save(s,FILE_NAME, HEADER_FILE);
    }
    public Organizer parse(List<String> entry)
    {
        return new Organizer(Integer.parseInt(entry.get(0)),entry.get(1),entry.get(2),entry.get(3).trim(),
                entry.get(4).trim(), entry.get(5).trim());
    }
    public String unparse(Organizer organizer)
    {
        return organizer.getId() + ", " + "\"" + organizer.getDescription() + "\", " + organizer.getFullname() + ", " +
                organizer.getUsername() + ", " + organizer.getPassword() + ", " +
                organizer.getEmail();
    }
}
