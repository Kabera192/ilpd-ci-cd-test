package rw.ac.ilpd.mis.shared.config.privilege.academic;

public class CurriculumModulePriv {

    // Get all curriculum modules (with pagination)
    public static final String GET_CURRICULUM_MODULES = "get_all_curriculum_modules";
    public static final String GET_CURRICULUM_MODULES_PATH = "/curriculum-modules";

    // Get a curriculum module by ID
    public static final String GET_CURRICULUM_MODULE_BY_ID = "get_curriculum_module_by_id";
    public static final String GET_CURRICULUM_MODULE_BY_ID_PATH = "/curriculum-modules/{id}";

    // Create a new curriculum module
    public static final String CREATE_CURRICULUM_MODULE = "create_curriculum_module";
    public static final String CREATE_CURRICULUM_MODULE_PATH = "/curriculum-modules";

    // Update an existing curriculum module (full update)
    public static final String UPDATE_CURRICULUM_MODULE = "update_curriculum_module";
    public static final String UPDATE_CURRICULUM_MODULE_PATH = "/curriculum-modules/{id}";

    // Partially update a curriculum module (PATCH)
    public static final String PATCH_CURRICULUM_MODULE = "patch_curriculum_module";
    public static final String PATCH_CURRICULUM_MODULE_PATH = "/curriculum-modules/{id}";

    // Delete a curriculum module
    public static final String DELETE_CURRICULUM_MODULE = "delete_curriculum_module";
    public static final String DELETE_CURRICULUM_MODULE_PATH = "/curriculum-modules/{id}";
}
