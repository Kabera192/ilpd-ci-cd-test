package rw.ac.ilpd.mis.shared.config.privilege.academic;

public final class ProhibitedTransferCouplePriv {


    private ProhibitedTransferCouplePriv() {}

    public static final String BASE_PATH = "/prohibited-transfer-couples";

    // Privilege names
    public static final String CREATE   = "create_prohibited_transfer_couple";
    public static final String READ     = "get_prohibited_transfer_couple";
    public static final String READ_ALL = "list_all_prohibited_transfer_couples";
    public static final String UPDATE   = "update_prohibited_transfer_couple";
    public static final String DELETE   = "delete_prohibited_transfer_couple";
    public static final String READ_ALL_ARCHIVE   = "list_all_archived_prohibited_transfer_couples";
    public static final String UPDATE_DELETE_STATUS ="update_delete_status_prohibited_transfer_couple" ;

    //Paths
    public static final String CREATE_PATH   = "";
    public static final String READ_PATH     = "/{id}";
    public static final String READ_ALL_PATH = "";
    public static final String UPDATE_PATH   =  "/{id}";
    public static final String DELETE_PATH   = "/{id}";
    public static final String READ_ALL_ARCHIVE_PATH ="/archives" ;
    public static final String UPDATE_DELETE_STATUS_PATH ="/{id}" ;

}
