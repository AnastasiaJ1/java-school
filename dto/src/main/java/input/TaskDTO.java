package input;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
@Data
@AllArgsConstructor
public class TaskDTO {
    private String name;
    private String description;
    private Long executor;
    private Integer laborCostsHours;
    private Date deadline;
    private String status;
    private Long Author;
    private Date creationDate;
    private Date changeDate;
}
