package cn.lex_mung.client_android.mvp.model.entity.order;

public class PaginatedBean {
    /**
     * pages : 1
     * end_row_num : 1
     * has_previous : 0
     * total_rows : 1
     * start_row_num : 1
     * page_num : 1
     * has_next : 0
     * page_size : 10
     */

    private int pages;
    private int end_row_num;
    private int has_previous;
    private int total_rows;
    private int start_row_num;
    private int page_num;
    private int has_next;
    private int page_size;

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getEnd_row_num() {
        return end_row_num;
    }

    public void setEnd_row_num(int end_row_num) {
        this.end_row_num = end_row_num;
    }

    public int getHas_previous() {
        return has_previous;
    }

    public void setHas_previous(int has_previous) {
        this.has_previous = has_previous;
    }

    public int getTotal_rows() {
        return total_rows;
    }

    public void setTotal_rows(int total_rows) {
        this.total_rows = total_rows;
    }

    public int getStart_row_num() {
        return start_row_num;
    }

    public void setStart_row_num(int start_row_num) {
        this.start_row_num = start_row_num;
    }

    public int getPage_num() {
        return page_num;
    }

    public void setPage_num(int page_num) {
        this.page_num = page_num;
    }

    public int getHas_next() {
        return has_next;
    }

    public void setHas_next(int has_next) {
        this.has_next = has_next;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }
}