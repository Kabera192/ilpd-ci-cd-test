package rw.ac.ilpd.mis.shared.config.privilege.academic;

public class StudyModePriv {

    public static final String GET_ALL = "get_all_study_modes";
    public static final String GET_ALL_PATH = "/study-modes";

    public static final String GET_BY_ID = "get_study_mode_by_id";
    public static final String GET_BY_ID_PATH = "/study-modes/{id}";

    public static final String CREATE = "create_study_mode";
    public static final String CREATE_PATH = "/study-modes";

    public static final String UPDATE = "update_study_mode";
    public static final String UPDATE_PATH = "/study-modes/{id}";

    public static final String PATCH = "patch_study_mode";
    public static final String PATCH_PATH = "/study-modes/{id}";

    public static final String DELETE = "delete_study_mode";
    public static final String DELETE_PATH = "/study-modes/{id}";
}
