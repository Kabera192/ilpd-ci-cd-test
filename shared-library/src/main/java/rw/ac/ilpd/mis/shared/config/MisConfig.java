package rw.ac.ilpd.mis.shared.config;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project MIS
 * @date 12/07/2025
 */

public class MisConfig {


    public static final String AUTH_TOKEN_USER_PATH = "/api/v1/auth/users/token/user";

    public static final String AUTH_PATH = "/api/v1/auth";

    public static final String NOTIFICATION_PATH ="/api/v1/notification";

    public static final String REGISTRATION_PATH="/api/v1/registration";

    public static final String PAYMENT_PATH ="/api/v1/payment";

    public static final String ACADEMIC_PATH="/api/v1/academic";

    public static final String HOSTELS_PATH="/api/v1/hostel";

    public static final String REPORTING_PATH="/api/v1/reporting";

    public static final String INVENTORY_PATH="/api/v1/inventory";

    public static final String STATUS_PATH = "/status";

    public static final String[] SWAGGER_WHITELIST = {
            // default (no context path)
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/swagger-config",
            // common extras
            "/api-docs/**",
            "/webjars/**"
    };

    static String pref_auth = MisConfig.AUTH_PATH; // e.g. "/api/v1/auth"
    static String pref_reg = MisConfig.REGISTRATION_PATH;
    static String pref_acad =MisConfig.ACADEMIC_PATH;
    public static final String[] PREFIXED = new String[] {
            pref_auth + "/v3/api-docs/**",
            pref_auth + "/v3/api-docs.yaml",
            pref_auth + "/swagger-ui.html",
            pref_auth + "/swagger-ui/**",
            pref_auth + "/v3/api-docs/swagger-config",
            pref_reg + "/v3/api-docs/**",
            pref_reg + "/v3/api-docs.yaml",
            pref_reg + "/swagger-ui.html",
            pref_reg + "/swagger-ui/**",
            pref_reg + "/v3/api-docs/swagger-config",
            pref_acad + "/v3/api-docs/**",
            pref_acad + "/v3/api-docs.yaml",
            pref_acad + "/swagger-ui.html",
            pref_acad + "/swagger-ui/**",
            pref_acad + "/v3/api-docs/swagger-config"
    };
}
