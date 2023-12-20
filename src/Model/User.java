package Model;

public class User {
    int ID;
    String username;
    String password;
    int role;

    public User(int id, String username, String password, int role) {
        ID = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public int getID() {
        return ID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "User{" +
                "ID=" + ID +
                ", Username='" + username + '\'' +
                ", Password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
