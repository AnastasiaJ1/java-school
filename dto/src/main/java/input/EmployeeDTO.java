package input;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class EmployeeDTO {
    private String lastname;
    private String firstname;
    private String surname;
    private String jobTitle;
    private Long account;



}
