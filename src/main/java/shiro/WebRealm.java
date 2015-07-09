package shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import service.UserService;
import util.WebUserUtil;

import javax.annotation.Resource;

public class WebRealm extends AuthorizingRealm {
    private PasswordService passwordService = null;
    @Resource
    private UserService userService = null;
    @Resource
    private WebUserUtil webUserUtil;

    public void setPasswordService(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        String username = (String) principals.getPrimaryPrincipal();

        if (username.length() > 0) {
            SimpleAuthorizationInfo authenticationInfo = new SimpleAuthorizationInfo();
            // authenticationInfo.setRoles(userService.findRolesStr(username));
            // authenticationInfo.setStringPermissions(userService.findPermissionsStr(username));

//            Set<String> permissions = new HashSet<String>();
//            permissions.add("user:*");
//            permissions.add("organization:view");
//            permissions.add("organization:create");
//            permissions.add("organization:edit");

            authenticationInfo.setStringPermissions(webUserUtil.GetUserPermissions());
//
//            Set<String> roles = new HashSet<String>();
//            roles.add("admin");


            return authenticationInfo;
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("web auth");
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();
        SimpleAuthenticationInfo authenticationInfo = null;

        String password = userService.getUserPassword(username);

        // ---
        WebPasswordService svc = (WebPasswordService) passwordService;
        authenticationInfo = new SimpleAuthenticationInfo(username, password, getName());

        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(svc.getSalt()));
        return authenticationInfo;
    }

    private static final String OR_OPERATOR = " | ";
    private static final String AND_OPERATOR = " & ";
    private static final String NOT_OPERATOR = "!";

    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        if (permission.contains(OR_OPERATOR)) {
            String[] permissions = permission.split(OR_OPERATOR);
            for (String orPermission : permissions) {
                if (isPermittedWithNotOperator(principals, orPermission)) {
                    return true;
                }
            }
            return false;
        } else if (permission.contains(AND_OPERATOR)) {
            String[] permissions = permission.split(AND_OPERATOR);
            for (String orPermission : permissions) {
                if (!isPermittedWithNotOperator(principals, orPermission)) {
                    return false;
                }
            }
            return true;
        } else {
            return isPermittedWithNotOperator(principals, permission);
        }
    }

    private boolean isPermittedWithNotOperator(PrincipalCollection principals, String permission) {
        if (permission.startsWith(NOT_OPERATOR)) {
            return !super.isPermitted(principals, permission.substring(NOT_OPERATOR.length()));
        } else {
            return super.isPermitted(principals, permission);
        }
    }

    @Override
    public boolean hasRole(PrincipalCollection principal, String roleIdentifier) {
        if (roleIdentifier.contains(OR_OPERATOR)) {
            String[] permissions = roleIdentifier.split(OR_OPERATOR);
            for (String orPermission : permissions) {
                if (hasRoleWithNotOperator(principal, orPermission)) {
                    return true;
                }
            }
            return false;
        } else if (roleIdentifier.contains(AND_OPERATOR)) {
            String[] permissions = roleIdentifier.split(AND_OPERATOR);
            for (String orPermission : permissions) {
                if (!hasRoleWithNotOperator(principal, orPermission)) {
                    return false;
                }
            }
            return true;
        } else {
            return hasRoleWithNotOperator(principal, roleIdentifier);
        }
    }

    private boolean hasRoleWithNotOperator(PrincipalCollection principals, String roleIdentifier) {
        if (roleIdentifier.startsWith(NOT_OPERATOR)) {
            return !super.hasRole(principals, roleIdentifier.substring(NOT_OPERATOR.length()));
        } else {
            return super.hasRole(principals, roleIdentifier);
        }
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
}