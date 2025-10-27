package rw.ac.ilpd.registrationservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * Configuration class for Redis and caching within the application.
 * This class provides beans for establishing Redis connections, setting up a Redis template,
 * and configuring a cache manager for the application. It supports customization of Redis
 * configurations such as connection details, time-to-live (TTL) for cache entries, and
 * serialization strategies for keys and values.
 */
@Configuration
@EnableCaching
public class RedisConfig {

    /**
     * Represents the hostname or IP address of the Redis server.
     * This value is configured using the property `spring.data.redis.host`
     * from the application's configuration files.
     * It defines the address where the application will connect
     * to the Redis instance for caching or data storage purposes.
     */
    @Value("${spring.data.redis.host}")
    private String redisHost;

    /**
     * Represents the port number for connecting to the Redis data store.
     * This value is sourced from the application configuration property
     * `spring.data.redis.port` and is intended to configure the Redis connection
     * factory and related components.
     */
    @Value("${spring.data.redis.port}")
    private int redisPort;

    /**
     * Specifies the time-to-live (TTL) for cached entries in Redis in milliseconds.
     * This value determines the lifespan of cache entries before they expire and are evicted.
     * The TTL is configurable through the property `spring.cache.redis.time-to-live`
     * in the application configuration file. If not explicitly set, a default value of 300000ms (5 minutes) is used.
     */
    @Value("${spring.cache.redis.time-to-live:300000}")
    private long ttlMillis;

    /**
     * Creates and configures a {@link LettuceConnectionFactory} for connecting to a Redis instance.
     * This method sets up the connection using a standalone Redis configuration,
     * including host and port details. Password configuration can also be added if necessary.
     *
     * @return an instance of {@link LettuceConnectionFactory} configured to communicate with Redis.
     */
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(redisHost);
        redisConfig.setPort(redisPort);
        // Add password if needed
        // redisConfig.setPassword(RedisPassword.of(redisPassword));

        return new LettuceConnectionFactory(redisConfig);
    }

    /**
     * Configures and provides a {@link RedisTemplate} bean for interacting with Redis.
     * This method customizes the serialization/deserialization mechanisms for keys and values
     * in Redis using {@link StringRedisSerializer} and {@link GenericJackson2JsonRedisSerializer}.
     *
     * @param connectionFactory the factory used to establish a connection to a Redis instance.
     * @return a fully configured {@link RedisTemplate} instance for Redis operations.
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // Use String serializer for keys
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        // Use JSON serializer for values
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        template.afterPropertiesSet();
        return template;
    }

    /**
     * Configures and provides a `CacheManager` bean to manage caching in the application.
     * The method sets up Redis-based caching configurations with default and specific cache settings.
     * It applies a default cache configuration for general use and a separate configuration for auth tokens
     * with a shorter time-to-live (TTL) duration.
     *
     * @param connectionFactory the RedisConnectionFactory used to establish Redis connections.
     * @return a configured instance of CacheManager for managing application-level caches.
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        // Default cache configuration
        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMillis(ttlMillis)).serializeKeysWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
                        new GenericJackson2JsonRedisSerializer())).disableCachingNullValues();

        // Specific cache configuration for auth tokens
        RedisCacheConfiguration authTokenConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(5)) // Auth tokens cached for 5 minutes
                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
                        new GenericJackson2JsonRedisSerializer()));

        return RedisCacheManager.builder(connectionFactory).cacheDefaults(defaultConfig)
                .withCacheConfiguration("auth-tokens", authTokenConfig).build();
    }
}