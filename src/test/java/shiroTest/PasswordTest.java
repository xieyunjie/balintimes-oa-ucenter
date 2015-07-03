package shiroTest;

import javax.annotation.Resource;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import shiro.WebPasswordService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
{ "classpath:applicationContext.xml" })
public class PasswordTest
{
	@Test
	public void PasswordTest1()
	{
		// String s = "12345";
		// String q = new SimpleHash("MD5", s, "balintimes.com").toString();
		// System.out.println(q);

		String algorithmName = "md5";
		String username = "liu";
		String password = "123";
		String salt1 = username;
		int hashIterations = 1;

		SimpleHash hash = new SimpleHash(algorithmName, "1", "#^8balintimes8!", 1);
		String encodedPassword = hash.toHex();
		System.out.println(encodedPassword);
	}
	
	
	@Resource
	private WebPasswordService  webPasswordService;
	@Test
	public void EnPassword()
	{
		System.out.println(webPasswordService.encryptPassword("www.qq.com"));
	}
}
