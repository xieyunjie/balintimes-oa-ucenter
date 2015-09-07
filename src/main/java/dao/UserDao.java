package dao;

import java.util.List;

import model.User;
import tuples.TuplePage;

public interface UserDao {

    User getUser(String uid);

    boolean createUser(User user);
    
    boolean createUserPost(User user);

    List<User> GetUserList();

    boolean ExistsUserName(String username);

    boolean ExistsUserName(String username, String useruid);
    
    void deleteUserPost(String useruid);
    
    boolean ExistsEmployeeName(String employeename);

    void updateUser(User user);

    void deleteUser(String uid, String employeename);

    String getUserPassword(String username);

    User getUserByName(String username);

    TuplePage<List<User>, Integer> GetUserList(String username, String deptname, int page, int pageSize);

    TuplePage<List<User>, Integer> GetUserList(String username, String employeename, String usertype, String isenable, int page, int pageSize);

    void UpdatePassword(String uID, String encryptPassword);

    void UpdateLastLogin(String username);
    
    List<User> GetUserTreeList();
    
    List<User> GetUserTreeSet(String employeeName);
    
    List<User> GetUserTreeListByCondition(String username, String employeename,String postuid,String organizationuid);
    
    User GetOneUser(String uid);
    
    User GetOneUserParent(String partentuid);
    
    User GetEmployee(String username);

    List<User> GetSuperiors(String username);
    
    List<User> GetSubordinates(String username);
    
	List<User> GetUserByEmpName(String empName);
}
