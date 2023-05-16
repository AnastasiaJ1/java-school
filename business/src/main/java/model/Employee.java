package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private Long id;
    private String lastname;
    private String firstname;
    private String surname;
    private String jobTitle;
    private Long account;
    private String email;
    private String status;

    //todo Странный конструктор..
    public Employee(long id) {
        this.setId(id);
    }

    @Override
    public String toString() {
        String tempId;
        String tempLastname;
        String tempFirstname;
        String tempSurname;
        String tempJobTitle;
        String tempAccount;
        String tempEmail;
        String tempStatus;

        if (id != null) tempId = id.toString();
        else tempId = "null";
        if (lastname != null) tempLastname = lastname;
        else tempLastname = "null";
        if (firstname != null) tempFirstname = firstname;
        else tempFirstname = "null";
        if (surname != null) tempSurname = surname;
        else tempSurname = "null";
        if (jobTitle != null) tempJobTitle = jobTitle;
        else tempJobTitle = "null";
        if (account != null) tempAccount = account.toString();
        else tempAccount = "null";
        if (email != null) tempEmail = email;
        else tempEmail = "null";
        if (status != null) tempStatus = status;
        else tempStatus = "null";
        return tempId +
                " ; " + tempLastname +
                " ; " + tempFirstname +
                " ; " + tempSurname +
                " ; " + tempJobTitle +
                " ; " + tempAccount +
                " ; " + tempEmail +
                " ; " + tempStatus
                ;
    }

    public void fromString(String fields) {
        List<String> fieldsList = List.of(fields.split(" ; "));
        if (!fieldsList.get(0).equals("null")) this.setId(Long.parseLong(fieldsList.get(0)));
        if (!fieldsList.get(1).equals("null")) this.setLastname(fieldsList.get(1));
        if (!fieldsList.get(2).equals("null")) this.setFirstname(fieldsList.get(2));
        if (!fieldsList.get(3).equals("null")) this.setSurname(fieldsList.get(3));
        if (!fieldsList.get(4).equals("null")) this.setJobTitle(fieldsList.get(4));
        if (!fieldsList.get(5).equals("null")) this.setAccount(Long.parseLong(fieldsList.get(5)));
        if (!fieldsList.get(6).equals("null")) this.setEmail(fieldsList.get(6));
        if (!fieldsList.get(7).equals("null")) this.setStatus(fieldsList.get(7));
    }
}
