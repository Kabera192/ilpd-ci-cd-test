package rw.ac.ilpd.mis.auth.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Retryer;
import feign.codec.Decoder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RequiredArgsConstructor
public class FeignClientConfig implements  RequestInterceptor {
    private final HttpServletRequest request;

    @Bean
    public Decoder feignDecoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        return new ResponseEntityDecoder(new SpringDecoder(messageConverters));
    }
    @Override
    public void apply(RequestTemplate requestTemplate) {
        String authHeader = request.getHeader("Authorization");
        log.info("authHeader: {}", authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            requestTemplate.header("Authorization", authHeader);
        }
    }
    @Bean
    public Retryer feignRetryer() {
        // Retryer.Default(period, maxPeriod, maxAttempts)
        return new Retryer.Default(
                100,
                TimeUnit.SECONDS.toMillis(1),
                5
        );
    }
}

