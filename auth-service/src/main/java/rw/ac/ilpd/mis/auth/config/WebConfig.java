package rw.ac.ilpd.mis.auth.config;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 24/07/2024
 */
import org.springframework.beans.factory.annotation.Autowired;
import rw.ac.ilpd.mis.shared.config.MisConfig;
import rw.ac.ilpd.mis.shared.security.AuthenticationInterceptor;
import rw.ac.ilpd.mis.shared.security.AuthorizationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new AuthorizationInterceptor())
                .order(2)
                .addPathPatterns(MisConfig.AUTH_PATH +"/**")
                .addPathPatterns("/**");
    }
}