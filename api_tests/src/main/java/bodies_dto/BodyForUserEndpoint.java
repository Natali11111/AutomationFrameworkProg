package bodies_dto;

public class BodyForUserEndpoint {
    private Integer id;
    private String userName;
    private String password;

    public BodyForUserEndpoint setId(Integer id) {
        this.id = id;
        return this;
    }

    public BodyForUserEndpoint setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public BodyForUserEndpoint setPassword(String password) {
        this.password = password;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
