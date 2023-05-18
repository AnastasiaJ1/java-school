package filters;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchFilter {
    private String firstname;
    private String lastname;
    private String surname;
    private String taskName;
}
