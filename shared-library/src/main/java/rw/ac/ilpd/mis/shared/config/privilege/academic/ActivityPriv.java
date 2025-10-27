package rw.ac.ilpd.mis.shared.config.privilege.academic;

public class ActivityPriv {

    // ---------- Activities ----------
    public static final String CREATE = "create_activity";
    public static final String CREATE_PATH = "/activities";

    public static final String GET_ALL = "get_all_activities";
    public static final String GET_ALL_PATH = "/activities";

    public static final String GET_BY_ID = "get_activity_by_id";
    public static final String GET_BY_ID_PATH = "/activities/{id}";

    public static final String UPDATE = "update_activity";
    public static final String UPDATE_PATH = "/activities/{id}";

    public static final String DELETE = "delete_activity";
    public static final String DELETE_PATH = "/activities/{id}";

    // ---------- Activity Levels ----------
    public static final String ADD_LEVEL = "add_activity_level";
    public static final String ADD_LEVEL_PATH = "/activities/{id}/activity-levels";

    public static final String GET_LEVELS = "get_activity_levels";
    public static final String GET_LEVELS_PATH = "/activities/{id}/activity-levels";

    public static final String DELETE_LEVEL = "delete_activity_level";
    public static final String DELETE_LEVEL_PATH = "/activities/{id}/activity-levels/{levelId}";

    // ---------- Activity Occurrences ----------
    public static final String ADD_OCCURRENCE = "add_activity_occurrence";
    public static final String ADD_OCCURRENCE_PATH = "/activities/{id}/activity-occurrences";

    public static final String GET_OCCURRENCES = "get_activity_occurrences";
    public static final String GET_OCCURRENCES_PATH = "/activities/{id}/activity-occurrences";

    public static final String DELETE_OCCURRENCE = "delete_activity_occurrence";
    public static final String DELETE_OCCURRENCE_PATH = "/activities/{id}/activity-occurrences/{occurrenceId}";

    // ---------- Attendance Missings ----------
    public static final String ADD_ATTENDANCE_MISSING = "add_attendance_missing";
    public static final String ADD_ATTENDANCE_MISSING_PATH = "/activities/{id}/activity-occurrences/{occurrenceId}/attendance-missings";

    public static final String GET_ATTENDANCE_MISSINGS = "get_attendance_missings";
    public static final String GET_ATTENDANCE_MISSINGS_PATH = "/activities/{id}/activity-occurrences/{occurrenceId}/attendance-missings";

    public static final String DELETE_ATTENDANCE_MISSING = "delete_attendance_missing";
    public static final String DELETE_ATTENDANCE_MISSING_PATH = "/activities/{id}/activity-occurrences/{occurrenceId}/attendance-missings/{missingId}";
}
