package rw.ac.ilpd.mis.shared.util.helpers;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 28/07/2025
 */

public class PagedResponse<T> {
    private List<T> data;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    // Constructors
    public PagedResponse() {}

    public PagedResponse(Page<T> pageResult){
        this.setData(pageResult.getContent());
        this.setPage(pageResult.getNumber());
        this.setSize(pageResult.getSize());
        this.setTotalElements(pageResult.getTotalElements());
        this.setTotalPages(pageResult.getTotalPages());
    }

    public PagedResponse(List<T> content, int page, int size, long totalElements, int totalPages) {
        this.data = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
