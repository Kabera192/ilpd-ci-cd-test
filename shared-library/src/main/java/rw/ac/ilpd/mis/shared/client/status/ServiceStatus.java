package rw.ac.ilpd.mis.shared.client.status;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 14/07/2025
 */
public class ServiceStatus implements Serializable {
    private String serviceId;
    private String serviceHost;
    private String serviceTime;

    public ServiceStatus() {
        this.serviceId = "Uninitialized";
        this.serviceHost = "Not Attempted";
        this.serviceTime = "Not Attempted";
    }

    public ServiceStatus(String serviceId) {
        this();
        this.serviceId = serviceId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceHost() {
        return serviceHost;
    }

    public void setServiceHost(String serviceHost) {
        this.serviceHost = serviceHost;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    @Override
    public String toString() {
        return "{" +
                "\"serviceId\":" + (serviceId == null ? "null" : "\"" + serviceId + "\"") + ", " +
                "\"serviceHost\":" + (serviceHost == null ? "null" : "\"" + serviceHost + "\"") + ", " +
                "\"serviceTime\":" + (serviceTime == null ? "null" : "\"" + serviceTime + "\"") +
                "}";
    }
}
