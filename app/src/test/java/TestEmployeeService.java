import com.digdes.school.project.filters.EmployeeSearchFilter;
import com.digdes.school.project.impl.EmployeeServiceImpl;
import com.digdes.school.project.input.EmployeeDTO;
import com.digdes.school.project.model.Employee;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest(classes = {com.digdes.school.project.Main.class})

public class TestEmployeeService {
    @Autowired
    @InjectMocks
    private EmployeeServiceImpl service;


    @Test
    void employeeService() {
        service.deleteAll();

        var employeeToSave = EmployeeDTO.builder().firstname("Иван").lastname("zdf").build();


        var actual1 = service.save(employeeToSave);

        // Assert save
        assertThat(actual1.getLastname()).usingDefaultComparator()
                .isEqualTo(employeeToSave.getLastname());


        // Assert search
        EmployeeSearchFilter employeeSearchFilter = EmployeeSearchFilter.builder()
                .lastname("zdf").firstname("Иван").build();
        List<Employee> employeeList = service.search(employeeSearchFilter);
        assertThat(employeeList.size()).usingDefaultComparator().isEqualTo(1);

        employeeSearchFilter.setAccount("sjnxf");
        List<Employee> employeeList1 = service.search(employeeSearchFilter);
        assertThat(employeeList1.size()).usingDefaultComparator().isEqualTo(0);

        // Assert change, get
        Employee employee = employeeList.get(0);
        System.out.println(employee);
        employee.setEmail("nv");
        service.change(employee);
        assertThat(service.get(employee.getId()).getEmail())
                .usingDefaultComparator().isEqualTo("nv");

        // Assert delete
        employeeSearchFilter.setAccount(null);
        service.delete(employee.getId());
        List<Employee> employeeList2 = service.search(employeeSearchFilter);
        assertThat(employeeList2.size()).usingDefaultComparator().isEqualTo(0);

        service.deleteAll();

    }
}
