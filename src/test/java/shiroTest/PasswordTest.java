package shiroTest;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;

public class PasswordTest
{
	@Test
	public void PasswordTest1()
	{
//		String s = "12345";
//		String q = new SimpleHash("MD5", s, "balintimes.com").toString();
//		System.out.println(q);
		
		String algorithmName = "md5";  
		String username = "liu";  
		String password = "123";  
		String salt1 = username;   
		int hashIterations = 1;  
		  
		SimpleHash hash = new SimpleHash(algorithmName, "1", "#^8balintimes8!", 1);  
		String encodedPassword = hash.toHex();   
		System.out.println(encodedPassword);
	}
}
