package rw.ac.ilpd.mis.shared.config.privilege.registration;

/**
 * Privilege constants for academic background operations.
 */
public final class AcademicBackgroundPriv {

    // Base path
    public static final String ACADEMIC_BACKGROUND_PATH = "/academic-backgrounds";

    // Create privileges
    public static final String CREATE_ACADEMIC_BACKGROUND = "create_academic_background";
    public static final String CREATE_ACADEMIC_BACKGROUND_PATH = "";

    // Read privileges
    public static final String GET_ACADEMIC_BACKGROUND = "get_academic_background";
    public static final String GET_ACADEMIC_BACKGROUND_BY_ID = "get_academic_background_by_id";
    public static final String GET_ACADEMIC_BACKGROUND_BY_ID_PATH = "/{id}";

    public static final String GET_ALL_ACADEMIC_BACKGROUNDS = "get_all_academic_backgrounds";
    public static final String GET_ALL_ACADEMIC_BACKGROUNDS_PATH = "";

    // Update privileges
    public static final String UPDATE_ACADEMIC_BACKGROUND = "update_academic_background";
    public static final String UPDATE_ACADEMIC_BACKGROUND_PATH = "/{id}";

    // Delete privileges
    public static final String DELETE_ACADEMIC_BACKGROUND = "delete_academic_background";
    public static final String DELETE_ACADEMIC_BACKGROUND_PATH = "/{id}";

    public static final String DELETE_ACADEMIC_BACKGROUND_BY_APPLICATION = "delete_academic_background_by_application";
    public static final String DELETE_ACADEMIC_BACKGROUND_BY_APPLICATION_PATH = "/application/{applicationId}";

    // Statistics privileges
    public static final String GET_DEGREE_DISTRIBUTION = "get_degree_distribution";
    public static final String GET_DEGREE_DISTRIBUTION_PATH = "/degree-distribution";

    public static final String GET_COUNTRY_DISTRIBUTION = "get_country_distribution";
    public static final String GET_COUNTRY_DISTRIBUTION_PATH = "/country-distribution";
}