package rw.ac.ilpd.mis.shared.util;
public class TextSanitizer {

    /**
     * Fully sanitizes a path by:
     * - Replacing backslashes '\' with '/'
     * - Collapsing multiple slashes to a single slash
     * - Removing trailing slashes
     * - Ensuring a single leading slash
     * - Returning "/" for null, blank, or invalid paths
     *
     * @param path Raw input path (user-generated)
     * @return Sanitized and normalized path (e.g. "/a/b/c")
     */
    public  String sanitizePath(String path) {
        if (path == null || path.isBlank()) {
            return "/";
        }

        // Step 1: Replace all backslashes with forward slashes
        String sanitized = path.replace("\\", "/");

        // Step 2: Collapse multiple forward slashes into one
        sanitized = sanitized.replaceAll("/{2,}", "/");

        // Step 3: Remove trailing slashes (but not root)
        sanitized = sanitized.replaceAll("/+$", "");

        // Step 4: Ensure leading slash
        if (!sanitized.startsWith("/")) {
            sanitized = "/" + sanitized;
        }

        return sanitized;
    }
    public static String cleanExtraSpace(String value) {
        return value == null ? null : value.trim().replaceAll("\\s{2,}", " ");
    }
}
