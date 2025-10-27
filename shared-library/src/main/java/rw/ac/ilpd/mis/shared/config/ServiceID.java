package rw.ac.ilpd.mis.shared.config;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project MIS ILPD
 * @date 12/07/2025
 */
public enum ServiceID {

    AuthSvc("AUTH-SERVICE"), RegistrationSvc("REGISTRATION-SERVICE"), AcademicSvc("ACADEMIC-SERVICE"),
    PaymentSvc("PAYMENT-SERVICE"), NotificationSvc("NOTIFICATION-SERVICE"), ReportingSvc("REPORTING-SERVICE"),
    HostelSvc("HOSTEL-SERVICE"), InventorySvc("INVENTORY-SERVICE"), GatewaySvc("GATEWAY-SERVICE"), DiscoverySvc("DISCOVERY-SERVICE");

    private String serviceId;

    ServiceID(String serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public String toString() {
        return serviceId;
    }
}
