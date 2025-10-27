package rw.ac.ilpd.mis.shared.client;

import rw.ac.ilpd.mis.shared.client.ApiClient;
import rw.ac.ilpd.mis.shared.config.MisConfig;
import rw.ac.ilpd.mis.shared.config.ServiceID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 15/07/2025
 */
public class UserAuthApiClient extends ApiClient {

    @Override
    protected ServiceID getServiceID() {
        return ServiceID.AuthSvc;
    }

    private static final Logger logger = LoggerFactory.getLogger(UserAuthApiClient.class);

    @Override
    protected String getServiceURI() {
        return MisConfig.AUTH_PATH;
    }

    @Override
    protected Object createObject(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected String getObjectId(int index) {
        throw new UnsupportedOperationException();
    }


}
