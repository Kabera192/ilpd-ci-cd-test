package rw.ac.ilpd.mis.shared.config.privilege.auth;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project misold
 * @date 07/08/2025
 */
public class UserPriv {

    public static final String GET_USERS =  "get_all_users";
    public static final String GET_USERS_PATH =  "/users";
    public static final String GET_USER_BY_ID =   "get_user_by_id";
    public static final String GET_USER_BY_ID_PATH =  "/user/{userId}";
    public static final String GET_USER_BY_EMAIL =   "get_user_by_email";
    public static final String GET_USER_BY_EMAIL_PATH =  "/email/{email}";
    public static final String GET_USER_BY_USERNAME =  "get_user_by_username";
    public static final String GET_USER_BY_USERNAME_PATH =  "/username/{username}";
    public static final String GET_UNIT_USERS = "get_unit_users";
    public static final String GET_UNIT_USERS_PATH = "/unit/{unitId}";
    public static final String GET_ROLE_USERS = "get_role_users";
    public static final String GET_ROLE_USERS_PATH = "/role/{roleId}";
    public static final String UPDATE_USER = "update_user";
    public static final String UPDATE_USER_PATH = "/edit/{userId}";
    public static final String CREATE_USER = "create_user";
    public static final String CREATE_USER_PATH = "/create";
}
