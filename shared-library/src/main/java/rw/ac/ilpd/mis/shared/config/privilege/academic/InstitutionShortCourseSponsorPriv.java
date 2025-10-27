package rw.ac.ilpd.mis.shared.config.privilege.academic;

public class InstitutionShortCourseSponsorPriv {

    public static final String GET_ALL = "get_all_institution_short_course_sponsors";
    public static final String GET_ALL_PATH = "/institution-short-course-sponsors";

    public static final String GET_BY_ID = "get_institution_short_course_sponsor_by_id";
    public static final String GET_BY_ID_PATH = "/institution-short-course-sponsors/{id}";

    public static final String CREATE = "create_institution_short_course_sponsor";
    public static final String CREATE_PATH = "/institution-short-course-sponsors";

    public static final String UPDATE = "update_institution_short_course_sponsor";
    public static final String UPDATE_PATH = "/institution-short-course-sponsors/{id}";

    public static final String PATCH = "patch_institution_short_course_sponsor";
    public static final String PATCH_PATH = "/institution-short-course-sponsors/{id}";

    public static final String DELETE = "delete_institution_short_course_sponsor";
    public static final String DELETE_PATH = "/institution-short-course-sponsors/{id}";
}

