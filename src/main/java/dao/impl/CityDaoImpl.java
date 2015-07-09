package dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mappers.CityMapper;
import model.City;

import org.springframework.stereotype.Repository;

import tuples.TuplePage;
import dao.CityDao;

/**
 * Created by AlexXie on 2015/7/14.
 */
@Repository
public class CityDaoImpl implements CityDao {

    @Resource
    private CityMapper cityMapper;

    public List<City> GetCityList() {
        return  this.cityMapper.GetCityList();
    }
    
   
    public TuplePage<List<City>, Integer> GetCityListByPage(City city, int page, int pageSize)
    {
    	 Map<String, Object> params = new HashMap<String, Object>(4);
         params.put("name", city.getName());  
         params.put("UID", city.getUid());  
         List<City> list = cityMapper.GetCityListByPage(params);
         int total=list.size();
         return new TuplePage<List<City>, Integer>(list, total);
    }
    
    
    public void UpdateCity(City city){
    	Map<String, Object> params = new HashMap<String, Object>(2);
        params.put("uid", city.getUid());
        params.put("name", city.getName());
//        this.cityMapper.UpdateCity(city);
        this.cityMapper.UpdateCity(params);
    }
    
    
    public void DeleteCity(String uid)
    {
    	this.cityMapper.DeleteCity(uid);
    }
    
    public boolean CreateCity(City city) {
    	try {
//    		System.out.println("daoimpl:"+city.getName()+city.getUid());
    		cityMapper.CreateCity(city);
    		return true;
			
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}		
	}
    
}
