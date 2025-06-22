package responses_dto;

import lombok.Data;

@Data
public class AuthorsDto {
    private Integer id;
    private Integer idBook;
    private String firstName;
    private String lastName;
}
