package rw.ac.ilpd.mis.shared.config.privilege.academic;

/**
 * Privilege constants for the intake coordinator from the academic service.
 * Follows the same pattern as other privilege classes in the shared library.
 * */
public final class IntakeCoordinatorPriv
{
    public static final String CREATE_INTAKE_COORDINATOR = "create_intake_coordinator";
    public static final String CREATE_INTAKE_COORDINATOR_PATH = "";

    public static final String GET_ALL_INTAKE_COORDINATORS = "get_all_intake_coordinators";
    public static final String GET_ALL_INTAKE_COORDINATORS_PATH = "";

    public static final String GET_INTAKE_COORDINATOR = "get_intake_coordinator";
    public static final String GET_INTAKE_COORDINATOR_PATH = "/{id}";

    public static final String UPDATE_INTAKE_COORDINATOR = "update_intake_coordinator";
    public static final String UPDATE_INTAKE_COORDINATOR_PATH = "/{id}";

    public static final String DELETE_INTAKE_COORDINATOR = "delete_intake_coordinator";
    public static final String DELETE_INTAKE_COORDINATOR_PATH = "/{id}";

    public static final String INTAKE_COORDINATOR_PATH = "/intake-coordinators";
}
