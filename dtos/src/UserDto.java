import java.util.Date;

public class UserDto {
    private long id;
    private String login;
    private String email;
    private String password;
    private Date insertTime;

    public UserDto(long id, String login, String email, String password, Date insertTime) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.password = password;
        this.insertTime = insertTime;
    }

    public long getID() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Date getInsertTime() {
        return insertTime;
    }
}