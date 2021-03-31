package entities;

public abstract class User {
    static int nrUsers;
    private final int id;
    protected String username;
    protected String fullname;
    protected String email;
    private String password;
    private CreditCard creditCard;

    public User(String username, String fullname, String email, String password){
        nrUsers ++;
        this.id = nrUsers;
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
    }
    public void setEmail(String email){this.email = email;}
    public String getEmail(){return email;}
    public void setUsername(String username)
    {
        this.username = username;
    }
    public void setFullname(String fullname)
    {
        this.fullname = fullname;
    }
    public String getPassword(){
        return password;
    }
    public String getUsername(){
        return username;
    }
    public String getFullname(){
        return fullname;
    }
    @Override
    public String toString() {
        return fullname + " (" + email + ") ";
    }
    public void setCreditCard(CreditCard card)
    {
        this.creditCard = card;
    }
    public CreditCard getCreditCard()
    {
        return creditCard;
    }
}
