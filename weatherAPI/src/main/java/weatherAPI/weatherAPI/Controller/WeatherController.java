package weatherAPI.weatherAPI.Controller;

import java.io.IOException;

import org.apache.http.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import weatherAPI.weatherAPI.Exception.WeatherServiceException;
import weatherAPI.weatherAPI.Service.WeatherService;

@ComponentScan("weatherAPI.weatherAPI.Configuration")
@Controller
public class WeatherController {

	@Value("${weather.api.base-url}")
	private String apiurl;

	@Value("${api.key}")
	private String apikey;

	@Autowired
	RestTemplate restTemp;

	@Autowired
	private WeatherService weatherService;

	@GetMapping("/weather")
	public String CityForm(String city, Model model) {

		model.addAttribute("city", city);
		return "formData";
	}

	@PostMapping("/weather")
	public ResponseEntity<String> getWeatherInfo(String city, Model model) throws ParseException, IOException {
		try {
			String response = (String) weatherService.getWeatherForCity(city);
			return ResponseEntity.ok(response);
		} catch (WeatherServiceException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error fetching weather data: " + e.getMessage());
		}
	}

}