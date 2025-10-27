package rw.ac.ilpd.mis.shared.config.privilege.academic;

public class StudyModeSessionPriv {

    public static final String GET_ALL = "get_all_study_mode_sessions";
    public static final String GET_ALL_PATH = "/study-mode-sessions";

    public static final String GET_BY_ID = "get_study_mode_session_by_id";
    public static final String GET_BY_ID_PATH = "/study-mode-sessions/{id}";

    public static final String CREATE = "create_study_mode_session";
    public static final String CREATE_PATH = "/study-mode-sessions";

    public static final String UPDATE = "update_study_mode_session";
    public static final String UPDATE_PATH = "/study-mode-sessions/{id}";

    public static final String PATCH = "patch_study_mode_session";
    public static final String PATCH_PATH = "/study-mode-sessions/{id}";

    public static final String DELETE = "delete_study_mode_session";
    public static final String DELETE_PATH = "/study-mode-sessions/{id}";
}
