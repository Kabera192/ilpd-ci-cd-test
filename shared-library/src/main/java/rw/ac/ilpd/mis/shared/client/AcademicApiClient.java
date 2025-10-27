package rw.ac.ilpd.mis.shared.client;

import rw.ac.ilpd.mis.shared.config.MisConfig;
import rw.ac.ilpd.mis.shared.config.ServiceID;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mid
 * @date 15/07/2025
 */
public class AcademicApiClient extends ApiClient{
    @Override
    protected ServiceID getServiceID() {
        return ServiceID.AcademicSvc;
    }

    @Override
    protected String getServiceURI() {
        return MisConfig.ACADEMIC_PATH;
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
