package ua.cinemabooking.repository;

import ua.cinemabooking.model.BillOrder;
import ua.cinemabooking.model.Seans;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by RATIBOR on 04.02.2017.
 */
public interface BillOrderRepository extends CrudRepository<BillOrder,Long> {

    List<BillOrder> findBySeans(Seans seans);
}
