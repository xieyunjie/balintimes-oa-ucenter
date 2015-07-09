package shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.subject.Subject;

public class Utils
{

	public static void logout()
	{
		Subject subject = SecurityUtils.getSubject();

		RealmSecurityManager securityManager = (RealmSecurityManager) SecurityUtils.getSecurityManager();
		WebRealm webRealm = (WebRealm) securityManager.getRealms().iterator().next();
		webRealm.clearAllCache();

		subject.logout();
	}
}

