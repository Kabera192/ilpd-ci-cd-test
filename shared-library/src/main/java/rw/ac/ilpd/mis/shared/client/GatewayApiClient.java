package rw.ac.ilpd.mis.shared.client;

import rw.ac.ilpd.mis.shared.config.ServiceID;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 15/07/2025
 */
public class GatewayApiClient extends ApiClient {

    @Override
    protected ServiceID getServiceID() {
        return ServiceID.GatewaySvc;
    }

    @Override
    protected String getServiceURI() {
        throw new UnsupportedOperationException();
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
