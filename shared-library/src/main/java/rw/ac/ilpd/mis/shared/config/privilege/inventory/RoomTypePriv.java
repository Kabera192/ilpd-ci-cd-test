package rw.ac.ilpd.mis.shared.config.privilege.inventory;

public class RoomTypePriv {

    public static final String GET_ALL = "get_all_room_types";
    public static final String GET_ALL_PATH = "/room-types";

    public static final String GET_BY_ID = "get_room_type_by_id";
    public static final String GET_BY_ID_PATH = "/room-types/{id}";

    public static final String CREATE = "create_room_type";
    public static final String CREATE_PATH = "/room-types";

    public static final String UPDATE = "update_room_type";
    public static final String UPDATE_PATH = "/room-types/{id}";

    public static final String DELETE = "delete_room_type";
    public static final String DELETE_PATH = "/room-types/{id}";
}
