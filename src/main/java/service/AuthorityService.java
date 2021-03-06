package service;

import model.*;
import model.authority.Employee;

import java.util.List;

/**
 * Created by AlexXie on 2015/7/31.
 */
public interface AuthorityService {


    List<Application> GetUserApplications(String username);

    List<Resource> GetUserMenu(String username, String applicationuid);

    List<Resource> GetUserPermissions(String username, String applicationuid);

    List<Resource> GetUserMenuPermissions(String username, String menuuid);

    List<Role> GetUserRoles(String username);

    Employee GetEmployee(String username);

    List<Employee> GetSuperiors(String username);

    List<Employee> GetSubordinates(String username);


//    User InitWebUser(String username);
//
//    List<Post> GetUserPosts(String useruid);

}
