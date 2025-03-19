package api_tests.pages;

import lombok.Data;

@Data
public class AuthorsDto {
    private int id;
    private int idBook;
    private String firstName;
    private String lastName;
}
