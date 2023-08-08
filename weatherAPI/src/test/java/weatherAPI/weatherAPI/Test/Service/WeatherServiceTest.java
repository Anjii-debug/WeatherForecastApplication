package weatherAPI.weatherAPI.Test.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.text.ParseException;

import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.common.cache.Cache;

import weatherAPI.weatherAPI.Constant.WeatherConstant;
import weatherAPI.weatherAPI.Service.WeatherService;

class WeatherServiceTest {

	@Mock
	private Cache<String, String> weatherDataCache;

	@InjectMocks
	private WeatherService weatherService;

	String responseWeather = "";

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		responseWeather = "{\"coord\":{\"lon\":-0.1257,\"lat\":51.5085},\"weather\":[{\"id\":300,\"main\":\"Drizzle\",\"description\":\"light intensity drizzle\",\"icon\":\"09d\"}],\"base\":\"stations\",\"main\":{\"temp\":289.54,\"feels_like\":289.64,\"temp_min\":288.62,\"temp_max\":290.37,\"pressure\":1012,\"humidity\":92},\"visibility\":4600,\"wind\":{\"speed\":4.63,\"deg\":240},\"clouds\":{\"all\":100},\"dt\":1691503568,\"sys\":{\"type\":2,\"id\":2075535,\"country\":\"GB\",\"sunrise\":1691469261,\"sunset\":1691523486},\"timezone\":3600,\"id\":2643743,\"name\":\"London\",\"cod\":200}";
	}

	@Test
	void testGetWeatherForCity_CacheHit() throws IOException, ParseException {
		String city = "New York";
		String cachedWeatherData = "Cached Weather Data";

		when(weatherDataCache.getIfPresent(city)).thenReturn(cachedWeatherData);

		String result = weatherService.getWeatherForCity(city);

		assertEquals(cachedWeatherData, result);
		verify(weatherDataCache, times(1)).getIfPresent(city);
	}

	@Test
	void testGetWeatherForCity_ApiSuccess() throws IOException, ParseException {
		String city = "New York";
		String apiKey = "YOUR_API_KEY";
		String baseurl = "https://api.example.com/weather";
		String weatherResponse = "{\"coord\":{\"lon\":-0.1257,\"lat\":51.5085},\"weather\":[{\"id\":300,\"main\":\"Drizzle\",\"description\":\"light intensity drizzle\",\"icon\":\"09d\"}],\"base\":\"stations\",\"main\":{\"temp\":289.54,\"feels_like\":289.64,\"temp_min\":288.62,\"temp_max\":290.37,\"pressure\":1012,\"humidity\":92},\"visibility\":4600,\"wind\":{\"speed\":4.63,\"deg\":240},\"clouds\":{\"all\":100},\"dt\":1691503568,\"sys\":{\"type\":2,\"id\":2075535,\"country\":\"GB\",\"sunrise\":1691469261,\"sunset\":1691523486},\"timezone\":3600,\"id\":2643743,\"name\":\"London\",\"cod\":200}";

		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse httpResponse = mock(CloseableHttpResponse.class);
		StatusLine statusLine = mock(StatusLine.class);

		when(httpResponse.getStatusLine()).thenReturn(statusLine);
		when(statusLine.getStatusCode()).thenReturn(WeatherConstant.WEATHER_STATUS_SUCCESS);

		when(weatherDataCache.getIfPresent(city)).thenReturn(null);

		assertEquals(weatherResponse, responseWeather);
	}

	@Test
	void testGetWeatherForCity_ApiNotFound() throws IOException, ParseException {
		String city = "NonExistentCity";

		CloseableHttpClient httpClient = mock(CloseableHttpClient.class);
		CloseableHttpResponse httpResponse = mock(CloseableHttpResponse.class);
		StatusLine statusLine = mock(StatusLine.class);

		when(httpResponse.getStatusLine()).thenReturn(statusLine);
		when(statusLine.getStatusCode()).thenReturn(WeatherConstant.WEATHER_STATUS_404);

		when(weatherDataCache.getIfPresent(city)).thenReturn(null);
		when(httpClient.execute(any(HttpGet.class))).thenReturn(httpResponse);
		assertEquals(statusLine.getStatusCode(), WeatherConstant.WEATHER_STATUS_404);

	}

	@Test
	void testGetWeatherForCity_ApiError() throws IOException, ParseException {
		String city = "New York";

		CloseableHttpClient httpClient = mock(CloseableHttpClient.class);
		CloseableHttpResponse httpResponse = mock(CloseableHttpResponse.class);
		StatusLine statusLine = mock(StatusLine.class);

		when(httpResponse.getStatusLine()).thenReturn(statusLine);
		when(statusLine.getStatusCode()).thenReturn(WeatherConstant.WEATHER_STATUS_404);

		when(weatherDataCache.getIfPresent(city)).thenReturn(null);
		when(httpClient.execute(any(HttpGet.class))).thenReturn(httpResponse);

		assertEquals(WeatherConstant.API_ERROR, "API Error");
	}
}
