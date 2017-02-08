package ua.cinemabooking.repository;

import org.springframework.stereotype.Repository;
import ua.cinemabooking.model.BillOrder;
import ua.cinemabooking.model.Seans;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by RATIBOR on 04.02.2017.
 */
@Repository
public interface BillOrderRepository extends CrudRepository<BillOrder,Long> {

    List<BillOrder> findBySeans(Seans seans);
}
