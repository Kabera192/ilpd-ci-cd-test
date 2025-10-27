package rw.ac.ilpd.mis.shared.config.privilege.academic;

public final class ShortCourseTopicLecturerEvaluationPriv {

    private ShortCourseTopicLecturerEvaluationPriv() {}
    // Base path
    public static final String BASE_PATH = "/short-course-topic-lecture-evaluations";

    // Privilege names
    public static final String CREATE_EVALUATION = "create_evaluation";
    public static final String GET_EVALUATION = "get_evaluation";

    // Paths
    public static final String CREATE_EVALUATION_PATH = "";
    public static final String GET_EVALUATION_PATH = BASE_PATH + "/{id}";
}
