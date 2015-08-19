package service;

import java.util.List;

import model.User;
import tuples.TuplePage;

public interface UserService {
    boolean create(User user);

    User getUser(String uid);

    void updateUser(User user);

    void deleteUser(String uid, String employeename);

    String getUserPassword(String username);

    User getUserByName(String username);

//    TuplePage<List<User>, Integer> GetUserList(String username, String deptname, int page, int pageSize);

    TuplePage<List<User>, Integer> GetUserList(String username, String employeename, String usertype, String isenable, int page, int pageSize);

    List<User> GetUserList();

    boolean ExistsUserName(String username);

    boolean ExistsUserName(String username, String uid);

    void UpdatePassword(String uID, String password);

    String UpdatePassword(String uid, String oldpassword, String newpassword) throws Exception;

    void UpdateLastLogin(String username);

    User InitWebUserByName(String username);
    
    List<User> GetUserTreeList();
    
    List<User> GetUserTreeSet(String employeeName);
    
    User GetOneUser(String uid);
    
    User GetOneUserParent(String partentuid);
}
