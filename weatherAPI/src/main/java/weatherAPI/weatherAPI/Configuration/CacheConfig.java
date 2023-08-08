package weatherAPI.weatherAPI.Configuration;

import com.google.common.cache.CacheBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

	@Bean(name = "weatherDataCache")
	public com.google.common.cache.Cache<String, String> weatherDataCache() {
		return CacheBuilder.newBuilder().maximumSize(100) // Maximum number of entries in the cache
				.expireAfterWrite(1, TimeUnit.HOURS) // Cache entry expiration time
				.build();
	}
}
