package api_tests.bodies;

public class BodyForAuthorEndpoint {
    private Integer id;
    private Integer bookId;
    private String firstName;
    private String lastName;

    public BodyForAuthorEndpoint setId(Integer id) {
        this.id = id;
        return this;
    }

    public BodyForAuthorEndpoint setBookId(Integer bookId) {
        this.bookId = bookId;
        return this;
    }

    public BodyForAuthorEndpoint setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public BodyForAuthorEndpoint setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public Integer getBookId() {
        return bookId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
