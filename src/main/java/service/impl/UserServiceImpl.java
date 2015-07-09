package service.impl;

import annotation.CustomerTransactional;
import dao.UserDao;
import model.User;
import org.apache.shiro.authc.credential.PasswordService;
import org.springframework.stereotype.Service;
import service.UserService;
import tuples.TuplePage;

import javax.annotation.Resource;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private PasswordService passwordService;
    @Resource
    private UserDao userDao;

    @CustomerTransactional
    public boolean create(User user) {
        user.setPassword(passwordService.encryptPassword(user.getPassword()));
        return userDao.createUser(user);
    }

    public User getUser(String uid) {
        return userDao.getUser(uid);
    }

    public void throwEx() {
        throw new RuntimeException("alex xie exception!");
    }

    public List<User> GetUserList() {
        return userDao.GetUserList();
    }

    public boolean ExistsUserName(String username) {
        return userDao.ExistsUserName(username);
    }

    public boolean ExistsUserName(String username, String uid) {
        return userDao.ExistsUserName(username, uid);
    }

    @CustomerTransactional
    public void updateUser(User user) {
        this.userDao.updateUser(user);
    }

    @CustomerTransactional
    public void deleteUser(String uid, String employeename) {
        this.userDao.deleteUser(uid, employeename);
    }

    public String getUserPassword(String username) {
        return this.userDao.getUserPassword(username);
    }

    public User getUserByName(String username) {
        return this.userDao.getUserByName(username);
    }

    //    public TuplePage<List<User>, Integer> GetUserList(String username, String deptname, int page, int pageSize) {
//        return this.userDao.GetUserList(username, deptname, page, pageSize);
//    }
    public TuplePage<List<User>, Integer> GetUserList(String username, String employeename, String usertype, String isenable, int page, int pageSize) {
        return this.userDao.GetUserList(username, employeename, usertype, isenable, page, pageSize);
    }

    @CustomerTransactional
    public void UpdatePassword(String uID, String password) {
        this.userDao.UpdatePassword(uID, passwordService.encryptPassword(password));

    }

    @CustomerTransactional
    public String UpdatePassword(String uid, String oldpassword, String newpassword) throws Exception {

        User user = this.getUser(uid);
        String dbpassword = user.getPassword();
        oldpassword = this.passwordService.encryptPassword(oldpassword);
        if (dbpassword.equalsIgnoreCase(oldpassword) == false) {
            throw new Exception("用户密码录入错误！");

        }
        this.UpdatePassword(uid, newpassword);

        return user.getUsername();
    }

    @CustomerTransactional
    public void UpdateLastLogin(String username) {
        this.userDao.UpdateLastLogin(username);
    }

    //
    public User InitWebUserByName(String username) {
        return this.userDao.getUserByName(username);
    }

}
