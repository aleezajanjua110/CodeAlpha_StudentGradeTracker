package Internship;

public class User {
    private int     id;
    private String  name;
    private String  username;
    private String  password;
    private boolean isAdmin;

    public User(int id, String name, String username,
                String password, boolean isAdmin) {
        this.id       = id;
        this.name     = name;
        this.username = username;
        this.password = password;
        this.isAdmin  = isAdmin;
    }

    public int     getId()       { return id; }
    public String  getName()     { return name; }
    public String  getUsername() { return username; }
    public String  getPassword() { return password; }
    public boolean isAdmin()     { return isAdmin; }

    public void setName(String name)         { this.name = name; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return name + " (" + username + ") — " + (isAdmin ? "Admin" : "User");
    }
}