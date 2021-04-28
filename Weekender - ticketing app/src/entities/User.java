package entities;

import java.util.Objects;

public abstract class User {
    static int nrUsers;
    private static int max = 1;
    private final int id;
    protected String username;
    protected String fullname;
    protected String email;
    private String password;

    public User(String username, String fullname, String email, String password){
        nrUsers ++;
        this.id = max ++;
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
    }
    public User(Integer id, String username, String fullname, String email, String password){
        nrUsers ++;
        this.id = id;
        max = Math.max(id,max) + 1;
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(fullname, user.fullname) &&
                Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, fullname, email, password);
    }
}
