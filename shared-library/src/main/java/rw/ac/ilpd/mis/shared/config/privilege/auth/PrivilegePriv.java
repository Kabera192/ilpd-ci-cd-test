package rw.ac.ilpd.mis.shared.config.privilege.auth;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 23/07/2025
 */

public final class PrivilegePriv {

    public static final String CREATE_PRIVILEGE = "create_privilege";
    public static final String CREATE_PRIVILEGE_PATH = "/create";
    public static final String DELETE_PRIVILEGE = "delete_privilege";
    public static final String DELETE_PRIVILEGE_PATH = "/delete/{privilegeId}";
    public static final String EDIT_PRIVILEGE = "edit_privilege";
    public static final String EDIT_PRIVILEGE_PATH = "/edit/{privilegeId}";
    public static final String GET_PRIVILEGE = "get_privilege";
    public static final String GET_PRIVILEGE_PATH =  "/{privilegeId}";
    public static final String GET_PRIVILEGE_BY_NAME = "get_privilege_by_name";
    public static final String GET_PRIVILEGE_BY_NAME_PATH =  "/privilege";
    public static final String GET_PRIVILEGES =  "get_all_privileges";
    public static final String GET_PRIVILEGES_PATH =  "/privileges";
    public static final String GET_ROLE_PRIVILEGES =  "get_role_privileges";
    public static final String GET_ROLE_PRIVILEGES_PATH =  "/role/{roleId}";
    public static final String GET_USER_PRIVILEGES =  "get_user_privileges";
    public static final String GET_USER_PRIVILEGES_PATH =  "/user/{userId}";
    public static final String GET_SEARCH_PRIVILEGE =  "search_privilege";
    public static final String GET_SEARCH_PRIVILEGE_PATH =  "/search";

}
