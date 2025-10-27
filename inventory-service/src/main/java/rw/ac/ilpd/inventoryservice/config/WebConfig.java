package rw.ac.ilpd.inventoryservice.config;

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
 * @date 25/08/2025
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//
        registry.addInterceptor(authenticationInterceptor)
                .order(1)
                .addPathPatterns("/**")
                .addPathPatterns("/inventory/**")
                .addPathPatterns(MisConfig.INVENTORY_PATH)
                .addPathPatterns(MisConfig.INVENTORY_PATH +"/**")
                .excludePathPatterns(MisConfig.SWAGGER_WHITELIST)
                .excludePathPatterns(MisConfig.PREFIXED);

        registry.addInterceptor(new AuthorizationInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .addPathPatterns("/inventory/**")
                .addPathPatterns(MisConfig.INVENTORY_PATH +"/**")
                .excludePathPatterns(MisConfig.SWAGGER_WHITELIST)
                .excludePathPatterns(MisConfig.PREFIXED);
    }
}
