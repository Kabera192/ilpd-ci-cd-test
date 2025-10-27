package rw.ac.ilpd.registrationservice.mapper;

import org.springframework.stereotype.Component;
import rw.ac.ilpd.sharedlibrary.dto.application.PagedResponse;

import java.util.List;

/**
 * A mapper component responsible for constructing paginated responses for a given set of data.
 * <p>
 * This class provides a utility method to transform a list of content items and pagination
 * details such as total elements, current page, and page size into a structured paginated response.
 */
@Component
public class PaginationMapper {

    /**
     * Converts a list of content and pagination details into a paged response object.
     *
     * @param <T>           the type of the items in the content
     * @param content       the list of items to be included in the paged response
     * @param totalElements the total number of elements across all pages
     * @param currentPage   the index of the current page (zero-based)
     * @param pageSize      the number of elements per page
     * @return a {@code PagedResponse<T>} containing the paginated data and associated metadata
     */
    public <T> PagedResponse<T> toPagedResponse(List<T> content, long totalElements, int currentPage, int pageSize) {
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);

        return PagedResponse.<T>builder().content(content).totalElements((int) totalElements).totalPages(totalPages)
                .currentPage(currentPage).pageSize(pageSize).hasNext(currentPage < totalPages - 1)
                .hasPrevious(currentPage > 0).build();
    }
}
