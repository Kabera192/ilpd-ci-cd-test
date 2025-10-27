package rw.ac.ilpd.mis.shared.config.privilege.academic;

public final class ShortCourseTopicPriv {

    private  ShortCourseTopicPriv() {}

    // Base path
    public static final String BASE_PATH = "/short-course-topics";

    // Privileges
    public static final String CREATE = "create_short_course_topic";
    public static final String GET = "get_short_course_topic";
    public static final String LIST_ALL = "list_all_short_course_topics";
    public static final String LIST_PAGED = "list_paged_short_course_topics";
    public static final String UPDATE = "update_short_course_topic";
    public static final String DELETE = "delete_short_course_topic";
    public static final String UNDO_DELETE = "undo_delete_short_course_topic";

    // Paths
    public static final String CREATE_PATH = "";
    public static final String GET_PATH = BASE_PATH + "/{id}";
    public static final String LIST_ALL_PATH = BASE_PATH + "/all";
    public static final String LIST_PAGED_PATH = BASE_PATH;
    public static final String UPDATE_PATH = BASE_PATH + "/{id}";
    public static final String DELETE_PATH = BASE_PATH + "/{id}";
    public static final String UNDO_DELETE_PATH = BASE_PATH + "/{id}";
}
