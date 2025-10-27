package rw.ac.ilpd.mis.shared.client;

import rw.ac.ilpd.mis.shared.config.MisConfig;
import rw.ac.ilpd.mis.shared.config.ServiceID;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project MIS
 * @date 15/07/2025
 */
public class RegistrationApiClient extends ApiClient{

    @Override
    protected ServiceID getServiceID() {
        return ServiceID.RegistrationSvc;
    }

    @Override
    protected String getServiceURI() {
        return MisConfig.REGISTRATION_PATH;
    }

    @Override
    protected Object createObject(String id) {
        return null;
    }

    @Override
    protected String getObjectId(int index) {
        return null;
    }
}
