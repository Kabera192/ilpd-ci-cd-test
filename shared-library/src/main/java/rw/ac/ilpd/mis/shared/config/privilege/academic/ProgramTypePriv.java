package rw.ac.ilpd.mis.shared.config.privilege.academic;

public final class ProgramTypePriv
{
    public static final String PROGRAM_TYPE_PATH = "/program-types";

    public static final String CREATE_PROGRAM_TYPE = "create_program_type";
    public static final String CREATE_PROGRAM_TYPE_PATH = "";

    public static final String GET_ALL_PROGRAM_TYPES = "get_all_program_types";
    public static final String GET_ALL_PROGRAM_TYPES_PATH = "";

    public static final String GET_PROGRAM_TYPE = "get_program_type";
    public static final String GET_PROGRAM_TYPE_PATH = "/{id}";

    public static final String UPDATE_PROGRAM_TYPE = "update_program_type";
    public static final String UPDATE_PROGRAM_TYPE_PATH = "/{id}";

    public static final String UPDATE_PROGRAM_TYPE_DELETE_STATUS = "update_program_type_delete_status";
    public static final String UPDATE_PROGRAM_TYPE_DELETE_STATUS_PATH = "/{id}";

    public static final String DELETE_PROGRAM_TYPE = "delete_program_type";
    public static final String DELETE_PROGRAM_TYPE_PATH = "/{id}";
}
