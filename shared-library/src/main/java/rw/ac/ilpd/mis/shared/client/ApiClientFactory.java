package rw.ac.ilpd.mis.shared.client;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project MIS
 * @date 15/07/2025
 */
import com.fasterxml.jackson.databind.ObjectMapper;
import rw.ac.ilpd.mis.shared.config.ServiceID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;

@Component
public class ApiClientFactory {

    @Autowired
    private LoadBalancerClient loadBalancer;  // still valid in Spring Cloud LoadBalancer

    @Value("${GatewaySvc.client.cp.size:100}")
    private int httpClientPoolSize;

    public ApiClient createApiClient(ServiceID serviceID, String authHeader) {
        ApiClient apiClient = switch (serviceID) {
            case AuthSvc -> new UserAuthApiClient();
            case RegistrationSvc -> new RegistrationApiClient();
            case AcademicSvc -> new AcademicApiClient();
            case PaymentSvc -> new PaymentApiClient();
            case ReportingSvc -> new ReportingApiClient();
            case HostelSvc -> new HostelApiClient();
            case InventorySvc -> new InventoryApiClient();
            case GatewaySvc -> new GatewayApiClient();
            default -> null;
        };

        if (apiClient != null) {
            apiClient.setAuthHeader(authHeader);
            apiClient.setLoadBalancer(loadBalancer);
            apiClient.setMapper(new ObjectMapper());
            apiClient.setClient(new HttpClient(httpClientPoolSize));
        }

        return apiClient;
    }
}
