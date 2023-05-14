package input;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectDTO {
    private Long code;
    private String name;
    private String description;
    private String status;
}
