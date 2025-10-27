package rw.ac.ilpd.mis.shared.config.privilege.auth;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 16/07/2025
 */

public final class RolePriv {

    public static final String CREATE_ROLE = "create_role";
    public static final String CREATE_ROLE_PATH = "/create";
    public static final String DELETE_ROLE = "delete_role";
    public static final String DELETE_ROLE_PATH = "/delete/{roleId}";
    public static final String EDIT_ROLE = "edit_role";
    public static final String EDIT_ROLE_PATH = "/edit/{roleId}";
    public static final String GET_ROLE = "get_role";
    public static final String GET_ROLE_PATH =  "/{roleId}";
    public static final String GET_ROLE_BY_NAME = "get_role_by_name";
    public static final String GET_ROLE_BY_NAME_PATH =  "/role";
    public static final String GET_ROLES =  "get_all_roles";
    public static final String GET_ROLES_PATH =  "/roles";
    public static final String GET_UNIT_ROLES =  "get_unit_roles";
    public static final String GET_UNIT_ROLES_PATH =  "/unit/{unitId}";
    public static final String GET_SEARCH_ROLE =  "search_role";
    public static final String GET_SEARCH_ROLE_PATH =  "/search";

}
