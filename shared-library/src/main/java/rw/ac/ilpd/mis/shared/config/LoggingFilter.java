package rw.ac.ilpd.mis.shared.config;

import jakarta.servlet.*;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 12/07/2025
 */
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = "SYSTEM";
        if (authentication != null && authentication.getPrincipal() != null) {
            userId = authentication.getName();
        }
        MDC.put("userId", userId);

        String ip = "UNKNOWN";
        if (request != null) {
            ip = request.getRemoteAddr();
        }
        MDC.put("remoteIP",ip);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}

    @Override
    public void init(FilterConfig arg0) throws ServletException {}

}
