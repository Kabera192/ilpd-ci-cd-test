package rw.ac.ilpd.mis.shared.config.privilege.academic;

public class CurriculumPriv {

    public static final String GET_ALL = "get_all_curriculums";
    public static final String GET_ALL_PATH = "/curriculums";

    public static final String GET_ALL_ALL = "get_all_all_curriculums";
    public static final String GET_ALL_ALL_PATH = "/curriculums/all";

    public static final String GET_BY_ID = "get_curriculum_by_id";
    public static final String GET_BY_ID_PATH = "/curriculums/{id}";

    public static final String SET_INACTIVE = "set_curriculum_inactive";
    public static final String SET_INACTIVE_PATH = "/curriculums/{id}/set-inactive";

    public static final String CREATE = "create_curriculum";
    public static final String CREATE_PATH = "/curriculums";

    public static final String UPDATE = "update_curriculum";
    public static final String UPDATE_PATH = "/curriculums/{id}";

    public static final String PATCH = "patch_curriculum";
    public static final String PATCH_PATH = "/curriculums/{id}";

    public static final String DELETE = "delete_curriculum";
    public static final String DELETE_PATH = "/curriculums/{id}";
}
