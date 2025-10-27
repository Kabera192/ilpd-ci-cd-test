package rw.ac.ilpd.mis.shared.config.privilege.academic;

    public final class UserPositionPriv {

        private UserPositionPriv() {}
        public static final String BASE_PATH = "/user-positions";
        // Create a new user position assignment
        public static final String CREATE_USER_POSITION = "create_user_position";
        public static final String CREATE_USER_POSITION_PATH = "";

        // Get all user positions
        public static final String GET_ALL_USER_POSITIONS = "get_all_user_positions";
        public static final String GET_ALL_USER_POSITIONS_PATH = "";

        // Get user positions with pagination
        public static final String GET_USER_POSITIONS_PAGINATED = "get_user_positions_paginated";
        public static final String GET_USER_POSITIONS_PAGINATED_PATH = "/paginated";

        // Get user position by ID
        public static final String GET_USER_POSITION_BY_ID = "get_user_position_by_id";
        public static final String GET_USER_POSITION_BY_ID_PATH = "/{id}";

        // Update user position
        public static final String UPDATE_USER_POSITION = "update_user_position";
        public static final String UPDATE_USER_POSITION_PATH = "/{id}";

        // Delete user position
        public static final String DELETE_USER_POSITION = "delete_user_position";
        public static final String DELETE_USER_POSITION_PATH = "/{id}";

        // Get user positions by user ID
        public static final String GET_USER_POSITIONS_BY_USER_ID = "get_user_positions_by_user_id";
        public static final String GET_USER_POSITIONS_BY_USER_ID_PATH = "/user/{userId}";

        // Get user positions by position ID
        public static final String GET_USER_POSITIONS_BY_POSITION_ID = "get_user_positions_by_position_id";
        public static final String GET_USER_POSITIONS_BY_POSITION_ID_PATH = "/position/{positionId}";

        // Get active user positions
        public static final String GET_ACTIVE_USER_POSITIONS = "get_active_user_positions";
        public static final String GET_ACTIVE_USER_POSITIONS_PATH = "/active";

        // Get expired user positions
        public static final String GET_EXPIRED_USER_POSITIONS = "get_expired_user_positions";
        public static final String GET_EXPIRED_USER_POSITIONS_PATH = "/expired";

        // Get user positions expiring soon
        public static final String GET_USER_POSITIONS_EXPIRING_SOON = "get_user_positions_expiring_soon";
        public static final String GET_USER_POSITIONS_EXPIRING_SOON_PATH = "/expiring-soon";

        // Get current user position
        public static final String GET_CURRENT_USER_POSITION = "get_current_user_position";
        public static final String GET_CURRENT_USER_POSITION_PATH = "/user/{userId}/current";

        // Terminate user position
        public static final String TERMINATE_USER_POSITION = "terminate_user_position";
        public static final String TERMINATE_USER_POSITION_PATH = "/{id}/terminate";
    }


