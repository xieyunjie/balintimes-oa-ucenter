package mappers;

import java.util.List;
import java.util.Map;

import model.City;

/**
 * Created by AlexXie on 2015/7/13.
 */
public interface CityMapper {


    List<City> GetCityList();
    
    List<City> GetCityListByPage(Map<String, Object> params);
    
    void UpdateCity(City city);
    
    void UpdateCity(Map<String, Object> params);

    void DeleteCity(String uid);
    
    void CreateCity(City city);

}
