package rw.ac.ilpd.registrationservice.exception;

public class DownstreamServiceException extends RuntimeException {
    private final int httpStatus;
    private final String responseBody;
    private final String serviceName;

    public DownstreamServiceException(int httpStatus, String responseBody, String serviceName) {
        super(String.format("Error from %s service: HTTP %d", serviceName, httpStatus));
        this.httpStatus = httpStatus;
        this.responseBody = responseBody;
        this.serviceName = serviceName;
    }

    public int getHttpStatus() { return httpStatus; }
    public String getResponseBody() { return responseBody; }
    public String getServiceName() { return serviceName; }
}