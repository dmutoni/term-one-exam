package rw.ac.rca.termOneExam.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.dto.CreateCityDTO;
import rw.ac.rca.termOneExam.repository.ICityRepository;
import static org.mockito.ArgumentMatchers.any;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceTest {
    @Mock
    private ICityRepository cityRepositoryMock;

    @InjectMocks
    private CityService cityService;

    @Test
    public void getAllCities() {
        when(cityRepositoryMock.findAll()).thenReturn(Arrays.asList(new City(101, "Kigali", 24, 75.2),
                new City(101, "Kigali", 24, 24)));
        assertEquals(75.2, cityService.getAll().get(0).getFahrenheit());
    }

    @Test
    public void getOneCity_SUCESS() {
        when(cityRepositoryMock.findById(101L)).thenReturn(Optional.of(new City(101, "Kigali", 24, 75.2)));
        assertEquals(75.2, cityService.getById(101).get().getFahrenheit());
    }


    @Test
    public void saveCity_SUCCESS() {
        CreateCityDTO dto = new CreateCityDTO("nyabihu", 25);
        City city = new City(101,"Akarabo", 24, 72.5);
        when(cityRepositoryMock.save(any(City.class))).thenReturn(city);
        assertEquals(72.5, cityService.save(dto).getFahrenheit());
    }

    @Test(expected = RuntimeException.class)
    public void create_fail_duplicateName() {
        CreateCityDTO city=new CreateCityDTO("Kigali",23);
        City createdCity=new City(105,"Kigali",24,72.5);

        when(cityRepositoryMock.existsByName(city.getName())).thenReturn(true);
        when(cityRepositoryMock.save(ArgumentMatchers.any(City.class))).thenReturn(createdCity);
        cityService.save(city);
    }
}
