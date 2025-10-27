package rw.ac.ilpd.mis.shared.config.privilege.academic;

public final class ModulePriv {
    private ModulePriv() {}
    public static final String BASE_PATH = "/modules";

    // Privilege constants
    public static final String CREATE_MODULE = "create_module";
    public static final String DELETE_MODULE = "delete_module";
    public static final String UPDATE_MODULE_DELETE_STATUS = "update_module_delete_status";
    public static final String GET_MODULE = "get_module";
    public static final String UPDATE_MODULE = "update_module";
    public static final String LIST_MODULES = "list_modules";

    // Paths
    public static final String CREATE_MODULE_PATH = "";
    public static final String DELETE_MODULE_PATH = "/{id}";
    public static final String UPDATE_MODULE_DELETE_STATUS_PATH =  "/{id}";
    public static final String GET_MODULE_PATH = "/{id}";
    public static final String UPDATE_MODULE_PATH = "/{id}";
    public static final String LIST_MODULES_PATH = "";

}
