package api_tests.responses;

import lombok.Data;

@Data
public class AuthorsDto {
    private Integer id;
    private Integer idBook;
    private String firstName;
    private String lastName;
}
