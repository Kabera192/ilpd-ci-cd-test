package rw.ac.ilpd.mis.shared.config.privilege.academic;

public final class AssessmentTypePriv {

    private AssessmentTypePriv() {}

    public static final String BASE_PATH = "/assessment-types";

    // Privilege constants
    public static final String CREATE_ASSESSMENT_TYPE = "create_assessment_type";
    public static final String GET_ASSESSMENT_TYPE_BY_ID = "get_assessment_type_by_id";
    public static final String GET_ASSESSMENT_TYPE_BY_ASSESSMENT_GROUP = "get_assessment_type_by_assessment_group";
    public static final String GET_ALL_ASSESSMENT_TYPES = "get_all_assessment_types";
    public static final String UPDATE_ASSESSMENT_TYPE = "update_assessment_type";
    public static final String DELETE_ASSESSMENT_TYPE = "delete_assessment_type";

    // Endpoint paths
    public static final String CREATE_ASSESSMENT_TYPE_PATH = "";
    public static final String GET_ASSESSMENT_TYPE_BY_ID_PATH = "/{id}";
    public static final String GET_ASSESSMENT_TYPE_BY_ASSESSMENT_GROUP_PATH = "/{assessmentGroupId}/assessment-group";
    public static final String GET_ALL_ASSESSMENT_TYPES_PATH = "";
    public static final String UPDATE_ASSESSMENT_TYPE_PATH = "/{id}";
    public static final String DELETE_ASSESSMENT_TYPE_PATH = "/{id}";
}
