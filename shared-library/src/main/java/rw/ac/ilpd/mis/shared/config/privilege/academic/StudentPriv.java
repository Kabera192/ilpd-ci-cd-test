package rw.ac.ilpd.mis.shared.config.privilege.academic;

public class StudentPriv {

    public static final String GET_ALL_STUDENTS = "get_all_students";
    public static final String GET_ALL_STUDENTS_PATH = "/students";

    public static final String GET_STUDENT_BY_ID = "get_student_by_id";
    public static final String GET_STUDENT_BY_ID_PATH = "/students/{id}";

    public static final String CREATE_STUDENT = "create_student";
    public static final String CREATE_STUDENT_PATH = "/students";

    public static final String UPDATE_STUDENT = "update_student";
    public static final String UPDATE_STUDENT_PATH = "/students/{id}";

    public static final String PATCH_STUDENT = "patch_student";
    public static final String PATCH_STUDENT_PATH = "/students/{id}";

    public static final String DELETE_STUDENT = "delete_student";
    public static final String DELETE_STUDENT_PATH = "/students/{id}";
}

