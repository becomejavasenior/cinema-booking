package ua.cinemabooking.service.pagination;


import com.github.dandelion.datatables.core.ajax.DataSet;
import org.springframework.stereotype.Component;
import ua.cinemabooking.model.Movie;

import java.util.List;

/**
 * @See https://dandelion.github.io/components/datatables/1.1.0/docs/html/#8-4-server-side-processing
 * Created by Andrey on 2/18/2017.
 */
@Component
public class MoviePagination extends Pagination<Movie> {
        public DataSet<Movie> getDataSet(int start, int length) {
                List<Movie> movies = getAll(start, length);
                return new DataSet<Movie>(movies, count(), count());
        }
}
