package shiroTest;

import javax.annotation.Resource;

import mappers.UserMapper;
import model.User;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import service.UserService;
import shiro.WebPasswordService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	@Resource
	private UserService userService;

	@Test
	public void EnPassword()
	{
		System.out.println(webPasswordService.encryptPassword("www.qq.com"));
	}

	private String printMsg() throws Exception {
		throw new Exception("这是什么错误？？？");
	}
	@Test
	public void printS(){
		try {
			this.printMsg();
		} catch (Exception e) {

			System.out.println(e.getMessage());
			System.out.println(e.toString());
		}
	}

	@Test
	public  void  UpdatePsw(){
		try {
			this.userService.UpdatePassword("14cc1718-28f6-4e02-a45e-a4b1694cc224", "1", "22");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	@Resource
	private UserMapper userMapper;

	@Test
	public  void GetUserList()
	{

		Map<String,Object> params = new HashMap<>(6);

		params.put("username","");
		params.put("employeename","");
		params.put("usertype","");
		//params.put("isenable",1);
		params.put("page",1);
		params.put("pageSize",50);

		List<User> list = this.userMapper.Pro_UserList(params);
		for (User user : list) {
			System.out.println(user.toString());
		}

        if (params.containsKey("totalcount")){
            System.out.println(params.get("totalcount"));
        }

	}
}
