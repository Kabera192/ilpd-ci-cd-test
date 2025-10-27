package rw.ac.ilpd.mis.shared.config.privilege.academic;

public class HeadOfModulePriv {

    private HeadOfModulePriv() {} // Prevent instantiation

    public static final String BASE_PATH = "/head-of-modules";

    // Privilege constants
    public static final String CREATE_HOM = "create_head_of_module";
    public static final String GET_HOM_BY_ID = "get_head_of_module_by_id";
    public static final String UPDATE_HOM = "update_head_of_module";
    public static final String UPDATE_TO_DATE = "update_head_of_module_ending_date";
    public static final String DELETE_HOM_BY_ID = "delete_head_of_module";
    public static final String PAGED_LIST_OF_ACTIVE_HOM = "get_paged_list_of_active_head_of_module";
    public static final String LIST_OF_ACTIVE_HOM = "list_of_active_head_of_module";
    public static final String PAGED_LIST_OF_HOM_HISTORY = "list_head_of_module_history";

    // Endpoint paths
    public static final String CREATE_HOM_PATH = "";
    public static final String GET_HOM_BY_ID_PATH = "/{id}";
    public static final String UPDATE_HOM_PATH = "/{id}";
    public static final String UPDATE_TO_DATE_PATH = "/{id}/ending-date";
    public static final String DELETE_HOM_BY_ID_PATH = "/{id}";
    public static final String PAGED_LIST_OF_ACTIVE_HOM_PATH = "/paged";
    public static final String LIST_OF_ACTIVE_HOM_PATH = "";
    public static final String PAGED_LIST_OF_HOM_HISTORY_PATH = "/paged/history";

}
