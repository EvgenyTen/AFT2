package redmine.model.project;

import lombok.Data;
import lombok.experimental.Accessors;
import redmine.db.requests.ProjectRequests;
import redmine.model.Generatable;
import redmine.model.role.Role;
import redmine.model.user.User;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static redmine.utils.StringGenerators.randomEnglishLowerString;

@Accessors(chain = true)
@Data
public class Project implements Generatable<Project> {
    private Integer id;
    private String name = "EvgProject" + randomEnglishLowerString(6);
    private String description = "Desc " + randomEnglishLowerString(15);
    private String homepage;
    private Integer parentId;
    private Boolean isPublic = true;
    private String identifier = "Autoproject" + randomEnglishLowerString(6);
    private LocalDateTime updatedOn = LocalDateTime.now();
    private LocalDateTime createdOn = LocalDateTime.now();
    private Integer status = 1;
    private Integer lft = 3;
    private Integer rgt = 3;
    private Boolean inheritMembers = false;
    private Map<User,Role> assignedUsers;

    @Override
    public Project read() {
        return null;
    }

    @Override
    public Project update() {
        return null;
    }

    @Override
    public Project create() {
        return ProjectRequests.createProject(this);
    }

    public static Project addUserAndRoleToProject(Project project, User user, Role role) {
        Map<User,Role> assignedUsers=new HashMap<>();
        assignedUsers.put(user,role);
        return ProjectRequests.addUserAndRoleToProject(project, user, role);
    }
}
