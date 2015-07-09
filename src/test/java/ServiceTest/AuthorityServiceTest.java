package ServiceTest;

import java.util.List;

import model.Application;
import model.Resource;
import model.Role;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dao.ApplicationDao;
import dao.ResourceDao;
import dao.RoleDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class AuthorityServiceTest {
	@javax.annotation.Resource
	private ApplicationDao applicationDao;
	@javax.annotation.Resource
	private ResourceDao resourceDao;
	@javax.annotation.Resource
	private RoleDao roleDao;

	@Test
	public void GetUserApplications() {
		String username="llll";
		 List<Application> list= this.applicationDao.GetUserApplications(username);
		 for(Application item:list){
			 System.out.println(item.getName());
		 }
	}

	@Test
	public void GetUserMenu() {
		String username="llll";
		String applicationuid="c95adb2d-30dd-11e5-8396-c86000a05d5f";
		List<Resource> list= this.resourceDao.GetUserMenu(username, applicationuid);
		for(Resource item:list){
			 System.out.println(item.getName());
		 }
	}

	@Test
	public void GetUserPermissions() {
		String username="llll";
		String applicationuid="c95adb2d-30dd-11e5-8396-c86000a05d5f";
		List<Resource> list= this.resourceDao.GetUserPermissions(username, applicationuid);
		for(Resource item:list){
			 System.out.println(item.getName());
		 }
	}

	@Test
	public void GetUserMenuPermissions() {
		String username="llll";
		String menuuid="6c3fa3bf-34f9-11e5-8396-c86000a05d5f";
		List<Resource> list= this.resourceDao.GetUserMenuPermissions(username, menuuid);
		for(Resource item:list){
			 System.out.println(item.getName());
		 }
	}

	@Test
	public void GetUserRoles() {
		String username="llll";
		List<Role> list=this.roleDao.GetUserRoles(username);
		for(Role item:list){
			 System.out.println(item.getName());
		 }
	}
	
	
}
