package ua.cinemabooking.service.pagination;

import java.util.List;

/**
 * Created by Andrey on 2/18/2017.
 */
public interface Pageable<T> {
        List<T> getAll(int start, int length);

}
