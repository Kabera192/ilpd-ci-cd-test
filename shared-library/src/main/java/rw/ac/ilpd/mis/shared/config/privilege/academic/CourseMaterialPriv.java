package rw.ac.ilpd.mis.shared.config.privilege.academic;

public final class CourseMaterialPriv {
        private CourseMaterialPriv() {}

        public static final String CREATE_COURSE_MATERIAL = "create_course_material";
        public static final String UPDATE_COURSE_MATERIAL = "update_course_material";
        public static final String GET_COURSE_MATERIAL_BY_ID = "get_course_material_by_id";
        public static final String GET_COURSE_MATERIAL_LIST = "get_course_material_list";

        // === Paths ===
        public static final String BASE_PATH = "/course-materials";
        public static final String CREATE_COURSE_MATERIAL_PATH = "";
        public static final String UPDATE_COURSE_MATERIAL_PATH = "/{id}";
        public static final String GET_PAGED_COURSE_MATERIALS_PATH = "";
        public static final String GET_COURSE_MATERIAL_BY_ID_PATH = "/{id}";
        public static final String GET_COURSE_MATERIAL_LIST_PATH = "/find-list";
    }

