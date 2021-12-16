package rw.ac.rca.termOneExam.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.repository.ICityRepository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
@DataJpaTest
@RunWith(SpringRunner.class)
public class CityUtilTest {

    @Autowired
    private ICityRepository cityRepository;

    @Test
    public void weatherShouldBeLessThan40DegreesTest_success(){
        List<City> cities = cityRepository.findAll();
        for(City city: cities){
            assertTrue(city.getWeather() < 40);
        }
    }

    @Test
    public void weatherShouldBeGreaterThan10DegreesTest_success(){
        List<City> cities = cityRepository.findAll();
        for(City city: cities){
            assertTrue(city.getWeather() > 10);
        }
    }

    @Test
    public void cityContainsKigaliAndMusanze_success(){
        List<City> cities = cityRepository.findAll();
        assertTrue(containsCityName("Kigali", cities) && containsCityName("Musanze", cities));
    }

    private Boolean containsCityName(String name, List<City> cities){
        for(City city: cities){
            if(city.getName().equals(name)) return true;
        }
        return false;
    }

    @Test
    public void testSpying(){
        ArrayList<City> arrayListSpy = spy(ArrayList.class);
        City city = new City(10,"Nyabihu",34,62);
        arrayListSpy.add(city);
        System.out.println(arrayListSpy.get(0).getWeather());//Test0
        System.out.println(arrayListSpy.size());//1

        arrayListSpy.add(city);
        arrayListSpy.add(city);
        System.out.println(arrayListSpy.size());//3

        when(arrayListSpy.size()).thenReturn(5);
        System.out.println(arrayListSpy.size());//5
        //now call is lost so 5 will be returned no matter what

        arrayListSpy.add(city);
        System.out.println(arrayListSpy.size());
    }

    @Test
    public void testMocking(){
        City city = new City(10,"Nyabihu",34,62);
        List<City> mockList = mock(List.class);
        when(mockList.size()).thenReturn(5);
        assertEquals(5, mockList.size());
        when(mockList.get(0)).thenReturn(city);
        assertEquals("Nyabihu", mockList.get(0).getName());
        assertEquals(null, mockList.get(1));
        verify(mockList,atLeast(1)).get(anyInt());
    }
}
