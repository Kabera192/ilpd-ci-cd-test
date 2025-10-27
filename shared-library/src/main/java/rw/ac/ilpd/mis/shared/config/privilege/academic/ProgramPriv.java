package rw.ac.ilpd.mis.shared.config.privilege.academic;

/**
 * Privilege constants for management of the Program resource in the academic service.
 * Follows the same pattern as other privilege classes in the shared library.
 */
public final class ProgramPriv
{
    public static final String PROGRAM_PATH = "/programs";

    public static final String CREATE_PROGRAM = "create_program";
    public static final String CREATE_PROGRAM_PATH = "";

    public static final String GET_ALL_PROGRAMS = "get_all_programs";
    public static final String GET_ALL_PROGRAMS_PATH = "";

    public static final String GET_PROGRAM = "get_program";
    public static final String GET_PROGRAM_PATH = "/{id}";

    public static final String UPDATE_PROGRAM = "update_program";
    public static final String UPDATE_PROGRAM_PATH = "/{id}";

    public static final String UPDATE_PROGRAM_DELETE_STATUS = "update_program_delete_status";
    public static final String UPDATE_PROGRAM_DELETE_STATUS_PATH = "/{id}";

    public static final String DELETE_PROGRAM = "delete_program";
    public static final String DELETE_PROGRAM_PATH = "/{id}";
}
