package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {

    private UUID id;
    private String lastname;
    private String firstname;
    private String surname;
    private String jobTitle;
    private Long account;
    private String email;
    private String status;


}
