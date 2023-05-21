import com.digdes.school.project.impl.EmployeeServiceImpl;
import com.digdes.school.project.impl.ProjectParticipantsServiceImpl;
import com.digdes.school.project.impl.ProjectServiceImpl;
import com.digdes.school.project.input.EmployeeDTO;
import com.digdes.school.project.input.ProjectDTO;
import com.digdes.school.project.input.ProjectParticipantsDTO;
import com.digdes.school.project.model.ProjectParticipants;
import com.digdes.school.project.model.id.ProjectParticipantsId;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = {com.digdes.school.project.Main.class})
public class TestProjectParticipantsService {
    @Autowired
    @InjectMocks
    private ProjectParticipantsServiceImpl service;
    @Autowired
    @InjectMocks
    private ProjectServiceImpl projectService;

    @Autowired
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    void projectParticipantsService() {
        service.deleteAll();
        employeeService.deleteAll();

        var employeeToSave = EmployeeDTO.builder().firstname("Иван").lastname("zdf").build();


        var actual11 = employeeService.save(employeeToSave);

        projectService.deleteAll();

        var projectToSave = ProjectDTO.builder().code("hnk").name("scdz").build();


        var actual2 = projectService.save(projectToSave);


        UUID id1 = actual11.getId();
        UUID id2 = actual2.getId();

        var projectParticipantsDTO = ProjectParticipantsDTO.builder()
                .projectId(id2).employeeId(id1).build();


        var actual1 = service.save(projectParticipantsDTO);

        // Assert save
        assertThat(actual1.getProjectId()).usingDefaultComparator()
                .isEqualTo(projectParticipantsDTO.getProjectId());

        //Assert getAll
        List<ProjectParticipants> projectList1 = service.getAllProjectParticipants(id2);
        assertThat(projectList1.size()).usingDefaultComparator().isEqualTo(1);

        //Assert delete
        service.delete(new ProjectParticipantsId(id2, id1));
        List<ProjectParticipants> projectList2 = service.getAllProjectParticipants(id2);
        assertThat(projectList2.size()).usingDefaultComparator().isEqualTo(0);
        service.deleteAll();
        employeeService.deleteAll();
        projectService.deleteAll();

    }
}
