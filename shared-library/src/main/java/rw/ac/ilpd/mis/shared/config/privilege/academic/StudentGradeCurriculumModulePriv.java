package rw.ac.ilpd.mis.shared.config.privilege.academic;

public final class StudentGradeCurriculumModulePriv {

    private StudentGradeCurriculumModulePriv() {}
    public static final String BASE_PATH = "/student-grade-curriculum-modules";

    // Privilege constants
    public static final String CREATE_STUDENT_GRADE_CURRICULUM_MODULE = "create_student_grade_curriculum_module";
    public static final String CREATE_STUDENT_GRADE_CURRICULUM_MODULE_LIST = "create_student_grade_curriculum_module_list";
    public static final String GET_STUDENT_GRADE_CURRICULUM_MODULE_BY_ID = "get_student_grade_curriculum_module_by_id";
    public static final String GET_ALL_STUDENT_GRADE_CURRICULUM_MODULES = "get_all_student_grade_curriculum_modules";
    public static final String UPDATE_STUDENT_GRADE_CURRICULUM_MODULE = "update_student_grade_curriculum_module";
    public static final String DELETE_STUDENT_GRADE_CURRICULUM_MODULE = "delete_student_grade_curriculum_module";

    // Paths for endpoints
    public static final String CREATE_STUDENT_GRADE_CURRICULUM_MODULE_PATH = "";
    public static final String CREATE_STUDENT_GRADE_CURRICULUM_MODULE_LIST_PATH = "/create-list";
    public static final String GET_STUDENT_GRADE_CURRICULUM_MODULE_BY_ID_PATH = "/{id}";
    public static final String GET_ALL_STUDENT_GRADE_CURRICULUM_MODULES_PATH = "/all";
    public static final String UPDATE_STUDENT_GRADE_CURRICULUM_MODULE_PATH = "/{id}";
    public static final String DELETE_STUDENT_GRADE_CURRICULUM_MODULE_PATH = "/{id}";
}
