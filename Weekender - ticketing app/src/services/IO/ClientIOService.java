package services.IO;
import entities.Client;
import java.util.List;

public class ClientIOService extends AbstractIOService<Client>{
    private static ClientIOService service;
    public static ClientIOService getClientIOService()
    {
        if(service == null) {
            service = new ClientIOService();
        }
        return service;
    }
    public List<Client> load()
    {
        String FILE_NAME = "data/clients.csv";
        return super.load(FILE_NAME);
    }
    public void save(List<Client> s)
    {
        String FILE_NAME = "data/clients.csv";
        String HEADER_FILE = "Id,Phone number, Username, Email, Full Name, Password";
        super.save(s,FILE_NAME, HEADER_FILE);
    }
    public Client parse(List<String> entry)
    {

        return new Client(Integer.parseInt(entry.get(0)),entry.get(1).trim(),entry.get(2),entry.get(3),entry.get(4),
                entry.get(5).trim());
    }
    public String unparse(Client client)
    {
        return client.getId() + ", " + client.getPhoneNumber() + ", " + client.getUsername() + ", " + client.getEmail() +
                ", " + client.getFullname() + ", " + client.getPassword();
    }
}
