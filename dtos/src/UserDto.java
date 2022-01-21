import java.util.Date;

public class UserDto {
    private long ID;
    private String nick;
    private String login;
    private String password;
    private Date insertTime;

    public UserDto(long id, String nick, String login, String password, Date insertTime) {
        this.ID = id;
        this.nick = nick;
        this.login = login;
        this.password = password;
        this.insertTime = insertTime;
    }

    public long getID() {
        return ID;
    }

    public String getNick() {
        return nick;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Date getInsertTime() {
        return insertTime;
    }
}