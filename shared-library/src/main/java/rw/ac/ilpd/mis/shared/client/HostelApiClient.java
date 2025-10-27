package rw.ac.ilpd.mis.shared.client;

import rw.ac.ilpd.mis.shared.config.MisConfig;
import rw.ac.ilpd.mis.shared.config.ServiceID;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 15/07/2025
 */
public class HostelApiClient extends ApiClient{
    @Override
    protected ServiceID getServiceID() {
        return ServiceID.HostelSvc;
    }

    @Override
    protected String getServiceURI() {
        return MisConfig.HOSTELS_PATH;
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
