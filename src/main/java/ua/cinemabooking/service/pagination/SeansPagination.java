package ua.cinemabooking.service.pagination;

import com.github.dandelion.datatables.core.ajax.DataSet;
import org.springframework.stereotype.Component;
import ua.cinemabooking.model.Seans;

import java.util.List;

/**
 * @See https://dandelion.github.io/components/datatables/1.1.0/docs/html/#8-4-server-side-processing
 * Created by Andrey on 2/19/2017.
 */
@Component
public class SeansPagination extends Pagination<Seans> {
        public DataSet<Seans> getDataSet(int start, int length) {
                List<Seans> seanses = getAll(start, length);
                return new DataSet<Seans>(seanses, count(), count());
        }
}
