package rw.ac.ilpd.mis.shared.config.privilege.reporting;

public class EvaluationFormPriv {

    // ------------------ FORM PRIVILEGES ------------------
    public static final String CREATE = "create_evaluation_form";
    public static final String CREATE_PATH = "/evaluation-forms";

    public static final String GET_BY_ID = "get_evaluation_form_by_id";
    public static final String GET_BY_ID_PATH = "/evaluation-forms/{id}";

    public static final String GET_ALL = "get_all_evaluation_forms";
    public static final String GET_ALL_PATH = "/evaluation-forms";

    public static final String DELETE = "delete_evaluation_form";
    public static final String DELETE_PATH = "/evaluation-forms/{id}";


    // ------------------ USER FILLER PRIVILEGES ------------------
    public static final String ADD_USER_FILLER = "add_user_filler_to_evaluation_form";
    public static final String ADD_USER_FILLER_PATH = "/evaluation-forms/{formId}/user-fillers";

    public static final String UPDATE_USER_FILLER = "update_user_filler_in_evaluation_form";
    public static final String UPDATE_USER_FILLER_PATH = "/evaluation-forms/{formId}/user-fillers/{fillerId}";

    public static final String DELETE_USER_FILLER = "delete_user_filler_from_evaluation_form";
    public static final String DELETE_USER_FILLER_PATH = "/evaluation-forms/{formId}/user-fillers/{fillerId}";

    public static final String LIST_USER_FILLERS = "list_user_fillers_for_evaluation_form";
    public static final String LIST_USER_FILLERS_PATH = "/evaluation-forms/{formId}/user-fillers";


    // ------------------ ANSWER PRIVILEGES ------------------
    public static final String ADD_ANSWER = "add_answer_to_evaluation_form";
    public static final String ADD_ANSWER_PATH = "/evaluation-forms/{formId}/answers";

    public static final String UPDATE_ANSWER = "update_answer_in_evaluation_form";
    public static final String UPDATE_ANSWER_PATH = "/evaluation-forms/{formId}/answers/{answerId}";

    public static final String DELETE_ANSWER = "delete_answer_from_evaluation_form";
    public static final String DELETE_ANSWER_PATH = "/evaluation-forms/{formId}/answers/{answerId}";

    public static final String LIST_ANSWERS = "list_answers_for_evaluation_form";
    public static final String LIST_ANSWERS_PATH = "/evaluation-forms/{formId}/answers";
}
