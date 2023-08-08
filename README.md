Weather Forecast Display Application
The Weather Forecast Display Application is a web-based application that allows users to check the weather forecast for different cities.
 It leverages external weather data providers to retrieve and display weather information in a user-friendly manner.

⦁		Table of Contents
⦁		Features
⦁		Getting Started
⦁		Prerequisites
⦁		Installation
⦁		Usage
⦁		Configuration

Features:-Users can enter a city name to check the weather forecast.
The application displays weather details such as temperature, humidity, wind speed, and conditions,etc .
Caching is implemented to reduce the number of requests to the external weather data provider's API.

Getting Started
Prerequisites
Java 8 or later
Spring Boot framework
Thymeleaf template engine
Google Guava library (for caching)
Apache HttpClient (or equivalent) for making HTTP requests

Installation
Clone the repository:   git clone (https://github.com/Anjii-debug/WeatherForecastApplication.git)

Build and run the application:

The application will start and be accessible at http://localhost:8065.
Usage
Open your web browser and navigate to http://localhost:8065
Enter the name of the city for which you want to check the weather forecast.
Click the "Submit" button.
The application will display the weather forecast details for the specified city.

Configuration
Configure the external weather data provider's API key in application.properties:
properties
api.key=efc8d78e2f415f15dbe9fbb75eb73c88
weather.api.base-url=http://api.openweathermap.org/data/2.5/weather
Adjust caching settings in CacheConfig.java:


