package rw.ac.ilpd.academicservice.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Retryer;
import feign.codec.Decoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component

public class FeignClientConfig implements  RequestInterceptor {

    @Bean
    public Decoder feignDecoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        return new ResponseEntityDecoder(new SpringDecoder(messageConverters));
    }
    @Override
    public void apply(RequestTemplate requestTemplate) {
    requestTemplate.header("Authorization", SecurityContextHolder.getContext().getAuthentication().getDetails().toString());
    }
    @Bean
    public Retryer feignRetryer() {
        // Retryer.Default(period, maxPeriod, maxAttempts)
        return new Retryer.Default(
                100,                // wait 100ms between retries
                TimeUnit.SECONDS.toMillis(1),  // max wait 1s
                5                   // retry up to 5 times
        );
    }
}

