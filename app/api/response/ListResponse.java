package api.response;

import java.util.List;

/**
 * Created by Hatake on 2015-02-21.
 */
public class ListResponse<T> {

    public List<T> list;
    public long totalElements;
    public long totalPages;

    public ListResponse(List<T> list, long totalElements, long totalPages) {
        this.list = list;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

}
