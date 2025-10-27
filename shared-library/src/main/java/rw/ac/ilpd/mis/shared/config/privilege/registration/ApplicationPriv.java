package rw.ac.ilpd.mis.shared.config.privilege.registration;

/**
 * Privilege constants for Application management in the Registration service.
 * Follows the same pattern as other privilege classes in the shared library.
 */
public final class ApplicationPriv {

    // View operations
    public static final String VIEW_APPLICATION = "view_application";
    public static final String VIEW_APPLICATION_PATH = "/{id}";
    
    public static final String VIEW_ALL_APPLICATIONS = "view_all_applications";
    public static final String VIEW_ALL_APPLICATIONS_PATH = "";
    
    public static final String VIEW_PAGED_APPLICATIONS = "view_paged_applications";
    public static final String VIEW_PAGED_APPLICATIONS_PATH = "/paged";
    
    public static final String VIEW_APPLICATIONS_BY_USER = "view_applications_by_user";
    public static final String VIEW_APPLICATIONS_BY_USER_PATH = "/user/{userId}";
    
    public static final String VIEW_APPLICATIONS_BY_STATUS = "view_applications_by_status";
    public static final String VIEW_APPLICATIONS_BY_STATUS_PATH = "/status/{status}";
    
    public static final String SEARCH_APPLICATIONS = "search_applications";
    public static final String SEARCH_APPLICATIONS_PATH = "/search";
    
    public static final String VIEW_APPLICATIONS_BY_DOCUMENT = "view_applications_by_document";
    public static final String VIEW_APPLICATIONS_BY_DOCUMENT_PATH = "/document/{documentId}";
    
    public static final String VIEW_APPLICATIONS_WITHOUT_DOCUMENTS = "view_applications_without_documents";
    public static final String VIEW_APPLICATIONS_WITHOUT_DOCUMENTS_PATH = "/without-documents";
    
    // Create operations
    public static final String CREATE_APPLICATION = "create_application";
    public static final String CREATE_APPLICATION_PATH = "";
    
    public static final String CREATE_APPLICATIONS_BATCH = "create_applications_batch";
    public static final String CREATE_APPLICATIONS_BATCH_PATH = "/batch";
    
    // Update operations
    public static final String UPDATE_APPLICATION = "update_application";
    public static final String UPDATE_APPLICATION_PATH = "/{id}";
    
    public static final String UPDATE_APPLICATION_STATUS = "update_application_status";
    public static final String UPDATE_APPLICATION_STATUS_PATH = "/{id}/status";
    
    // Delete operations
    public static final String DELETE_APPLICATION = "delete_application";
    public static final String DELETE_APPLICATION_PATH = "/{id}";
    
    // Document operations
    public static final String ADD_DOCUMENT_TO_APPLICATION = "add_document_to_application";
    public static final String ADD_DOCUMENT_TO_APPLICATION_PATH = "/{applicationId}/documents";
    
    public static final String VIEW_APPLICATION_DOCUMENTS = "view_application_documents";
    public static final String VIEW_APPLICATION_DOCUMENTS_PATH = "/{applicationId}/documents";
    
    public static final String REMOVE_DOCUMENT_FROM_APPLICATION = "remove_document_from_application";
    public static final String REMOVE_DOCUMENT_FROM_APPLICATION_PATH = "/{applicationId}/documents/{documentId}";
    
    public static final String VIEW_ALL_DOCUMENTS = "view_all_documents";
    public static final String VIEW_ALL_DOCUMENTS_PATH = "/documents";
    
    public static final String VIEW_DOCUMENT = "view_document";
    public static final String VIEW_DOCUMENT_PATH = "/documents/{documentId}";
    
    public static final String UPDATE_DOCUMENT_STATUS = "update_document_status";
    public static final String UPDATE_DOCUMENT_STATUS_PATH = "/documents/{documentId}/status";
    
    public static final String VIEW_DOCUMENTS_BY_STATUS = "view_documents_by_status";
    public static final String VIEW_DOCUMENTS_BY_STATUS_PATH = "/documents/status/{status}";
    
    // Analytics operations
    public static final String VIEW_APPLICATION_ANALYTICS = "view_application_analytics";
    public static final String VIEW_APPLICATION_ANALYTICS_PATH = "/analytics";
    
    public static final String VIEW_APPLICATION_STATUS_COUNTS = "view_application_status_counts";
    public static final String VIEW_APPLICATION_STATUS_COUNTS_PATH = "/analytics/status-counts";
    
    public static final String VIEW_APPLICATION_INTAKE_COUNTS = "view_application_intake_counts";
    public static final String VIEW_APPLICATION_INTAKE_COUNTS_PATH = "/analytics/intake-counts";
    
    public static final String VIEW_DAILY_APPLICATION_COUNTS = "view_daily_application_counts";
    public static final String VIEW_DAILY_APPLICATION_COUNTS_PATH = "/analytics/daily-counts";
    
    public static final String VIEW_DOCUMENT_STATISTICS = "view_document_statistics";
    public static final String VIEW_DOCUMENT_STATISTICS_PATH = "/analytics/document-statistics";
    public static final String APPLICATIONS_PATH = "/applications";

}