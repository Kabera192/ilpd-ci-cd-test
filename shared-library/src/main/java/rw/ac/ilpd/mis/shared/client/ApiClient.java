package rw.ac.ilpd.mis.shared.client;


/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project MIS
 * @date 14/07/2025
 */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import rw.ac.ilpd.mis.shared.config.MisConfig;
import rw.ac.ilpd.mis.shared.config.ServiceID;
import rw.ac.ilpd.mis.shared.dto.user.UserAuth;
import rw.ac.ilpd.mis.shared.client.status.OperationStatus;
import rw.ac.ilpd.mis.shared.client.status.ServiceStatus;
import rw.ac.ilpd.mis.shared.util.helpers.JsonUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.MediaType;

public abstract class ApiClient {

    protected String authHeader;
    protected LoadBalancerClient loadBalancer;
    protected ObjectMapper mapper;
    protected HttpClient client;

    private static final Logger logger = LoggerFactory.getLogger(ApiClient.class);

    public void setAuthHeader(String authHeader) {
        this.authHeader = authHeader;
    }

    public void setLoadBalancer(LoadBalancerClient loadBalancer) {
        this.loadBalancer = loadBalancer;
    }

    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public void setClient(HttpClient client) {
        this.client = client;
    }

    protected abstract ServiceID getServiceID();

    protected ServiceID getEndpointServiceID() {
        return getServiceID();
    }

    protected abstract String getServiceURI();

    protected abstract Object createObject(String id);

    protected abstract String getObjectId(int index);

    public OperationStatus insertBootstrapData() {
        throw new UnsupportedOperationException();
    }

    public OperationStatus insertData(int index) {
        String id = getObjectId(index);
        Object object = createObject(id);
        return insertData(id, object);
    }

    public HttpClientResponse getAuthHeader() {
        ServiceInstance instance = loadBalancer.choose(getEndpointServiceID().toString());
        ServiceStatus serviceStatus = new ServiceStatus(getEndpointServiceID().toString());
        if (instance == null) {
            serviceStatus.setServiceHost("Unknown");
            serviceStatus.setServiceTime("Service not available");
            return null;
        }
        String host = instance.getHost();
        int port = instance.getPort();
        try {
            String uri = new StringBuilder().append(MisConfig.AUTH_TOKEN_USER_PATH).toString();
            HttpClientResponse response = client.sendGet(host, port, uri, authHeader, "");
            logger.info("Status code: {} and body: {}", response.getStatus(), response.getBody());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error fetching server status: {}", e.getMessage());
            serviceStatus.setServiceHost(host+":"+port);
            serviceStatus.setServiceTime("Not Reachable: "+e.getMessage());
            return null;
        }
    }

    public UserAuth getLoggedUser(){
        try{
            HttpClientResponse response = getAuthHeader();
            JsonObject fullJson = JsonParser.parseString(response.getBody()).getAsJsonObject();
            JsonObject dataJson = fullJson.getAsJsonObject ("data");
            UserAuth userAuth =  JsonUtility.parse(dataJson.toString(), UserAuth.class);
            return userAuth;
        }catch (Exception ex){
            ex.printStackTrace();
            logger.error("User not found : {}", ex.getMessage());
        }
        return null;
    }


    public OperationStatus insertData(String id, Object object) {
        String uri = getServiceURI() + "/" + id;
        OperationStatus operationStatus = new OperationStatus(false);
        operationStatus.setMessage("Unable to convert object to json: " + object.toString());
        String jsonObject;
        try {
            jsonObject = mapper.writeValueAsString(object);
        } catch (JsonProcessingException jpe) {
            logger.error("Unable to insert data for object {} due to JSON processing", object, jpe);
            return operationStatus;
        }

        if (jsonObject == null) {
            logger.error("Unable to process insert data for object {}", object);
            return operationStatus;
        }

        return insertData(uri, jsonObject);
    }

    protected OperationStatus insertData(String uri, String data) {
        OperationStatus operationStatus = new OperationStatus(false);
        ServiceInstance instance = loadBalancer.choose(getEndpointServiceID().toString());

        if (instance == null) {
            String msg = "Unknown host for: " + getEndpointServiceID();
            logger.error("Service not available: {}", getEndpointServiceID());
            operationStatus.setMessage(msg);
            return operationStatus;
        }

        try {
            HttpClientResponse response = client.sendPut(
                    instance.getHost(),
                    instance.getPort(),
                    uri,
                    authHeader,
                    MediaType.APPLICATION_JSON_VALUE,
                    data
            );
            logger.debug("Data creation response code: {} and body: {}", response.getStatus(), response.getBody());
            operationStatus.setSuccess(true);
            operationStatus.setMessage("Inserted data: " + data);
        } catch (Exception e) {
            logger.error("Error inserting data: {}", data, e);
            operationStatus.setMessage("Error inserting data: " + e.getMessage());
        }

        return operationStatus;
    }

    public OperationStatus deleteData() {
        OperationStatus operationStatus = new OperationStatus(false);
        ServiceInstance instance = loadBalancer.choose(getEndpointServiceID().toString());

        if (instance == null) {
            String msg = "Unknown host for: " + getEndpointServiceID();
            logger.error("Service not available: {}", getEndpointServiceID());
            operationStatus.setMessage(msg);
            return operationStatus;
        }

        try {
            String uri = getServiceURI();
            HttpClientResponse response = client.sendDelete(
                    instance.getHost(),
                    instance.getPort(),
                    uri,
                    authHeader
            );
            logger.info("Data deletion response code: {} and body: {}", response.getStatus(), response.getBody());
            operationStatus.setSuccess(true);
            operationStatus.setMessage("Deleted data");
        } catch (Exception e) {
            logger.error("Error deleting data", e);
            operationStatus.setMessage("Error deleting data: " + e.getMessage());
        }

        return operationStatus;
    }

    public ServiceStatus getStatus() {
        ServiceInstance instance = loadBalancer.choose(getEndpointServiceID().toString());
        ServiceStatus serviceStatus = new ServiceStatus(getEndpointServiceID().toString());

        if (instance == null) {
            serviceStatus.setServiceHost("Unknown");
            serviceStatus.setServiceTime("Service not available");
            return serviceStatus;
        }

        String host = instance.getHost();
        int port = instance.getPort();

        try {
            HttpClientResponse response = client.sendGet(
                    host,
                    port,
                    MisConfig.STATUS_PATH,
                    authHeader,
                    ""
            );
            logger.info("Status code: {} and body: {}", response.getStatus(), response.getBody());
            return new Gson().fromJson(response.getBody(), ServiceStatus.class);
        } catch (Exception e) {
            logger.error("Error fetching server status: {}", e.getMessage());
            serviceStatus.setServiceHost(host + ":" + port);
            serviceStatus.setServiceTime("Not Reachable: " + e.getMessage());
            return serviceStatus;
        }
    }


}
