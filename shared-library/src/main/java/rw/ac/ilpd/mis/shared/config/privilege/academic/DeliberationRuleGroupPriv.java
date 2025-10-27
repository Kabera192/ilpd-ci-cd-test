package rw.ac.ilpd.mis.shared.config.privilege.academic;

public class DeliberationRuleGroupPriv {

    // ---------- Deliberation Rule Groups ----------
    public static final String CREATE = "create_deliberation_rule_group";
    public static final String CREATE_PATH = "/deliberation-rule-groups";

    public static final String GET_ALL = "get_all_deliberation_rule_groups";
    public static final String GET_ALL_PATH = "/deliberation-rule-groups";

    public static final String GET_BY_ID = "get_deliberation_rule_group_by_id";
    public static final String GET_BY_ID_PATH = "/deliberation-rule-groups/{id}";

    public static final String UPDATE = "update_deliberation_rule_group";
    public static final String UPDATE_PATH = "/deliberation-rule-groups/{id}";

    public static final String DELETE = "delete_deliberation_rule_group";
    public static final String DELETE_PATH = "/deliberation-rule-groups/{id}";

    // ---------- Deliberation Rule Group Curriculums ----------
    public static final String ADD_CURRICULUM = "add_deliberation_rule_group_curriculum";
    public static final String ADD_CURRICULUM_PATH = "/deliberation-rule-groups/{id}/deliberation-rule-group-curriculums";

    public static final String REMOVE_CURRICULUM = "remove_deliberation_rule_group_curriculum";
    public static final String REMOVE_CURRICULUM_PATH = "/deliberation-rule-groups/{id}/deliberation-rule-group-curriculums/{curriculumId}";

    // ---------- Deliberation Rules Thresholds ----------
    public static final String ADD_THRESHOLD = "add_deliberation_rules_threshold";
    public static final String ADD_THRESHOLD_PATH = "/deliberation-rule-groups/{id}/deliberation-rules-thresholds";

    public static final String REMOVE_THRESHOLD = "remove_deliberation_rules_threshold";
    public static final String REMOVE_THRESHOLD_PATH = "/deliberation-rule-groups/{id}/deliberation-rules-thresholds/{thresholdId}";
}
