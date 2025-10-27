package rw.ac.ilpd.mis.shared.config.privilege.academic;

public class IntakeAssessmentGroupPriv {

    public static final String CREATE = "create_intake_assessment_group";
    public static final String CREATE_PATH = "/intake-assessment-groups";

    public static final String GET_BY_ID = "get_intake_assessment_group_by_id";
    public static final String GET_BY_ID_PATH = "/intake-assessment-groups/{id}";

    public static final String GET_BY_INTAKE_AND_COMPONENT = "get_intake_assessment_groups_by_intake_and_component";
    public static final String GET_BY_INTAKE_AND_COMPONENT_PATH = "/intake-assessment-groups/intake/{intakeId}/component/{componentId}";

    public static final String UPDATE = "update_intake_assessment_group";
    public static final String UPDATE_PATH = "/intake-assessment-groups/{id}";

    public static final String DELETE = "delete_intake_assessment_group";
    public static final String DELETE_PATH = "/intake-assessment-groups/{id}";

    public static final String ADD_STUDENT = "add_student_to_intake_assessment_group";
    public static final String ADD_STUDENT_PATH = "/intake-assessment-groups/{id}/students";

    public static final String REMOVE_STUDENT = "remove_student_from_intake_assessment_group";
    public static final String REMOVE_STUDENT_PATH = "/intake-assessment-groups/{id}/students/{studentId}";

    public static final String UPDATE_LEADERSHIP = "update_student_leadership_in_intake_assessment_group";
    public static final String UPDATE_LEADERSHIP_PATH = "/intake-assessment-groups/{id}/students/{studentId}/leadership";
}
