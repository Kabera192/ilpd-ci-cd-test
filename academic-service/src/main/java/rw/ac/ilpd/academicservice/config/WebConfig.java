package rw.ac.ilpd.academicservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import rw.ac.ilpd.mis.shared.config.MisConfig;
import rw.ac.ilpd.mis.shared.security.AuthenticationInterceptor;
import rw.ac.ilpd.mis.shared.security.AuthorizationInterceptor;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 18/08/2025
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(authenticationInterceptor)
                .order(1)
                .addPathPatterns("/**")
                .addPathPatterns("/academic/**")
                .addPathPatterns(MisConfig.ACADEMIC_PATH)
                .addPathPatterns(MisConfig.ACADEMIC_PATH +"/**")
                .excludePathPatterns(MisConfig.SWAGGER_WHITELIST)
                .excludePathPatterns(MisConfig.PREFIXED);

        registry.addInterceptor(new AuthorizationInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .addPathPatterns("/academic/**")
                .addPathPatterns(MisConfig.ACADEMIC_PATH +"/**")
                .excludePathPatterns(MisConfig.SWAGGER_WHITELIST)
                .excludePathPatterns(MisConfig.PREFIXED);
    }
}
