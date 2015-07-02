package shiro;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import service.UserService;

public class WebRealm extends AuthorizingRealm
{
	private PasswordService passwordService = null;
	@Resource
	private UserService userService = null;

	public PasswordService getPasswordService()
	{
		return passwordService;
	}

	public void setPasswordService(PasswordService passwordService)
	{
		this.passwordService = passwordService;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
	{
		/*
		 * // TODO Auto-generated method stub
		 * return null;
		 */

		String username = (String) principals.getPrimaryPrincipal();
		if (username.length() > 0)
		{
			SimpleAuthorizationInfo authenticationInfo = new SimpleAuthorizationInfo();
			// authenticationInfo.setRoles(userService.findRolesStr(username));
			// authenticationInfo.setStringPermissions(userService.findPermissionsStr(username));
			return authenticationInfo;
		}
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException
	{
		System.out.println("web auth");
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
		String username = usernamePasswordToken.getUsername();
		SimpleAuthenticationInfo authenticationInfo = null;

		String password = userService.getUserPassword(username);

		// ---
		WebPasswordService svc = (WebPasswordService) passwordService;
		authenticationInfo = new SimpleAuthenticationInfo(username, password, getName());

		authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(svc.getSalt()));
		// ---

		// String input = new String(usernamePasswordToken.getPassword());

		// if (passwordService.passwordsMatch(input, password))
		// {
		// String salt2 = new SecureRandomNumberGenerator().nextBytes().toHex();
		// authenticationInfo = new SimpleAuthenticationInfo(username, password,
		// this.getName());
		//
		// authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(salt2));
		//
		// }
		// else
		// {
		// throw new IncorrectCredentialsException("Web登录失败，请检查用户或者密码");
		// }
		return authenticationInfo;
	}
}