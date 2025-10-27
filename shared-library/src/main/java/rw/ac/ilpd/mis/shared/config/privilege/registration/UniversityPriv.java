package rw.ac.ilpd.mis.shared.config.privilege.registration;

/**
 * Privilege constants for University management in the Registration service.
 * Follows the same pattern as other privilege classes in the shared library.
 */
public final class UniversityPriv {

    // View operations
    public static final String VIEW_UNIVERSITY = "view_university";
    public static final String VIEW_UNIVERSITY_PATH = "/{id}";
    
    public static final String VIEW_ALL_UNIVERSITIES = "view_all_universities";
    public static final String VIEW_ALL_UNIVERSITIES_PATH = "";
    
    public static final String VIEW_PAGED_UNIVERSITIES = "view_paged_universities";
    public static final String VIEW_PAGED_UNIVERSITIES_PATH = "/paged";
    
    public static final String SEARCH_UNIVERSITIES = "search_universities";
    public static final String SEARCH_UNIVERSITIES_PATH = "/search";
    
    public static final String VIEW_UNIVERSITIES_BY_COUNTRY = "view_universities_by_country";
    public static final String VIEW_UNIVERSITIES_BY_COUNTRY_PATH = "/country/{country}";
    
    // Create operations
    public static final String CREATE_UNIVERSITY = "create_university";
    public static final String CREATE_UNIVERSITY_PATH = "";
    
    // Update operations
    public static final String UPDATE_UNIVERSITY = "update_university";
    public static final String UPDATE_UNIVERSITY_PATH = "/{id}";
    
    // Delete operations
    public static final String DELETE_UNIVERSITY = "delete_university";
    public static final String DELETE_UNIVERSITY_PATH = "/{id}";
    
    // Analytics operations
    public static final String VIEW_ANALYTICS = "view_analytics";
    public static final String VIEW_COUNTRY_COUNTS_PATH = "/analytics/country-counts";
    public static final String UNIVERSITY_PATH = "/universities";
}