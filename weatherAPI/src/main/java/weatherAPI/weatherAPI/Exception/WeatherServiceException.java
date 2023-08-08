package weatherAPI.weatherAPI.Exception;

public class WeatherServiceException extends RuntimeException {

	public WeatherServiceException(String message) {
		super(message);
	}

	public WeatherServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}