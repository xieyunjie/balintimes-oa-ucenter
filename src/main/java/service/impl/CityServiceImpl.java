package service.impl;

//import java.util.HashMap;
import java.util.List;
//import java.util.Map;

import javax.annotation.Resource;

import model.City;

import org.springframework.stereotype.Service;

import service.CityService;
import tuples.TuplePage;
import annotation.CustomerTransactional;
import dao.CityDao;

/**
 * Created by AlexXie on 2015/7/14.
 */
@Service("cityService")
public class CityServiceImpl implements CityService {


    @Resource
    private CityDao cityDao;

    public List<City> GetCityList() {
        return  this.cityDao.GetCityList();
    }
    
    public TuplePage<List<City>, Integer> GetCityListByPage(City city, int page, int pageSize)
    {
    	return this.cityDao.GetCityListByPage(city, page, pageSize);
    }
    
    @CustomerTransactional
    public void UpdateCity(City city){
//    	Map<String, Object> params = new HashMap<String, Object>(2);
//        params.put("uid", uid);
//        params.put("name", cityName);
        this.cityDao.UpdateCity(city);
    }
    
    @CustomerTransactional
    public void DeleteCity(String uid)
    {
    	this.cityDao.DeleteCity(uid);
    }
    
    public boolean CreateCity(City city){
    	return this.cityDao.CreateCity(city);    	
    }
}
