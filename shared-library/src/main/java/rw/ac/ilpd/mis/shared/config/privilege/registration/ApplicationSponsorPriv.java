package rw.ac.ilpd.mis.shared.config.privilege.registration;

/**
 * Privilege constants for Application Sponsor management in the Registration service.
 * Follows the same pattern as other privilege classes in the shared library.
 */
public final class ApplicationSponsorPriv {

    // View operations
    public static final String VIEW_APPLICATION_SPONSOR = "view_application_sponsor";
    public static final String VIEW_APPLICATION_SPONSOR_PATH = "/{id}";

    public static final String VIEW_ALL_APPLICATION_SPONSORS = "view_all_application_sponsors";
    public static final String VIEW_ALL_APPLICATION_SPONSORS_PATH = "";

    public static final String VIEW_PAGED_APPLICATION_SPONSORS = "view_paged_application_sponsors";
    public static final String VIEW_PAGED_APPLICATION_SPONSORS_PATH = "/paged";

    public static final String VIEW_APPLICATION_SPONSORS_BY_APPLICATION = "view_application_sponsors_by_application";
    public static final String VIEW_APPLICATION_SPONSORS_BY_APPLICATION_PATH = "/application/{applicationId}";

    public static final String VIEW_APPLICATION_SPONSORS_BY_SPONSOR = "view_application_sponsors_by_sponsor";
    public static final String VIEW_APPLICATION_SPONSORS_BY_SPONSOR_PATH = "/sponsor/{sponsorId}";

    // Create operations
    public static final String CREATE_APPLICATION_SPONSOR = "create_application_sponsor";
    public static final String CREATE_APPLICATION_SPONSOR_PATH = "";

    // Update operations
    public static final String UPDATE_APPLICATION_SPONSOR = "update_application_sponsor";
    public static final String UPDATE_APPLICATION_SPONSOR_PATH = "/{id}";

    // Delete operations
    public static final String DELETE_APPLICATION_SPONSOR = "delete_application_sponsor";
    public static final String DELETE_APPLICATION_SPONSOR_PATH = "/{id}";

    public static final String DELETE_APPLICATION_SPONSORS_BY_APPLICATION =
            "delete_application_sponsors_by_application";
    public static final String DELETE_APPLICATION_SPONSORS_BY_APPLICATION_PATH = "/application/{applicationId}";

    public static final String DELETE_APPLICATION_SPONSORS_BY_SPONSOR = "delete_application_sponsors_by_sponsor";
    public static final String DELETE_APPLICATION_SPONSORS_BY_SPONSOR_PATH = "/sponsor/{sponsorId}";

    // Analytics operations
    public static final String VIEW_SPONSOR_APPLICATION_COUNTS = "view_sponsor_application_counts";
    public static final String VIEW_SPONSOR_APPLICATION_COUNTS_PATH = "/analytics/sponsor-application-counts";

    public static final String VIEW_APPLICATION_SPONSOR_COUNTS = "view_application_sponsor_counts";
    public static final String VIEW_APPLICATION_SPONSOR_COUNTS_PATH = "/analytics/application-sponsor-counts";
    public static final String SPONSORS = "/application-sponsors";
    ;
}