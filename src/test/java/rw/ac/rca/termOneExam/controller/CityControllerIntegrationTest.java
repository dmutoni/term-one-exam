package rw.ac.rca.termOneExam.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.dto.CreateCityDTO;
import rw.ac.rca.termOneExam.service.CityService;
import rw.ac.rca.termOneExam.utils.APICustomResponse;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CityControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getAll_success() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/cities/all", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getById_success() {
        ResponseEntity<City> response = restTemplate.getForEntity("/api/cities/id/101", City.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Kigali", response.getBody().getName());
    }

    @Test
    public void getById_404() {
        ResponseEntity<APICustomResponse> response = restTemplate.getForEntity("/api/cities/id/200", APICustomResponse.class);

//        assertTrue(response.getBody().isStatus());
//        assertEquals("City not found with id 200", response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void create_success(){
        City city =  new City("nyabihu",300);
        ResponseEntity<City> response = this.restTemplate.postForEntity("/api/cities/add", city, City.class);

        System.out.println("=================================="+response);
        assertEquals(201, response.getStatusCodeValue());
//        assertEquals("Nyabihu", response.getBody().getName());
    }

    @Test
    public void create_400() {
        City city = new City("Musanze", 11);
        ResponseEntity<City> response = restTemplate.postForEntity("/api/cities/add", city, City.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
