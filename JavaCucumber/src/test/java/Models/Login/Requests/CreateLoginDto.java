package Models.Login.Requests;

public class CreateLoginDto {
    public String username;
    public String password;

    public CreateLoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
