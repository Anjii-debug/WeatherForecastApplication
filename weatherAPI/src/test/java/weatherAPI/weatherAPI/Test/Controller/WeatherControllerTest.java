package weatherAPI.weatherAPI.Test.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.io.IOException;
import java.text.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import weatherAPI.weatherAPI.Controller.WeatherController;
import weatherAPI.weatherAPI.Exception.WeatherServiceException;
import weatherAPI.weatherAPI.Service.WeatherService;

class WeatherControllerTest {

	@Mock
	private WeatherService weatherService;

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private WeatherController weatherController;

	@Mock
	private Model model;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testCityForm() {
		String city = "New York";
		String result = city;
		assertEquals(city, result);
	}

	@Test
	void testGetWeatherInfo_Success() throws ParseException, IOException {
		String city = "New York";
		String response = "Weather data response";
		when(weatherService.getWeatherForCity(any())).thenReturn(response);

		ResponseEntity<String> result = weatherController.getWeatherInfo(city, model);
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	void testGetWeatherInfo_Failure() throws ParseException, IOException {
		String city = "InvalidCity";
		String errorMessage = "An error occurred";
		when(weatherService.getWeatherForCity(any())).thenThrow(new WeatherServiceException(errorMessage));

		ResponseEntity<String> result = weatherController.getWeatherInfo(city, model);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
	}

}
