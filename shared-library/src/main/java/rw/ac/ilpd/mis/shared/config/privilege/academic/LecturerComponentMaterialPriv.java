package rw.ac.ilpd.mis.shared.config.privilege.academic;

public final class LecturerComponentMaterialPriv
{
    public static final String LECTURER_COMPONENT_MATERIAL_PATH = "/lecturer-component-materials";

    public static final String CREATE_LECTURER_COMPONENT_MATERIAL = "create_lecturer_component_material";
    public static final String CREATE_LECTURER_COMPONENT_MATERIAL_PATH = "";

    public static final String GET_LECTURER_COMPONENT_MATERIAL = "get_lecturer_component_material";
    public static final String GET_LECTURER_COMPONENT_MATERIAL_PATH = "/{id}";

    public static final String GET_MATERIALS_BY_LECTURER_COMPONENT = "get_materials_by_lecturer_component";
    public static final String GET_MATERIALS_BY_LECTURER_COMPONENT_PATH = "/lecturer-component/{lecturerComponentId}";

    public static final String UPDATE_LECTURER_COMPONENT_MATERIAL = "update_lecturer_component_material";
    public static final String UPDATE_LECTURER_COMPONENT_MATERIAL_PATH = "/{id}";

    public static final String DELETE_LECTURER_COMPONENT_MATERIAL = "delete_lecturer_component_material";
    public static final String DELETE_LECTURER_COMPONENT_MATERIAL_PATH = "/{id}";
}
