import com.digdes.school.project.filters.ProjectSearchFilter;
import com.digdes.school.project.impl.ProjectServiceImpl;
import com.digdes.school.project.input.ProjectDTO;
import com.digdes.school.project.model.Project;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = {com.digdes.school.project.Main.class})
public class TestProjectService {
    @Autowired
    @InjectMocks
    private ProjectServiceImpl service;

    @Test
    void projectService() {
        service.deleteAll();

        var projectToSave = ProjectDTO.builder().code("hnk").name("scdz").build();


        var actual1 = service.save(projectToSave);

        // Assert save
        assertThat(actual1.getCode()).usingDefaultComparator()
                .isEqualTo(projectToSave.getCode());


        // Assert search
        ProjectSearchFilter projectSearchFilter = ProjectSearchFilter.builder()
                .code("hnk").build();
        List<Project> projectList = service.search(projectSearchFilter);
        assertThat(projectList.size()).usingDefaultComparator().isEqualTo(1);

        projectSearchFilter.setDescription("sjnxf");
        List<Project> projectList1 = service.search(projectSearchFilter);
        assertThat(projectList1.size()).usingDefaultComparator().isEqualTo(0);

        // Assert change, get
        Project project = projectList.get(0);
        System.out.println(project);
        project.setDescription("nv");
        service.change(project);
        assertThat(service.get(project.getId()).getDescription())
                .usingDefaultComparator().isEqualTo("nv");

        // Assert delete
        projectSearchFilter.setDescription(null);
        service.delete(project.getId());
        List<Project> projectList2 = service.search(projectSearchFilter);
        assertThat(projectList2.size()).usingDefaultComparator().isEqualTo(0);

    }
}
