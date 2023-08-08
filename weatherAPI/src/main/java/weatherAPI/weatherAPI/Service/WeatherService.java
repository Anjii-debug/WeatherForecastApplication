package weatherAPI.weatherAPI.Service;

import java.io.IOException;

import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.google.common.cache.Cache;

import weatherAPI.weatherAPI.Constant.WeatherConstant;
import weatherAPI.weatherAPI.Exception.WeatherServiceException;

@Service
public class WeatherService {

	@Value("${api.key}")
	private String apiKey;

	@Value("${weather.api.base-url}")
	private String baseurl;

	@Autowired
	private Cache<String, String> weatherDataCache;

	public String getWeatherForCity(String city) throws WeatherServiceException, ParseException, IOException {

		String cachedWeatherData = weatherDataCache.getIfPresent(city);
		if (cachedWeatherData != null) {
			return cachedWeatherData; // Return cached data
		}
		HttpClient httpClient = HttpClients.createDefault();
		String url = baseurl + "?q=" + city + "&appid=" + apiKey;
		try (CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(new HttpGet(url))) {
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == WeatherConstant.WEATHER_STATUS_SUCCESS) {
				weatherDataCache.put(city, response.toString());
				return EntityUtils.toString(response.getEntity());
			} else if (statusCode == WeatherConstant.WEATHER_STATUS_404) {
				throw new WeatherServiceException(WeatherConstant.CITY_NOT_FOUND);
			}
		} catch (WeatherServiceException e) {

			throw new WeatherServiceException(" " + city + " - " + WeatherConstant.CITY_NOT_FOUND);
		}

		return WeatherConstant.API_ERROR;
	}
}
