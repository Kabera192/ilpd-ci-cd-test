package rw.ac.ilpd.mis.shared.config.privilege.inventory;

public class RoomPriv {

    public static final String GET_ALL = "get_all_rooms";
    public static final String GET_ALL_PATH = "/rooms";

    public static final String GET_BY_ID = "get_room_by_id";
    public static final String GET_BY_ID_PATH = "/rooms/{id}";

    public static final String CREATE = "create_room";
    public static final String CREATE_PATH = "/rooms";

    public static final String UPDATE = "update_room";
    public static final String UPDATE_PATH = "/rooms/{id}";

    public static final String PATCH = "patch_room";
    public static final String PATCH_PATH = "/rooms/{id}";

    public static final String DELETE = "delete_room";
    public static final String DELETE_PATH = "/rooms/{id}";
}
