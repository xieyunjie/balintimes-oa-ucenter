package mappers;

import java.util.List;
import java.util.Map;

import model.User;

public interface UserMapper {
    User getUser(String uid);

    void createUser(User user);

    List<User> GetUserList();

    List<String> CheckUserName(String username);

    void updateUser(User user);

    void deleteUser(Map<String, Object> params);

    String getUserPassword(String username);

    User getUserByName(String username);

    List<User> GetUserByPage(Map<String, Object> params);

    int GetUserTotalCount(Map<String, Object> params);

    void UpdatePassword(Map<String, Object> params);

    void UpdateLastLogin(String username);

    List<User> Pro_UserList(Map<String, Object> params);
}
