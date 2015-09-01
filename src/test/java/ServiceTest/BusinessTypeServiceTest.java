package ServiceTest;

import java.util.List;

import javax.annotation.Resource;

import model.base.BusinessType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dao.base.BusinessTypeDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class BusinessTypeServiceTest {
	@Resource
	private BusinessTypeDao businessTypeDao;

	@Test
	public void getBusinessTypeList() {
		List<BusinessType> list = this.businessTypeDao.GetBusinessTypeList("日");
		for (BusinessType item : list) {
			System.out.println(item.getName());
		}
	}
}
